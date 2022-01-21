import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
    {
        path: '/',
        redirect: '/page/1'
    },
    {
        path: '/page/:page',
        name: 'Home',
        component: Home
    },
    {
        path: '/post/:pid',
        component: () => import('@/views/Post')
    },
    {
        path: '/random',
        component: () => import('@/views/Random')
    },
    {
        path: '/register',
        component: () => import('@/views/Register')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
