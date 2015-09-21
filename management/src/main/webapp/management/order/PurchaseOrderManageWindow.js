/**
 * Created by liuxh on 15-8-5
 */
Ext.define("OrderManagementDesktop.PurchaseOrderManageWindow", {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var purchaseOrderToolBar = {
            xtype: 'toolbar',
            region: 'north',
            items: [{
                xtype: 'buttongroup',
                columns: 3,
                defaultType: 'textfield',
                defaults: {
                    labelAlign: 'right',
                    labelWidth: 60
                },
                items: [{
                    id: 'orderBeginTimeFromId',
                    fieldLabel: '开始时间',
                    name: 'orderBeginTimeFrom',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, -7)
                }, {
                    id: 'orderBeginTimeToId',
                    fieldLabel: '至',
                    name: 'orderBeginTimeTo',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
                }, {
                    id: 'orderStatusId',
                    fieldLabel: '订单状态',
                    name: 'orderStatus',
                    xtype: 'combo',
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'status',
                    editable: false,
                    store: {
                        fields: ['name', 'status'],
                        data: nogemasa.management.Dic.orderStatusEnum
                    }
                }]
            }]
        };
        var purchaseOrderStore = Ext.create('Ext.data.JsonStore', {
            storeId: "purchaseOrderStore",
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'store.name', 'status', 'beginTime', 'createUser.username', 'endTime', 'commitUser.username'],
            pageSize: 20,
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/order/purchase/create.json',
                    read: _appctx + '/order/purchase/read.json',
                    update: _appctx + '/order/purchase/update.json',
                    destroy: _appctx + '/order/purchase/destroy.json'
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
        purchaseOrderStore.loadPage(1);
        var purchaseOrderRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        var purchaseOrderGrid = {
            id: me.id + 'OrderMainQuery_Order_OrderGrid',
            xtype: 'grid',
            title: '采购订单',
            flex: 6,
            width: '100%',
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        purchaseOrderStore.loadPage(1);
                    }
                }, {
                    xtype: 'button',
                    text: '导出当前页Excel',
                    type: 'excel',
                    handler: function () {
                        alert("添加导出Excel功能");
                        grid2Excel(Ext.getCmp(me.id + 'OrderMainQuery_Order_OrderGrid'), '采购订单查询');
                    }
                }, {
                    xtype: 'button',
                    text: '新增采购订单',
                    handler: function (button, e) {
                        purchaseOrderRowEditing.cancelEdit();
                        purchaseOrderStore.insert(0, {
                            "createUser.username" : username,// username在index.jsp中定义
                            "store.id": storeSid,// storeSid 在index.jsp中定义
                            "store.name": storeName,// storeName 在index.jsp中定义
                            beginTime: Ext.util.Format.date(new Date(), 'Y-m-d H:i:s'),
                            endTime: '',
                            status: '0'
                        });
                        purchaseOrderRowEditing.startEdit(0, 1);
                    }
                }]
            },
            store: purchaseOrderStore,
            plugins: [purchaseOrderRowEditing],
            columns: [{
                xtype: 'rownumberer',
                text: '序号',
                width: 60
            }, {
                text: '门店id',
                dataIndex: 'store.id',
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
                    if(value) {
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
                width: 50,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑采购订单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('OrderManagementDesktop.PurchaseOrderDetailWindow', {
                            purchaseOrder: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {
                    icon: '../images/icons/fam/delete.gif',
                    tooltip: '删除采购订单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        Ext.Msg.confirm('删除？', '确实要删除该采购订单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        purchaseOrderStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除采购订单失败");
                                        }
                                        purchaseOrderStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }]
            }],
            listeners: {
                edit: function (editor, context, eOpts) {
                    purchaseOrderStore.sync({
                        success: function (batch, options) {
                            purchaseOrderStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建采购订单失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新采购订单失败");
                            }
                            purchaseOrderStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    purchaseOrderStore.reload();
                }
            },
            bbar: {
                xtype: 'pagingtoolbar',
                store: purchaseOrderStore,
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
            title: '采购订单信息',
            layout: 'border',
            items: [purchaseOrderToolBar, {
                xtype: 'panel',
                region: 'center',
                layout: 'vbox',
                items: [purchaseOrderGrid]
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