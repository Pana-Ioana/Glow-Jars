<template>
  <v-container class="py-10">
    <div class="orders-shell">
      <div class="page-header">
        <p class="eyebrow">Glow Jar</p>
        <h1 class="page-title">Istoric comenzi</h1>
        <p class="page-subtitle">
          Vezi toate comenzile plasate din contul tău.
        </p>
      </div>

      <v-card
        v-if="loading"
        class="orders-card pa-8 text-center"
        rounded="xl"
        elevation="0"
      >
        Se încarcă comenzile...
      </v-card>

      <v-card
        v-else-if="errorMessage"
        class="orders-card pa-8 text-center"
        rounded="xl"
        elevation="0"
      >
        <h2 class="empty-title mb-3">A apărut o problemă</h2>
        <p class="empty-text mb-6">{{ errorMessage }}</p>

        <v-btn class="gold-btn" @click="loadOrders">
          Încearcă din nou
        </v-btn>
      </v-card>

      <v-card
        v-else-if="orders.length === 0"
        class="orders-card pa-10 text-center"
        rounded="xl"
        elevation="0"
      >
        <h2 class="empty-title mb-3">Nu ai comenzi încă</h2>
        <p class="empty-text mb-6">
          Când plasezi prima comandă, ea va apărea aici.
        </p>

        <v-btn class="gold-btn" @click="router.push('/collection')">
          Vezi colecția
        </v-btn>
      </v-card>

      <div v-else class="orders-list">
        <v-card
          v-for="order in orders"
          :key="order.id"
          class="order-card pa-6"
          rounded="xl"
          elevation="0"
        >
          <div class="order-top">
            <div>
              <div class="order-number">
                Comanda #{{ order.id }}
              </div>
              <div class="order-date">
                {{ formatDate(order.createdAt) }}
              </div>
            </div>

            <v-chip class="status-chip" size="small">
              {{ order.status }}
            </v-chip>
          </div>

          <v-divider class="my-4" />

          <div class="order-meta">
            <div><strong>Nume:</strong> {{ order.fullName }}</div>
            <div><strong>Email:</strong> {{ order.email }}</div>
            <div><strong>Telefon:</strong> {{ order.phone }}</div>
            <div><strong>Adresă:</strong> {{ order.address }}</div>
          </div>

          <div class="items-wrap mt-5">
            <div
              v-for="(item, index) in order.items"
              :key="item.id ?? index"
              class="order-item"
            >
              <div class="item-main">
                <div class="item-name">{{ item.productName }}</div>
                <div class="item-subtitle">
                  {{ item.quantity }} x {{ formatPrice(item.price) }}
                </div>

                <div
                  v-if="item.customization"
                  class="item-customization"
                >
                  {{ parseCustomization(item.customization) }}
                </div>
              </div>
            </div>
          </div>

          <v-divider class="my-4" />

          <div class="order-bottom">
            <span>Total</span>
            <strong>{{ formatPrice(order.total) }}</strong>
          </div>
        </v-card>
      </div>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const loading = ref(true)
const errorMessage = ref('')
const orders = ref<any[]>([])

const API_BASE =
  import.meta.env.VITE_API_BASE_URL || 'https://glow-jars-production.up.railway.app:8080'

function formatDate(value: string) {
  if (!value) return '-'

  return new Date(value).toLocaleString('ro-RO', {
    dateStyle: 'medium',
    timeStyle: 'short'
  })
}

function formatPrice(value: number) {
  if (typeof value !== 'number') return '0 RON'
  return `${value.toFixed(2)} RON`
}

function parseCustomization(customization: string) {
  try {
    const parsed = JSON.parse(customization)
    const parts: string[] = []

    if (parsed.style) parts.push(`Stil: ${parsed.style}`)
    if (parsed.material) parts.push(`Material: ${parsed.material}`)
    if (parsed.priceRange) parts.push(`Buget: ${parsed.priceRange}`)
    if (parsed.size) parts.push(`Mărime: ${parsed.size}`)
    if (parsed.premiumOnly) parts.push('Premium only: Da')
    if (parsed.notes) parts.push(`Note: ${parsed.notes}`)

    return parts.length ? parts.join(' • ') : customization
  } catch {
    return customization
  }
}

async function loadOrders() {
  loading.value = true
  errorMessage.value = ''

  const user = JSON.parse(localStorage.getItem('glowJarUser') || 'null')

  if (!user) {
    router.push('/login')
    return
  }

  if (!user.email) {
    errorMessage.value = 'Utilizatorul logat nu are email salvat.'
    loading.value = false
    return
  }

  try {
    const response = await fetch(
      `${API_BASE}/api/orders?email=${encodeURIComponent(user.email)}`
    )

    if (!response.ok) {
      throw new Error('Nu s-au putut încărca comenzile.')
    }

    orders.value = await response.json()
  } catch (error) {
    console.error(error)
    errorMessage.value = 'Nu am putut încărca istoricul comenzilor.'
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
.orders-shell {
  max-width: 980px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 36px;
}

.eyebrow {
  margin-bottom: 8px;
  font-size: 12px;
  letter-spacing: 0.18em;
  text-transform: uppercase;
  color: #b08b47;
}

.page-title {
  margin: 0 0 10px;
  font-size: clamp(30px, 5vw, 44px);
  line-height: 1.08;
  color: #3e2c33;
}

.page-subtitle {
  max-width: 620px;
  margin: 0 auto;
  color: #6d5f66;
  font-size: 15px;
  line-height: 1.7;
}

.orders-list {
  display: grid;
  gap: 20px;
}

.orders-card,
.order-card {
  border: 1px solid rgba(90, 59, 59, 0.08);
  background: rgba(255, 255, 255, 0.96);
  box-shadow: 0 18px 50px rgba(60, 30, 30, 0.08);
}

.order-top {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}

.order-number {
  font-size: 22px;
  font-weight: 700;
  color: #3e2c33;
}

.order-date {
  margin-top: 6px;
  color: #7a6b72;
  font-size: 14px;
}

.status-chip {
  background: rgba(213, 174, 88, 0.14) !important;
  color: #8e6d29 !important;
  font-weight: 700;
}

.order-meta {
  display: grid;
  gap: 8px;
  color: #5f4f56;
  font-size: 14px;
  line-height: 1.6;
}

.items-wrap {
  display: grid;
  gap: 12px;
}

.order-item {
  padding: 14px 16px;
  border-radius: 18px;
  background: #faf7f5;
  border: 1px solid rgba(90, 59, 59, 0.06);
}

.item-name {
  font-weight: 700;
  color: #3e2c33;
}

.item-subtitle {
  margin-top: 4px;
  color: #6d5f66;
  font-size: 14px;
}

.item-customization {
  margin-top: 8px;
  color: #84747c;
  font-size: 13px;
  line-height: 1.55;
}

.order-bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #3e2c33;
  font-size: 16px;
}

.empty-title {
  color: #3e2c33;
}

.empty-text {
  color: #6d5f66;
}

.gold-btn {
  background: #d5ae58 !important;
  color: white !important;
  border-radius: 999px !important;
  text-transform: none !important;
  letter-spacing: 0 !important;
  font-weight: 700 !important;
  box-shadow: none !important;
}

.gold-btn:hover {
  background: #c79f49 !important;
}

@media (max-width: 700px) {
  .order-top {
    flex-direction: column;
    align-items: flex-start;
  }

  .order-bottom {
    font-size: 15px;
  }
}
</style>