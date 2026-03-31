<template>
  <v-main>
    <v-container class="product-shell py-12">
      <div v-if="loading" class="state-box">
        Se încarcă produsul...
      </div>

      <div v-else-if="error" class="state-box error-text">
        {{ error }}
      </div>

      <div v-else-if="product" class="product-layout">
        <div class="product-media-card">
          <div class="product-image-wrap">
            <img
              v-if="product.imageUrl && !imageBroken"
              :src="product.imageUrl"
              :alt="product.name"
              class="product-image"
              @error="imageBroken = true"
            />
            <div v-else class="product-placeholder">
              Glow Jar
            </div>

            <div class="product-badges">
              <span v-if="product.bestseller" class="mini-badge">Bestseller</span>
              <span v-if="product.newIn" class="mini-badge soft-badge">New in</span>
            </div>
          </div>
        </div>

        <div class="product-info-card">
          <div class="product-meta">
            {{ prettyCategory(product.categorySlug) }} · {{ prettyLevel(product.level) }}
          </div>

          <h1 class="brand-serif product-title">
            {{ product.name }}
          </h1>

          <p class="product-short">
            {{ product.shortDescription }}
          </p>

          <p class="product-description">
            {{ product.description }}
          </p>

          <div class="details-list" v-if="details.length">
            <div v-for="detail in details" :key="detail" class="detail-item">
              {{ detail }}
            </div>
          </div>

          <div class="made-to-order-box">
            Acest jar este realizat la comandă.
          </div>

          <div class="selector-block">
            <div class="selector-label">Alege mărimea</div>

            <div class="size-options">
              <button
                v-for="option in product.options"
                :key="option.id"
                type="button"
                class="size-chip"
                :class="{ active: selectedOption?.id === option.id, disabled: !option.available }"
                :disabled="!option.available"
                @click="selectedOptionId = option.id"
              >
                {{ prettySize(option.size) }}
              </button>
            </div>
          </div>

          <div v-if="selectedOption" class="price-box">
            <div class="price-main">
              {{ selectedOption.price }} lei
            </div>

            <div class="price-sub"> Minim
              {{ selectedOption.piecesCount }} piese incluse
            </div>
          </div>

          <div class="qty-row">
            <div class="selector-label">Cantitate</div>

            <div class="qty-box">
              <button type="button" class="qty-btn" @click="decreaseQty">−</button>
              <span class="qty-value">{{ quantity }}</span>
              <button type="button" class="qty-btn" @click="increaseQty">+</button>
            </div>
          </div>

          <div class="actions-row">
            <v-btn
              class="gold-btn add-btn"
              size="large"
              :disabled="!selectedOption || !selectedOption.available"
              @click="addToCart"
            >
              Adaugă în coș
            </v-btn>
          </div>

          <div v-if="addedMessage" class="added-message">
            {{ addedMessage }}
          </div>
        </div>
      </div>
    </v-container>
  </v-main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue"
import { useRoute } from "vue-router"

type ProductOption = {
  id: number
  size: string
  piecesCount: number
  price: number
  available: boolean
}

type Product = {
  id: number
  name: string
  slug: string
  categorySlug: string
  level: string
  shortDescription: string
  description: string
  detailOne?: string
  detailTwo?: string
  detailThree?: string
  imageUrl?: string
  bestseller?: boolean
  newIn?: boolean
  featured?: boolean
  rating?: number
  reviewsCount?: number
  materialTone?: string
  vibe?: string
  options: ProductOption[]
}

type CartItem = {
  id: string
  productId: number
  slug: string
  name: string
  imageUrl?: string
  quantity: number
  price: number
  selectedSize: string
  piecesCount: number
}

const route = useRoute()

const loading = ref(false)
const error = ref("")
const product = ref<Product | null>(null)
const imageBroken = ref(false)
const selectedOptionId = ref<number | null>(null)
const quantity = ref(1)
const addedMessage = ref("")

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
  atelier: "Atelier",
  collector: "Collector",
  edit: "Edit",
  deluxe: "Deluxe",
  premium: "Premium",
  dream: "Dream",
}

const selectedOption = computed(() => {
  if (!product.value || selectedOptionId.value === null) return null
  return product.value.options.find((option) => option.id === selectedOptionId.value) || null
})

const details = computed(() => {
  if (!product.value) return []
  return [product.value.detailOne, product.value.detailTwo, product.value.detailThree].filter(
    Boolean
  ) as string[]
})

function prettyCategory(slug: string) {
  return categoryLabels[slug] || slug
}

function prettyLevel(level: string) {
  return levelLabels[level] || level
}

function prettySize(size: string) {
  if (size === "small") return "Small"
  if (size === "medium") return "Medium"
  if (size === "large") return "Large"
  return size
}

function increaseQty() {
  quantity.value += 1
}

function decreaseQty() {
  if (quantity.value > 1) quantity.value -= 1
}

async function fetchProduct() {
  loading.value = true
  error.value = ""

  try {
    const slug = route.params.slug
    const res = await fetch(`http://localhost:8080/api/products/${slug}`)

    if (!res.ok) {
      throw new Error(`Nu s-a putut încărca produsul. (${res.status})`)
    }

    const data = await res.json()
    product.value = data as Product

    if (product.value.options && product.value.options.length > 0) {
  const firstAvailable = product.value.options.find(o => o.available)

  selectedOptionId.value = firstAvailable
    ? firstAvailable.id
    : product.value.options[0]?.id ?? null
} else {
  selectedOptionId.value = null
}
  } catch (err: unknown) {
    if (err instanceof Error) {
      error.value = err.message
    } else {
      error.value = "A apărut o eroare la încărcarea produsului."
    }
  } finally {
    loading.value = false
  }
}

