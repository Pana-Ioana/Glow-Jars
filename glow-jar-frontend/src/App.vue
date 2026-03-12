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
            <div class="text-caption" style="opacity: 0.7">
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

        <div class="d-flex ga-2">
          <v-btn
  variant="text"
  @click="$router.push('/login')"
>
  Autentificare
</v-btn>
          <v-btn color="accent" rounded="xl" class="btn-soft">
            Creează jar
          </v-btn>
        </div>
      </v-container>
    </v-app-bar>

    <router-view />
  </v-app>
</template>

<script setup lang="ts">
import { watch } from "vue"
import { useRoute, useRouter } from "vue-router"

const router = useRouter()
const route = useRoute()

const collectionLinks = [
  { key: "all", label: "Toate", path: "/collection" },
  { key: "romantic", label: "Romantic & pastel", path: "/collection?category=romantic" },
  { key: "minimal", label: "Minimal & clean", path: "/collection?category=minimal" },
  { key: "elegant", label: "Elegant & clasic", path: "/collection?category=elegant" },
  { key: "bold", label: "Bold & statement", path: "/collection?category=bold" },
  { key: "dark", label: "Dark & mysterious", path: "/collection?category=dark" },
  { key: "boho", label: "Boho & natural", path: "/collection?category=boho" },
  { key: "glam", label: "Glam & festive", path: "/collection?category=glam" },
  { key: "celestial", label: "Celestial & dreamy", path: "/collection?category=celestial" },
  { key: "vintage", label: "Vintage inspired", path: "/collection?category=vintage" },
]

const goHome = () => {
  window.location.href = "/"
}

const tryScrollToHow = () => {
  const el = document.getElementById("how-it-works")
  if (!el) return false

  el.scrollIntoView({
    behavior: "smooth",
    block: "start",
  })

  return true
}

const startHowScrollRetry = () => {
  let tries = 0
  const maxTries = 40

  const interval = window.setInterval(() => {
    tries += 1
    const done = tryScrollToHow()

    if (done || tries >= maxTries) {
      window.clearInterval(interval)
      sessionStorage.removeItem("pendingScrollTarget")
    }
  }, 120)
}

const goToHowItWorks = async () => {
  if (route.path === "/") {
    startHowScrollRetry()
    return
  }

  sessionStorage.setItem("pendingScrollTarget", "how-it-works")
  await router.push("/")
}

watch(
  () => route.path,
  (newPath) => {
    if (newPath !== "/") return

    const pending = sessionStorage.getItem("pendingScrollTarget")
    if (pending !== "how-it-works") return

    startHowScrollRetry()
  },
  { immediate: true }
)
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
</style>