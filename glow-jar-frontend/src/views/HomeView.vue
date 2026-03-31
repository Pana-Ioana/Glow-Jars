<template>
  <div>
    <v-main>
      <v-container class="py-16">
        <v-sheet
          v-intersect="reveal"
          class="reveal boutique-sheet pa-10 pa-md-16"
          color="surface"
          elevation="0"
        >
          <v-row align="center">
            <v-col cols="12" md="7">
              <div class="brand-serif text-h3 text-md-h2 mb-4">
                Curated Mystery Jewelry
              </div>

              <div class="text-body-1 mb-6 hero-subtitle">
                Surprize fine. Împachetate cu grijă. Alege o estetică, selectează un nivel și
                lasă-ne să îți curăm surpriza.
              </div>

              <div class="d-flex flex-wrap ga-3">
                <v-btn color="accent" rounded="xl" size="large" class="btn-soft">
                  Creează jar
                </v-btn>

                <v-btn
                  variant="outlined"
                  rounded="xl"
                  size="large"
                  class="btn-soft"
                  @click="router.push('/products')"
                >
                  Vezi colecția
                </v-btn>
              </div>

              <div class="d-flex flex-wrap ga-2 mt-6">
                <v-chip variant="outlined" rounded="xl">Mystery controlat</v-chip>
                <v-chip variant="outlined" rounded="xl">Valoare garantată</v-chip>
                <v-chip variant="outlined" rounded="xl">Upgrade rar</v-chip>
                <v-chip variant="outlined" rounded="xl">Drops limitate</v-chip>
              </div>
            </v-col>

            <v-col cols="12" md="5">
              <div class="hero-image-wrap">
                <img :src="heroImg" class="hero-img" alt="Hero jar" />
              </div>
            </v-col>
          </v-row>
        </v-sheet>

        <div
          v-intersect="reveal"
          class="reveal section-header mt-10 mb-6"
        >
          <div>
            <div class="brand-serif text-h5 section-heading">
              Estetici / categorii
            </div>
            <div class="text-body-2 section-subtitle">
              Vezi toate stilurile sau intră pe o categorie.
            </div>
          </div>

          <v-btn
            variant="outlined"
            rounded="xl"
            class="btn-soft"
            @click="router.push('/products')"
          >
            Vezi toate
          </v-btn>
        </div>

        <v-row v-intersect="reveal" class="reveal reveal-stagger">
          <v-col v-for="c in categories" :key="c.id" cols="12" sm="6" md="4">
            <v-sheet class="boutique-card pa-6 category-card" color="surface" elevation="0">
              <div class="d-flex align-center justify-space-between ga-4">
                <div>
                  <div class="brand-serif text-h6 section-heading">
                    {{ c.titlu }}
                  </div>
                  <div class="text-body-2 section-subtitle-strong">
                    {{ c.descriere }}
                  </div>
                </div>

                <v-btn icon variant="text" @click="router.push(c.path)">
                  <v-icon icon="mdi-arrow-right" />
                </v-btn>
              </div>
            </v-sheet>
          </v-col>
        </v-row>

        <div
          v-intersect="reveal"
          class="reveal d-flex align-center justify-space-between mt-16 mb-6"
        >
          <div>
            <div class="brand-serif text-h5 section-heading">
              Cele mai populare
            </div>
            <div class="text-body-2 section-subtitle">
              Un preview cu cele mai cumpărate jar-uri.
            </div>
          </div>

          <v-btn
            variant="text"
            class="btn-soft"
            color="accent"
            @click="router.push('/collection')"
          >
            Vezi colecția →
          </v-btn>
        </div>

        <v-row v-intersect="reveal" class="reveal reveal-stagger">
          <v-col v-for="jar in bestsellers" :key="jar.id" cols="12" md="4">
            <v-card class="boutique-card mood-card" elevation="0">
              <div class="mood-media">
                <img v-if="jar.img" :src="jar.img" class="mood-img" :alt="jar.titlu" />
                <div v-else class="mood-media-placeholder">Imagine jar</div>
              </div>

              <v-card-title class="brand-serif text-h6 section-heading">
                {{ jar.titlu }}
              </v-card-title>

              <v-card-text class="text-body-2 section-subtitle-strong">
                {{ jar.descriere }}
              </v-card-text>

              <v-card-actions class="px-4 pb-4">
                <v-btn
                  color="accent"
                  variant="text"
                  class="btn-soft"
                  @click="router.push(`/products?category=${jar.categorySlug}`)"
                >
                  Descoperă
                </v-btn>
                <v-spacer />
                <v-chip size="small" rounded="xl" variant="outlined">
                  de la {{ jar.pretMin }}
                </v-chip>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>

        <div
          id="how-it-works"
          v-intersect="reveal"
          class="reveal section-title mt-16"
        >
          <span class="section-line"></span>
          <h2>Cum funcționează</h2>
          <span class="section-line"></span>
        </div>

        <v-row v-intersect="reveal" class="reveal reveal-stagger mt-2">
          <v-col cols="12" md="4">
            <v-sheet class="boutique-card pa-6 how-card" color="surface" elevation="0">
              <div class="d-flex align-center ga-3 mb-2">
                <v-avatar size="34" color="secondary" rounded="lg">
                  <span class="brand-serif">1</span>
                </v-avatar>
                <div class="brand-serif text-h6 section-heading">Alegi estetica</div>
              </div>
              <div class="text-body-2 section-subtitle-strong">
                Selectezi un stil și un nivel.
              </div>
            </v-sheet>
          </v-col>

          <v-col cols="12" md="4">
            <v-sheet class="boutique-card pa-6 how-card" color="surface" elevation="0">
              <div class="d-flex align-center ga-3 mb-2">
                <v-avatar size="34" color="primary" rounded="lg">
                  <span class="brand-serif">2</span>
                </v-avatar>
                <div class="brand-serif text-h6 section-heading">Noi curăm</div>
              </div>
              <div class="text-body-2 section-subtitle-strong">
                Surpriză controlată, cu vibe boutique.
              </div>
            </v-sheet>
          </v-col>

          <v-col cols="12" md="4">
            <v-sheet class="boutique-card pa-6 how-card" color="surface" elevation="0">
              <div class="d-flex align-center ga-3 mb-2">
                <v-avatar size="34" color="secondary" rounded="lg">
                  <span class="brand-serif">3</span>
                </v-avatar>
                <div class="brand-serif text-h6 section-heading">Tu desfaci</div>
              </div>
              <div class="text-body-2 section-subtitle-strong">
                Primești jar-ul și poți prinde upgrade-uri rare.
              </div>
            </v-sheet>
          </v-col>
        </v-row>

        <v-sheet
          v-intersect="reveal"
          class="reveal boutique-sheet pa-10 mt-16"
          color="surface"
          elevation="0"
        >
          <v-row align="center">
            <v-col cols="12" md="8">
              <div class="text-overline drop-label">
                COLECȚIE LIMITATĂ
              </div>
              <div class="brand-serif text-h3 mt-2 section-heading">
                Spring Awakening
              </div>
              <div class="text-body-2 mt-2 section-subtitle-strong">
                Drop special disponibil pentru o perioadă limitată.
              </div>
            </v-col>

            <v-col cols="12" md="4" class="d-flex justify-md-end mt-6 mt-md-0">
              <v-btn
                color="accent"
                rounded="xl"
                size="large"
                class="btn-soft"
                @click="router.push('/drops')"
              >
                Vezi drop
              </v-btn>
            </v-col>
          </v-row>
        </v-sheet>

        <div v-intersect="reveal" class="reveal">
          <AppFooter />
        </div>
      </v-container>
    </v-main>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue"
