/**
 * Created by liuxh on 15-8-1
 */
Ext.define('MemberManagementDesktop.MemberManageWindow', {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var memberStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: ['memberInfo.sid', 'memberInfo.groupid', 'memberInfo.subscribe', 'memberInfo.openid', 'memberInfo.nickname',
                'memberInfo.sex', 'memberInfo.city', 'memberInfo.country', 'memberInfo.province', 'memberInfo.language',
                'memberInfo.headimgurl', 'memberInfo.subscribe_time', 'memberInfo.unionid', 'memberInfo.remark',
                'points', 'card_no'],
            pageSize: 20,
            storeId: 'memberStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/member/read.json'
                },
                idParam: 'sid',
                reader: {
                    type: 'json',
                    root: 'list'
                }
            },
            listeners: {
                beforeload: function (store, operation, eOpts) {
                    var queryField = Ext.getCmp(me.id + '_memberFilter');
                    if (queryField) {
                        store.getProxy().setExtraParam('query', encodeURI(queryField.getValue()));
                    }
                }
            }
        });
        memberStore.loadPage(1);
        this.tabs = [{
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_storeTab',
            title: '门店管理',
            header: false,
            columns: [{
                text: '会员编号',
                dataIndex: 'memberInfo.sid',
                hidden: true,
                hideable: false
            }, {
                text: '分组编号',
                dataIndex: 'memberInfo.groupid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'memberInfo.unionid',
                text: '唯一编号',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'memberInfo.subscribe',
                text: '状态',
                width: 60,
                renderer: function (value, p, record) {
                    if (value) {
                        return "<span style='color:green;font-weight:bold;'>关注</span>";
                    } else {
                        return "<span style='color:red;font-weight:bold;'>未关注</span>";
                    }
                },
                menuDisabled: true
            }, {
                dataIndex: 'memberInfo.openid',
                text: 'openid',
                flex: 3
            }, {
                dataIndex: 'memberInfo.nickname',
                text: '昵称',
                flex: 3
            }, {
                dataIndex: 'memberInfo.sex',
                text: '性别',
                flex: 1,
                renderer: function (value) {
                    for (var i in nogemasa.management.Dic.sex) {
                        var sex = nogemasa.management.Dic.sex[i];
                        if (sex.code == value) {
                            return sex.text;
                        }
                    }
                    return value;
                }
            }, {
                dataIndex: 'memberInfo.city',
                text: '城市',
                flex: 1
            }, {
                dataIndex: 'memberInfo.country',
                text: '国家',
                flex: 1
            }, {
                dataIndex: 'memberInfo.province',
                text: '省份',
                flex: 1
            }, {
                dataIndex: 'memberInfo.language',
                text: '语言',
                flex: 1
            }, {
                dataIndex: 'memberInfo.headimgurl',
                text: '头像',
                flex: 1
            }, {
                dataIndex: 'memberInfo.subscribe_time',
                text: '关注时间',
                width: 150,
                renderer: function (value) {
                    return Ext.util.Format.date(new Date(parseInt(value * 1000)), 'Y-m-d H:i:s');
                }
            }, {
                dataIndex: 'memberInfo.remark',
                text: '备注',
                flex: 1
            }, {
                dataIndex: 'points',
                text: '积分',
                flex: 1
            }, {
                dataIndex: 'card_no',
                text: '会员卡号',
                flex: 1
            }],
            store: memberStore,
            columnLines: true,
            //tbar: [{
            //    xtype: 'textfield',
            //    fieldLabel: '快速查找:',
            //    id: me.id + '_memberFilter',
            //    labelWidth: 60,
            //    labelAlign: 'right'
            //}, {
            //    xtype: 'button',
            //    text: '查找',
            //    handler: function () {
            //        memberStore.loadPage(1);
            //    }
            //}],
            bbar: {
                xtype: 'pagingtoolbar',
                store: memberStore,
                displayInfo: true
            },
            sortableColumns: false
        }];
    },
    createWindow: function () {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow(me.id);
        if (!win) {
            win = desktop.createWindow({
                id: me.id,
                title: me.title,
                width: 720,
                height: 480,
                iconCls: me.launcher.iconCls,
                animCollapse: false,
                border: false,
                constrainHeader: true,
                layout: 'fit',
                items: [{
                    xtype: 'tabpanel',
                    activeTab: 2,
                    bodyStyle: 'padding: 5px;',
                    closeAction: 'hide',
                    items: me.tabs
                }],
                minHeight: 480,
                minWidth: 720,
                minTabWidth: 50,
                maximized: true
            });
        }
        return win;
    }
});