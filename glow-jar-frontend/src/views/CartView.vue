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

          <v-text-field
            v-model="form.firstName"
            label="Prenume"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.lastName"
            label="Nume"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.email"
            label="Email"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.phone"
            label="Telefon"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.address"
            label="Adresă"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.city"
            label="Oraș"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.county"
            label="Județ / sector"
            variant="outlined"
            class="mb-4"
          />

          <v-text-field
            v-model="form.postalCode"
            label="Cod poștal"
            variant="outlined"
            class="mb-4"
          />

          <v-select
            v-model="form.paymentMethod"
            label="Metodă de plată"
            :items="paymentOptions"
            variant="outlined"
            class="mb-4"
          />

          <v-textarea
            v-model="form.orderNotes"
            label="Note pentru comandă"
            variant="outlined"
            rows="3"
            auto-grow
          />

          <v-btn class="gold-btn mt-6" block size="large" @click="placeOrder">
            Plasează comanda
          </v-btn>
        </v-card>

        <v-card class="summary-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Rezumat comandă</h2>

          <div class="checkout-items">
            <div
              v-for="item in cartItems"
              :key="item.id"
              class="checkout-item"
            >
              <div>
                <div class="checkout-item-title">{{ item.name }}</div>
                <div class="checkout-item-subtitle">
                  {{ item.quantity }} x {{ item.price }} RON
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
                </div>
              </div>

              <strong>{{ item.quantity * item.price }} RON</strong>
            </div>
          </div>

          <v-divider class="my-4" />

          <div class="summary-line">
            <span>Subtotal</span>
            <strong>{{ subtotal }} RON</strong>
          </div>

          <div class="summary-line">
            <span>Livrare</span>
            <strong>{{ shippingCost === 0 ? 'Gratuită' : `${shippingCost} RON` }}</strong>
          </div>

          <div class="summary-line total-line">
            <span>Total</span>
            <strong>{{ total }} RON</strong>
          </div>
        </v-card>
      </div>

      <v-snackbar v-model="snackbar" timeout="2500">
        Comanda a fost plasată cu succes.
      </v-snackbar>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { computed, reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const snackbar = ref(false)
const cartItems = ref<any[]>([])

const paymentOptions = ['Card online', 'Ramburs la livrare', 'Transfer bancar']

const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  address: '',
  city: '',
  county: '',
  postalCode: '',
  paymentMethod: '',
  orderNotes: ''
})

function loadCart() {
  try {
    cartItems.value = JSON.parse(localStorage.getItem('glowJarCart') || '[]')
  } catch {
    cartItems.value = []
  }
}

const subtotal = computed(() =>
  cartItems.value.reduce((total, item) => total + item.price * item.quantity, 0)
)

const shippingCost = computed(() => {
  if (subtotal.value >= 250) return 0
  if (subtotal.value === 0) return 0
  return 18
})

const total = computed(() => subtotal.value + shippingCost.value)

function placeOrder() {
  localStorage.removeItem('glowJarCart')
  snackbar.value = true

  setTimeout(() => {
    router.push('/')
  }, 1200)
}

onMounted(() => {
  loadCart()
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
}

.form-card,
.summary-card,
.empty-card {
  background: rgba(255, 255, 255, 0.78);
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 10px 30px rgba(120, 90, 110, 0.06);
}

.section-title {
  font-size: 24px;
  color: #2f2428;
}

.checkout-items {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.checkout-item {
  display: flex;
  justify-content: space-between;
  gap: 14px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
}

.checkout-item-title {
  font-weight: 700;
  color: #2f2428;
}

.checkout-item-subtitle,
.checkout-item-custom {
  font-size: 13px;
  color: #6d5f66;
  margin-top: 4px;
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

@media (max-width: 960px) {
  .checkout-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 32px;
  }
}
</style>