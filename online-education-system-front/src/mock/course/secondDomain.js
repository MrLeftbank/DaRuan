import Mock from 'mockjs'

export default Mock.mock(RegExp('/secondDomain' + '.*'), 'post', (options) => {
    if (options.body === '1') {
        return {
            status: 200,
            message: '请求列表成功！',
            data: [
                {
                    id:'1',
                    name:'test5',
                },
                {
                    id:'2',
                    name:'test6',
                },
                {
                    id:'3',
                    name:'test8',
                }
                ,
                {
                    id:'4',
                    name:'test32',
                }
                ,
                {
                    id:'5',
                    name:'test53',
                }
                ,
                {
                    id:'6',
                    name:'test26',
                }
            ]
        }
    }
    else return {
        status: 200,
        message: '请求列表成功！',
        data: [
            {
                id:'1',
                name:'test',
            },
            {
                id:'2',
                name:'test2',
            },
            {
                id:'3',
                name:'test3',
            }
            ,
            {
                id:'4',
                name:'test4',
            }
            ,
            {
                id:'5',
                name:'test5',
            }
            ,
            {
                id:'6',
                name:'test6',
            }
        ]
    }
})