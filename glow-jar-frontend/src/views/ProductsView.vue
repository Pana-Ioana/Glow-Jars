<template>
  <v-main>
    <v-container fluid class="products-shell py-10 px-0">
      <div class="products-header mb-6">
        <div>
          <div class="brand-serif text-h4 section-heading">
            {{ pageTitle }}
          </div>
          <div class="text-body-2 section-subtitle mt-1">
            {{ pageSubtitle }}
          </div>
        </div>
      </div>

      <div class="top-controls mb-6">
        <div class="results-count">
          {{ filteredProducts.length }} produse
        </div>

        <div class="top-controls-right">
          <v-select
            v-model="itemsPerPage"
            label="Pe pagină"
            variant="outlined"
            density="comfortable"
            :items="[20, 30, 40]"
            hide-details
            class="page-size-select"
          />

          <v-select
            v-model="filters.sort"
            label="Sortează"
            variant="outlined"
            density="comfortable"
            :items="sortItems"
            item-title="title"
            item-value="value"
            hide-details
            class="sort-select"
            @update:model-value="applyFilters"
          />
        </div>
      </div>

      <div class="layout">
        <div class="filters">
          <v-sheet class="filters-card pa-4" elevation="0">
            <v-select
              v-model="filters.category"
              label="Categorie"
              :items="categoryItems"
              item-title="title"
              item-value="value"
              clearable
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-select
              v-model="filters.level"
              label="Nivel"
              :items="levelItems"
              item-title="title"
              item-value="value"
              clearable
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-select
              v-model="filters.size"
              label="Mărime"
              :items="sizeItems"
              item-title="title"
              item-value="value"
              clearable
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-select
              v-model="filters.materialTone"
              label="Metal"
              :items="materialItems"
              item-title="title"
              item-value="value"
              clearable
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-select
              v-model="filters.vibe"
              label="Vibe"
              :items="vibeItems"
              item-title="title"
              item-value="value"
              clearable
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-text-field
              v-model.number="filters.minPrice"
              label="Preț minim"
              type="number"
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-text-field
              v-model.number="filters.maxPrice"
              label="Preț maxim"
              type="number"
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-text-field
              v-model.number="filters.minPieces"
              label="Piese minime"
              type="number"
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-text-field
              v-model.number="filters.maxPieces"
              label="Piese maxime"
              type="number"
              variant="outlined"
              density="comfortable"
              hide-details="auto"
              class="mb-3"
            />

            <v-checkbox
              v-model="filters.bestseller"
              label="Bestseller"
              hide-details
              density="comfortable"
            />

            <v-checkbox
              v-model="filters.newIn"
              label="New in"
              hide-details
              density="comfortable"
            />

            <v-checkbox
              v-model="filters.availableOnly"
              label="Disponibile"
              hide-details
              density="comfortable"
            />

            <div class="filters-actions mt-4">
              <v-btn class="gold-btn flex-grow-1" @click="applyFilters">
                Aplică
              </v-btn>

              <v-btn variant="outlined" class="btn-soft" @click="resetFilters">
                Reset
              </v-btn>
            </div>
          </v-sheet>
        </div>

        <div class="products products-pane">
          <div v-if="loading" class="state-box">
            Se încarcă...
          </div>

          <div v-else-if="error" class="state-box error-text">
            {{ error }}
          </div>

          <div v-else-if="!paginatedProducts.length" class="state-box">
            Nu există produse pentru selecția asta.
          </div>

          <div v-else class="products-grid">
            <div
              v-for="product in paginatedProducts"
              :key="product.id"
              class="product-card"
              @click="goToProduct(product.slug)"
            >
              <div class="product-image-wrap">
                <img
                  v-if="product.imageUrl && !brokenImages[product.id]"
                  :src="product.imageUrl"
                  :alt="product.name"
                  class="product-image"
                  @error="markBrokenImage(product.id)"
                />

                <div v-else class="product-placeholder">
                  Glow Jar
                </div>

                <div class="product-badges">
                  <span v-if="product.bestseller" class="mini-badge">
                    Bestseller
                  </span>

                  <span v-if="product.newIn" class="mini-badge soft-badge">
                    New in
                  </span>
                </div>
              </div>

              <div class="product-content">
                <div class="product-meta">
                  {{ prettyCategory(product.categorySlug) }} · {{ prettyLevel(product.level) }}
                </div>

                <div class="product-title brand-serif">
                  {{ product.name }}
                </div>

                <div class="product-description">
                  {{ product.shortDescription }}
                </div>

                <div class="product-footer">
                  <div class="product-price">
                    de la {{ product.minPrice ?? "-" }} lei
                  </div>

                  <div class="product-pieces">
                    {{ product.minPieces ?? "-" }}+ piese
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-if="totalPages > 1" class="pagination-wrap">
            <v-pagination
              v-model="currentPage"
              :length="totalPages"
              rounded="circle"
              total-visible="7"
            />
          </div>
        </div>
      </div>
    </v-container>
  </v-main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue"
