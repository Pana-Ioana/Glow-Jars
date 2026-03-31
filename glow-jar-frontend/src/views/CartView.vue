<template>
  <v-container class="py-10">
    <div class="checkout-shell">
      <div class="page-header">
        <p class="eyebrow">Glow Jar</p>
        <h1 class="page-title">Checkout</h1>
        <p class="page-subtitle">
          Completează detaliile comenzii pentru a finaliza cumpărăturile.
        </p>
      </div>

      <div v-if="cartItems.length === 0">
        <v-card class="empty-card pa-10" rounded="xl" elevation="0">
          <div class="text-center">
            <h2 class="empty-title mb-3">Nu ai produse în coș</h2>
            <p class="empty-text mb-6">
              Adaugă mai întâi ceva în coș ca să poți continua.
            </p>

            <v-btn class="gold-btn" @click="router.push('/collection')">
              Înapoi la shopping
            </v-btn>
          </div>
        </v-card>
      </div>

      <div v-else class="checkout-grid">
        <v-card class="form-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Date de livrare</h2>

          <v-form ref="checkoutFormRef" @submit.prevent="placeOrder">
            <v-text-field
              v-model="form.firstName"
              label="Prenume"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Prenumele'), nameRule('Prenumele')]"
            />

            <v-text-field
              v-model="form.lastName"
              label="Nume"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Numele'), nameRule('Numele')]"
            />

            <v-text-field
              v-model="form.email"
              label="Email"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Emailul'), emailRule]"
            />

            <v-text-field
              v-model="form.phone"
              label="Telefon"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Telefonul'), phoneRule]"
            />

            <v-text-field
              v-model="form.address"
              label="Adresă"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Adresa')]"
            />

            <v-text-field
              v-model="form.city"
              label="Oraș"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Orașul'), nameRule('Orașul')]"
            />

            <v-text-field
              v-model="form.county"
              label="Județ / sector"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Județul / sectorul')]"
            />

            <v-text-field
              v-model="form.postalCode"
              label="Cod poștal"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Codul poștal'), postalCodeRule]"
            />

            <div class="payment-block mb-4">
              <div class="payment-label mb-3">Metodă de plată</div>

              <v-radio-group
                v-model="form.paymentMethod"
                inline
                :rules="[requiredRule('Metoda de plată')]"
              >
                <v-radio
                  v-for="option in paymentOptions"
                  :key="option"
                  :label="option"
                  :value="option"
                  color="#d5ae58"
                />
              </v-radio-group>
            </div>

            <v-textarea
              v-model="form.orderNotes"
              label="Note pentru comandă"
              variant="outlined"
              rows="3"
              auto-grow
              :rules="[notesRule]"
            />

            <div v-if="formError" class="form-error-message mt-4">
              {{ formError }}
            </div>

            <div v-if="isSubmitting" class="form-info-message mt-4">
              Se procesează comanda...
            </div>

            <v-btn
              class="gold-btn mt-6"
              block
              size="large"
              type="submit"
              :loading="isSubmitting"
              :disabled="isSubmitting"
            >
              Plasează comanda
            </v-btn>
          </v-form>
        </v-card>

        <v-card class="summary-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Rezumat comandă</h2>

          <div class="checkout-items">
            <div
              v-for="(item, index) in cartItems"
              :key="`${item.id}-${index}`"
              class="checkout-item"
            >
              <div class="checkout-item-left">
                <div class="checkout-item-title">{{ item.name }}</div>

                <div class="checkout-item-subtitle">
                  {{ formatPrice(item.price) }} / bucată
                </div>

                <div v-if="item.customization" class="checkout-item-custom">
                  <div v-if="item.customization.style">
                    Stil: {{ item.customization.style }}
                  </div>
                  <div v-if="item.customization.material">
                    Material: {{ item.customization.material }}
                  </div>
                  <div v-if="item.customization.priceRange">
                    Buget: {{ item.customization.priceRange }}
                  </div>
                  <div v-if="item.customization.size">
                    Mărime: {{ item.customization.size }}
                  </div>
                  <div v-if="item.customization.premiumOnly">
                    Premium only: Da
                  </div>
                  <div v-if="item.customization.notes">
                    Note: {{ item.customization.notes }}
                  </div>
                </div>

                <div class="item-actions mt-4">
                  <div class="qty-box">
                    <v-btn
                      icon
                      size="x-small"
                      variant="text"
                      class="qty-btn"
                      @click="decreaseQuantity(index)"
                    >
                      <v-icon size="18">mdi-minus</v-icon>
                    </v-btn>

                    <span class="qty-value">{{ item.quantity }}</span>

                    <v-btn
                      icon
                      size="x-small"
                      variant="text"
                      class="qty-btn"
                      @click="increaseQuantity(index)"
                    >
                      <v-icon size="18">mdi-plus</v-icon>
                    </v-btn>
                  </div>

                  <v-btn
                    variant="text"
                    class="delete-btn"
                    @click="removeItem(index)"
                  >
                    Șterge
                  </v-btn>
                </div>
              </div>

              <div class="checkout-item-right">
                <strong>{{ formatPrice(item.quantity * item.price) }}</strong>
              </div>
            </div>
          </div>

          <v-divider class="my-4" />

          <div class="summary-line">
            <span>Subtotal</span>
            <strong>{{ formatPrice(subtotal) }}</strong>
          </div>

          <div class="summary-line">
            <span>Livrare</span>
            <strong>
              {{ shippingCost === 0 ? 'Gratuită' : formatPrice(shippingCost) }}
            </strong>
          </div>

          <div class="summary-line total-line">
            <span>Total</span>
            <strong>{{ formatPrice(total) }}</strong>
          </div>
        </v-card>
      </div>

      <v-dialog v-model="orderSuccessDialog" max-width="460" persistent>
        <v-card class="success-dialog pa-6" rounded="xl" elevation="0">
          <div class="success-icon-wrap mb-4">
            <v-icon size="42" class="success-icon">mdi-check-circle</v-icon>
          </div>

          <h2 class="success-title mb-2 text-center">Comanda a fost plasată</h2>
          <p class="success-text text-center mb-6">
            Îți mulțumim! Comanda ta a fost înregistrată cu succes.
          </p>

          <v-btn class="gold-btn" block size="large" @click="confirmOrderSuccess">
            OK
          </v-btn>
        </v-card>
      </v-dialog>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { computed, reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

type CartItem = {
  id: string | number
  productId?: number
  optionId?: number | null
  name: string
  price: number
  quantity: number
  customization?: {
    style?: string
    material?: string
    priceRange?: string
    size?: string
    premiumOnly?: boolean
    notes?: string
  }
}

type LoggedUser = {
  id?: number
  firstName?: string
  lastName?: string
  email?: string
}

const router = useRouter()
const orderSuccessDialog = ref(false)
const cartItems = ref<CartItem[]>([])
const checkoutFormRef = ref()
const formError = ref('')
const isSubmitting = ref(false)

const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const paymentOptions = [
  'Card online',
  'Ramburs la livrare',
  'Transfer bancar'
]

const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  address: '',
  city: '',
  county: '',
  postalCode: '',
  paymentMethod: 'Card online',
  orderNotes: ''
})

