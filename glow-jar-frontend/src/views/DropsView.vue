<template>
  <div>
    <v-main>
      <v-container class="py-16">

        <!-- TITLE -->
        <div class="mb-10">
          <div class="brand-serif text-h3 section-heading mb-2">
            Drops
          </div>
          <div class="text-body-1 section-subtitle">
            Colecții limitate Glow Jar. Unele apar doar o dată.
          </div>
        </div>

        <!-- CURRENT DROP -->
        <v-sheet
          class="boutique-sheet pa-10 mb-16 current-drop"
          color="surface"
          elevation="0"
        >
          <v-row align="center">

            <v-col cols="12" md="7">
              <div class="text-overline drop-label">
                DROP CURENT
              </div>

              <div class="brand-serif text-h3 mt-2 section-heading">
                Spring Awakening
              </div>

              <div class="text-body-1 mt-4 section-subtitle-strong">
                O colecție inspirată de începutul primăverii — lumină, flori delicate,
                bijuterii fine și surprize cu energie fresh.
              </div>

              <div class="mt-6 d-flex ga-3 flex-wrap">
                <v-chip variant="outlined" rounded="xl">
                  Aprilie 2026
                </v-chip>

                <v-chip variant="outlined" rounded="xl">
                  Drop limitat
                </v-chip>
              </div>

              <v-btn
                class="mt-6 btn-soft"
                color="accent"
                rounded="xl"
                size="large"
              >
                Explorează drop
              </v-btn>

            </v-col>

            <v-col cols="12" md="5">
              <div class="drop-image-wrap">
                <img :src="currentDropImg" class="drop-img" />
              </div>
            </v-col>

          </v-row>
        </v-sheet>

        <!-- PAST DROPS -->
        <div class="mb-6">
          <div class="brand-serif text-h5 section-heading">
            Drops anterioare
          </div>
        </div>

        <v-row>

          <v-col
            v-for="drop in pastDrops"
            :key="drop.id"
            cols="12"
            md="4"
          >
            <v-card class="boutique-card drop-card" elevation="0">

              <div class="drop-media">
                <img :src="drop.img" class="drop-media-img" />
              </div>

              <v-card-title class="brand-serif text-h6 section-heading">
                {{ drop.title }}
              </v-card-title>

              <v-card-text class="text-body-2 section-subtitle-strong">
                {{ drop.description }}
              </v-card-text>

              <v-card-actions class="px-4 pb-4">

                <v-chip
                  size="small"
                  variant="outlined"
                  rounded="xl"
                >
                  Încheiat
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

const currentDropImg = new URL(
  "../assets/jars/springawakening.png",
  import.meta.url
).href

const pastDrops = [
  {
    id: "winter-glow",
    title: "Winter Glow",
    description:
      "Un drop elegant inspirat de luminile iernii și de serile festive.",
    img: new URL("../assets/jars/winterglow.png", import.meta.url).href,
  },
  {
    id: "velvet-nights",
    title: "Velvet Nights",
    description:
      "O colecție misterioasă cu accente dark și bijuterii statement.",
    img: new URL("../assets/jars/velvetnights.png", import.meta.url).href,
  },
  {
    id: "golden-hour",
    title: "Golden Hour",
    description:
      "Inspirat de lumina caldă a apusului și eleganța aurie.",
    img: new URL("../assets/jars/gh.png", import.meta.url).href,
  },
]

</script>

<style scoped>

.section-heading{
  color: rgba(90,59,59,0.95);
}

.section-subtitle{
  opacity:.75;
}

.section-subtitle-strong{
  opacity:.85;
}

.current-drop{
  border-radius:24px;
}

.drop-label{
  letter-spacing:2px;
  opacity:.7;
}

.drop-image-wrap{
  border-radius:24px;
  overflow:hidden;
}

.drop-img{
  width:100%;
  height:280px;
  object-fit:cover;
}

.drop-card{
  min-height:340px;
  display:flex;
  flex-direction:column;
}

.drop-media{
  height:180px;
  overflow:hidden;
  border-top-left-radius:22px;
  border-top-right-radius:22px;
}

.drop-media-img{
  width:100%;
  height:100%;
  object-fit:cover;
}

</style>