<script setup>
const data = [
    { no: 1, title: "查询班级", methodTitle: "queryClass", time: "1:18", status:"健康" },
    { no: 2, title: "新增班级", methodTitle: "addClass", time: "1:18", status:"健康" },
];

const columns = [
    { title: "No", key: "no", width: 100 },
    { title: "接口名", key: "title", width: 200 },
    { title: "接口方法名", key: "methodTitle", width: 200 },
    { title: "持续运行时长", key: "time" },
    { title: "状态", key: "status", width: 100 },
];

const data2 = [
    { no: 1, title: "查询用户", methodTitle: "queryUser", time: "1:18", status:"健康" },
    { no: 2, title: "新增用户", methodTitle: "addUser", time: "0", status:"疑似下线" },
];

const columns2 = [
    { title: "No", key: "no", width: 100 },
    { title: "接口名", key: "title", width: 200 },
    { title: "接口方法名", key: "methodTitle", width: 200 },
    { title: "持续运行时长", key: "time" },
    { title: "状态", key: "status", width: 100 },
];
</script>

<template>
    <div style="display: flex; justify-content: center; padding: 30px;">
        <div style="width: 1200px">
            <n-collapse style="font-size: 100px">
                <n-collapse-item name="1">
                    <template #header>
                        <div style="font-size: 20px">班级 [c.class]</div>
                    </template>
                    <n-data-table :columns="columns" :data="data" :pagination=false :bordered="false"/>
                </n-collapse-item>
                <n-collapse-item name="2">
                    <template #header>
                        <div style="font-size: 20px; color: red">用户 [user.class]</div>
                    </template>
                    <template #header-extra>
                        <div style="font-size: 17px; color: red">存在阻塞接口</div>
                    </template>
                    <n-data-table :columns="columns2" :data="data2" :pagination=false :bordered="false"/>
                </n-collapse-item>
            </n-collapse>
        </div>
    </div>
</template>

<style scoped>

</style>