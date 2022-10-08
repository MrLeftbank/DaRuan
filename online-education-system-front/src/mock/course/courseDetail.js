
import Mock from 'mockjs'

export default [Mock.mock('/coursedetail', 'post', (options) => {
    console.log(options);
    return {
        status: 200,
        message: '请求成功！',
        data: {
            id: 8,
            price: '888',
            buy:'1',
            bought:1,
            titlePic: '@/assets/photo/course/01.jpg',
            title:'1天速成springboot',
            teacher_name:'李明',
            teacher_:'Java的发展历史，可追溯到1990年。当时Sun Microsystem公司为了发展消费性电子产品而进行了一个名为Green的项目计划。该计划 负责人是James Gosling。',
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
})]