function formatPrice(value: number) {
  if (typeof value !== 'number' || Number.isNaN(value)) return '0.00 RON'
  return `${value.toFixed(2)} RON`
}

function requiredRule(fieldName: string) {
  return (value: string) => {
    if (value && value.toString().trim().length > 0) return true
    return `${fieldName} este obligatoriu.`
  }
}

function nameRule(fieldName: string) {
  return (value: string) => {
    const trimmed = value?.trim() || ''
    const regex = /^[A-Za-zĂÂÎȘŞȚŢăâîșşțţ\s'-]+$/
    return regex.test(trimmed) || `${fieldName} nu poate conține cifre sau simboluri invalide.`
  }
}

const emailRule = (value: string) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return emailRegex.test(value?.trim()) || 'Introdu un email valid.'
}

const phoneRule = (value: string) => {
  const cleaned = (value || '').replace(/\s+/g, '')
  const phoneRegex = /^(\+4|0)[0-9]{9}$/
  return phoneRegex.test(cleaned) || 'Introdu un număr de telefon valid.'
}

const postalCodeRule = (value: string) => {
  const postalRegex = /^[0-9]{6}$/
  return postalRegex.test((value || '').trim()) || 'Codul poștal trebuie să aibă 6 cifre.'
}

const notesRule = (value: string) => {
  if (!value) return true
  return value.trim().length <= 300 || 'Notele pentru comandă pot avea maximum 300 de caractere.'
}

