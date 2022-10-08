//引入mockjs
import Mock from 'mockjs'
import '@/mock/course/domain'
import '@/mock/course/course'
import '@/mock/course/teacher'
import '@/mock/course/secondDomain'
import '@/mock/course/article'
import '@/mock/course/chapter'
import '@/mock/course/video'
import '@/mock/course/courseDetail'
//使用mockjs模拟数据

Mock.mock(RegExp('/user/login' + '.*'), 'post', (options) => {
    var Random = Mock.Random.image
    return {
        status: 200,
        message: '请求列表成功！',
        data: {
            nickname: 'test222',
            userPhoto: Random('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
        },
    }
})
// title="test" id="1" cover="test" star="20" price="10"





// {
//     "ret":0,
//     "data":
//       {
//         "mtime": "@datetime",//随机生成日期时间
//         "score|1-800": 800,//随机生成1-800的数字
//         "rank|1-100":  100,//随机生成1-100的数字
//         "stars|1-5": 5,//随机生成1-5的数字
//         "nickname": "@cname",//随机生成中文名字
//       }

Mock.mock('/coursedetail', 'post', (options) => {
    console.log(options);
    const Random = require('mockjs').Random;
    return {
        status: 200,
        message: '请求成功！',
        data: {
            id: 8,
            price: '888',
           
            bought:1,
            
            //cover: '@/assets/photo/course/01.jpg',
            cover:   Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
            title:'1天速成springboot',
            teacherName:'李明',
            intro:'Java的发展历史，可追溯到1990年。当时Sun Microsystem公司为了发展消费性电子产品而进行了一个名为Green的项目计划。该计划 负责人是James Gosling。',
            chapters:[
            {   id:1,
                label:'java历史简介',
            },
            {
                id:2,
                label:'java基础语法',
            }
            ]
        },
    }
})

Mock.mock('/teacher_course', 'post', (options) => {
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
                    "cover": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                    "time": "@date('yyyy-MM-dd hh:mm:ss')",
                    "comment": "@int(1,1000)",
                    "thumbsUp": "@int(1,1000)",
                    "star": "@int(1,1000)",
                    "price": "@int(1,1000)",
                }
            ]
        })
    }
})

Mock.mock('/teacherdetail', 'post', (options) => {
    console.log(options);
    const Random = require('mockjs').Random;
    return {
        status: 200,
        message: '请求成功！',
        data: {

            price: '886',
           
            bought:1,
            
            cover:  Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Teacher Mock'),
          
            teacherName:'李明',
            resume:'项目经理、技术讲师、SUN、 SCJP、SCWCD、原工信部移动云计算教育培训中心教学总监。 十年软件开发经验：参与完成辽宁某高校远程教学管理系统、慧文信息门户系统、日本麦卡尔超市管理系统、崎玉市外来人口登记系统、深海视频会议管理系统',
           
        },
    }
})

Mock.mock('/question', 'get', (options) => {
    const Random = Mock.Random
    return {
        status: 200,
        message: '请求列表成功！',
        data: Mock.mock({
            'list|5': [
                {
                    "id|+1": 1,
                    "userId": "@cword(4)",
                    "title": "@cword(5,20)",
                    "content": "@csentence(20,50)",
                    "cover": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                    "time": "@date('yyyy-MM-dd hh:mm:ss')",

                }
            ]
        })
    }
})

Mock.mock('/onequestion', 'post', (options) => {
    const Random = require('mockjs').Random;
    return {
        status: 200,
        message: '请求成功！',
        data: {
            
            title: 'java如何快速入门',          
            cover:   Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),           
            userId: '李明',
            content:'Java的发展历史，可追溯到1990年。当时Sun Microsystem公司为了发展消费性电子产品而进行了一个名为Green的项目计划。该计划 负责人是James Gosling。',
            time: '2022-07-03 02:55:23',
        },
    }
})

Mock.mock('/answer', 'post', (options) => {
    const Random = Mock.Random
    return {
        status: 200,
        message: '请求列表成功！',
        data: Mock.mock({
            'list|5': [
                {
                    "id|+1": 1,
                    "userId": "@cword(4)",
                   
                    "content": "@csentence(20,50)",
                    "photo": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                    "time": "@date('yyyy-MM-dd hh:mm:ss')",

                }
            ]
        })
    }
})

Mock.mock('/comment', 'post', (options) => {
    const Random = Mock.Random
    return {
        status: 200,
        message: '请求列表成功！',
        data: Mock.mock({
            'list|3': [
                {
                    "id|+1": 1,
                    "userId": "@cword(4)",
                   
                    "content": "@csentence(20,50)",
                    "photo": Random.image('200x100', '#ffcc33', '#FFF', 'png', 'Fast Mock'),
                    "time": "@date('yyyy-MM-dd hh:mm:ss')",

                }
            ]
        })
    }
})