import { useRouter } from "vue-router"
import AppFooter from "../components/AppFooter.vue"

const router = useRouter()

const heroImg = new URL("../assets/jars/homejar.png", import.meta.url).href

const categories = [
  {
    id: "all",
    titlu: "Toate jar-urile",
    descriere: "Vezi colecția completă.",
    path: "/collection",
  },
  {
    id: "romantic",
    titlu: "Romantic & pastel",
    descriere: "Stiluri delicate și feminine.",
    path: "/collection?category=romantic",
  },
  {
    id: "bold",
    titlu: "Statement & glam",
    descriere: "Strălucire și energie.",
    path: "/collection?category=bold",
  },
]

const bestsellers = [
  {
    id: "soft",
    titlu: "Soft Girl",
    categorySlug: "soft-girl",
    img: new URL("../assets/jars/homesoft.png", import.meta.url).href,
    descriere: "Univers delicat cu tonuri pastel.",
    pretMin: 90,
  },
  {
    id: "golden",
    titlu: "Golden Muse",
    categorySlug: "golden-muse",
    img: new URL("../assets/jars/homegolden.png", import.meta.url).href,
    descriere: "Eleganță aurie sofisticată.",
    pretMin: 120,
  },
  {
    id: "dark",
    titlu: "Dark Romance",
    categorySlug: "dark-romance",
    img: new URL("../assets/jars/homedark.png", import.meta.url).href,
    descriere: "Vibe misterios și dramatic.",
    pretMin: 110,
  },
]

