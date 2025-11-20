import axios from 'axios';

function request(axiosConfig) {
    const service = axios.create(
        {
            baseURL: `${process.env.VUE_APP_API_BASE_PREFIX}`,
            timeout: 60000,
        }
    );

    // 请求拦截
    service.interceptors.request.use(config => {
        return config
    }, err => {
        console.log('req error', err);
    })

    // 响应拦截
    service.interceptors.response.use(res => {
        return res.data
    }, err => {
        console.log('res error', err);
    })

    return service(axiosConfig)
}

export default request;