function loadCart() {
  try {
    const storedCart = JSON.parse(localStorage.getItem('glowJarCart') || '[]')
    cartItems.value = Array.isArray(storedCart) ? storedCart : []
  } catch {
    cartItems.value = []
  }
}

function saveCart() {
  localStorage.setItem('glowJarCart', JSON.stringify(cartItems.value))
}

function loadCurrentUser() {
  try {
    const user = JSON.parse(localStorage.getItem('glowJarUser') || 'null') as LoggedUser | null

    if (!user) return

    form.firstName = user.firstName || form.firstName
    form.lastName = user.lastName || form.lastName
    form.email = user.email || form.email
  } catch {
    //
  }
}

function increaseQuantity(index: number) {
  const item = cartItems.value[index]
  if (!item) return
  item.quantity += 1
  saveCart()
}

function decreaseQuantity(index: number) {
  const item = cartItems.value[index]
  if (!item) return

  if (item.quantity > 1) {
    item.quantity -= 1
    saveCart()
  } else {
    removeItem(index)
  }
}

function removeItem(index: number) {
  cartItems.value.splice(index, 1)
  saveCart()
}

const subtotal = computed(() =>
  cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
)

const shippingCost = computed(() => {
  if (subtotal.value >= 250) return 0
  if (subtotal.value === 0) return 0
  return 18
})

const total = computed(() => subtotal.value + shippingCost.value)

