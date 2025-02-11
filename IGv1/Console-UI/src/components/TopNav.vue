<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const to = (path) => {
    router.push(path);
};
</script>

<template>
    <div style="display: flex; align-items: center; width: 100%; position: relative;">
        <div style="margin-left: auto; position: absolute; left: 10px;">
            <div style="display: flex; align-items: center;">
                <n-button size="large" style="background-color: #FFFFFF" @click="to('/')">
                    心跳监控
                </n-button>
                <n-button size="large" style="margin-left: 10px; background-color: #FFFFFF" @click="to('/ic')">
                    接口管理
                </n-button>
            </div>
        </div>

        <div style="display: flex; align-items: center; margin: 0 auto;">
            <div style="width: 50vh; margin-right: 2px;">
                <n-input placeholder="搜索接口" size="large" />
            </div>
            <div>
                <n-button type="primary" size="large">
                    Search
                </n-button>
            </div>
        </div>

        <div style="margin-left: auto; position: absolute; right: 20px;">
            <n-button type="info" size="large">
                生成PDF
            </n-button>
        </div>
    </div>
</template>

<style scoped>

</style>