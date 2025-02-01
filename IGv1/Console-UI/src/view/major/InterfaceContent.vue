<script setup>
import { ref } from "vue";


const activeKey = ref(null);
const menuOptions = [
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
];
</script>

<template>
    <div style="display: flex; height: calc(100vh - 50px);">
        <div style="width: 12%; background-color: rgb(197 197 197 / 9%)">
            <n-menu
                v-model:value="activeKey"
                :root-indent="36"
                :indent="12"
                :options="menuOptions"
            />
        </div>

        <div style="padding: 20px 40px 20px 40px; width: 88%">
            <div style="display: flex; align-items: center;">
                <div style="font-size: 30px">
                    新增班级
                </div>
                <div style="margin-left: 5px">
                    <n-tag type="warning">
                        POST
                    </n-tag>
                </div>
                <div style="margin-left: 10px; font-size: 18px">
                    /api/token/signin
                </div>
            </div>

            <div style="margin-top: 10px;width: 100%">
                <n-card content-style="padding: 0;" embedded>
                    <n-tabs type="line" size="large" :tabs-padding="20" pane-style="padding: 10px; width: 90%">
                        <n-tab-pane name="描述">
                            调用此接口可以自定义新增班级
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
                                        <tr>
                                            <td><n-tag type="info">name</n-tag></td>
                                            <td>String</td>
                                            <td>班级的名称</td>
                                            <td><n-tag type="success" round>√</n-tag></td>
                                        </tr>
                                        <tr>
                                            <td><n-tag type="info">num</n-tag></td>
                                            <td>Integer</td>
                                            <td>班级最大人数</td>
                                            <td><n-tag type="warning" round>×</n-tag></td>
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
                                            <td><n-tag type="info">name</n-tag></td>
                                            <td>String</td>
                                            <td>班级的名称</td>
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
                    <div>
                        <n-button disabled size="large">
                            POST
                        </n-button>
                    </div>
                    <div style="width: 10vh; margin-right: 2px;">
                        <n-input placeholder="服务器地址" size="large" value="localhost:8080"/>
                    </div>
                    <div style="width: 20vh; margin-right: 2px;">
                        <n-input placeholder="接口地址" size="large" value="/api/token/signin"/>
                    </div>
                    <div>
                        <n-button type="info" size="large">
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
                                            <tr>
                                                <td><n-tag type="info">name</n-tag></td>
                                                <td>String</td>
                                                <td><n-input placeholder="参数值" size="large"/></td>
                                            </tr>
                                            <tr>
                                                <td><n-tag type="info">num</n-tag></td>
                                                <td>Integer</td>
                                                <td><n-input placeholder="参数值" size="large"/></td>
                                            </tr>
                                            </tbody>
                                        </n-table>
                                    </n-space>
                                </div>
                            </n-tab-pane>
                            <n-tab-pane name="授权">
                                调用此接口可以自定义新增班级
                            </n-tab-pane>
                            <n-tab-pane name="Header">
                                调用此接口可以自定义新增班级
                            </n-tab-pane>
                            <n-tab-pane name="Body">
                                调用此接口可以自定义新增班级
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
                                200
                            </n-tag>
                            <div style="margin-left: 20px">
                                格式：JSON
                            </div>
                        </div>
                    </div>

                    <div style="margin-top: 15px">
                        <n-input placeholder="返回响应" type="textarea" :autosize="{minRows: 10,maxRows: 30,}"/>
                    </div>
                </n-card>
            </div>
        </div>
    </div>
</template>

<style scoped>

</style>
