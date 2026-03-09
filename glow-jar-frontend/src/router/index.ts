import { createRouter, createWebHistory } from "vue-router"
import HomeView from "@/views/HomeView.vue"
import CollectionView from "@/views/CollectionView.vue"

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", name: "home", component: HomeView },
    { path: "/collection", name: "collection", component: CollectionView },
  ],
})