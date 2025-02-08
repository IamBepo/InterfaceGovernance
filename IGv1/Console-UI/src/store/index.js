import { createStore } from 'vuex'

export default createStore({
    state: {
        login_flag: true,
    },
    mutations: {
        showLogin(state,value){
            state.login_flag = value
        },
    },
    actions: {

    },
})