//封装axios
import axios from 'axios'
import { getTokenFromLocalStorage } from './token'

const http = axios.create({

    //baseURL : 'http://62.234.54.216:8000',
    // baseURL : 'https://120.53.94.209',
    baseURL : 'http://192.168.3.4:8080',
    timeout : 50000
})
//请求拦截
http.interceptors.request.use((config) => {
    const token = getTokenFromLocalStorage()
    if(token) {
        config.data = {
            ...config.data,
            token:token
        }
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
}, (error) => {
    return Promise.reject(error)
})
//响应拦截
http.interceptors.response.use((response ) => {
    if(response.data?.code > 300) {
        return Promise.reject(response)
    }
    //2xx状态码
    return response
}, (error) => {
    //非2xx状态码
    return Promise.reject(error)
})

export {http}