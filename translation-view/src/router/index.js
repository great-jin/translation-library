import {createRouter, createWebHashHistory} from 'vue-router'
import {routers} from "@/router/components";

const routes = routers

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