import { useRoute, useRouter } from "vue-router"

type ProductOption = {
  size?: string
  available?: boolean
}

type Product = {
  id: number
  slug: string
  name: string
  categorySlug: string
  level: string
  shortDescription?: string
  imageUrl?: string
  bestseller?: boolean
  newIn?: boolean
  materialTone?: string
  vibe?: string
  rating?: number
  minPrice?: number
  minPieces?: number
  options?: ProductOption[]
}

type Filters = {
  category: string
  level: string
  size: string
  minPrice: number | null
  maxPrice: number | null
  minPieces: number | null
  maxPieces: number | null
  materialTone: string
  vibe: string
  bestseller: boolean
  newIn: boolean
  availableOnly: boolean
  sort: string
}

const route = useRoute()
const router = useRouter()

const products = ref<Product[]>([])
const loading = ref(false)
const error = ref("")
const brokenImages = ref<Record<number, boolean>>({})
const currentPage = ref(1)
const itemsPerPage = ref(20)

const categoryItems = [
  { title: "Soft Girl", value: "soft-girl" },
  { title: "Golden Muse", value: "golden-muse" },
  { title: "Minimal Glow", value: "minimal-glow" },
  { title: "Dark Romance", value: "dark-romance" },
  { title: "Rose Quartz", value: "rose-quartz" },
  { title: "Celestial Glow", value: "celestial-glow" },
  { title: "Vintage Muse", value: "vintage-muse" },
  { title: "Cottage Bloom", value: "cottage-bloom" },
  { title: "Edgy Chic", value: "edgy-chic" },
  { title: "Boho Dream", value: "boho-dream" },
  { title: "Glam Goddess", value: "glam-goddess" },
]

const levelItems = [
  { title: "Essential", value: "essential" },
  { title: "Signature", value: "signature" },
  { title: "Luxe", value: "luxe" },
]

const sizeItems = [
  { title: "Small", value: "small" },
  { title: "Medium", value: "medium" },
  { title: "Large", value: "large" },
]

const materialItems = [
  { title: "Gold", value: "gold" },
  { title: "Silver", value: "silver" },
  { title: "Mixed", value: "mixed" },
]

const vibeItems = [
  { title: "Soft", value: "soft" },
  { title: "Romantic", value: "romantic" },
  { title: "Minimal", value: "minimal" },
  { title: "Dramatic", value: "dramatic" },
  { title: "Glam", value: "glam" },
  { title: "Boho", value: "boho" },
  { title: "Vintage", value: "vintage" },
]

const sortItems = [
  { title: "Cele mai noi", value: "newest" },
  { title: "Preț crescător", value: "priceAsc" },
  { title: "Preț descrescător", value: "priceDesc" },
  { title: "Rating", value: "ratingDesc" },
  { title: "Nume A-Z", value: "nameAsc" },
]

const categoryLabels: Record<string, string> = {
  "soft-girl": "Soft Girl",
  "golden-muse": "Golden Muse",
  "minimal-glow": "Minimal Glow",
  "dark-romance": "Dark Romance",
  "rose-quartz": "Rose Quartz",
  "celestial-glow": "Celestial Glow",
  "vintage-muse": "Vintage Muse",
  "cottage-bloom": "Cottage Bloom",
  "edgy-chic": "Edgy Chic",
  "boho-dream": "Boho Dream",
  "glam-goddess": "Glam Goddess",
}

const levelLabels: Record<string, string> = {
  essential: "Essential",
  signature: "Signature",
  luxe: "Luxe",
}

const defaultFilters = (): Filters => ({
  category: "",
  level: "",
  size: "",
  minPrice: null,
  maxPrice: null,
  minPieces: null,
  maxPieces: null,
  materialTone: "",
  vibe: "",
  bestseller: false,
  newIn: false,
  availableOnly: false,
  sort: "newest",
})

const filters = ref<Filters>(defaultFilters())

