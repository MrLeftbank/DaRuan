import request from '@/utils/request'

export function req(url, method, data, headers, timeout) {
    if(headers)return request({
        url: url,
        method: method,
        data: data,
        headers: headers,
        timeout: timeout
    })
    return request({
        url: url,
        method: method,
        data: data,
        timeout: timeout
    })
}