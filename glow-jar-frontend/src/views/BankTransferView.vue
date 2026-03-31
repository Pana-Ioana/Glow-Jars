<template>
  <v-container class="py-10">
    <div class="transfer-shell" v-if="order">
      <div class="page-header">
        <p class="eyebrow">Glow Jar</p>
        <h1 class="page-title">Instrucțiuni pentru transfer bancar</h1>
        <p class="page-subtitle">
          Comanda ta a fost înregistrată. Pentru procesare, te rugăm să efectuezi
          plata folosind detaliile de mai jos.
        </p>
      </div>

      <v-card class="transfer-card pa-6 pa-md-8" rounded="xl" elevation="0">
        <div class="transfer-top">
          <div>
            <div class="status-badge">
              În așteptarea plății
            </div>
            <h2 class="transfer-title mt-4">
              Comanda {{ order.orderNumber }}
            </h2>
            <p class="transfer-note">
              După confirmarea plății, comanda va intra în procesare.
            </p>
          </div>

          <div class="amount-box">
            <span class="amount-label">Total de plată</span>
            <strong class="amount-value">{{ order.total }} RON</strong>
          </div>
        </div>

        <v-divider class="my-6" />

        <div class="transfer-grid">
          <div class="details-block">
            <h3 class="block-title">Date beneficiar</h3>

            <div class="detail-row">
              <span class="detail-label">Firmă</span>
              <span class="detail-value">Glow Jar Boutique SRL</span>
            </div>

            <div class="detail-row">
              <span class="detail-label">CUI</span>
              <span class="detail-value">RO41892756</span>
            </div>

            <div class="detail-row">
              <span class="detail-label">Nr. Reg. Com.</span>
              <span class="detail-value">J40/15872/2024</span>
            </div>

            <div class="detail-row detail-row-copy">
              <div>
                <span class="detail-label">IBAN</span>
                <span class="detail-value">RO49AAAA1B31007593840000</span>
              </div>
              <v-btn class="copy-btn" variant="text" @click="copyText('RO49AAAA1B31007593840000')">
                Copiază
              </v-btn>
            </div>

            <div class="detail-row">
              <span class="detail-label">Bancă</span>
              <span class="detail-value">Banca Comercială Română</span>
            </div>

            <div class="detail-row detail-row-copy">
              <div>
                <span class="detail-label">Detalii plată</span>
                <span class="detail-value">{{ order.orderNumber }}</span>
              </div>
              <v-btn class="copy-btn" variant="text" @click="copyText(order.orderNumber)">
                Copiază
              </v-btn>
            </div>
          </div>

          <div class="details-block soft-block">
            <h3 class="block-title">Informații importante</h3>

            <ul class="info-list">
              <li>Te rugăm să menționezi numărul comenzii la detalii plată.</li>
              <li>Transferul trebuie efectuat în maximum 48 de ore.</li>
              <li>Procesarea comenzii începe după confirmarea plății.</li>
            </ul>

            <div class="deadline-box mt-5">
              <span class="deadline-label">Scadență plată</span>
              <strong class="deadline-value">{{ paymentDeadline }}</strong>
            </div>
          </div>
        </div>

        <v-divider class="my-6" />

        <div class="summary-section">
          <h3 class="block-title mb-4">Rezumat comandă</h3>

          <div class="summary-items">
            <div
              v-for="(item, index) in order.items"
              :key="item.id + '-' + index"
              class="summary-item"
            >
              <div>
                <div class="summary-item-title">{{ item.name }}</div>
                <div class="summary-item-subtitle">
                  {{ item.quantity }} x {{ item.price }} RON
                </div>
              </div>

              <strong>{{ item.quantity * item.price }} RON</strong>
            </div>
          </div>

          <div class="totals-wrap mt-5">
            <div class="summary-line">
              <span>Subtotal</span>
              <strong>{{ order.subtotal }} RON</strong>
            </div>

            <div class="summary-line">
              <span>Livrare</span>
              <strong>{{ order.shipping === 0 ? 'Gratuită' : `${order.shipping} RON` }}</strong>
            </div>

            <div class="summary-line total-line">
              <span>Total</span>
              <strong>{{ order.total }} RON</strong>
            </div>
          </div>
        </div>

        <div class="actions-row mt-8">
          <v-btn class="ghost-btn" @click="router.push('/collection')">
            Continuă shoppingul
          </v-btn>

          <v-btn class="gold-btn" @click="openConfirmDialog">
            Am înțeles
          </v-btn>
        </div>
      </v-card>
    </div>

    <div v-else>
      <v-card class="transfer-card pa-8 text-center" rounded="xl" elevation="0">
        <h2 class="page-title mb-3">Nu există o comandă recentă</h2>
        <p class="page-subtitle mb-6">
          Nu am găsit informații despre o comandă pentru transfer bancar.
        </p>
        <v-btn class="gold-btn" @click="router.push('/checkout')">
          Înapoi la checkout
        </v-btn>
      </v-card>
    </div>

    <v-dialog v-model="confirmDialog" max-width="430" persistent>
      <v-card class="success-dialog pa-6" rounded="xl" elevation="0">
        <div class="success-icon-wrap mb-4">
          <v-icon size="42" class="success-icon">mdi-bank-check</v-icon>
        </div>

        <h2 class="success-title text-center mb-2">Perfect</h2>
        <p class="success-text text-center mb-6">
          Instrucțiunile de plată au fost afișate. Poți reveni în homepage.
        </p>

        <v-btn class="gold-btn" block @click="goHome">
          OK
        </v-btn>
      </v-card>
    </v-dialog>

    <v-snackbar v-model="copiedSnackbar" timeout="1800">
      Copiat cu succes.
    </v-snackbar>
  </v-container>
