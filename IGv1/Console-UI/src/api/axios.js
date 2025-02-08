import Axios from 'axios'
import { useMessage } from "naive-ui";

const message = useMessage();

const axois = Axios.create({
    baseURL: 'http://localhost:8080', //请求后端数据的基本地址，自定义
    timeout: 10000,                   //请求超时设置，单位ms
    header: 'Content-Type:application/x-www-form-urlencoded'
})

// request拦截器
// axois.interceptors.request.use(config => {
//     let token = localStorage.getItem('token')
//     if (token) {
//         config.headers['token'] = token // 让每个请求携带自定义token
//     }
//     return config
// }, error => {
//     console.log(error) // for debug
// })

// respone拦截器
axois.interceptors.response.use(
    response => {
        const res = response.data

        if (res.code != '200') {
            message.error("网络似乎出现了问题.");
            return Promise.reject('error')
        } else {
            return res
        }
    },
    error => {
        message.error(error.message);
        console.log('错误:' + error.message)

        if (error.response.status == 504) {
            console.log('连接超时')
        } else {
            console.log(error)
        }
        return Promise.reject(error)
    }
)

export default axois