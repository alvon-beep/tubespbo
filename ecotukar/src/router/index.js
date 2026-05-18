import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import CustomerDashboard from '../views/CustomerDashboard.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import CourierDashboard from '../views/CourierDashboard.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'home', component: Home },
    { path: '/customer', name: 'customer', component: CustomerDashboard },
    { path: '/admin', name: 'admin', component: AdminDashboard },
    { path: '/courier', name: 'courier', component: CourierDashboard }
  ]
})

export default router