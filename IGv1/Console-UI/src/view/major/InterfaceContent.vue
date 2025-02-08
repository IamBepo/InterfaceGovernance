<script setup>
import {computed, onMounted, ref} from "vue";
import igApi from "../../api/IgApi";
import axios from "axios";

const interfaces = ref(null)
const activeInterface = ref(null)
const interfaceMenuOptions = ref([
    {
        label: "用户",
        key: "user",
    },
    {
        label: "班级",
        key: "class",
        children: [
            {
                label: "查询班级",
                key: "query-class",
                tag: "GET", // 自定义标签内容
            },
            {
                label: "新增班级",
                key: "add-class"
            }
        ]
    },
])

const interfaceInfo = ref(null)
const serverUrl = ref("http://localhost:8080")
const parsedParams = computed(() => {   // 解析 params 数据 (String username 用户名 1)
    if (!interfaceInfo.value || !interfaceInfo.value.params) return [];

    return interfaceInfo.value.params.map(paramStr => {
        const parts = paramStr.split(" ");
        if (parts.length < 3) return null;

        return {
            type: parts[0],        // 类型 (String, Integer)
            name: parts[1],        // 参数名 (username)
            description: parts[2], // 描述 (用户名)
            required: parts[3] === "1" // 必要性 (1 = 必要，0 = 非必要)
        };
    }).filter(p => p !== null); // 过滤掉解析失败的项
})
const paramValues = ref({})
const responseData = ref(null)
const responseCode = ref("...")

// TODO 返回响应、授权、Header、Body待完成

function getIGInfo() {
    igApi.getIGInfo().then(res => {
        console.log(res);
        interfaces.value = res.data

        interfaceMenuOptions.length = 0
        interfaceMenuOptions.value = Object.entries(interfaces.value).map(([groupName, apiList]) => ({
            label: groupName,
            key: groupName,
            children: apiList.map(api => ({
                label: api.apiName,
                key: `${groupName}-${api.methodName}`,
                tag: api.httpMethod,
            }))
        }));
    })
}

function interfaceMenuClick(key) {
    console.log("选中的接口:", key);
    const [groupName, methodName] = key.split("-");
    interfaceInfo.value = interfaces.value[groupName]?.find(api => api.methodName === methodName)
}

const sendRequest = async () => {
    try {
        const method = interfaceInfo.value["httpMethod"].toUpperCase();
        let url = serverUrl.value + interfaceInfo.value["url"];

        let response;
        if (method === "GET") {
            const paramValuesArray = Object.values(paramValues.value).filter(val => val !== "");
            if (paramValuesArray.length > 0) {
                url += "/" + paramValuesArray.map(encodeURIComponent).join("/");
            }
            console.log("请求 URL:", url);
            response = await axios.get(url);
        } else {
            // TODO POST请求存在BUG
            console.log("请求 URL:", url);
            response = await axios.post(url, paramValues.value, {
                headers: { "Content-Type": "application/json" }
            });
        }

        responseData.value = JSON.stringify(response.data, null, 2);
        responseCode.value = response.data.code
    } catch (error) {
        console.error(error.message);
    }
};

onMounted(() => {
    getIGInfo();
})
</script>

