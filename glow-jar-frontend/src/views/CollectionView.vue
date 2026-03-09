<template>
  <div>
    <v-main>
      <v-container class="py-16">
        <div class="d-flex align-center justify-space-between mb-8 flex-wrap ga-4">
          <div>
            <div class="brand-serif text-h3 section-heading mb-2">
              Toate jar-urile
            </div>
            <div class="text-body-1 section-subtitle-strong">
              Explorează întreaga colecție Glow Jar și descoperă stilul care ți se potrivește.
            </div>
          </div>

          <v-select
            v-model="selectedCategory"
            :items="filterOptions"
            item-title="title"
            item-value="value"
            label="Filtrează după stil"
            variant="outlined"
            density="comfortable"
            hide-details
            class="collection-filter"
          />
        </div>

        <v-row>
          <v-col
            v-for="jar in filteredJars"
            :key="jar.id"
            cols="12"
            sm="6"
            md="4"
            lg="3"
          >
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
                <v-btn color="accent" variant="text" class="btn-soft">
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
      </v-container>
    </v-main>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from "vue"
import { useRoute } from "vue-router"

type CategoryKey =
  | "all"
  | "romantic"
  | "minimal"
  | "elegant"
  | "bold"
  | "dark"
  | "boho"
  | "glam"
  | "celestial"
  | "vintage"

const route = useRoute()
const selectedCategory = ref<CategoryKey>("all")

const filterOptions = [
  { title: "Toate", value: "all" },
  { title: "Romantic & pastel", value: "romantic" },
  { title: "Minimal & clean", value: "minimal" },
  { title: "Elegant & clasic", value: "elegant" },
  { title: "Bold & statement", value: "bold" },
  { title: "Dark & mysterious", value: "dark" },
  { title: "Boho & natural", value: "boho" },
  { title: "Glam & festive", value: "glam" },
  { title: "Celestial & dreamy", value: "celestial" },
  { title: "Vintage inspired", value: "vintage" },
]

const jars = [
  {
    id: "soft",
    titlu: "Soft Girl",
    categorie: "romantic" as CategoryKey,
    img: new URL("../assets/jars/soft.png", import.meta.url).href,
    descriere:
      "Un univers delicat, cu accente romantice și tonuri pastel care completează un stil feminin și visător.",
    pretMin: 90,
  },
  {
    id: "golden",
    titlu: "Golden Muse",
    categorie: "elegant" as CategoryKey,
    img: new URL("../assets/jars/golden.png", import.meta.url).href,
    descriere:
      "O estetică inspirată de eleganța aurie, cu o energie caldă și sofisticată, potrivită pentru apariții memorabile.",
    pretMin: 120,
  },
  {
    id: "minimal",
    titlu: "Minimal Glow",
    categorie: "minimal" as CategoryKey,
    img: new URL("../assets/jars/minim.png", import.meta.url).href,
    descriere:
      "Linii curate, echilibru și rafinament discret pentru un stil modern care nu are nevoie de exagerări.",
    pretMin: 100,
  },
  {
    id: "dark",
    titlu: "Dark Romance",
    categorie: "dark" as CategoryKey,
    img: new URL("../assets/jars/dark.png", import.meta.url).href,
    descriere:
      "Un vibe profund și misterios, cu accente dramatice și o eleganță care atrage fără să fie evidentă.",
    pretMin: 110,
  },
  {
    id: "rose",
    titlu: "Rose Quartz",
    categorie: "romantic" as CategoryKey,
    img: new URL("../assets/jars/rq.png", import.meta.url).href,
    descriere:
      "O estetică luminoasă și romantică, inspirată de nuanțe fine de roz și o energie blândă, feminină.",
    pretMin: 130,
  },
  {
    id: "celestial",
    titlu: "Celestial Glow",
    categorie: "celestial" as CategoryKey,
    img: new URL("../assets/jars/celestial.png", import.meta.url).href,
    descriere:
      "Inspirat de cerul nopții și de lumina subtilă a stelelor, pentru un stil visător și ușor misterios.",
    pretMin: 120,
  },
  {
    id: "vintage",
    titlu: "Vintage Muse",
    categorie: "vintage" as CategoryKey,
    img: new URL("../assets/jars/vintage.png", import.meta.url).href,
    descriere:
      "Un mix între clasic și modern, cu influențe retro reinterpretate într-o manieră elegantă și actuală.",
    pretMin: 140,
  },
  {
    id: "cottage",
    titlu: "Cottage Bloom",
    categorie: "boho" as CategoryKey,
    img: new URL("../assets/jars/cottage.png", import.meta.url).href,
    descriere:
      "O atmosferă naturală și romantică, inspirată de flori, lumină caldă și simplitate autentică.",
    pretMin: 110,
  },
  {
    id: "edgy",
    titlu: "Edgy Chic",
    categorie: "bold" as CategoryKey,
    img: new URL("../assets/jars/edgy.png", import.meta.url).href,
    descriere:
      "Un stil îndrăzneț și modern, cu accente metalice și o energie urbană care nu trece neobservată.",
    pretMin: 130,
  },
  {
    id: "boho",
    titlu: "Boho Dream",
    categorie: "boho" as CategoryKey,
    img: new URL("../assets/jars/boho.png", import.meta.url).href,
    descriere:
      "O estetică liberă și artistică, cu influențe boeme și o energie relaxată, perfectă pentru spiritul liber.",
    pretMin: 120,
  },
  {
    id: "glam",
    titlu: "Glam Goddess",
    categorie: "glam" as CategoryKey,
    img: new URL("../assets/jars/glam.png", import.meta.url).href,
    descriere:
      "Strălucire și opulență pentru un stil care emană încredere și atracție, perfect pentru ocazii speciale.",
    pretMin: 150,
  },
]

const validCategories: CategoryKey[] = [
  "all",
  "romantic",
  "minimal",
  "elegant",
  "bold",
  "dark",
  "boho",
  "glam",
  "celestial",
  "vintage",
]

const syncCategoryFromQuery = () => {
  const category = route.query.category
  if (typeof category === "string" && validCategories.includes(category as CategoryKey)) {
    selectedCategory.value = category as CategoryKey
  } else {
    selectedCategory.value = "all"
  }
}

watch(() => route.query.category, syncCategoryFromQuery, { immediate: true })

const filteredJars = computed(() => {
  if (selectedCategory.value === "all") return jars
  return jars.filter((jar) => jar.categorie === selectedCategory.value)
})
</script>

<style scoped>
.collection-filter {
  min-width: 300px;
}

.section-heading {
  color: rgba(90, 59, 59, 0.95);
}

.section-subtitle-strong {
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
</style>