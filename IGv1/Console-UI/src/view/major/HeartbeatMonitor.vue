<script setup>
import store from '../../store'
import {computed, onMounted, ref, watch} from "vue";
import igApi from "../../api/IgApi";

const interfaceMenuOptions = computed(() => store.state.interfaceMenuOptions);

const columns = [
    { title: "No", key: "no", width: 100 },
    { title: "接口名", key: "title", width: 200 },
    { title: "接口方法名", key: "methodTitle", width: 200 },
    { title: "持续阻塞时长", key: "time" },
    { title: "状态", key: "status", width: 100 },
];

const apiData = ref({})
const apiStatus = ref({})

const updateTableData = (menuOptions) => {
    let updatedData = {};

    Object.entries(menuOptions).forEach(([category, apis]) => {
        updatedData[category] = apis.children.map((api, index) => {
            const [, methodName] = api.key.split("-");
            return {
                top: apis.label,
                no: index + 1,
                title: api.label,
                methodTitle: methodName,
                time: "0",
                status: "健康"
            };
        });
    });

    apiData.value = updatedData;
};

const updateTableStatus = () => {
    Object.keys(apiData.value).forEach(category => {
        apiData.value[category] = apiData.value[category].map(item => {
            const statusInfo = apiStatus.value[item.methodTitle];

            return {
                ...item,
                time: statusInfo ? `${statusInfo.duration} 秒` : "0",
                status: statusInfo && statusInfo.status === "阻塞" ? "[×] 阻塞" : "健康"
            };
        });
    });
};

const fetchApiStatus = async () => {
    try {
        const response = await igApi.getHeartInfo();
        apiStatus.value = response.data || {};
        updateTableStatus();
    } catch (error) {
        console.error("获取 API 状态失败:", error);
    }
};

watch(interfaceMenuOptions, (newOptions) => {
    updateTableData(newOptions);
}, { deep: true, immediate: true });

onMounted(() => {
    fetchApiStatus();
    setInterval(fetchApiStatus, 1000);
});
</script>

<template>
    <div style="display: flex; justify-content: center; padding: 30px;">
        <div style="width: 1200px">
            <n-collapse style="font-size: 100px">
                <n-collapse-item v-for="(apis, category) in apiData" :key="category" :name="category">
                    <template #header>
                        <div style="font-size: 20px"> {{ apis[0].top }}</div>
                    </template>
                    <template #header-extra>
                        <div style="font-size: 17px; color: red" v-if="apis.some(api => api.status === '[×] 阻塞')">
                            存在阻塞接口
                        </div>
                    </template>
                    <n-data-table
                        :columns="columns"
                        :data="apis"
                        :pagination="false"
                        :bordered="false"
                    />
                </n-collapse-item>
            </n-collapse>
        </div>
    </div>
</template>

<style scoped>

</style>