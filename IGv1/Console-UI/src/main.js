import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

// UI框架
import naive from 'naive-ui'

// 路由
import router from './router'

// 全局状态
import store from './store'


createApp(App)
    .use(naive)
    .use(router)
    .use(store)
    .mount('#app')