async function sendOrderToBackend() {
  const fullName = `${form.firstName.trim()} ${form.lastName.trim()}`.trim()
  const fullAddress = `${form.address.trim()}, ${form.city.trim()}, ${form.county.trim()}, ${form.postalCode.trim()}`

  const payload = {
    email: form.email.trim(),
    fullName,
    phone: form.phone.trim(),
    address: fullAddress,
    total: total.value,
    items: cartItems.value.map((item) => ({
      productName: item.name,
      quantity: item.quantity,
      price: item.price,
      customization: item.customization
        ? JSON.stringify({
            ...item.customization,
            paymentMethod: form.paymentMethod,
            orderNotes: form.orderNotes?.trim() || ''
          })
        : JSON.stringify({
            paymentMethod: form.paymentMethod,
            orderNotes: form.orderNotes?.trim() || ''
          })
    }))
  }

  const response = await fetch(`${API_BASE}/api/orders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload)
  })

  if (!response.ok) {
    let errorMessage = 'A apărut o eroare la finalizarea comenzii.'

    try {
      const text = await response.text()
      if (text) errorMessage = text
    } catch {
      //
    }

    throw new Error(errorMessage)
  }

  return response.json()
}

async function placeOrder() {
  formError.value = ''

  if (!cartItems.value.length) {
    formError.value = 'Coșul este gol.'
    return
  }

  const validationResult = await checkoutFormRef.value?.validate()

  if (!validationResult?.valid) {
    formError.value = 'Te rog verifică și completează corect toate câmpurile.'
    return
  }

  try {
    isSubmitting.value = true

    const backendResponse = await sendOrderToBackend()

    const orderPayload = {
      backendOrder: backendResponse,
      customer: { ...form },
      items: [...cartItems.value],
      subtotal: subtotal.value,
      shipping: shippingCost.value,
      total: total.value,
      orderNumber: backendResponse?.id ? `GJ-${backendResponse.id}` : `GJ-${Date.now()}`,
      createdAt: new Date().toISOString()
    }

    localStorage.setItem('glowJarLastOrder', JSON.stringify(orderPayload))
    localStorage.removeItem('glowJarCart')
    cartItems.value = []

    if (form.paymentMethod === 'Transfer bancar') {
      router.push('/bank-transfer')
      return
    }

    if (form.paymentMethod === 'Card online') {
      router.push('/card-payment')
      return
    }

    orderSuccessDialog.value = true
  } catch (error) {
    formError.value =
      error instanceof Error
        ? error.message
        : 'A apărut o eroare la finalizarea comenzii.'
  } finally {
    isSubmitting.value = false
  }
}

function confirmOrderSuccess() {
  orderSuccessDialog.value = false
  router.push('/')
}

onMounted(() => {
  loadCart()
  loadCurrentUser()
})
</script>

<style scoped>
.checkout-shell {
  max-width: 1200px;
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

.checkout-grid {
  display: grid;
  grid-template-columns: 1.2fr 0.8fr;
  gap: 24px;
  align-items: start;
}

.form-card,
.summary-card,
.empty-card,
.success-dialog {
  background: rgba(255, 255, 255, 0.82);
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 10px 30px rgba(120, 90, 110, 0.06);
}

.section-title {
  font-size: 24px;
  color: #2f2428;
}

.payment-block {
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 18px;
  padding: 16px 18px 6px;
  background: rgba(252, 248, 245, 0.8);
}

.payment-label {
  font-size: 14px;
  font-weight: 700;
  color: #43363c;
}

.checkout-items {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.checkout-item {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.checkout-item-left {
  flex: 1;
}

.checkout-item-right {
  white-space: nowrap;
  color: #2f2428;
  font-size: 15px;
}

.checkout-item-title {
  font-weight: 700;
  color: #2f2428;
  font-size: 16px;
}

.checkout-item-subtitle,
.checkout-item-custom {
  font-size: 13px;
  color: #6d5f66;
  margin-top: 4px;
}

.item-actions {
  display: flex;
  align-items: center;
  gap: 14px;
  flex-wrap: wrap;
}

.qty-box {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  border: 1px solid rgba(0, 0, 0, 0.08);
  border-radius: 999px;
  padding: 4px 8px;
  background: rgba(250, 247, 244, 0.9);
}

.qty-btn {
  color: #5d4a52 !important;
}

.qty-value {
  min-width: 20px;
  text-align: center;
  font-weight: 700;
  color: #2f2428;
}

.delete-btn {
  color: #9a4f63 !important;
  text-transform: none;
  font-weight: 600;
  padding-inline: 0 !important;
  min-width: auto;
}

.summary-line {
  display: flex;
  justify-content: space-between;
  margin-bottom: 14px;
  color: #43363c;
}

.total-line {
  font-size: 18px;
}

.empty-title {
  color: #2f2428;
}

.empty-text {
  color: #64575e;
}

.gold-btn {
  background: #d5ae58 !important;
  color: white !important;
  border-radius: 999px;
  text-transform: none;
  font-weight: 600;
  box-shadow: none;
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

.form-error-message {
  padding: 12px 14px;
  border-radius: 14px;
  background: rgba(176, 58, 75, 0.08);
  color: #a33b50;
  border: 1px solid rgba(176, 58, 75, 0.14);
  font-size: 14px;
}

.form-info-message {
  padding: 12px 14px;
  border-radius: 14px;
  background: rgba(213, 174, 88, 0.12);
  color: #8b6a23;
  border: 1px solid rgba(213, 174, 88, 0.2);
  font-size: 14px;
}

@media (max-width: 960px) {
  .checkout-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 32px;
  }

  .checkout-item {
    flex-direction: column;
  }

  .checkout-item-right {
    align-self: flex-start;
  }
}
</style>