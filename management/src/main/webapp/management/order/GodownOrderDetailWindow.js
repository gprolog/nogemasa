/**
 * Created by liuxh on 15-8-7.
 */
var storeName = storeName || "";
Ext.define('OrderManagementDesktop.GodownOrderDetailWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: storeName + '入库单详细列表',
            items: [me.gridpanel],
            layout: "fit",
            modal: true,
            width: 700,
            height: 440,
            bbar: [{
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
        var godownOrderListStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: ['sid', 'goods.sid', 'goods.name', 'count', 'costPrice'],
            pageSize: 10000,
            storeId: 'godownOrderListStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/order/godown/list/read/' + me.godownOrder.sid + '.json',
                    create: _appctx + '/order/godown/list/create.json?godownOrderSid=' + me.godownOrder.sid,
                    update: _appctx + '/order/godown/list/update.json'
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
        godownOrderListStore.loadPage(1);
        var godownOrderListRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true
        });
        var goodsStore = Ext.create('Ext.data.JsonStore', {
            storeId: 'goodsStore',
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'name', 'goodsSize', 'color', 'fabrics', 'style', 'sn'],
            pageSize: 1000,
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/goods/listPutInGoods.json'
                },
                idParam: 'sid',
                reader: {
                    type: 'json',
                    root: 'list'
                }
            }
        });
        goodsStore.load(1);
        this.gridpanel = {
            id: 'godownOrderListPanel',
            xtype: 'grid',
            store: godownOrderListStore,
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        godownOrderListStore.loadPage(1);
                    }
                }, {
                    xtype: 'button',
                    text: '新增',
                    handler: function (button, e) {
                        godownOrderListRowEditing.cancelEdit();
                        godownOrderListStore.insert(0, {
                            'goods.name': '请选择',
                            count: '0',
                            costPrice: '0'
                        });
                        godownOrderListRowEditing.startEdit(0, 1);
                    }
                }]
            },
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'goods.sid',
                hidden: true,
                hideable: false
            }, {
                text: '商品名称',
                dataIndex: 'goods.name',
                width: 150,
                editor: {
                    xtype: 'combo',
                    store: goodsStore,
                    queryMode: 'local',
                    valueField: 'sid',
                    displayField: 'name',
                    triggerAction: 'all',
                    allowBlank: false,
                    editable: false,
                    model: 'local'
                }
            }, {
                text: '数量',
                dataIndex: 'count',
                width: 150,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                text: '成本价',
                dataIndex: 'costPrice',
                width: 150,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 80,
                items: [{
                    tooltip: '删除采购',
                    icon: '../images/icons/fam/delete.gif',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var sid = rec.get("sid");
                        Ext.Msg.confirm('删除？', '确实要删除采购吗?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        godownOrderListStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.create) {
                                            Ext.Msg.alert("操作失败", "创建商品失败");
                                        } else if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除商品失败");
                                        }
                                        godownOrderListStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }],
                hideable: false
            }],
            plugins: [godownOrderListRowEditing],
            columnLines: true,
            sortableColumns: true,
            overflowY: 'scroll',
            listeners: {
                edit: function (editor, context, eOpts) {
                    godownOrderListStore.sync({
                        success: function (batch, options) {
                            godownOrderListStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建商品失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新商品失败");
                            }
                            godownOrderListStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    godownOrderListStore.reload();
                }
            }
        };
    },
    cancel: function () {
        this.close();
    }
});
