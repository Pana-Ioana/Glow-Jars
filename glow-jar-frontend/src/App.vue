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
          <v-btn variant="text" @click="router.push('/collection')">
            Colecție
          </v-btn>
          <v-btn variant="text" @click="router.push('/drops')">
            Drops
          </v-btn>    
          <v-btn variant="text">
            Cum funcționează
          </v-btn>
        </div>

        <div class="d-flex ga-2">
          <v-btn variant="text" class="d-none d-sm-inline-flex">
            Autentificare
          </v-btn>
          <v-btn color="accent" rounded="xl">
            Creează jar
          </v-btn>
        </div>
      </v-container>
    </v-app-bar>

    <router-view />
  </v-app>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router"

const router = useRouter()

const goHome = () => {
  window.location.href = "/"
}
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
}
</style>