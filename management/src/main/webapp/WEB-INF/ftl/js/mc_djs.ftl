/**
 * mc_djs：the dynamic javascript of manage console
 * Created by liuxh on 15-7-13.
 */
Ext.Loader.setPath('Ext.ux.desktop', _appctx + '/desktop');
Ext.Loader.setPath('Ext.ux.window', _appctx + '/js/notification');
Ext.Loader.setPath('SearchDesktop', _appctx + '/admin');
Ext.Loader.setPath('StoreManagementDesktop', _appctx + '/management/store');
Ext.Loader.setPath('GoodsManagementDesktop', _appctx + '/management/goods');
Ext.Loader.setPath('InventoryManagementDesktop', _appctx + '/management/inventory');
Ext.Loader.setPath('OrderManagementDesktop', _appctx + '/management/order');
Ext.Loader.setPath('SaleRecordManagementDesktop', _appctx + '/management/sale');
Ext.Loader.setPath('MemberManagementDesktop', _appctx + '/management/member');
Ext.Loader.setPath('nogemasa.management', 'management');
Ext.Loader.require('nogemasa.management.Dic');

Ext.onReady(function () {
    var searchDesktopApp = Ext.create('SearchDesktop.App', {
        modules: [
        <#if isAdmin>
            Ext.create('SearchDesktop.AuthManageWindow', {
                id: 'auth_manage_win',
                title: '授权管理',
                iconCls: 'auth-shortcut',
                launcher: {
                    text: '授权管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('StoreManagementDesktop.StoreManageWindow', {
                id: 'store_manage_win',
                title: '门店管理',
                iconCls: 'auth-shortcut',
                launcher: {
                    text: '门店管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('GoodsManagementDesktop.GoodsManageWindow', {
                id: 'goods_manage_win',
                title: '商品管理',
                iconCls: 'grid-shortcut',
                launcher: {
                    text: '商品管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('OrderManagementDesktop.PurchaseOrderManageWindow', {
                id: 'purchase_order_manage_win',
                title: '采购订单管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '采购订单管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('OrderManagementDesktop.GodownOrderManageWindow', {
                id: 'godown_order_manage_win',
                title: '入库单单管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '入库单单管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('GoodsManagementDesktop.InventoryManageWindow', {
                id: 'inventory_manage_win',
                title: '库存管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '库存管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('InventoryManagementDesktop.InventoryCheckManageWindow', {
                id: 'inventory_check_manage_win',
                title: '库存盘点管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '库存盘点管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('SaleRecordManagementDesktop.SaleRecordManageWindow', {
                id: 'sale_record_manage_win',
                title: '销售记录管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '销售记录管理',
                    iconCls: 'tabs'
                }
            }),
            Ext.create('MemberManagementDesktop.MemberManageWindow', {
                id: 'member_manage_win',
                title: '会员管理',
                iconCls: 'notepad-shortcut',
                launcher: {
                    text: '会员管理',
                    iconCls: 'tabs'
                }
            })
        </#if>
        ]
    });
    if (searchDesktopApp.modules.length == 0) {
        Ext.Msg.alert('无权限', '对不起，您没有此系统的任何使用权限，请与信息系统部搜索组联系。');
    }
});