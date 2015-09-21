/**
 * Created by liuxh on 15-8-7.
 */
Ext.define('GoodsManagementDesktop.InventoryUpdateWindow', {
    extend: 'Ext.window.Window',
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: me.inventory["goods.name"] + '库存修改',
            items: [this.inventoryForm],
            layout: "fit",
            modal: true,
            width: 400,
            height: 200
        });
    },
    initComponents: function () {
        var me = this;
        var form = new Ext.FormPanel({
            bodyStyle: 'padding:5px 5px 0',
            layout: 'form',
            items: [{
                xtype: 'textfield',
                name: 'goodsName',
                id: 'goodsName',
                anchor: '100%',
                fieldLabel: '商品名称',
                value: me.inventory["goods.name"]
            }, {
                xtype: 'textfield',
                name: 'goodsName',
                id: 'goodsName',
                anchor: '100%',
                fieldLabel: '商品名称',
                value: me.inventory["goods.name"]
            }, {
                xtype: 'numberfield',
                anchor: '100%',
                name: 'count',
                id: 'count',
                fieldLabel: '库存',
                value: me.inventory["count"],
                minValue: 0
            }],
            buttonAlign: 'center',
            buttons: [{
                text: '保存',
                icon: '../images/icons/fam/accept.png',
                handler: function () {
                    var count = form.getForm().findField('count').getValue();
                    var goodsName = form.getForm().findField('goodsName').getValue();
                }
            }, {
                text: '关闭',
                icon: '../images/icons/fam/cross.gif',
                handler: function () {
                    me.close(this);
                }
            }
            ]
        });
        this.inventoryForm = form;
    },
    save: function () {
        var me = this;
        console.log(me);
        var form = me.inventoryForm.getForm().getEl().dom;
        console.log(form);
        form.getForm().submit({
            url: '/management/inventory/updateGoodsInventory/' + me.inventory["goods.sid"] + '/' + me.inventory["goods.sn"] + '.json',
            //method:'POST',
            waitTitle: "提示",
            waitMsg: 'Submitting your data',
            success: function (form, action) {
                alert("库存修改成功");
                this.close();
            },
            failure: function (form, action) {
                alert("库存修改失败");
            }
        });
    }
});
