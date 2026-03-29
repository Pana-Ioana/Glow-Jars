<template>
  <v-app>
    <v-app-bar
      height="72"
      elevation="4"
      class="topbar"
    >
      <v-container class="d-flex align-center justify-space-between">
        <div class="d-flex align-center ga-3 logo-click" @click="goHome">
          <v-avatar size="34" color="secondary" rounded="lg">
            <span class="brand-serif">GJ</span>
          </v-avatar>

          <div>
            <div class="brand-serif text-h6">Glow Jar</div>
            <div class="text-caption brand-subtitle">
              Experiență boutique curată
            </div>
          </div>
        </div>

        <div class="d-none d-md-flex ga-2">
          <v-menu
            open-on-hover
            open-delay="120"
            close-delay="120"
            location="bottom"
            offset="10"
          >
            <template #activator="{ props }">
              <v-btn
                v-bind="props"
                variant="text"
                class="btn-soft"
                @click.stop="router.push('/collection')"
              >
                Colecție
              </v-btn>
            </template>

            <v-sheet class="collection-menu pa-3" elevation="0">
              <div class="brand-serif text-subtitle-1 mb-3 collection-menu-title">
                Explorează colecția
              </div>

              <div class="collection-grid">
                <v-btn
                  v-for="item in collectionLinks"
                  :key="item.key"
                  variant="text"
                  class="collection-link"
                  @click="router.push(item.path)"
                >
                  {{ item.label }}
                </v-btn>
              </div>
            </v-sheet>
          </v-menu>

          <v-btn variant="text" class="btn-soft" @click="router.push('/drops')">
            Drops
          </v-btn>

          <v-btn variant="text" class="btn-soft" @click="goToHowItWorks">
            Cum funcționează
          </v-btn>
        </div>

        <div class="d-flex align-center ga-2">
          <template v-if="currentUser">
            <v-menu
              v-model="userMenu"
              open-on-hover
              open-delay="120"
              close-delay="120"
              location="bottom end"
              offset="10"
            >
              <template #activator="{ props }">
                <div
                  v-bind="props"
                  class="user-chip"
                >
                  <v-icon size="18" class="me-2">mdi-account-circle-outline</v-icon>
                  <span>Bună, {{ currentUser.firstName }}</span>
                  <v-icon size="16" class="ms-2">mdi-chevron-down</v-icon>
                </div>
              </template>

              <v-card class="user-menu-card pa-2" elevation="0">
                <v-btn
                  block
                  variant="text"
                  class="user-menu-btn"
                  @click="switchAccount"
                >
                  Schimbă contul
                </v-btn>

                <v-btn
                  block
                  variant="text"
                  class="user-menu-btn logout-btn"
                  @click="logout"
                >
                  Logout
                </v-btn>
              </v-card>
            </v-menu>
          </template>

          <template v-else>
            <v-btn
              variant="text"
              class="btn-soft"
              @click="router.push('/login')"
            >
              Autentificare
            </v-btn>
          </template>

          <v-btn class="gold-btn" @click="router.push('/customize-jar')">
            Creează jar
          </v-btn>

          <v-menu
            v-model="cartMenu"
            open-on-hover
            :close-on-content-click="false"
            location="bottom end"
            offset="10"
          >
            <template #activator="{ props }">
              <v-btn
                v-bind="props"
                icon
                variant="text"
                class="cart-btn"
                @mouseenter="loadCart"
                @click.stop="goToCart"
              >
                <v-badge
                  :content="itemCount"
                  :model-value="itemCount > 0"
                  color="warning"
                >
                  <v-icon size="24">mdi-cart-outline</v-icon>
                </v-badge>
              </v-btn>
            </template>

            <v-card class="cart-preview pa-3" elevation="0">
              <div class="brand-serif text-subtitle-1 mb-3 cart-preview-title">
                Coșul tău
              </div>

              <div v-if="!cartItems.length" class="empty-cart">
                Nu ai produse în coș momentan.
              </div>

              <div v-else>
                <div class="cart-preview-list">
                  <div
                    v-for="item in cartItems"
                    :key="item.id"
                    class="mini-cart-item"
                  >
                    <div class="mini-cart-row">
                      <div class="mini-cart-text">
                        <div class="mini-title">{{ item.name }}</div>

                        <div class="mini-subtitle">
                          {{ item.quantity }} x {{ item.price }} RON
                        </div>

                        <div v-if="item.customization" class="mini-custom">
                          <div v-if="item.customization.style">
                            <strong>Stil:</strong> {{ item.customization.style }}
                          </div>

                          <div v-if="item.customization.material">
                            <strong>Material:</strong> {{ item.customization.material }}
                          </div>

                          <div v-if="item.customization.priceRange">
                            <strong>Buget:</strong> {{ item.customization.priceRange }}
                          </div>

                          <div v-if="item.customization.size">
                            <strong>Mărime:</strong> {{ item.customization.size }}
                          </div>

                          <div v-if="item.customization.premiumOnly">
                            <strong>Premium only:</strong> Da
                          </div>

                          <div v-if="item.customization.notes">
                            <strong>Note:</strong> {{ item.customization.notes }}
                          </div>
                        </div>
                      </div>

                      <v-btn
                        icon
                        size="x-small"
                        variant="text"
                        color="error"
                        class="mini-delete-btn"
                        @click.stop="removeFromCart(item.id)"
                      >
                        <v-icon size="18">mdi-trash-can-outline</v-icon>
                      </v-btn>
                    </div>
                  </div>
                </div>

                <v-divider class="my-3" />

                <div class="cart-summary-row">
                  <span>Subtotal</span>
                  <strong>{{ subtotal }} RON</strong>
                </div>

                <v-btn
                  block
                  class="gold-btn mt-3"
                  @click="goToCart"
                >
                  Mergi la checkout
                </v-btn>
              </div>
            </v-card>
          </v-menu>
        </div>
      </v-container>
    </v-app-bar>

    <router-view />
  </v-app>