const pageTitle = computed(() => {
  if (filters.value.category) {
    return categoryLabels[filters.value.category] || "Produse"
  }

  if (typeof route.query.search === "string" && route.query.search.trim()) {
    return `Rezultate pentru „${route.query.search}”`
  }

  return "Toate jar-urile"
})

const pageSubtitle = computed(() => {
  if (filters.value.category) {
    return "Descoperă produsele din categoria selectată și joacă-te cu filtrele."
  }

  if (typeof route.query.search === "string" && route.query.search.trim()) {
    return "Am găsit produsele care se potrivesc căutării tale."
  }

  return "Descoperă toate produsele Glow Jar disponibile acum."
})

const filteredProducts = computed<Product[]>(() => {
  let result = [...products.value]

  if (filters.value.category) {
    result = result.filter((p) => p.categorySlug === filters.value.category)
  }

  if (filters.value.level) {
    result = result.filter((p) => p.level === filters.value.level)
  }

  if (filters.value.materialTone) {
    result = result.filter((p) => p.materialTone === filters.value.materialTone)
  }

  if (filters.value.vibe) {
    result = result.filter((p) => p.vibe === filters.value.vibe)
  }

  if (filters.value.bestseller) {
    result = result.filter((p) => !!p.bestseller)
  }

  if (filters.value.newIn) {
    result = result.filter((p) => !!p.newIn)
  }

  if (filters.value.size) {
    result = result.filter((p) =>
      p.options?.some((o) => o.size === filters.value.size)
    )
  }

  if (filters.value.availableOnly) {
    result = result.filter((p) =>
      p.options?.some((o) => !!o.available)
    )
  }

  const minPrice = filters.value.minPrice
  if (minPrice !== null) {
    result = result.filter((p) => (p.minPrice ?? 0) >= minPrice)
  }

  const maxPrice = filters.value.maxPrice
  if (maxPrice !== null) {
    result = result.filter((p) => (p.minPrice ?? 0) <= maxPrice)
  }

  const minPieces = filters.value.minPieces
  if (minPieces !== null) {
    result = result.filter((p) => (p.minPieces ?? 0) >= minPieces)
  }

  const maxPieces = filters.value.maxPieces
  if (maxPieces !== null) {
    result = result.filter((p) => (p.minPieces ?? 0) <= maxPieces)
  }

  switch (filters.value.sort) {
    case "priceAsc":
      result.sort((a, b) => (a.minPrice ?? 0) - (b.minPrice ?? 0))
      break
    case "priceDesc":
      result.sort((a, b) => (b.minPrice ?? 0) - (a.minPrice ?? 0))
      break
    case "ratingDesc":
      result.sort((a, b) => (b.rating ?? 0) - (a.rating ?? 0))
      break
    case "nameAsc":
      result.sort((a, b) => (a.name ?? "").localeCompare(b.name ?? ""))
      break
    default:
      result.sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
  }

  return result
})

const totalPages = computed(() => {
  return Math.max(1, Math.ceil(filteredProducts.value.length / itemsPerPage.value))
})

const paginatedProducts = computed<Product[]>(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value
  const end = start + itemsPerPage.value
  return filteredProducts.value.slice(start, end)
})

function syncFiltersFromRoute() {
  filters.value.category =
    typeof route.query.category === "string" ? route.query.category : ""
}

async function fetchProducts() {
  loading.value = true
  error.value = ""

  try {
    const params = new URLSearchParams()

    if (typeof route.query.search === "string" && route.query.search.trim()) {
      params.append("search", route.query.search)
    }

    const url = `http://localhost:8080/api/products${params.toString() ? `?${params.toString()}` : ""}`
    const res = await fetch(url)

    if (!res.ok) {
      throw new Error(`Nu s-au putut încărca produsele. (${res.status})`)
    }

    const data = await res.json()

    if (!Array.isArray(data)) {
      throw new Error("Răspuns invalid de la server.")
    }

    products.value = data as Product[]
  } catch (err: unknown) {
    if (err instanceof Error) {
      error.value = err.message
    } else {
      error.value = "A apărut o eroare la încărcarea produselor."
    }
    products.value = []
  } finally {
    loading.value = false
  }
}

function applyFilters() {
  currentPage.value = 1
}

function resetFilters() {
  filters.value = defaultFilters()
  currentPage.value = 1
}

function goToProduct(slug: string) {
  router.push(`/products/${slug}`)
}

function markBrokenImage(productId: number) {
  brokenImages.value[productId] = true
}

function prettyCategory(slug: string) {
  return categoryLabels[slug] || slug
}

