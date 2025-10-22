import request from './axios';

export function listComments(params) {
    return request({
        url: `/api/comments/list?language=${params}`,
        method: 'get'
    })
}

export function getComments(params) {
    return request({
        url: `/api/comments/${params}`,
        method: 'get'
    })
}

