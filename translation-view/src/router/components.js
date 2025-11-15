import HomeView from "@/views/index.vue";
import ProductView from "@/views/product/index.vue";
import TranslateView from "@/views/translate/index.vue";

export const routers = [
    {
        path: '/',
        name: 'home',
        component: HomeView,
        children: [
            {
                path: '/product',
                name: 'product',
                component: ProductView
            }, {
                path: '/translate',
                name: 'translate',
                component: TranslateView
            }
        ]
    }
]