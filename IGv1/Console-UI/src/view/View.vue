<script setup>
import TopNav from "../components/TopNav.vue";
import Login from "../components/Login.vue";
</script>

<template>
    <div style="display: flex;flex-direction: column;min-height: 100vh;">
        <div style="position: fixed;top: 0;width: 100%;z-index: 999;height: 45px;padding: 5px;background-color: #41444E">
            <TopNav></TopNav>
        </div>
        <div class="midMajor">
            <router-view></router-view>
        </div>

        <div>
            <Login></Login>
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