function addToCart() {
  if (!product.value || !selectedOption.value) return

  const cartRaw = localStorage.getItem("glowJarCart")
  const cart: CartItem[] = cartRaw ? JSON.parse(cartRaw) : []

  const existingItem = cart.find(
    (item) =>
      item.productId === product.value!.id &&
      item.selectedSize === selectedOption.value!.size
  )

  if (existingItem) {
    existingItem.quantity += quantity.value
  } else {
    cart.push({
      id: `${product.value.id}-${selectedOption.value.size}`,
      productId: product.value.id,
      slug: product.value.slug,
      name: product.value.name,
      imageUrl: product.value.imageUrl,
      quantity: quantity.value,
      price: selectedOption.value.price,
      selectedSize: selectedOption.value.size,
      piecesCount: selectedOption.value.piecesCount,
    })
  }

  localStorage.setItem("glowJarCart", JSON.stringify(cart))
  window.dispatchEvent(new Event("storage"))

  addedMessage.value = "Produsul a fost adăugat în coș."
  setTimeout(() => {
    addedMessage.value = ""
  }, 2500)
}

onMounted(fetchProduct)
</script>

<style scoped>
.product-shell {
  max-width: 1400px;
  margin: 0 auto;
  padding-left: 24px;
  padding-right: 24px;
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

.product-layout {
  display: grid;
  grid-template-columns: minmax(0, 0.95fr) minmax(0, 1.05fr);
  gap: 32px;
  align-items: start;
}

.product-media-card,
.product-info-card {
  background: rgba(255, 255, 255, 0.98);
  border: 1px solid rgba(90, 59, 59, 0.08);
  border-radius: 28px;
  overflow: hidden;
}

.product-image-wrap {
  position: relative;
  width: 100%;
  height: 640px;
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
  top: 14px;
  left: 14px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.mini-badge {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.94);
  border: 1px solid rgba(90, 59, 59, 0.08);
  font-size: 12px;
  font-weight: 700;
  color: #5a3b3b;
}

.soft-badge {
  background: rgba(248, 232, 238, 0.92);
}

.product-info-card {
  padding: 32px;
}

.product-meta {
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.45px;
  color: rgba(90, 59, 59, 0.55);
  margin-bottom: 12px;
}

.product-title {
  font-size: 42px;
  line-height: 1.05;
  color: #5a3b3b;
  margin-bottom: 16px;
}

.product-short {
  font-size: 18px;
  color: rgba(90, 59, 59, 0.9);
  line-height: 1.55;
  margin-bottom: 14px;
}

.product-description {
  font-size: 15px;
  color: rgba(90, 59, 59, 0.78);
  line-height: 1.7;
  margin-bottom: 22px;
}

.details-list {
  display: grid;
  gap: 10px;
  margin-bottom: 22px;
}

.detail-item {
  padding: 12px 14px;
  border-radius: 16px;
  background: rgba(248, 232, 238, 0.42);
  color: #5a3b3b;
  font-size: 14px;
}

.made-to-order-box {
  padding: 14px 16px;
  border-radius: 18px;
  background: rgba(213, 174, 88, 0.12);
  color: #5a3b3b;
  font-weight: 700;
  margin-bottom: 24px;
}

.selector-block {
  margin-bottom: 22px;
}

.selector-label {
  font-size: 14px;
  font-weight: 700;
  color: #5a3b3b;
  margin-bottom: 12px;
}

.size-options {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.size-chip {
  min-width: 108px;
  height: 46px;
  padding: 0 18px;
  border-radius: 999px;
  border: 1px solid rgba(90, 59, 59, 0.12);
  background: white;
  color: #5a3b3b;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s ease;
}

.size-chip:hover {
  border-color: rgba(213, 174, 88, 0.5);
}

.size-chip.active {
  background: #d5ae58;
  color: white;
  border-color: #d5ae58;
}

.size-chip.disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

.price-box {
  padding: 18px 20px;
  border-radius: 22px;
  background: rgba(255, 255, 255, 0.9);
  border: 1px solid rgba(90, 59, 59, 0.08);
  margin-bottom: 22px;
}

.price-main {
  font-size: 32px;
  font-weight: 800;
  color: #5a3b3b;
  line-height: 1.1;
  margin-bottom: 6px;
}

.price-sub {
  color: rgba(90, 59, 59, 0.72);
  font-size: 14px;
}

.qty-row {
  margin-bottom: 24px;
}

.qty-box {
  display: inline-flex;
  align-items: center;
  gap: 18px;
  padding: 10px 14px;
  border-radius: 999px;
  border: 1px solid rgba(90, 59, 59, 0.12);
  background: white;
}

.qty-btn {
  width: 28px;
  height: 28px;
  border: none;
  background: transparent;
  color: #5a3b3b;
  font-size: 22px;
  line-height: 1;
  cursor: pointer;
}

.qty-value {
  min-width: 18px;
  text-align: center;
  font-weight: 700;
  color: #5a3b3b;
}

.actions-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.add-btn {
  min-width: 220px;
}

.added-message {
  margin-top: 16px;
  color: #4f7a4f;
  font-weight: 700;
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

@media (max-width: 1100px) {
  .product-layout {
    grid-template-columns: 1fr;
  }

  .product-image-wrap {
    height: 520px;
  }
}

@media (max-width: 700px) {
  .product-shell {
    padding-left: 16px;
    padding-right: 16px;
  }

  .product-info-card {
    padding: 22px;
  }

  .product-title {
    font-size: 32px;
  }

  .product-image-wrap {
    height: 380px;
  }

  .size-chip {
    min-width: 94px;
  }

  .add-btn {
    width: 100%;
  }

  .actions-row {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>