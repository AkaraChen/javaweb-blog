import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'

const routes = [
    {
        path: '/',
        name: 'Home',
        redirect: '/posts'
    },
    {
        path: '/posts',
        component: () => import('@/views/Posts')
    },
    {
        path: '/users',
        component: () => import('@/views/Users')
    },
    {
        path: '/setting',
        component: () => import('@/views/Setting')
    },
    {
        path: '/post/:id',
        component: () => import('@/views/Post')
    },
    {
        path: '/add/post',
        component: () => import('@/views/PostAdd')
    },
    {
        path: '/user/:id',
        component: () => import('@/views/User')
    },
    {
        path: '/add/user',
        component: () => import('@/views/UserAdd')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
