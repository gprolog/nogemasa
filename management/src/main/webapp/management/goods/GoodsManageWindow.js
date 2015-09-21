/**
 * Created by liuxh on 15-8-2
 */
Ext.define('GoodsManagementDesktop.GoodsManageWindow', {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var goodsStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: ['sid', 'name', 'goodsSize', 'color', 'fabrics', 'style', 'sn', 'putInStatus'],
            pageSize: 20,
            storeId: 'goodsStore',
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/management/goods/create.json',
                    read: _appctx + '/management/goods/read.json',
                    update: _appctx + '/management/goods/update.json'
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
                    var queryField = Ext.getCmp(me.id + '_goodsFilter');
                    if (queryField) {
                        store.getProxy().setExtraParam('query', queryField.getValue());
                    }
                }
            }
        });
        goodsStore.loadPage(1);
        var goodsRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        this.tabs = [{
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_goodsTab',
            title: '商品管理',
            header: false,
            columns: [{
                dataIndex: 'name',
                text: '商品名',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'goodsSize',
                text: '尺寸',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'color',
                text: '颜色',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'fabrics',
                text: '材质',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'style',
                text: '款型',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'sn',
                text: '条形码',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'putInStatus',
                text: '状态',
                width: 60,
                renderer: function (value, p, record) {
                    if (value == '2') {
                        return "<span style='color:green;font-weight:bold;'>准入</span>";
                    } else {
                        return "<span style='color:red;font-weight:bold;'>禁入</span>";
                    }
                },
                menuDisabled: true
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 80,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑商品价格',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var win = Ext.create('GoodsManagementDesktop.GoodsPriceWindow', {
                            goods: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {}, {
                    tooltip: '禁入/准入',
                    getClass: function (v, metaData, record) {
                        if (record.get("putInStatus") == '1') {
                            return "accept_goods";
                        } else {
                            return "cross_goods"
                        }
                    },
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var sid = rec.get("sid");
                        var putInStatus = rec.get("putInStatus");
                        var str = '';
                        if(putInStatus == '1') {
                            str = '准入'
                        } else {
                            str = '禁入'
                        }
                        Ext.Msg.confirm('修改？', '确定' + str + '商品吗?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                Ext.Ajax.request({
                                    url: _appctx + '/management/goods/updateGoodsPutInStatus.json',
                                    params: {
                                        "sid": sid,
                                        "putInStatus": putInStatus
                                    },
                                    success: function (response) {
                                        goodsStore.loadPage(1);
                                    },
                                    failure: function (e) {
                                        Ext.Msg.alert("操作失败", "禁入/准入操作失败");
                                        goodsStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }],
                hideable: false
            }],
            store: goodsStore,
            columnLines: true,
            tbar: [{
                xtype: 'textfield',
                fieldLabel: '快速查找:',
                id: me.id + '_goodsFilter',
                labelWidth: 60,
                labelAlign: 'right'
            }, {
                xtype: 'button',
                text: '查找',
                handler: function () {
                    goodsStore.loadPage(1);
                }
            }, '-', {
                xtype: 'button',
                text: '新增商品',
                handler: function (button, e) {
                    goodsRowEditing.cancelEdit();
                    goodsStore.insert(0, {
                        name: '新商品'
                    });
                    goodsRowEditing.startEdit(0, 1);
                    var queryField = Ext.getCmp(me.id + '_goodsFilter');
                    if (queryField) {
                        queryField.setValue('');
                    }
                }
            }, '-', {
                xtype: 'button',
                text: '一键禁入',
                handler: function (button, e) {
                    Ext.Msg.confirm('一键禁入？', '确实要一键禁入全部商品吗?', function (buttonId) {
                        if (buttonId == 'ok' || buttonId == 'yes') {
                            Ext.Ajax.request({
                                url: _appctx + '/management/goods/resetAllPutInStatus.json',
                                success: function (response) {
                                    goodsStore.loadPage(1);
                                },
                                failure: function (e) {
                                    Ext.Msg.alert("操作失败", "一键禁入失败");
                                    goodsStore.loadPage(1);
                                }
                            });
                        }
                    });
                }
            }],
            plugins: [goodsRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: goodsStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                edit: function (editor, context, eOpts) {
                    goodsStore.sync({
                        success: function (batch, options) {
                            goodsStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建商品失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新商品失败");
                            }
                            goodsStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    goodsStore.reload();
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