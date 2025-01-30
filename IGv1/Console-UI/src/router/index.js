import { createRouter,createWebHistory } from "vue-router";
import View from '../view/View.vue'
import InterfaceContent from "../view/major/InterfaceContent.vue";


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
                    name:'InterfaceContent',
                    component:InterfaceContent
                },
            ]
        },
    ]
})


export default router;