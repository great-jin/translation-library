import HomeView from "@/views/index.vue";
import ProductView from "@/views/product/index.vue";
import DetailView from "@/views/detail/index.vue";
import TranslateView from "@/views/translate/index.vue";
import ChatView from "@/views/chat/index.vue";

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
                path: '/detail',
                name: 'detail',
                component: DetailView
            }, {
                path: '/translate',
                name: 'translate',
                component: TranslateView
            }, {
                path: '/chat',
                name: 'chat',
                component: ChatView
            }
        ]
    }
]