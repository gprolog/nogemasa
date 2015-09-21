/**
 * Created by liuxh on 15-8-5
 */
Ext.define("SaleRecordManagementDesktop.SaleRecordManageWindow", {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var saleRecordToolBar = {
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
                    id: 'saleTimeFromId',
                    fieldLabel: '开始时间',
                    name: 'saleTimeFrom',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, -7)
                }, {
                    id: 'saleTimeToId',
                    fieldLabel: '至',
                    name: 'saleTimeTo',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
                }]
            }]
        };
        var saleRecordContentStore = Ext.create('Ext.data.JsonStore', {
            storeId: "saleRecordContentStore",
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'store', 'employee', 'member', 'promotion', 'saleTime', 'costType', 'totalPrice', 'totalCost'],
            pageSize: 20,
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/sale/content/read.json'
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
        saleRecordContentStore.loadPage(1);
        var saleRecordContentGrid = {
            id: me.id + 'MainQuery_SaleRecordContent_Grid',
            xtype: 'grid',
            title: '销售单列表',
            flex: 6,
            width: '100%',
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        saleRecordContentStore.loadPage(1);
                    }
                }]
            },
            store: saleRecordContentStore,
            columns: [{
                xtype: 'rownumberer',
                text: '序号',
                width: 60
            }, {
                text: '销售单编号',
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                text: '门店id',
                dataIndex: 'store.sid',
                hidden: true,
                hideable: false,
                renderer: function (value) {
                    if (value) {
                        return value.sid;
                    }
                    return '';
                }
            }, {
                text: '门店',
                dataIndex: 'store',
                renderer: function (value) {
                    if (value) {
                        return value.name;
                    }
                    return '';
                }
            }, {
                text: '导购id',
                dataIndex: 'employee',
                hidden: true,
                hideable: false,
                renderer: function (value) {
                    if (value) {
                        return value.sid;
                    }
                    return '';
                }
            }, {
                text: '导购',
                dataIndex: 'employee',
                renderer: function (value) {
                    if (value) {
                        return value.name;
                    }
                    return '';
                }
            }, {
                text: '会员卡号',
                dataIndex: 'member',
                renderer: function (value) {
                    if (value) {
                        return value.card_no;
                    }
                    return '';
                }
            }, {
                text: '活动id',
                dataIndex: 'promotion',
                hidden: true,
                hideable: false,
                renderer: function (value) {
                    if (value) {
                        return value.sid;
                    }
                    return '';
                }
            }, {
                text: '活动方法',
                dataIndex: 'promotion',
                renderer: function (value) {
                    if (value) {
                        return value.countMethod;
                    }
                    return '';
                }
            }, {
                text: '销售时间时间',
                dataIndex: 'saleTime',
                width: 150,
                renderer: function (value) {
                    return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d H:i:s');
                }
            }, {
                text: '收银方式',
                dataIndex: 'costType',
                renderer: function (value) {
                    for (var i in nogemasa.management.Dic.costType) {
                        var costType = nogemasa.management.Dic.costType[i];
                        if (costType.code == value) {
                            return costType.text;
                        }
                    }
                    return value;
                }
            }, {
                text: '商品总原价',
                dataIndex: 'totalPrice',
                width: 150
            }, {
                text: '商品总售价',
                dataIndex: 'totalCost',
                width: 150
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 150,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '查看销售单明细',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('SaleRecordManagementDesktop.SaleRecordDetailWindow', {
                            content: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }]
            }],
            bbar: {
                xtype: 'pagingtoolbar',
                store: saleRecordContentStore,
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
            title: '销售单列表',
            layout: 'border',
            items: [saleRecordToolBar, {
                xtype: 'panel',
                region: 'center',
                layout: 'vbox',
                items: [saleRecordContentGrid]
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