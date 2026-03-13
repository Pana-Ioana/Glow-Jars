import { createRouter, createWebHistory } from "vue-router"
import HomeView from "../views/HomeView.vue"
import CollectionView from "../views/CollectionView.vue"
import DropsView from "../views/DropsView.vue"
import LoginView from "../views/LoginView.vue"
import CustomizeJarView from "../views/CustomizeJarView.vue"
import CartView from "../views/CartView.vue"

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/collection",
      name: "collection",
      component: CollectionView,
    },
    {
      path: "/drops",
      name: "drops",
      component: DropsView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
    {
      path: "/customize-jar",
      name: "customize-jar",
      component: CustomizeJarView,
    },
    {
      path:"/cart",
      name: "cart",
      component: CartView,
    }
  ],
})

export default router