import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Cadastro from '../views/Cadastro.vue'
import Clientes from '../views/Clientes.vue'
import Upload from '../views/Upload.vue'
import Dashboard from '../views/Dashboard.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    }, 
    {
        path: '/cadastro',
        name: 'cadastro',
        component: Cadastro
    },
    {
        path: '/clientes',
        name: 'clientes',
        component: Clientes,
        meta: { requiresAuth: true }
    },
    {
        path: '/upload',
        name: 'upload',
        component: Upload,
        meta: { requiresAuth: true }
    },
    {
        path: '/dashboard',
        name: 'dashboard',
        component: Dashboard,
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Guarda Global de Navegação
router.beforeEach((to, from, next) => {
  const usuarioLogado = localStorage.getItem('usuario')

  // Se a rota exige autenticação e o usuário NÃO está logado
  if (to.meta.requiresAuth && !usuarioLogado) {
    next('/login')
  } 
  // Se o usuário já está logado e tenta ir para o login/cadastro
  else if ((to.path === '/login' || to.path === '/cadastro') && usuarioLogado) {
    next('/upload')
  } 
  else {
    next() // Permite a navegação
  }
})

export default router