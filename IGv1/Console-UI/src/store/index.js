import { createStore } from 'vuex'

export default createStore({
    state: {
        demo: '',
    },
    mutations: {
        demoUpdate(state,value){
            state.demo = value
        },
    },
    actions: {

    },
})