/**
 * Created by liuxh on 15-7-7.
 */
Ext.define('SearchDesktop.AuthManageWindow', {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var authorityStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: ['sid', 'authority', 'authorityDesc'],
            pageSize: 20,
            storeId: 'authorityStore',
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/admin/auth/authority/create.json',
                    read: _appctx + '/admin/auth/authority/read.json',
                    update: _appctx + '/admin/auth/authority/update.json',
                    destroy: _appctx + '/admin/auth/authority/destroy.json'
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
                    expandData: true
                }
            }
        });
        authorityStore.loadPage(1);
        var authorityRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true
        });
        var groupStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: ['sid', 'groupName', 'groupDesc'],
            pageSize: 20,
            storeId: 'groupStore',
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/admin/auth/group/create.json',
                    read: _appctx + '/admin/auth/group/read.json',
                    destroy: _appctx + '/admin/auth/group/destroy.json',
                    update: _appctx + '/admin/auth/group/update.json'
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
                    expandData: true
                }
            },
            listeners: {
                beforeload: function (store, operation, eOpts) {
                    var queryField = Ext.getCmp(me.id + '_groupFilter');
                    if (queryField) {
                        store.getProxy().setExtraParam('query', queryField.getValue());
                    }
                }
            }
        });
        groupStore.loadPage(1);
        var groupRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true
        });
        var userStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: [{
                name: 'sid',
                type: 'string'
            }, {
                name: 'username',
                type: 'string'
            }, {
                name: 'enabled',
                type: 'boolean'
            }],
            pageSize: 20,
            storeId: 'userStore',
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/admin/auth/user/create.json',
                    read: _appctx + '/admin/auth/user/read.json',
                    destroy: _appctx + '/admin/auth/user/destroy.json'
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
                    expandData: true
                }
            },
            listeners: {
                beforeload: function (store, operation, eOpts) {
                    var queryField = Ext.getCmp(me.id + '_userFilter');
                    if (queryField) {
                        store.getProxy().setExtraParam('query', queryField.getValue());
                    }
                }
            }
        });
        userStore.loadPage(1);
        var userRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        this.tabs = [{
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_authorityTab',
            title: '权限管理',
            header: false,
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'authority',
                text: '权限名',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                dataIndex: 'authorityDesc',
                text: '权限描述',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 50,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/delete.gif',
                    tooltip: '删除权限',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        Ext.Msg.confirm('删除？', '确实要删除权限"' + rec.get('authority') + '"?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        authorityStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除权限失败");
                                        }
                                        authorityStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }]
            }],
            store: authorityStore,
            columnLines: true,
            tbar: [{
                xtype: 'button',
                text: '新增权限',
                handler: function (button, e) {
                    authorityRowEditing.cancelEdit();
                    authorityStore.insert(0, {
                        authority: '权限',
                        authorityDesc: '权限描述'
                    });
                    authorityRowEditing.startEdit(0, 1);
                }
            }],
            plugins: [authorityRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: authorityStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                edit: function (editor, context, eOpts) {
                    authorityStore.sync({
                        success: function (batch, options) {
                            authorityStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建权限失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新权限失败");
                            }
                            authorityStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    authorityStore.reload();
                }
            }
        }, {xtype: 'gridpanel',
            closable: false,
            id: me.id + '_groupTab',
            title: '用户组管理',
            header: false,
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'groupName',
                text: '用户组名称',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                dataIndex: 'groupDesc',
                text: '用户组描述',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 50,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑用户组',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('SearchDesktop.AuthGroupAuthorityWindow', {
                            group: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {
                    icon: '../images/icons/fam/delete.gif',
                    tooltip: '删除用户组',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        Ext.Msg.confirm('删除？', '确实要删除用户组"' + rec.get('groupName') + '"?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        groupStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除用户组失败");
                                        }
                                        groupStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }]
            }],
            store: groupStore,
            columnLines: true,
            tbar: [{
                xtype: 'textfield',
                fieldLabel: '快速查找:',
                id: me.id + '_groupFilter',
                labelWidth: 60,
                labelAlign: 'right'
            }, {
                xtype: 'button',
                text: '查找',
                handler: function () {
                    groupStore.loadPage(1);
                }
            }, '-', {
                xtype: 'button',
                text: '新增用户组',
                handler: function (button, e) {
                    groupRowEditing.cancelEdit();
                    groupStore.insert(0, {
                        groupName: '用户组名称',
                        groupDesc: '用户组描述'
                    });
                    groupRowEditing.startEdit(0, 1);
                }
            }],
            plugins: [groupRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: groupStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                edit: function (editor, context, eOpts) {
                    groupStore.sync({
                        success: function (batch, options) {
                            groupStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建用户组失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新用户组失败");
                            }
                            groupStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    groupStore.reload();
                }
            }
        }, {
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_userTab',
            title: '用户管理',
            header: false,
            columns: [{
                dataIndex: 'username',
                text: '用户名',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                dataIndex: 'enabled',
                text: '状态',
                width: 60,
                renderer: function(value, p, record){
                    if(value) {
                        return "<span style='color:green;font-weight:bold;'>正常</span>";
                    } else {
                        return "<span style='color:red;font-weight:bold;'>停用</span>";
                    }
                },
                menuDisabled: true
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 80,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑用户组权限',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('SearchDesktop.AuthUserGroupWindow', {
                            user: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {}, {
                    tooltip: '删除用户',
                    getClass: function(v, metaData, record) {
                        if(record.get("enabled")) {
                            return "disabled_user";
                        } else {
                            return "enabled_user"
                        }
                    },
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var sid = rec.get("sid");
                        var enabled = rec.get("enabled");
                        if(enabled) {
                            Ext.Msg.confirm('失效？', '确实要使用户"' + rec.get('username') + '"失效吗?', function (buttonId) {
                                if (buttonId == 'ok' || buttonId == 'yes') {
                                    grid.getStore().remove(rec);
                                    grid.getStore().sync({
                                        success: function (batch, options) {
                                            userStore.loadPage(1);
                                        },
                                        failure: function (batch, options) {
                                            if (options.operations.create) {
                                                Ext.Msg.alert("操作失败", "创建用户失败");
                                            } else if (options.operations.destroy) {
                                                Ext.Msg.alert("操作失败", "删除用户失败");
                                            }
                                            userStore.loadPage(1);
                                        }
                                    });
                                }
                            });
                        } else {
                            Ext.Msg.confirm('生效？', '确实要使用户"' + rec.get('username') + '"重新生效吗?', function (buttonId) {
                                if (buttonId == 'ok' || buttonId == 'yes') {
                                    Ext.Ajax.request({
                                        url: _appctx + '/admin/auth/user/enabled.json',
                                        params: {
                                            "sid": sid,
                                            "enabled" : enabled
                                        },
                                        success: function(response){
                                            userStore.loadPage(1);
                                        },
                                        failure: function (e) {
                                            Ext.Msg.alert("操作失败", "使用户生效操作失败");
                                            userStore.loadPage(1);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }],
                hideable: false
            }],
            store: userStore,
            columnLines: true,
            tbar: [{
                xtype: 'textfield',
                fieldLabel: '快速查找:',
                id: me.id + '_userFilter',
                labelWidth: 60,
                labelAlign: 'right'
            }, {
                xtype: 'button',
                text: '查找',
                handler: function () {
                    userStore.loadPage(1);
                }
            }, '-', {
                xtype: 'button',
                text: '新增用户',
                handler: function (button, e) {
                    userRowEditing.cancelEdit();
                    userStore.insert(0, {
                        enabled: true,
                        username: '新用户'
                    });
                    userRowEditing.startEdit(0, 1);
                    var queryField = Ext.getCmp(me.id + '_userFilter');
                    if (queryField) {
                        queryField.setValue('');
                    }
                }
            }],
            plugins: [userRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: userStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                beforeedit: function (editor, context, eOpts) {
                    if (context.record.get('exist')) {
                        Ext.create('Ext.ux.window.Notification', {
                            position: 'tr',
                            useXAxis: true,
                            cls: 'ux-notification-light',
                            iconCls: 'ux-notification-icon-information',
                            closable: true,
                            title: '',
                            html: '不能修改用户信息！',
                            slideInDuration: 800,
                            slideBackDuration: 1500,
                            autoCloseDelay: 4000,
                            slideInAnimation: 'elasticIn',
                            slideBackAnimation: 'elasticIn'
                        }).show();
                        return false;
                    }
                },
                edit: function (editor, context, eOpts) {
                    userStore.sync({
                        success: function (batch, options) {
                            userStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建用户失败");
                            } else if (options.operations.destroy) {
                                Ext.Msg.alert("操作失败", "删除用户失败");
                            }
                            userStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    userStore.reload();
                }
            }
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
