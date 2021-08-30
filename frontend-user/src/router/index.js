import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/Home'
  },
  {
    path: '/Home',
    name: 'Home',
    component: () => import('../views/Home')
  },
  {
    path: '/About',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/music',
    name: 'Music',
    component: () => import('../views/Music/MusicList.vue')
  },
  {
    path: '/search',
    name: 'Search',
    props: route => ({ keyWords: route.query.key }),
    component: () => import('@/views/SearchResult.vue')
  },
  // Login区
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Login/Register.vue')
  },
  {
    path: '/forget',
    name: 'Forget',
    component: () => import('../views/Login/Forget.vue')
  },
  // Articles区
  {
    path: '/articles',
    name: 'Articles',
    component: () => import('../views/Articles/Articles.vue')
  },
  {
    path: '/articles/create',
    name: 'ArticleCreate',
    props: true,
    component: () => import('../views/Articles/ArticleEdit.vue')
  },
  {
    path: '/articles/:id',
    name: 'ArticleDetails',
    props: true,
    component: () => import('../views/Articles/ArticleDetails.vue')
  },
  {
    path: '/articles/edit/:id',
    name: 'ArticleEdit',
    props: true,
    component: () => import('../views/Articles/ArticleEdit.vue')
  },
  // Users区
  {
    path: '/users/:id',
    name: 'UserDetails',
    props: true,
    component: () => import('../views/Users/UserDetails.vue')
  },
  {
    path: '/settings',
    name: 'UserSettings',
    component: () => import('../views/Users/UserSettings.vue')

  },
  // Tools区
  {
    path: '/tools',
    name: 'Tools',
    component: () => import('../views/Tools/Tools.vue')
  },
  {
    path: '/tools/conditions',
    name: 'Conditions',
    component: () => import('../views/Tools/Conditions.vue')
  },
  {
    path: '/words/:id',
    name: 'WordDetails',
    props: true,
    component: () => import('../views/WordDetails.vue')
  },
  {
    path: '*',
    component: () => import('../views/NotFound.vue')
  },
  {
    path: '/NotFound',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue')
  },
  {
    path: '/Forbidden',
    name: 'Forbidden',
    component: () => import('../views/Forbidden.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

router.beforeEach((to, from, next) => {
  store.commit('tab', [to.name])
  store.commit('drawerVisibility', false)
  next()
})
