<script setup>
import TopNav from "../components/TopNav.vue";
import Login from "../components/Login.vue";
import store from '../store'
import {computed, onMounted} from "vue";

/*const demoValue = computed(() => store.state.demo)

function demo() {
    store.commit('demoUpdate', '哈哈哈哈')
}


onMounted(() => {
    demo()
})*/
</script>

<template>
<!--    <div>-->
<!--        <p>Store 中的值：{{ demoValue }}</p>-->
<!--    </div>-->
    <div style="display: flex;flex-direction: column;min-height: 100vh;">
        <div style="position: fixed;top: 0;width: 100%;z-index: 999;height: 45px;padding: 5px;background-color: #41444E">
            <TopNav></TopNav>
        </div>
        <div class="midMajor">
            <router-view></router-view>
        </div>
    </div>
</template>

<style scoped>
.midMajor {
    flex: 1;
    margin-top: 50px;
    background-color: #F4F5F8;
}
</style>