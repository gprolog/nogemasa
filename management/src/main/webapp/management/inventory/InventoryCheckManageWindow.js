/**
 * Created by liuxh on 15-8-5
 */
Ext.define("InventoryManagementDesktop.InventoryCheckManageWindow", {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var inventoryCheckContentStore = Ext.create('Ext.data.JsonStore', {
            storeId: "inventoryCheckContentStore",
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'store.sid', 'store.name', 'status', 'beginTime', 'createUser.username', 'endTime', 'commitUser.username'],
            pageSize: 20,
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/inventory/check/content/create.json',
                    read: _appctx + '/inventory/check/content/read.json',
                    destroy: _appctx + '/inventory/check/content/destroy.json'
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
        inventoryCheckContentStore.loadPage(1);
        var inventoryCheckContentRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        var inventoryCheckContentGrid = {
            id: me.id + 'MainQuery_Grid',
            xtype: 'grid',
            title: '盘点单',
            flex: 6,
            width: '100%',
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        inventoryCheckContentStore.loadPage(1);
                    }
                }, {
                    xtype: 'button',
                    text: '新增盘点单',
                    handler: function (button, e) {
                        inventoryCheckContentRowEditing.cancelEdit();
                        inventoryCheckContentStore.insert(0, {
                            "createUser.username": username,// username在index.jsp中定义
                            "store.sid": storeSid,// storeSid 在index.jsp中定义
                            "store.name": storeName,// storeName 在index.jsp中定义
                            beginTime: Ext.util.Format.date(new Date(), 'Y-m-d H:i:s'),
                            endTime: '',
                            status: '0'
                        });
                        inventoryCheckContentRowEditing.startEdit(0, 1);
                    }
                }]
            },
            store: inventoryCheckContentStore,
            plugins: [inventoryCheckContentRowEditing],
            columns: [{
                xtype: 'rownumberer',
                text: '序号',
                width: 60
            }, {
                text: '门店id',
                dataIndex: 'store.sid',
                hidden: true,
                hideable: false
            }, {
                text: '编号',
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                text: '门店',
                dataIndex: 'store.name'
            }, {
                text: '订单状态',
                dataIndex: 'status',
                renderer: function (value) {
                    for (var i in nogemasa.management.Dic.orderStatusEnum) {
                        var status = nogemasa.management.Dic.orderStatusEnum[i];
                        if (status.status == value) {
                            return status.name;
                        }
                    }
                    return value;
                },
                editor: {
                    xtype: 'combo',
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'status',
                    editable: false,
                    store: {
                        fields: ['name', 'status'],
                        data: nogemasa.management.Dic.orderStatusEnum
                    }
                }
            }, {
                text: '创建时间',
                dataIndex: 'beginTime',
                width: 150,
                renderer: function (value) {
                    return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d H:i:s');
                },
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                text: '创建人',
                dataIndex: 'createUser.username',
                width: 150
            }, {
                text: '完成时间',
                dataIndex: 'endTime',
                width: 150,
                renderer: function (value) {
                    if (value) {
                        return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d H:i:s');
                    }
                    return '';
                }
            }, {
                text: '提交人',
                dataIndex: 'commitUser.username',
                width: 150
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 150,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑盘点单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        var win = Ext.create('InventoryManagementDesktop.InventoryCheckDetailWindow', {
                            checkContent: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {}, {
                    icon: '../images/icons/fam/delete.gif',
                    tooltip: '删除盘点单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        Ext.Msg.confirm('删除？', '确实要删除该盘点单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        inventoryCheckContentStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除盘点单失败");
                                        }
                                        inventoryCheckContentStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }, {}, {
                    icon: '../images/icons/fam/accept.gif',
                    tooltip: '完成盘点单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        Ext.Msg.confirm('完成？', '确实要完成该盘点单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                Ext.Ajax.request({
                                    url: _appctx + '/inventory/check/content/commit.json',
                                    params: {
                                        sid: rec.data.sid,
                                        storeSid: rec.data["store.sid"]
                                    },
                                    success: function (response) {
                                        inventoryCheckContentStore.loadPage(1);
                                    },
                                    failure: function (e) {
                                        Ext.Msg.alert("操作失败", "完成订单失败");
                                        inventoryCheckContentStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }]
            }],
            listeners: {
                edit: function (editor, context, eOpts) {
                    inventoryCheckContentStore.sync({
                        success: function (batch, options) {
                            inventoryCheckContentStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建盘点单失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新盘点单失败");
                            }
                            inventoryCheckContentStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    inventoryCheckContentStore.reload();
                }
            },
            bbar: {
                xtype: 'pagingtoolbar',
                store: inventoryCheckContentStore,
                displayInfo: true,
                displayMsg: '共{2}条记录，当前显示第{0}至{1}条'
            }
        };
        this.tabs = [{
            xtype: 'panel',
            extend: 'Ext.panel.Panel',
            requires: [],
            closable: false,
            autoScroll: true,
            title: '盘点单信息',
            layout: 'border',
            items: [{
                xtype: 'panel',
                region: 'center',
                layout: 'vbox',
                items: [inventoryCheckContentGrid]
            }]
        }];
    },
    createWindow: function () {
        var me = this;
        var desktop = me.app.getDesktop();
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