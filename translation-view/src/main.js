import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// element-plus icon
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 引入 Icon
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(ElementPlus)
app.use(router)
app.mount('#app')
