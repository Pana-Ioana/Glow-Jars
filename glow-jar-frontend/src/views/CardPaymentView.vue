<template>
  <v-container class="py-10">
    <div class="payment-shell">
      <div class="page-header">
        <p class="eyebrow">Glow Jar</p>
        <h1 class="page-title">Plată cu cardul</h1>
        <p class="page-subtitle">
          Finalizează plata în siguranță prin Stripe.
        </p>
      </div>

      <v-card class="payment-card pa-6 pa-md-8" rounded="xl" elevation="0">
        <div v-if="order" class="summary-box mb-6">
          <div class="summary-line">
            <span>Număr comandă</span>
            <strong>{{ order.orderNumber }}</strong>
          </div>
          <div class="summary-line">
            <span>Total de plată</span>
            <strong>{{ order.total }} RON</strong>
          </div>
        </div>

        <div v-if="loading" class="text-center py-6">
          <v-progress-circular indeterminate />
          <p class="mt-4">Se pregătește formularul de plată...</p>
        </div>

        <div v-if="errorMessage" class="error-box mb-4">
          {{ errorMessage }}
        </div>

        <div ref="paymentElementRef" class="payment-element-host mb-6"></div>

        <v-btn
          class="gold-btn"
          block
          size="large"
          :loading="submitting"
          :disabled="loading || submitting || !paymentReady"
          @click="submitPayment"
        >
          Plătește acum
        </v-btn>

        <p class="test-card-note mt-4">
          Card test: 4242 4242 4242 4242 · orice dată viitoare · orice CVC
        </p>
      </v-card>
    </div>

    <v-dialog v-model="successDialog" max-width="440" persistent>
      <v-card class="success-dialog pa-6" rounded="xl" elevation="0">
        <div class="success-icon-wrap mb-4">
          <v-icon size="42" class="success-icon">mdi-check-circle</v-icon>
        </div>

        <h2 class="success-title text-center mb-2">Plată reușită</h2>
        <p class="success-text text-center mb-6">
          Comanda ta a fost achitată cu succes.
        </p>

        <v-btn class="gold-btn" block @click="goHome">
          OK
        </v-btn>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup lang="ts">
import { nextTick, onBeforeUnmount, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { loadStripe, type Stripe, type StripeElements, type StripePaymentElement } from '@stripe/stripe-js'

type OrderPayload = {
  total: number
  orderNumber: string
}

const router = useRouter()

const loading = ref(true)
const submitting = ref(false)
const successDialog = ref(false)
const errorMessage = ref('')
const paymentReady = ref(false)
const order = ref<OrderPayload | null>(null)
const paymentElementRef = ref<HTMLElement | null>(null)

const stripePromise = loadStripe('pk_test_51TH6Tq2MZa6l3iFuWpKPpQPplGigK7fE7DXcoPA9oAf1lFl4JyZcZdL06Ywan7WmKs5xWBNitUI2sUk1oybxKk4M00kKZm1Fi8')

let stripeInstance: Stripe | null = null
let elementsInstance: StripeElements | null = null
let paymentElementInstance: StripePaymentElement | null = null
let mountedOnce = false

function loadOrder() {
  try {
    const raw = localStorage.getItem('glowJarLastOrder')
    order.value = raw ? JSON.parse(raw) : null
  } catch {
    order.value = null
  }
}

async function setupStripe() {
  if (mountedOnce) return

  if (!order.value) {
    errorMessage.value = 'Nu există o comandă pregătită pentru plată.'
    loading.value = false
    return
  }

  try {
    const response = await fetch('https://glow-jars-production.up.railway.app/api/payments/create-payment-intent', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        amount: Math.round(order.value.total * 100),
        currency: 'ron'
      })
    })

    if (!response.ok) {
      const errorText = await response.text()
      throw new Error(errorText || 'Nu s-a putut crea PaymentIntent.')
    }

    const data = await response.json()

    stripeInstance = await stripePromise

    if (!stripeInstance) {
      throw new Error('Stripe nu s-a încărcat corect.')
    }

    await nextTick()

    if (!paymentElementRef.value) {
      throw new Error('Containerul Stripe nu există în DOM.')
    }

    elementsInstance = stripeInstance.elements({
      clientSecret: data.clientSecret,
      appearance: {
        theme: 'stripe'
      }
    })

    paymentElementInstance = elementsInstance.create('payment')

    paymentElementInstance.on('ready', () => {
      paymentReady.value = true
      loading.value = false
    })

    paymentElementInstance.mount(paymentElementRef.value)
    mountedOnce = true
  } catch (error: any) {
    console.error('STRIPE SETUP ERROR:', error)
    errorMessage.value = error.message || 'A apărut o eroare la inițializarea plății.'
    loading.value = false
  }
}

async function submitPayment() {
  if (!stripeInstance || !elementsInstance || !paymentElementInstance || !paymentReady.value) {
    errorMessage.value = 'Formularul de plată nu este pregătit încă.'
    return
  }

  submitting.value = true
  errorMessage.value = ''

  const { error } = await stripeInstance.confirmPayment({
    elements: elementsInstance,
    confirmParams: {
      return_url: `${window.location.origin}/`
    },
    redirect: 'if_required'
  })

  submitting.value = false

  if (error) {
    errorMessage.value = error.message || 'Plata a eșuat.'
    return
  }

  localStorage.removeItem('glowJarCart')
  successDialog.value = true
}

function goHome() {
  successDialog.value = false
  router.push('/')
}

onMounted(async () => {
  loadOrder()
  await nextTick()
  await setupStripe()
})

onBeforeUnmount(() => {
  if (paymentElementInstance) {
    paymentElementInstance.destroy()
    paymentElementInstance = null
  }
  elementsInstance = null
  stripeInstance = null
  mountedOnce = false
})
</script>

<style scoped>
.payment-shell {
  max-width: 760px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 32px;
}

.eyebrow {
  font-size: 14px;
  color: #866d5c;
  margin-bottom: 8px;
}

.page-title {
  font-size: 42px;
  line-height: 1.1;
  margin-bottom: 10px;
  color: #2f2428;
}

.page-subtitle {
  font-size: 18px;
  color: #64575e;
}

.payment-card,
.success-dialog {
  background: rgba(255, 255, 255, 0.82);
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 10px 30px rgba(120, 90, 110, 0.06);
}

.summary-box {
  padding: 18px;
  border-radius: 18px;
  background: rgba(250, 247, 244, 0.9);
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.summary-line {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  color: #43363c;
}

.summary-line + .summary-line {
  margin-top: 10px;
}

.payment-element-host {
  min-height: 92px;
}

.error-box {
  padding: 14px 16px;
  border-radius: 14px;
  background: rgba(178, 58, 72, 0.08);
  color: #b23a48;
  border: 1px solid rgba(178, 58, 72, 0.14);
}

.gold-btn {
  background: #d5ae58 !important;
  color: white !important;
  border-radius: 999px;
  text-transform: none;
  font-weight: 600;
  box-shadow: none;
}

.test-card-note {
  font-size: 13px;
  color: #75666d;
  text-align: center;
}

.success-icon-wrap {
  display: flex;
  justify-content: center;
}

.success-icon {
  color: #d5ae58;
}

.success-title {
  color: #2f2428;
  font-size: 28px;
}

.success-text {
  color: #64575e;
  font-size: 15px;
  line-height: 1.6;
}

@media (max-width: 960px) {
  .page-title {
    font-size: 32px;
  }
}
</style>