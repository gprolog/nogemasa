/**
 * Created by liuxh on 15-8-1
 */
Ext.define('StoreManagementDesktop.StoreManageWindow', {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var storesStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: [{
                name: 'sid',
                type: 'string'
            }, {
                name: 'name',
                type: 'string'
            }, {
                name: 'address',
                type: 'string'
            }, {
                name: 'phone',
                type: 'string'
            }, {
                name: 'shopowner',
                type: 'string'
            }, {
                name: 'enabled',
                type: 'boolean'
            }],
            pageSize: 20,
            storeId: 'storesStore',
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/management/store/create.json',
                    read: _appctx + '/management/store/read.json',
                    update: _appctx + '/management/store/update.json',
                    destroy: _appctx + '/management/store/destroy.json'
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
                    var queryField = Ext.getCmp(me.id + '_storeFilter');
                    if (queryField) {
                        store.getProxy().setExtraParam('query', queryField.getValue());
                    }
                }
            }
        });
        storesStore.loadPage(1);
        var storesRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        this.tabs = [{
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_storeTab',
            title: '门店管理',
            header: false,
            columns:[{
                dataIndex: 'name',
                text: '门店名',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'address',
                text: '门店地址',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'phone',
                text: '联系电话',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'shopowner',
                text: '店长',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
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
                width: 100,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑门店员工',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('StoreManagementDesktop.EmployeeWindow', {
                            stores: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {}, {
                    tooltip: '删除门店',
                    getClass: function(v, metaData, record){
                        if(record.get("enabled")) {
                            return "disabled_store";
                        } else {
                            return "enabled_store"
                        }
                    },
                    handler: function(grid, rowIndex, colIndex){
                        var rec = grid.getStore().getAt(rowIndex);
                        var sid = rec.get("sid");
                        var enabled = rec.get("enabled");
                        if(enabled) {
                            Ext.Msg.confirm('失效？', '确实要使门店"' + rec.get('name') + '"失效吗?', function (buttonId) {
                                if (buttonId == 'ok' || buttonId == 'yes') {
                                    grid.getStore().remove(rec);
                                    grid.getStore().sync({
                                        success: function (batch, options) {
                                            storesStore.loadPage(1);
                                        },
                                        failure: function (batch, options) {
                                            if (options.operations.create) {
                                                Ext.Msg.alert("操作失败", "创建门店失败");
                                            } else if (options.operations.destroy) {
                                                Ext.Msg.alert("操作失败", "删除门店失败");
                                            }
                                            storesStore.loadPage(1);
                                        }
                                    });
                                }
                            });
                        } else {
                            Ext.Msg.confirm('生效？', '确实要使门店"' + rec.get('name') + '"重新生效吗?', function (buttonId) {
                                if (buttonId == 'ok' || buttonId == 'yes') {
                                    Ext.Ajax.request({
                                        url: _appctx + '/management/store/enabled.json',
                                        params: {
                                            "sid": sid,
                                            "enabled" : enabled
                                        },
                                        success: function(response){
                                            storesStore.loadPage(1);
                                        },
                                        failure: function (e) {
                                            Ext.Msg.alert("操作失败", "使门店生效操作失败");
                                            storesStore.loadPage(1);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }],
                hideable: false
            }],
            store: storesStore,
            columnLines: true,
            tbar: [{
                xtype: 'textfield',
                fieldLabel: '快速查找:',
                id: me.id + '_storeFilter',
                labelWidth: 60,
                labelAlign: 'right'
            }, {
                xtype: 'button',
                text: '查找',
                handler: function () {
                    storesStore.loadPage(1);
                }
            }, '-', {
                xtype: 'button',
                text: '新增门店',
                handler: function (button, e) {
                    storesRowEditing.cancelEdit();
                    storesStore.insert(0, {
                        name: '新门店',
                        enabled: true
                    });
                    storesRowEditing.startEdit(0, 1);
                    var queryField = Ext.getCmp(me.id + '_storeFilter');
                    if (queryField) {
                        queryField.setValue('');
                    }
                }
            }],
            plugins: [storesRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: storesStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                edit: function (editor, context, eOpts) {
                    storesStore.sync({
                        success: function (batch, options) {
                            storesStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建门店失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新门店失败");
                            }
                            storesStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    storesStore.reload();
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