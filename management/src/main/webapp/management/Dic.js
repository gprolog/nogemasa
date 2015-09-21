/**
 * 字典
 * Created by liuxh on 15-8-5
 */
Ext.define('nogemasa.management.Dic', {
    statics: {
        orderStatusEnum: [{
            name: '不限',
            status: ''
        }, {
            name: '新建',
            status: '0'
        }, {
            name: '修改',
            status: '1'
        }, {
            name: '完成',
            status: '2'
        }],
        sex: [{
            code: '0',
            text: '未知'
        }, {
            code: '1',
            text: '男性'
        }, {
            code: '2',
            text: '女性'
        }],
        subscribe: [{
            code: '0',
            text: '未关注'
        }, {
            code: '1',
            text: '关注'
        }],
        costType: [{
            code: '1',
            text: '现金'
        }, {
            code: '2',
            text: '银行卡'
        }]
    }
});