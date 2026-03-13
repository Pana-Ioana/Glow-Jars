<template>
  <v-container class="py-10">
    <div class="page-shell">
      <div class="page-header">
        <p class="eyebrow">Glow Jar personalizat</p>
        <h1 class="page-title">Creează un jar exact pe gustul tău</h1>
        <p class="page-subtitle">
          Spune-ne vibe-ul dorit, materialele preferate, nivelul de premium și bugetul.
        </p>
      </div>

      <div class="config-grid">
        <v-card class="config-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Preferințele tale</h2>

          <v-text-field
            v-model="form.style"
            label="Cum ai vrea să arate jar-ul?"
            placeholder="ex. coquette, old money, dark romantic, clean girl, celestial..."
            variant="outlined"
            class="mb-4"
          />

          <v-select
            v-model="form.material"
            label="Material principal"
            :items="materials"
            variant="outlined"
            class="mb-4"
          />

          <v-switch
            v-model="form.premiumOnly"
            label="Vreau doar premium jewelry / high-end pieces"
            color="warning"
            inset
            class="mb-2"
          />

          <v-select
            v-model="form.priceRange"
            label="Price range"
            :items="priceRanges"
            variant="outlined"
            class="mb-4"
          />

          <v-select
            v-model="form.size"
            label="Mărime / fit"
            :items="sizes"
            variant="outlined"
            class="mb-4"
          />

          <v-textarea
            v-model="form.notes"
            label="Ce ai vrea să conțină / ce să evităm"
            placeholder="ex. prefer cercei mici, fără perle, mai mult gold decât silver, fără piese foarte chunky..."
            variant="outlined"
            rows="4"
            auto-grow
          />
        </v-card>

        <v-card class="summary-card pa-6" rounded="xl" elevation="0">
          <h2 class="section-title mb-4">Rezumatul jar-ului</h2>

          <div class="summary-row">
            <span>Stil / vibe</span>
            <strong>{{ form.style || '—' }}</strong>
          </div>

          <div class="summary-row">
            <span>Material</span>
            <strong>{{ form.material || '—' }}</strong>
          </div>

          <div class="summary-row">
            <span>Premium only</span>
            <strong>{{ form.premiumOnly ? 'Da' : 'Nu' }}</strong>
          </div>

          <div class="summary-row">
            <span>Price range</span>
            <strong>{{ form.priceRange || '—' }}</strong>
          </div>

          <div class="summary-row">
            <span>Mărime</span>
            <strong>{{ form.size || '—' }}</strong>
          </div>

          <div class="summary-notes">
            <span>Note client</span>
            <p>{{ form.notes || 'Fără instrucțiuni speciale momentan.' }}</p>
          </div>

          <v-divider class="my-4" />

          <div class="summary-row total-row">
            <span>Preț estimativ</span>
            <strong>{{ estimatedPrice }} RON</strong>
          </div>

          <v-btn
            class="gold-btn mt-6"
            block
            size="large"
            @click="addCustomJarToCart"
          >
            Adaugă în coș
          </v-btn>
        </v-card>
      </div>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { computed, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const materials = ['Argint', 'Aur', 'Mixed metals']
const priceRanges = ['50 - 100 RON', '100 - 200 RON', '200 - 350 RON', '350+ RON']
const sizes = ['Universal / mix', 'Mic', 'Mediu', 'Mare', 'Inele ajustabile only']

const form = reactive({
  style: '',
  material: '',
  premiumOnly: false,
  priceRange: '',
  size: '',
  notes: ''
})

const estimatedPrice = computed(() => {
  if (form.premiumOnly) return 349
  if (form.priceRange === '50 - 100 RON') return 89
  if (form.priceRange === '100 - 200 RON') return 149
  if (form.priceRange === '200 - 350 RON') return 249
  if (form.priceRange === '350+ RON') return 349
  return 129
})

function addCustomJarToCart() {
  const existingCart = JSON.parse(localStorage.getItem('glowJarCart') || '[]')

  existingCart.push({
    id: Date.now(),
    type: 'custom-jar',
    name: 'Glow Jar personalizat',
    price: estimatedPrice.value,
    quantity: 1,
    customization: {
      style: form.style,
      material: form.material,
      premiumOnly: form.premiumOnly,
      priceRange: form.priceRange,
      size: form.size,
      notes: form.notes
    }
  })

  localStorage.setItem('glowJarCart', JSON.stringify(existingCart))
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

@media (max-width: 960px) {
  .config-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 32px;
  }
}
</style>