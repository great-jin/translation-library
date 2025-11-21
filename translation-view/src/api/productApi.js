import request from './core/axios';

export function listProduct(params) {
    return request({
        url: `/api/product/list?language=${params}`,
        method: 'get'
    })
}

export function getDetail(params) {
    return request({
        url: `/api/product/getDetail?language=${params}`,
        method: 'get'
    })
}
