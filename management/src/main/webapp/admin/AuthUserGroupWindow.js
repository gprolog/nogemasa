/**
 * Created by liuxh on 15-7-8.
 */
Ext.define('SearchDesktop.AuthUserGroupWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: me.user.username + '的用户组',
            items: [me.gridpanel],
            layout: "fit",
            modal: true,
            width: 700,
            height: 440,
            bbar: [{
                xtype: 'button',
                text: '修改',
                handler: function () {
                    me.save();
                }
            }, {
                xtype: 'button',
                text: '退出',
                handler: function () {
                    me.cancel();
                }
            }]
        });
    },
    initComponents: function () {
        var me = this;
        var store = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: [{
                name: 'sid',
                type: 'string'
            }, {
                name: 'userSid',
                type: 'string'
            }, {
                name: 'groupSid',
                type: 'int'
            }, {
                name: 'groupName',
                type: 'string'
            }, {
                name: 'groupDesc',
                type: 'string'
            }, {
                name: 'enabled',
                type: 'boolean'
            }],
            pageSize: 10000,
            storeId: 'authAdminUserGroupStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/admin/auth/userGroup/list/' + me.user.sid + '.json',
                    update: _appctx + '/admin/auth/userGroup/update.json'
                },
                idParam: 'sid',
                reader: {
                    type: 'json',
                    root: 'list'
                },
                writer: {
                    type: 'json',
                    encode: true,
                    root: 'data',
                    expandData: true,
                    allowSingle: false
                }
            }
        });
        store.loadPage(1);
        this.gridpanel = Ext.create('Ext.grid.Panel', {
            id: me.user.username + '_userGroupPanel',
            store: store,
            columnLines: true,
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'userSid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'groupSid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'enabled',
                text: '授权',
                width: 45,
                xtype: 'checkcolumn',
                hideable: false
            }, {
                dataIndex: 'groupName',
                text: '用户组',
                flex: 1
            }, {
                dataIndex: 'groupDesc',
                text: '用户组描述',
                flex: 1
            }],
            sortableColumns: true,
            overflowY: 'scroll'
        });
    },
    save: function () {
        var me = this;
        this.gridpanel.getStore().sync({
            success: function (batch, options) {
                me.close();
            },
            failure: function (batch, options) {
                Ext.Msg.alert("操作失败", "更新用户组失败");
            }
        });
    },
    cancel: function () {
        this.close();
    }
});
