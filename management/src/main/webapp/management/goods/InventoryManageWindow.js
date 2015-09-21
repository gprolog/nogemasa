/**
 * Created by liuxh on 15-8-2
 */
Ext.define('GoodsManagementDesktop.InventoryManageWindow', {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var inventoryStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            clearOnPageLoad: true,
            fields: ['sid', 'store.name', 'goods.name', 'goods.goodsSize', 'goods.color', 'goods.fabrics', 'goods.style', 'goods.sn', 'count'],
            pageSize: 20,
            storeId: 'goodsStore',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/inventory/read.json'
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
        inventoryStore.loadPage(1);
        this.tabs = [{
            xtype: 'gridpanel',
            closable: false,
            id: me.id + '_inventoryTab',
            title: '商品管理',
            header: false,
            columns: [{
                dataIndex: 'store.name',
                text: '门店名',
                flex: 1
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
                dataIndex: 'count',
                text: '库存数',
                flex: 1
            }],
            store: inventoryStore,
            columnLines: true,
            bbar: {
                xtype: 'pagingtoolbar',
                store: inventoryStore,
                displayInfo: true
            },
            sortableColumns: false
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