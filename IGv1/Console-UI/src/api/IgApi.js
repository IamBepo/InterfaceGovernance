import axios from "./axios.js";
import qs from "qs";

export default {

    /**
     * 获取接口信息
     * @returns
     */
    getIGInfo() {
        return axios({
            url: '/api/ig/get/info',
            method: 'GET'
        })
    },
}