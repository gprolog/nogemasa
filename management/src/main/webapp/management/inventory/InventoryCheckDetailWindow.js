/**
 * Created by liuxh on 15-8-7.
 */
var storeName = storeName || "";
Ext.define('InventoryManagementDesktop.InventoryCheckDetailWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: storeName + '盘点单详细列表',
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
        var inventoryCheckDetailStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: ['sid', 'goods.sid', 'goods.name', 'goods.goodsSize', 'goods.color', 'goods.fabrics', 'goods.style', 'goods.sn', 'preCount', 'postCount'],
            pageSize: 10000,
            storeId: 'inventoryCheckDetailStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/inventory/check/detail/read/' + me.checkContent.sid + '.json',
                    update: _appctx + '/inventory/check/detail/create.json?contentSid=' + me.checkContent.sid
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
        inventoryCheckDetailStore.loadPage(1);
        var inventoryCheckDetailRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true
        });
        this.gridpanel = {
            id: 'inventoryCheckDetailPanel',
            xtype: 'grid',
            store: inventoryCheckDetailStore,
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        inventoryCheckDetailStore.loadPage(1);
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
                dataIndex: 'goods.name',
                text: '商品名',
                flex: 1
            }, {
                dataIndex: 'goods.goodsSize',
                text: '大小',
                flex: 1
            }, {
                dataIndex: 'goods.color',
                text: '颜色',
                flex: 1
            }, {
                dataIndex: 'goods.fabrics',
                text: '材质',
                flex: 1
            }, {
                dataIndex: 'goods.style',
                text: '款式',
                flex: 1
            }, {
                dataIndex: 'goods.sn',
                text: '条形码',
                flex: 1
            }, {
                text: '盘点前数量',
                dataIndex: 'preCount',
                width: 150
            }, {
                text: '盘点后数量',
                dataIndex: 'postCount',
                width: 150,
                editor: {
                    xtype: 'numberfield',
                    allowBlank: false,
                    minValue: -1
                }
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 80,
                items: [{
                    tooltip: '删除盘点单',
                    icon: '../images/icons/fam/delete.gif',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var sid = rec.get("sid");
                        if (sid) {
                            Ext.Msg.confirm('删除操作？', '确实要删除吗?', function (buttonId) {
                                if (buttonId == 'ok' || buttonId == 'yes') {
                                    Ext.Ajax.request({
                                        url: _appctx + '/inventory/check/detail/delete.json',
                                        params: {
                                            "sid": sid
                                        },
                                        success: function (response) {
                                            inventoryCheckDetailStore.loadPage(1);
                                        },
                                        failure: function (e) {
                                            Ext.Msg.alert("操作失败", "删除失败");
                                            inventoryCheckDetailStore.loadPage(1);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }],
                hideable: false
            }],
            plugins: [inventoryCheckDetailRowEditing],
            columnLines: true,
            sortableColumns: true,
            overflowY: 'scroll',
            listeners: {
                edit: function (editor, context, eOpts) {
                    inventoryCheckDetailStore.sync({
                        success: function (batch, options) {
                            inventoryCheckDetailStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新失败");
                            }
                            inventoryCheckDetailStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    inventoryCheckDetailStore.reload();
                }
            }
        };
    },
    cancel: function () {
        this.close();
    }
});