<template>
    <div style="display: flex; height: calc(100vh - 50px);">
        <div style="width: 12%; background-color: rgb(197 197 197 / 9%)">
            <n-menu
                v-model:value="activeInterface"
                :root-indent="36"
                :indent="12"
                :options="interfaceMenuOptions"
                @update:value="interfaceMenuClick"
            />
        </div>

        <div style="padding: 20px 40px 20px 40px; width: 88%">
            <div v-if="interfaceInfo">
                <div style="display: flex; align-items: center;">
                    <div style="font-size: 30px">
                        {{ interfaceInfo.apiName }}
                    </div>
                    <div style="margin-left: 5px">
                        <n-tag type="warning">
                            {{ interfaceInfo.httpMethod }}
                        </n-tag>
                    </div>
                    <div style="margin-left: 10px; font-size: 18px">
                        {{ interfaceInfo.url }}
                    </div>
                </div>

                <div style="margin-top: 10px;width: 100%">
                    <n-card content-style="padding: 0;" embedded>
                        <n-tabs type="line" size="large" :tabs-padding="20" pane-style="padding: 10px; width: 90%">
                            <n-tab-pane name="描述">
                                <div v-if="interfaceInfo.description" style="font-size: 15px; padding: 10px">
                                    {{ interfaceInfo.description }}
                                </div>
                                <div v-else style="color: gray; font-size: 15px; padding: 10px">暂无描述</div>
                            </n-tab-pane>
                            <n-tab-pane name="请求参数">
                                <div style="width: 70%">
                                    <n-space vertical>
                                        <n-table striped>
                                            <thead>
                                                <tr>
                                                    <th style="width: 25%">参数名</th>
                                                    <th style="width: 25%">参数类型</th>
                                                    <th style="width: 45%">描述</th>
                                                    <th style="width: 5%">必要</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr v-for="param in parsedParams" :key="param.name">
                                                    <td><n-tag type="info">{{ param.name }}</n-tag></td>
                                                    <td>{{ param.type }}</td>
                                                    <td>{{ param.description }}</td>
                                                    <td>
                                                        <n-tag :type="param.required ? 'success' : 'warning'" round>
                                                            {{ param.required ? '√' : '×' }}
                                                        </n-tag>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </n-table>
                                    </n-space>
                                </div>
                            </n-tab-pane>
                            <n-tab-pane name="返回响应">
                                <div style="width: 70%">
                                    <n-space vertical>
                                        <n-table striped>
                                            <thead>
                                            <tr>
                                                <th style="width: 25%">数据名</th>
                                                <th style="width: 25%">数据类型</th>
                                                <th style="width: 45%">描述</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td><n-tag type="info">x</n-tag></td>
                                                <td>x</td>
                                                <td>x</td>
                                            </tr>
                                            </tbody>
                                        </n-table>
                                    </n-space>
                                </div>
                            </n-tab-pane>
                        </n-tabs>
                    </n-card>
                </div>

                <div>
                    <div style="font-size: 30px">
                        调试
                    </div>
                    <div style="display: flex; align-items: center;">
                        <div style="width: 70px">
                            <n-button disabled size="large" style="width: 100%">
                                {{ interfaceInfo.httpMethod }}
                            </n-button>
                        </div>
                        <div style="width: 15vh; margin-right: 2px;">
                            <n-input placeholder="服务器地址" size="large" :value="serverUrl"/>
                        </div>
                        <div style="width: 20vh; margin-right: 2px;">
                            <n-input placeholder="接口地址" size="large" :value="interfaceInfo.url"/>
                        </div>
                        <div>
                            <n-button type="info" size="large" @click="sendRequest">
                                发送
                            </n-button>
                        </div>
                    </div>
                    <div>
                        <n-card content-style="padding: 0;" embedded>
                            <n-tabs type="line" size="large" :tabs-padding="20" pane-style="padding: 10px; width: 90%">
                                <n-tab-pane name="参数">
                                    <div style="width: 70%">
                                        <n-space vertical>
                                            <n-table striped>
                                                <thead>
                                                <tr>
                                                    <th style="width: 25%">参数名</th>
                                                    <th style="width: 25%">参数类型</th>
                                                    <th style="width: 45%">值</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <tr v-for="param in parsedParams" :key="param.name">
                                                        <td><n-tag type="info">{{ param.name }}</n-tag></td>
                                                        <td>{{ param.type }}</td>
                                                        <td><n-input v-model:value="paramValues[param.name]" placeholder="参数值" size="large"/></td>
                                                    </tr>
                                                </tbody>
                                            </n-table>
                                        </n-space>
                                    </div>
                                </n-tab-pane>
                                <n-tab-pane name="授权">
                                    x
                                </n-tab-pane>
                                <n-tab-pane name="Header">
                                    x
                                </n-tab-pane>
                                <n-tab-pane name="Body">
                                    x
                                </n-tab-pane>
                            </n-tabs>
                        </n-card>
                    </div>
                </div>

                <div style="margin-top: 10px">
                    <n-card>
                        <div>
                            <div style="display: flex; align-items: center;">
                                <div>
                                    状态码：
                                </div>
                                <n-tag type="success" round>
                                    {{ responseCode }}
                                </n-tag>
                                <div style="margin-left: 20px">
                                    格式：JSON
                                </div>
                            </div>
                        </div>

                        <div style="margin-top: 15px">
                            <n-input v-model:value="responseData" placeholder="返回响应" type="textarea" :autosize="{minRows: 10,maxRows: 30,}"/>
                        </div>
                    </n-card>
                </div>
            </div>


            <div v-else style="font-size: 20px; color: gray;">
                请选择接口查看详细信息
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>
