import Mock from 'mockjs'

export default [Mock.mock('/video', 'post', (options) => {
    console.log(options)
    return {
        'name': 'test video',
        'url': 'test',
    }
}),
Mock.mock(RegExp('/upload' + '.*'), 'post', (options) => {
    console.log(options)
    const Random = Mock.Random
    return {

        status: 200,
        message: '请求列表成功！',
        data: Mock.mock({
            "id|+1": 1,
            "name": "@cword(10)",
            "title": "@cword(5,50)",
            "info": "@csentence(20,50)",
            "price": "@float(10,100,10,100)",
            "cover": Random.image('400x400', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
            "time": "@date('yyyy-MM-dd hh:mm:ss')",
            "comment": "@int(1,1000)",
            "thumbsUp": "@int(1,1000)",
            "videoName": "@cword(5,50)",
            "videoUrl": "@cword(5,50)",
            "fileName": "@cword(5,50)",
            "fileUrl": "@cword(5,50)",
        })
    }
}),
Mock.mock(RegExp('/get' + '.*'), 'get', (options) => {
    console.log(options)
    const Random = Mock.Random
    return {
        status: 200,
        message: '请求列表成功！',
        data: Mock.mock({
            "id|+1": 1,
            "name": "@cword(10)",
            "title": "@cword(5,50)",
            "info": "@csentence(20,50)",
            "price": "@float(10,100,10,100)",
            "cover": Random.image('400x400', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
            "time": "@date('yyyy-MM-dd hh:mm:ss')",
            "comment": "@int(1,1000)",
            "thumbsUp": "@int(1,1000)",
            "videoName": "@cword(5,50)",
            "videoUrl": "@cword(5,50)",
            "fileName": "@cword(5,50)",
            "fileUrl": "@cword(5,50)",
        })
    }
}),
Mock.mock(RegExp('/section' + '.*'), 'delete', (options) => {
    console.log(options)
    const Random = Mock.Random
    return {
        status: 200,
        message: '请求列表成功！',
    }
})
]