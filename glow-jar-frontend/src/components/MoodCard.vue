<template>
  <v-card :style="{ backgroundColor: mood.color }" rounded="xl" elevation="1">
    <v-card-title class="brand-serif text-h6">
      {{ mood.title }}
    </v-card-title>

    <v-card-text>
      <div class="text-body-2 mb-3">
        {{ mood.description }}
      </div>

      <div class="text-subtitle-2">
        From {{ mood.basePrice }} RON
      </div>
    </v-card-text>

    <v-card-actions>
      <v-btn color="accent" variant="text" @click="$emit('select', mood)">
        Explore
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import type { Mood } from "@/data/moods"

defineProps<{
  mood: Mood
}>()
</script>