</template>

<script setup lang="ts">
import { computed, ref, watch, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const cartMenu = ref(false)
const userMenu = ref(false)
const cartItems = ref<any[]>([])
const currentUser = ref<any | null>(null)

const collectionLinks = [
  { key: 'all', label: 'Toate', path: '/collection' },
  { key: 'romantic', label: 'Romantic & pastel', path: '/collection?category=romantic' },
  { key: 'minimal', label: 'Minimal & clean', path: '/collection?category=minimal' },
  { key: 'elegant', label: 'Elegant & clasic', path: '/collection?category=elegant' },
  { key: 'bold', label: 'Bold & statement', path: '/collection?category=bold' },
  { key: 'dark', label: 'Dark & mysterious', path: '/collection?category=dark' },
  { key: 'boho', label: 'Boho & natural', path: '/collection?category=boho' },
  { key: 'glam', label: 'Glam & festive', path: '/collection?category=glam' },
  { key: 'celestial', label: 'Celestial & dreamy', path: '/collection?category=celestial' },
  { key: 'vintage', label: 'Vintage inspired', path: '/collection?category=vintage' }
]

const itemCount = computed(() =>
  cartItems.value.reduce((total, item) => total + (item.quantity || 0), 0)
)

const subtotal = computed(() =>
  cartItems.value.reduce((total, item) => total + (item.price || 0) * (item.quantity || 0), 0)
)

function loadCart() {
  try {
    cartItems.value = JSON.parse(localStorage.getItem('glowJarCart') || '[]')
  } catch {
    cartItems.value = []
  }
}

function loadCurrentUser() {
  try {
    currentUser.value = JSON.parse(localStorage.getItem('glowJarUser') || 'null')
  } catch {
    currentUser.value = null
  }
}

function removeFromCart(id: number) {
  cartItems.value = cartItems.value.filter((item) => item.id !== id)
  localStorage.setItem('glowJarCart', JSON.stringify(cartItems.value))
}

function switchAccount() {
  userMenu.value = false
  router.push('/login')
}

function logout() {
  localStorage.removeItem('glowJarUser')
  currentUser.value = null
  userMenu.value = false
  router.push('/')
}

function goHome() {
  router.push('/')
}

function tryScrollToHow() {
  const el = document.getElementById('how-it-works')
  if (!el) return false

  el.scrollIntoView({
    behavior: 'smooth',
    block: 'start'
  })

  return true
}

function startHowScrollRetry() {
  let tries = 0
  const maxTries = 40

  const interval = window.setInterval(() => {
    tries += 1
    const done = tryScrollToHow()

    if (done || tries >= maxTries) {
      window.clearInterval(interval)
      sessionStorage.removeItem('pendingScrollTarget')
    }
  }, 120)
}

async function goToHowItWorks() {
  if (route.path === '/') {
    startHowScrollRetry()
    return
  }

  sessionStorage.setItem('pendingScrollTarget', 'how-it-works')
  await router.push('/')
}

function goToCart() {
  cartMenu.value = false
  router.push('/cart')
}

function handleStorageChange() {
  loadCurrentUser()
  loadCart()
}

watch(
  () => route.path,
  (newPath) => {
    loadCart()
    loadCurrentUser()

    if (newPath !== '/') return

    const pending = sessionStorage.getItem('pendingScrollTarget')
    if (pending !== 'how-it-works') return

    startHowScrollRetry()
  },
  { immediate: true }
)

watch(cartMenu, (isOpen) => {
  if (isOpen) loadCart()
})

onMounted(() => {
  loadCart()
  loadCurrentUser()
  window.addEventListener('storage', handleStorageChange)
})

onBeforeUnmount(() => {
  window.removeEventListener('storage', handleStorageChange)
})
</script>

<style scoped>
.topbar {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: rgba(255, 255, 255, 0.94) !important;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(90, 59, 59, 0.1);
}

.brand-subtitle {
  opacity: 0.7;
}

.logo-click {
  cursor: pointer;
  transition: transform 0.2s ease, opacity 0.2s ease;
}

.logo-click:hover {
  transform: translateY(-1px);
  opacity: 0.85;
}

.btn-soft {
  text-transform: none !important;
  letter-spacing: 0 !important;
  font-weight: 700 !important;
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

.cart-btn {
  color: rgba(90, 59, 59, 0.9) !important;
}

.user-chip {
  display: flex;
  align-items: center;
  min-height: 42px;
  padding: 0 14px;
  border-radius: 999px;
  background: rgba(213, 174, 88, 0.12);
  color: #5a3b3b;
  font-weight: 700;
  cursor: default;
  transition: all 0.2s ease;
}

.user-chip:hover {
  background: rgba(213, 174, 88, 0.2);
}

.user-menu-card {
  min-width: 190px;
  border-radius: 18px;
  border: 1px solid rgba(90, 59, 59, 0.1);
  background: rgba(255, 255, 255, 0.98);
  box-shadow: 0 16px 45px rgba(60, 30, 30, 0.12);
}

.user-menu-btn {
  justify-content: flex-start;
  text-transform: none !important;
  letter-spacing: 0 !important;
  font-weight: 600 !important;
  color: rgba(90, 59, 59, 0.88) !important;
}

.logout-btn {
  color: #a64646 !important;
}

.collection-menu {
  min-width: 360px;
  border-radius: 20px;
  border: 1px solid rgba(90, 59, 59, 0.1);
  background: rgba(255, 255, 255, 0.96);
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  box-shadow: 0 16px 45px rgba(60, 30, 30, 0.12);
}

.collection-menu-title {
  color: rgba(90, 59, 59, 0.95);
}

.collection-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 6px;
}

.collection-link {
  justify-content: flex-start;
  text-transform: none !important;
  letter-spacing: 0 !important;
  font-weight: 600 !important;
  color: rgba(90, 59, 59, 0.82) !important;
}

.cart-preview {
  min-width: 360px;
  max-width: 390px;
  border-radius: 22px;
  border: 1px solid rgba(90, 59, 59, 0.1);
  background: rgba(255, 255, 255, 0.97);
  box-shadow: 0 16px 45px rgba(60, 30, 30, 0.12);
}

.cart-preview-title {
  color: rgba(90, 59, 59, 0.95);
}

.empty-cart {
  color: rgba(90, 59, 59, 0.72);
  font-size: 14px;
  padding: 10px 2px 6px;
}

.cart-preview-list {
  max-height: 280px;
  overflow-y: auto;
  padding-right: 4px;
}

.mini-cart-item {
  padding: 10px 0;
  border-bottom: 1px solid rgba(90, 59, 59, 0.08);
}

.mini-cart-item:last-child {
  border-bottom: none;
}

.mini-cart-row {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 10px;
}

.mini-cart-text {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.mini-title {
  font-weight: 700;
  color: #3e2c33;
}

.mini-subtitle {
  font-size: 13px;
  color: #6d5f66;
}

.mini-custom {
  margin-top: 4px;
  font-size: 12px;
  color: #7b6c73;
  line-height: 1.45;
}

.mini-delete-btn {
  margin-top: 2px;
  flex-shrink: 0;
}

.cart-summary-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #3e2c33;
}

@media (max-width: 960px) {
  .cart-preview {
    min-width: 300px;
  }

  .collection-menu {
    min-width: 320px;
  }

  .user-chip span {
    max-width: 110px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style>