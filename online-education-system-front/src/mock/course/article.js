import Mock from 'mockjs'

export default [
    Mock.mock('/article', 'get', (options) => {
        const Random = Mock.Random
        return {
            status: 200,
            message: '请求列表成功！',
            data: Mock.mock({
                'list|8': [
                    {
                        "id|+1": 1,
                        "name": "@cword(10)",
                        "title": "@cword(5,50)",
                        "info": "@csentence(20,50)",
                        "price": "@float(10,100,10,100)",
                        "cover": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                        "time": "@date('yyyy-MM-dd hh:mm:ss')",
                        "comment": "@int(1,1000)",
                        "thumbsUp": "@int(1,1000)",
                    }
                ]
            })
        }
    }),
    Mock.mock('/article/hot', 'get', (options) => {
        const Random = Mock.Random
        return {
            status: 200,
            message: '请求列表成功！',
            data: Mock.mock({
                'list|8': [
                    {
                        "id|+1": 1,
                        "name": "@cword(10)",
                        "title": "@cword(5,50)",
                        "info": "@csentence(20,50)",
                        "price": "@float(10,100,10,100)",
                        "cover": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                        "time": "@date('yyyy-MM-dd hh:mm:ss')",
                        "comment": "@int(1,1000)",
                        "thumbsUp": "@int(1,1000)",
                    }
                ]
            })
        }
    })
]