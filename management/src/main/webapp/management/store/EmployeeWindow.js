/**
 * Created by liuxh on 15-7-7.
 */
Ext.define('StoreManagementDesktop.EmployeeWindow', {
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel', 'Ext.data.JsonStore'],
    gridpanel: null,
    constructor: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.initComponents();
        var me = this;
        this.superclass.constructor.call(this, {
            title: me.stores.name + '的员工',
            items: [me.gridpanel],
            layout: "fit",
            modal: true,
            width: 700,
            height: 440,
            bbar: [{
                xtype: 'button',
                text: '修改',
                handler: function () {
                    me.save();
                }
            }, {
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
        var employeeStore = Ext.create('Ext.data.JsonStore', {
            autoLoad: false,
            fields: ['sid', 'store.sid', 'name', 'entryTime', 'identityNo', 'phone', 'qq'],
            pageSize: 10000,
            storeId: me.stores.sid + '_Store',
            proxy: {
                type: 'ajax',
                api: {
                    read: _appctx + '/management/employee/list/' + me.stores.sid + '.json',
                    create: _appctx + '/management/employee/create.json',
                    update: _appctx + '/management/employee/update.json'
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
        employeeStore.loadPage(1);
        var employeeRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        this.gridpanel = Ext.create('Ext.grid.Panel', {
            id: me.stores.sid + '_Panel',
            store: employeeStore,
            columnLines: true,
            tbar: [{
                xtype: 'button',
                text: '新增门店',
                handler: function (button, e) {
                    employeeRowEditing.cancelEdit();
                    employeeStore.insert(0, {
                        'store.sid': me.stores.sid,
                        name: '新员工',
                        entryTime: Ext.util.Format.date(new Date(), 'Y-m-d')
                    });
                    employeeRowEditing.startEdit(0, 1);
                    var queryField = Ext.getCmp(me.id + '_storeFilter');
                    if (queryField) {
                        queryField.setValue('');
                    }
                }
            }],
            columns: [{
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'store.sid',
                hidden: true,
                hideable: false
            }, {
                dataIndex: 'name',
                text: '姓名',
                flex: 2,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'entryTime',
                text: '入职时间',
                width: 150,
                renderer: function (value) {
                    return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d');
                },
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'identityNo',
                text: '身份证号',
                width: 200,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'phone',
                text: '手机',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }, {
                dataIndex: 'qq',
                text: 'QQ',
                flex: 1,
                editor: {
                    xtype: 'textfield',
                    allowBlank: true
                }
            }],
            plugins: [employeeRowEditing],
            bbar: {
                xtype: 'pagingtoolbar',
                store: employeeStore,
                displayInfo: true
            },
            sortableColumns: false,
            listeners: {
                edit: function (editor, context, eOpts) {
                    employeeStore.sync({
                        success: function (batch, options) {
                            employeeStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建门店失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新门店失败");
                            }
                            employeeStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    employeeStore.reload();
                }
            }
        });
    },
    save: function () {
        var me = this;
        this.gridpanel.getStore().sync({
            success: function (batch, options) {
                me.close();
            },
            failure: function (batch, options) {
                Ext.Msg.alert("操作失败", "更新组权限失败");
            }
        });
    },
    cancel: function () {
        this.close();
    }
});
