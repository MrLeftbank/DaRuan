import Mock from 'mockjs'

export default Mock.mock('/teacher', 'get', (options) => {
    const Random = Mock.Random
    var data = Mock.mock({
        'list|8': [
            {
                "id|+1": 1,
                "name": "@cword(10)",
                "title": "@cword(20)",
                "info": "@csentence(20,50)",
                "price": "@float(10,100,10,100)",
                "photo": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock')
            }
        ]
    })
    return {
        status: 200,
        message: '请求列表成功！',
        data: data
        // [
        //     {
        //         id: '1',
        //         name: '@cname()',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     },
        //     {
        //         id: '2',
        //         name: 'test2',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     },
        //     {
        //         id: '3',
        //         name: 'test3',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     },
        //     {
        //         id: '3',
        //         name: 'test3',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     },
        //     {
        //         id: '3',
        //         name: 'test3',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     },
        //     {
        //         id: '3',
        //         name: 'test3',
        //         photo: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        //         info: "20",
        //     }
        // ]
    }
})