function prettyLevel(level: string) {
  return levelLabels[level] || level
}

watch(itemsPerPage, () => {
  currentPage.value = 1
})

watch(filteredProducts, () => {
  if (currentPage.value > totalPages.value) {
    currentPage.value = 1
  }
})

onMounted(() => {
  syncFiltersFromRoute()
  fetchProducts()
})

watch(
  () => route.query,
  () => {
    syncFiltersFromRoute()
    fetchProducts()
  },
  { deep: true }
)
</script>

<style scoped>
.products-shell {
  max-width: 1400px;
  margin: 0 auto;
  padding-left: 24px;
  padding-right: 24px;
}

.products-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 16px;
}

.top-controls {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
  flex-wrap: wrap;
}

.top-controls-right {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.page-size-select {
  width: 130px;
}

.sort-select {
  width: 230px;
}

.section-heading {
  color: rgba(90, 59, 59, 0.95);
}

.section-subtitle {
  opacity: 0.76;
}

.layout {
  display: grid;
  grid-template-columns: 240px 1fr;
  gap: 24px;
  align-items: start;
}

.filters {
  width: 100%;
}

.products {
  width: 100%;
}

.filters-card {
  width: 100%;
  border-radius: 24px;
  border: 1px solid rgba(90, 59, 59, 0.08);
  background: rgba(255, 255, 255, 0.97);
  position: sticky;
  top: 96px;
}

.filters-actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.results-count {
  color: rgba(90, 59, 59, 0.72);
  font-size: 14px;
}

.state-box {
  padding: 56px 22px;
  text-align: center;
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.88);
  border: 1px solid rgba(90, 59, 59, 0.08);
  color: rgba(90, 59, 59, 0.78);
}

.error-text {
  color: #9a4747;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.product-card {
  display: flex;
  flex-direction: column;
  background: rgba(255, 255, 255, 0.98);
  border: 1px solid rgba(90, 59, 59, 0.08);
  border-radius: 24px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.22s ease, box-shadow 0.22s ease;
}

.product-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 16px 38px rgba(60, 30, 30, 0.1);
}

.product-image-wrap {
  position: relative;
  width: 100%;
  height: 300px;
  overflow: hidden;
  background: linear-gradient(
    180deg,
    rgba(248, 232, 238, 0.7),
    rgba(234, 220, 248, 0.45)
  );
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.product-placeholder {
  width: 100%;
  height: 100%;
  display: grid;
  place-items: center;
  color: rgba(90, 59, 59, 0.58);
  font-weight: 700;
  letter-spacing: 0.4px;
}

.product-badges {
  position: absolute;
  top: 12px;
  left: 12px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.mini-badge {
  padding: 5px 9px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.94);
  border: 1px solid rgba(90, 59, 59, 0.08);
  font-size: 11px;
  font-weight: 700;
  color: #5a3b3b;
}

.soft-badge {
  background: rgba(248, 232, 238, 0.92);
}

.product-content {
  padding: 18px;
  background: white;
  display: flex;
  flex-direction: column;
  gap: 8px;
  flex-grow: 1;
}

.product-meta {
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.4px;
  color: rgba(90, 59, 59, 0.55);
}

.product-title {
  font-size: 24px;
  color: #5a3b3b;
  line-height: 1.2;
  margin: 0;
}

.product-description {
  font-size: 14px;
  color: rgba(90, 59, 59, 0.82);
  line-height: 1.6;
  min-height: 68px;
}

.product-footer {
  margin-top: auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}

.product-price {
  font-weight: 700;
  color: #5a3b3b;
  font-size: 14px;
}

.product-pieces {
  font-size: 12px;
  color: rgba(90, 59, 59, 0.72);
}

.pagination-wrap {
  display: flex;
  justify-content: center;
  margin-top: 32px;
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

.btn-soft {
  text-transform: none !important;
  letter-spacing: 0 !important;
  font-weight: 700 !important;
}

@media (max-width: 1400px) {
  .products-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 1100px) {
  .layout {
    grid-template-columns: 1fr;
  }

  .filters-card {
    position: static;
  }

  .products-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 960px) {
  .top-controls-right {
    width: 100%;
  }

  .page-size-select,
  .sort-select {
    width: 100%;
  }
}

@media (max-width: 700px) {
  .products-shell {
    padding-left: 16px;
    padding-right: 16px;
  }

  .products-grid {
    grid-template-columns: 1fr;
  }

  .product-image-wrap {
    height: 260px;
  }
}
</style>