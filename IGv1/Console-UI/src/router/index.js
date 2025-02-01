import { createRouter,createWebHistory } from "vue-router";
import View from '../view/View.vue'
import InterfaceContent from "../view/major/InterfaceContent.vue";
import HeartbeatMonitor from "../view/major/HeartbeatMonitor.vue";


const router = createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/',
            name:'View',
            component:View,
            children:[
                {
                    path:'/',
                    name:'HeartbeatMonitor',
                    component:HeartbeatMonitor
                },
                {
                    path:'/ic',
                    name:'InterfaceContent',
                    component:InterfaceContent
                }
            ]
        },
    ]
})


export default router;