</template>

<script setup lang="ts">
import { computed, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

type OrderItem = {
  id: string | number
  name: string
  price: number
  quantity: number
}

type OrderPayload = {
  customer: {
    firstName: string
    lastName: string
    email: string
    phone: string
    address: string
    city: string
    county: string
    postalCode: string
    paymentMethod: string
    orderNotes: string
  }
  items: OrderItem[]
  subtotal: number
  shipping: number
  total: number
  orderNumber: string
  createdAt: string
}

const router = useRouter()
const order = ref<OrderPayload | null>(null)
const copiedSnackbar = ref(false)
const confirmDialog = ref(false)

function loadOrder() {
  try {
    const raw = localStorage.getItem('glowJarLastOrder')
    order.value = raw ? JSON.parse(raw) : null
  } catch {
    order.value = null
  }
}

const paymentDeadline = computed(() => {
  if (!order.value?.createdAt) return '-'

  const date = new Date(order.value.createdAt)
  date.setHours(date.getHours() + 48)

  return date.toLocaleString('ro-RO', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
})

async function copyText(value: string) {
  try {
    await navigator.clipboard.writeText(value)
    copiedSnackbar.value = true
  } catch {
    copiedSnackbar.value = false
  }
}

function openConfirmDialog() {
  confirmDialog.value = true
}

function goHome() {
  confirmDialog.value = false
  router.push('/')
}

onMounted(() => {
  loadOrder()
})
</script>

<style scoped>
.transfer-shell {
  max-width: 1100px;
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

.transfer-card,
.success-dialog {
  background: rgba(255, 255, 255, 0.82);
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 10px 30px rgba(120, 90, 110, 0.06);
}

.transfer-top {
  display: flex;
  justify-content: space-between;
  gap: 18px;
  align-items: flex-start;
  flex-wrap: wrap;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  padding: 8px 14px;
  border-radius: 999px;
  background: rgba(213, 174, 88, 0.12);
  color: #9d7b2f;
  font-weight: 700;
  font-size: 13px;
}

.transfer-title {
  font-size: 28px;
  color: #2f2428;
}

.transfer-note {
  color: #6b5b62;
  margin-top: 8px;
}

.amount-box {
  min-width: 220px;
  padding: 18px 20px;
  border-radius: 22px;
  background: linear-gradient(135deg, rgba(249, 243, 232, 0.95), rgba(255, 255, 255, 0.92));
  border: 1px solid rgba(213, 174, 88, 0.22);
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.amount-label {
  font-size: 13px;
  color: #7a666d;
}

.amount-value {
  font-size: 30px;
  color: #2f2428;
  line-height: 1;
}

.transfer-grid {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  gap: 22px;
}

.details-block {
  padding: 22px;
  border-radius: 22px;
  background: rgba(250, 248, 246, 0.95);
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.soft-block {
  background: linear-gradient(180deg, rgba(252, 247, 241, 0.92), rgba(248, 244, 240, 0.98));
}

.block-title {
  font-size: 20px;
  color: #2f2428;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding: 14px 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.detail-row-copy {
  align-items: center;
}

.detail-label {
  display: block;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  color: #907f86;
  margin-bottom: 6px;
}

.detail-value {
  display: block;
  font-size: 15px;
  font-weight: 700;
  color: #2f2428;
  word-break: break-word;
}

.copy-btn {
  text-transform: none;
  color: #9d7b2f !important;
  font-weight: 700;
}

.info-list {
  margin: 16px 0 0;
  padding-left: 18px;
  color: #5f5258;
  line-height: 1.7;
}

.deadline-box {
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.deadline-label {
  display: block;
  font-size: 12px;
  color: #907f86;
  margin-bottom: 4px;
  text-transform: uppercase;
  letter-spacing: 0.04em;
}

.deadline-value {
  color: #2f2428;
  font-size: 17px;
}

.summary-section {
  margin-top: 4px;
}

.summary-items {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}

.summary-item-title {
  font-weight: 700;
  color: #2f2428;
}

.summary-item-subtitle {
  font-size: 13px;
  color: #6d5f66;
  margin-top: 4px;
}

.totals-wrap {
  max-width: 360px;
  margin-left: auto;
}

.summary-line {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
  color: #43363c;
}

.total-line {
  font-size: 18px;
}

.actions-row {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  flex-wrap: wrap;
}

.gold-btn {
  background: #d5ae58 !important;
  color: white !important;
  border-radius: 999px;
  text-transform: none;
  font-weight: 600;
  box-shadow: none;
}

.ghost-btn {
  border: 1px solid rgba(0, 0, 0, 0.08) !important;
  color: #43363c !important;
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

@media (max-width: 960px) {
  .transfer-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 32px;
  }

  .transfer-title {
    font-size: 24px;
  }

  .summary-item,
  .detail-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .detail-row-copy {
    align-items: flex-start;
  }

  .actions-row {
    justify-content: stretch;
    flex-direction: column;
  }
}
</style>