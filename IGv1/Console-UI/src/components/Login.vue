<script setup>
import store from '../store'
import {computed} from "vue";

const login_flag = computed(() => store.state.login_flag)

function onPositiveClick() {
    store.commit('showLogin', !login_flag.value)
}
</script>

<template>
    <div style="">
        <n-modal v-model:show="login_flag">
            <n-card
                style="width: 600px; height: 200px;"
                title="认证"
                :bordered="false"
                size="huge"
                role="dialog"
                aria-modal="true"
            >
                <div>
                    <n-input placeholder="输入密钥" size="large"/>
                </div>
                <div style="margin-top: 10px;">
                    <n-button style="width: 100%" type="info" @click="onPositiveClick" size="large">验证</n-button>
                </div>
            </n-card>
        </n-modal>
    </div>
</template>

<style scoped>

</style>