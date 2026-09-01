import { createRouter, createWebHistory } from 'vue-router'

import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Cadastro from '../views/Cadastro.vue'
import Relatorios from '../views/Relatorios.vue'
import Upload from '../views/Upload.vue'
import Graficos from '../views/Graficos.vue'

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
        path: '/relatorios',
        name: 'relatorios',
        component: Relatorios
    },
    {
        path: '/upload',
        name: 'upload',
        component: Upload
    },
    {
        path: '/Graficos',
        name: 'Graficos',
        component: Graficos
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router