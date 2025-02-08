import { createStore } from 'vuex'

export default createStore({
    state: {
        login_flag: true,
        interfaceMenuOptions: []
    },
    mutations: {
        showLogin(state,value){
            state.login_flag = value
        },
        setInterfaceMenuOptions(state, menuOptions) {
            state.interfaceMenuOptions = menuOptions;
        }
    },
    actions: {
        updateInterfaceMenuOptions({ commit }, menuOptions) {
            commit('setInterfaceMenuOptions', menuOptions);
        }
    },
})