const reveal = (isIntersecting: boolean, entries: IntersectionObserverEntry[]) => {
  const el = entries[0]?.target as HTMLElement | undefined
  if (!el) return
  if (isIntersecting) el.classList.add("is-visible")
}

const setupStagger = () => {
  const blocks = document.querySelectorAll(".reveal-stagger")
  blocks.forEach((block) => {
    const cards = block.querySelectorAll(".boutique-card, .v-card")
    cards.forEach((card, i) => {
      ;(card as HTMLElement).style.transitionDelay = `${Math.min(i * 90, 360)}ms`
    })
  })
}

onMounted(() => {
  setupStagger()
})
</script>

<style scoped>
#how-it-works {
  scroll-margin-top: 100px;
}

.reveal {
  opacity: 0;
  transform: translateY(18px);
  transition: opacity 0.7s ease, transform 0.7s cubic-bezier(.2,.8,.2,1);
  will-change: opacity, transform;
}

.reveal.is-visible {
  opacity: 1;
  transform: translateY(0);
}

.reveal-stagger .boutique-card,
.reveal-stagger .v-card {
  opacity: 0;
  transform: translateY(14px);
  transition: opacity 0.55s ease, transform 0.55s cubic-bezier(.2,.8,.2,1);
}

.reveal-stagger.is-visible .boutique-card,
.reveal-stagger.is-visible .v-card {
  opacity: 1;
  transform: translateY(0);
}

.hero-image-wrap {
  display: flex;
  justify-content: center;
  border-radius: 24px;
  overflow: hidden;
}

.hero-img {
  width: 100%;
  height: 340px;
  object-fit: cover;
  display: block;
}

.hero-subtitle {
  max-width: 58ch;
  opacity: 0.85;
}

.mood-media {
  height: 200px;
  overflow: hidden;
  border-top-left-radius: 22px;
  border-top-right-radius: 22px;
}

.mood-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.mood-media-placeholder {
  height: 100%;
  display: grid;
  place-items: center;
  background: linear-gradient(180deg, rgba(248, 232, 238, 0.65), rgba(234, 220, 248, 0.35));
  color: rgba(90, 59, 59, 0.6);
  font-weight: 700;
}

.mood-card {
  display: flex;
  flex-direction: column;
  min-height: 420px;
}

.mood-card .v-card-text {
  flex-grow: 1;
}

.section-heading {
  color: rgba(90, 59, 59, 0.95);
}

.section-subtitle {
  opacity: 0.75;
}

.section-subtitle-strong {
  opacity: 0.85;
}

.how-card {
  min-height: 136px;
}

.drop-label {
  letter-spacing: 2px;
  opacity: 0.7;
}

.category-card {
  min-height: 132px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 16px;
}
</style>