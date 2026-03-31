import { createRouter, createWebHistory } from "vue-router"
import HomeView from "../views/HomeView.vue"
import CollectionView from "../views/CollectionView.vue"
import DropsView from "../views/DropsView.vue"
import LoginView from "../views/LoginView.vue"
import CustomizeJarView from "../views/CustomizeJarView.vue"
import CartView from "../views/CartView.vue"
import RegisterView from '../views/RegisterView.vue'
import ProductsView from '../views/ProductsView.vue'
import ProductView from '../views/ProductView.vue'
import BankTransferView from '../views/BankTransferView.vue'
import CardPaymentView from '../views/CardPaymentView.vue'
import VerifyEmailView from '../views/VerifyEmailView.vue'
import ForgotPasswordView from '../views/ForgotPasswordView.vue'
import ResetPasswordView from '../views/ResetPasswordView.vue'
import OrdersView from "../views/OrdersView.vue"

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
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
    },
    { path: '/products', name: 'products', component: ProductsView },
    { path: '/products/:slug', name: 'product', component: ProductView },
    {
      path: '/bank-transfer',
      name: 'bank-transfer',
        component: BankTransferView
    },
    {
  path: '/card-payment',
  name: 'card-payment',
  component: CardPaymentView
},
  { path: '/verify-email', name: 'verify-email', component: VerifyEmailView },
  { path: '/forgot-password', name: 'forgot-password', component: ForgotPasswordView },
  { path: '/reset-password', name: 'reset-password', component: ResetPasswordView },
  {path: '/orders', name: 'orders', component: OrdersView},
  ],
})

export default router