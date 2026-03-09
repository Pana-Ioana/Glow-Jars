import { createRouter, createWebHistory } from "vue-router"
import HomeView from "@/views/HomeView.vue"
import CollectionView from "@/views/CollectionView.vue"
import DropsView from "@/views/DropsView.vue"

export default createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", name: "home", component: HomeView },
    { path: "/collection", name: "collection", component: CollectionView },
    { path: "/drops", name: "drops", component: DropsView}
  ],
})