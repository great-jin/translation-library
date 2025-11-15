import request from './core/axios';

export function listProduct(params) {
    return request({
        url: `/api/product/list?language=${params}`,
        method: 'get'
    })
}
