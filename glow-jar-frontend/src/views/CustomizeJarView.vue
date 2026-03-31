<template>
  <v-container class="py-10">
    <div class="page-shell">
      <div class="page-header">
        <p class="eyebrow">Glow Jar personalizat</p>
        <h1 class="page-title">Creează un jar exact pe gustul tău</h1>
        <p class="page-subtitle">
          Alege vibe-ul, materialele și pachetul dorit.
        </p>
      </div>

      <div class="config-grid">
        <v-card class="config-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Preferințele tale</h2>

          <v-form ref="formRef" @submit.prevent="addCustomJarToCart">
            <v-text-field
              v-model="form.style"
              label="Stil / vibe"
              placeholder="coquette, clean girl, dark romance..."
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Stilul')]"
            />

            <v-select
              v-model="form.material"
              label="Material"
              :items="materials"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Materialul')]"
            />

            <v-select
              v-model="form.package"
              label="Pachet"
              :items="packages"
              item-title="label"
              item-value="value"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Pachetul')]"
            />

            <v-switch
              v-model="form.premiumOnly"
              label="Premium jewelry only (+500 RON)"
              color="warning"
              inset
              class="mb-4"
            />

            <v-select
              v-model="form.size"
              label="Mărime inele"
              :items="sizes"
              variant="outlined"
              class="mb-4"
              :rules="[requiredRule('Mărimea')]"
            />

            <v-textarea
              v-model="form.notes"
              label="Note"
              variant="outlined"
              rows="3"
              auto-grow
              :rules="[maxLengthRule(300)]"
            />

            <div v-if="error" class="form-error mt-4">
              {{ error }}
            </div>

            <v-btn class="gold-btn mt-6" block type="submit">
              Adaugă în coș
            </v-btn>
          </v-form>
        </v-card>

        <v-card class="summary-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Rezumat</h2>

          <div class="summary-row">
            <span>Stil</span>
            <strong>{{ form.style || '—' }}</strong>
          </div>

          <div class="summary-row">
            <span>Material</span>
            <strong>{{ form.material || '—' }}</strong>
          </div>

          <div class="summary-row">
            <span>Pachet</span>
            <strong>{{ selectedPackageLabel }}</strong>
          </div>

          <div class="summary-row">
            <span>Premium</span>
            <strong>{{ form.premiumOnly ? '+500 RON' : 'Nu' }}</strong>
          </div>

          <div class="summary-row">
            <span>Mărime inele</span>
            <strong>{{ form.size || '—' }}</strong>
          </div>

          <div class="summary-notes mt-4">
            <span>Note</span>
            <p>{{ form.notes || 'Fără instrucțiuni speciale momentan.' }}</p>
          </div>

          <v-divider class="my-4" />

          <div class="summary-row">
            <span>Preț pachet</span>
            <strong>{{ basePrice }} RON</strong>
          </div>

          <div class="summary-row" v-if="form.premiumOnly">
            <span>Premium addon</span>
            <strong>+500 RON</strong>
          </div>

          <div class="summary-row total-row">
            <span>Total</span>
            <strong>{{ finalPrice }} RON</strong>
          </div>
        </v-card>
      </div>

      <v-snackbar v-model="snackbar" timeout="2000">
        Adăugat în coș 
      </v-snackbar>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from 'vue'

const formRef = ref()
const error = ref('')
const snackbar = ref(false)

const PREMIUM_ADDON_PRICE = 500

const materials = ['Argint', 'Aur', 'Mixed metals']
const sizes = ['Mic (47-50)', 'Mediu (50-54)', 'Mare (54+)', 'Mix']

const packages = [
  { label: '6-8 piese • 99 RON', value: 99, pieces: '6-8' },
  { label: '10-12 piese • 149 RON', value: 149, pieces: '10-12' },
  { label: '14-16 piese • 199 RON', value: 199, pieces: '14-16' },
  { label: '18-20 piese • 249 RON', value: 249, pieces: '18-20' }
]

const form = reactive({
  style: '',
  material: '',
  package: null as number | null,
  premiumOnly: false,
  size: '',
  notes: ''
})

function requiredRule(field: string) {
  return (v: unknown) => (!!v ? true : `${field} este obligatoriu`)
}

function maxLengthRule(max: number) {
  return (v: string) =>
    !v || v.length <= max || `Maxim ${max} caractere`
}

const selectedPackage = computed(() =>
  packages.find((p) => p.value === form.package)
)

const selectedPackageLabel = computed(() =>
  selectedPackage.value?.label || '—'
)

const basePrice = computed(() => form.package || 0)

const premiumAddon = computed(() => (form.premiumOnly ? PREMIUM_ADDON_PRICE : 0))

const finalPrice = computed(() => basePrice.value + premiumAddon.value)

async function addCustomJarToCart() {
  error.value = ''

  const valid = await formRef.value?.validate()
  if (!valid?.valid) {
    error.value = 'Completează toate câmpurile obligatorii.'
    return
  }

  const cart = JSON.parse(localStorage.getItem('glowJarCart') || '[]')

  cart.push({
    id: Date.now(),
    type: 'custom-jar',
    name: 'Glow Jar personalizat',
    price: finalPrice.value,
    quantity: 1,
    customization: {
      style: form.style,
      material: form.material,
      selectedPackage: selectedPackage.value?.label || '',
      packagePrice: basePrice.value,
      premiumOnly: form.premiumOnly,
      premiumAddon: premiumAddon.value,
      pieces: selectedPackage.value?.pieces || '',
      size: form.size,
      notes: form.notes
    }
  })

  localStorage.setItem('glowJarCart', JSON.stringify(cart))
  snackbar.value = true
}
</script>

<style scoped>
.page-shell {
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

.config-grid {
  display: grid;
  grid-template-columns: 1.3fr 0.9fr;
  gap: 24px;
}

.config-card,
.summary-card {
  background: rgba(255, 255, 255, 0.78);
  border: 1px solid rgba(0, 0, 0, 0.06);
  box-shadow: 0 10px 30px rgba(120, 90, 110, 0.06);
}

.section-title {
  font-size: 24px;
  color: #2f2428;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 14px;
  color: #3b2f34;
}

.summary-notes span {
  display: block;
  font-weight: 600;
  margin-bottom: 6px;
  color: #3b2f34;
}

.summary-notes p {
  color: #5f5359;
  line-height: 1.5;
}

.total-row {
  font-size: 18px;
}

.gold-btn {
  background: #d5ae58 !important;
  color: white !important;
  border-radius: 999px;
  text-transform: none;
  font-weight: 600;
  box-shadow: none;
}

.form-error {
  padding: 12px 14px;
  border-radius: 14px;
  background: rgba(176, 58, 75, 0.08);
  color: #a33b50;
  border: 1px solid rgba(176, 58, 75, 0.14);
  font-size: 14px;
}

@media (max-width: 960px) {
  .config-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 32px;
  }
}
</style>