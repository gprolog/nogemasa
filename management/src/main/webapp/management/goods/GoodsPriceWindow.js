/**
 * Created by liuxh on 15-8-7.
 */
var storeName = storeName || "";
Ext.define('GoodsManagementDesktop.GoodsPriceWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: me.goods.name + '价格表',
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
        var goodsPriceStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: ['sid', 'store.sid', 'store.name', 'price'],
            pageSize: 10000,
            storeId: 'goodsPriceStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/price/read/' + me.goods.sid + '.json',
                    create: _appctx + '/management/price/create/' + me.goods.sid + '.json',
                    update: _appctx + '/management/price/update/' + me.goods.sid + '.json'
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
        goodsPriceStore.loadPage(1);
        var goodsPriceRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true
        });
        var storeStore = Ext.create('Ext.data.JsonStore', {
            storeId: 'storeStore',
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'name', 'address', 'phone', 'shopowner'],
            pageSize: 1000,
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/store/read.json?start=0&limit=1000'
                },
                idParam: 'sid',
                reader: {
                    type: 'json',
                    root: 'list'
                }
            }
        });
        storeStore.load(1);
        this.gridpanel = {
            id: 'goodsPricePanel',
            xtype: 'grid',
            store: goodsPriceStore,
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        goodsPriceStore.loadPage(1);
                    }
                }, {
                    xtype: 'button',
                    text: '新增',
                    handler: function (button, e) {
                        goodsPriceRowEditing.cancelEdit();
                        goodsPriceStore.insert(0, {
                            'store.name': '请选择',
                            price: '0'
                        });
                        goodsPriceRowEditing.startEdit(0, 1);
                    }
                }]
            },
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'store.sid',
                hidden: true,
                hideable: false
            }, {
                text: '门店名称',
                dataIndex: 'store.name',
                width: 150,
                editor: {
                    xtype: 'combo',
                    store: storeStore,
                    queryMode: 'local',
                    valueField: 'sid',
                    displayField: 'name',
                    triggerAction: 'all',
                    allowBlank: false,
                    editable: false,
                    model: 'local',
                    listeners: {
                        select: function (com, records, options) {
                            console.log(com, records, options);
                            console.log(goodsPriceRowEditing.getCmp());
                        }
                    }
                }
            }, {
                text: '价格',
                dataIndex: 'price',
                width: 150,
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }],
            plugins: [goodsPriceRowEditing],
            columnLines: true,
            sortableColumns: true,
            overflowY: 'scroll',
            listeners: {
                edit: function (editor, context, eOpts) {
                    goodsPriceStore.sync({
                        success: function (batch, options) {
                            goodsPriceStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建价格失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新价格失败");
                            }
                            goodsPriceStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    goodsPriceStore.reload();
                }
            }
        };
    },
    cancel: function () {
        this.close();
    }
});
