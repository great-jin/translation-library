import request from './core/axios';

export function translate(params) {
    return request({
        url: `/api/nllb/translate`,
        method: 'post',
        data: params
    })
}

