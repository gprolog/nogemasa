/**
 * Created by liuxh on 15-8-7.
 */
Ext.define('SaleRecordManagementDesktop.SaleRecordDetailWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: '销售单明细',
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
        var saleRecordDetailStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: ['sid', 'content.sid', 'goods.name', 'goods.sn', 'goodsPrice', 'goodsCost'],
            pageSize: 10000,
            storeId: 'saleRecordDetailStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/sale/detail/list/' + me.content.sid + '.json'
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
        saleRecordDetailStore.loadPage(1);
        this.gridpanel = {
            id: 'saleRecordDetailPanel',
            xtype: 'grid',
            store: saleRecordDetailStore,
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        saleRecordDetailStore.loadPage(1);
                    }
                }]
            },
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'content.sid',
                hidden: true,
                hideable: false
            }, {
                text: '商品名称',
                dataIndex: 'goods.name',
                width: 150
            }, {
                text: '商品条形码',
                dataIndex: 'goods.sn',
                width: 150
            }, {
                text: '商品原价',
                dataIndex: 'goodsPrice',
                width: 150
            }, {
                text: '商品售价',
                dataIndex: 'goodsCost',
                width: 150,
                renderer: function (value) {
                    return (Number(value)).toFixed(2);
                }
            }],
            columnLines: true,
            sortableColumns: true,
            overflowY: 'scroll'
        };
    },
    cancel: function () {
        this.close();
    }
});
