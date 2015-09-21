/**
 * Created by liuxh on 15-8-5
 */
Ext.define("OrderManagementDesktop.GodownOrderManageWindow", {
    extend: 'Ext.ux.desktop.Module',
    requires: ['Ext.tab.Panel', 'Ext.data.JsonStore'],
    id: null,
    title: null,
    init: function () {
        var me = this;
        var godownOrderToolBar = {
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
                    id: 'orderBeginTimeFromId',
                    fieldLabel: '开始时间',
                    name: 'orderBeginTimeFrom',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, -7)
                }, {
                    id: 'orderBeginTimeToId',
                    fieldLabel: '至',
                    name: 'orderBeginTimeTo',
                    xtype: 'datefield',
                    submitFormat: 'Y-m-d g:i:s',
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, 1)
                }]
            }]
        };
        var godownOrderStore = Ext.create('Ext.data.JsonStore', {
            storeId: "godownOrderStore",
            autoLoad: true,
            clearOnPageLoad: true,
            fields: ['sid', 'store.sid', 'store.name', 'status', 'beginTime', 'createUser.username', 'endTime', 'commitUser.username'],
            pageSize: 20,
            proxy: {
                type: 'ajax',
                api: {
                    create: _appctx + '/order/godown/create.json',
                    read: _appctx + '/order/godown/read.json',
                    destroy: _appctx + '/order/godown/destroy.json'
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
        godownOrderStore.loadPage(1);
        var godownOrderRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToEdit: 2,
            autoCancel: true,
            editing: false
        });
        var godownOrderGrid = {
            id: me.id + 'OrderMainQuery_Order_OrderGrid',
            xtype: 'grid',
            title: '入库单',
            flex: 6,
            width: '100%',
            tbar: {
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: '查询',
                    handler: function () {
                        godownOrderStore.loadPage(1);
                    }
                }, {
                    xtype: 'button',
                    text: '新增入库单',
                    handler: function (button, e) {
                        godownOrderRowEditing.cancelEdit();
                        godownOrderStore.insert(0, {
                            "createUser.username": username,// username在index.jsp中定义
                            "store.sid": storeSid,// storeSid 在index.jsp中定义
                            "store.name": storeName,// storeName 在index.jsp中定义
                            beginTime: Ext.util.Format.date(new Date(), 'Y-m-d H:i:s'),
                            endTime: '',
                            status: '0'
                        });
                        godownOrderRowEditing.startEdit(0, 1);
                    }
                }]
            },
            store: godownOrderStore,
            plugins: [godownOrderRowEditing],
            columns: [{
                xtype: 'rownumberer',
                text: '序号',
                width: 60
            }, {
                text: '门店id',
                dataIndex: 'store.sid',
                hidden: true,
                hideable: false
            }, {
                text: '编号',
                dataIndex: 'sid',
                hidden: true,
                hideable: false
            }, {
                text: '门店',
                dataIndex: 'store.name'
            }, {
                text: '订单状态',
                dataIndex: 'status',
                renderer: function (value) {
                    for (var i in nogemasa.management.Dic.orderStatusEnum) {
                        var status = nogemasa.management.Dic.orderStatusEnum[i];
                        if (status.status == value) {
                            return status.name;
                        }
                    }
                    return value;
                },
                editor: {
                    xtype: 'combo',
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'status',
                    editable: false,
                    store: {
                        fields: ['name', 'status'],
                        data: nogemasa.management.Dic.orderStatusEnum
                    }
                }
            }, {
                text: '创建时间',
                dataIndex: 'beginTime',
                width: 150,
                renderer: function (value) {
                    return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d H:i:s');
                },
                editor: {
                    xtype: 'textfield',
                    allowBlank: false
                }
            }, {
                text: '创建人',
                dataIndex: 'createUser.username',
                width: 150
            }, {
                text: '完成时间',
                dataIndex: 'endTime',
                width: 150,
                renderer: function (value) {
                    if (value) {
                        return Ext.util.Format.date(new Date(parseInt(value)), 'Y-m-d H:i:s');
                    }
                    return '';
                }
            }, {
                text: '提交人',
                dataIndex: 'commitUser.username',
                width: 150
            }, {
                xtype: 'actioncolumn',
                text: '操作',
                width: 150,
                hideable: false,
                items: [{
                    icon: '../images/icons/fam/cog_edit.png',
                    tooltip: '编辑入库单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        var win = Ext.create('OrderManagementDesktop.GodownOrderDetailWindow', {
                            godownOrder: rec.data
                        });
                        Ext.getCmp(me.id).add(win);
                        win.show();
                    }
                }, {}, {
                    icon: '../images/icons/fam/delete.gif',
                    tooltip: '删除入库单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        Ext.Msg.confirm('删除？', '确实要删除该入库单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                grid.getStore().remove(rec);
                                grid.getStore().sync({
                                    success: function (batch, options) {
                                        godownOrderStore.loadPage(1);
                                    },
                                    failure: function (batch, options) {
                                        if (options.operations.destroy) {
                                            Ext.Msg.alert("操作失败", "删除入库单失败");
                                        }
                                        godownOrderStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }, {}, {
                    icon: '../images/icons/fam/accept.gif',
                    tooltip: '完成入库单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        if (rec.data.status === '2') {
                            return;
                        }
                        Ext.Msg.confirm('完成？', '确实要完成该入库单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                Ext.Ajax.request({
                                    url: _appctx + '/order/godown/commit.json',
                                    params: {
                                        sid: rec.data.sid,
                                        storeSid: rec.data["store.sid"]
                                    },
                                    success: function (response) {
                                        godownOrderStore.loadPage(1);
                                    },
                                    failure: function (e) {
                                        Ext.Msg.alert("操作失败", "完成订单失败");
                                        godownOrderStore.loadPage(1);
                                    }
                                });
                            }
                        });
                    }
                }, {}, {
                    icon: '../images/icons/fam/add.png',
                    tooltip: '导入入库单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        var add_winForm = Ext.create('Ext.form.Panel', {
                            contentType: 'multipart/form-data',
                            frame: true,
                            width: 340,
                            bodyPadding: 5,
                            fieldDefaults: {
                                labelAlign: 'left',
                                labelWidth: 90,
                                anchor: '100%'
                            },
                            items: [{
                                xtype: 'hiddenfield',// 隐藏的文本框
                                name: 'hiddenfield1',
                                value: '隐藏的文本框'
                            }, {
                                xtype: 'filefield',// 文件上传
                                name: 'file',
                                fieldLabel: '上传',
                                labelWidth: 50,
                                msgTarget: 'side',
                                allowBlank: false,
                                anchor: '100%',
                                buttonText: '选择文件',
                                validator: function (value) {
                                    var arr = value.split('.');
                                    if (arr[arr.length - 1] != 'xls') {
                                        return '文件不合法！！！';
                                    } else {
                                        return true;
                                    }
                                }
                            }]
                        });

                        //创建window面板，表单面板是依托window面板显示的
                        var syswin = Ext.create('Ext.window.Window', {
                            title: "导入入库单",
                            width: 350,
                            //height : 120,
                            //plain : true,
                            iconCls: "addicon",
                            // 不可以随意改变大小
                            resizable: false,
                            // 是否可以拖动
                            // draggable:false,
                            collapsible: true, // 允许缩放条
                            closeAction: 'close',
                            closable: true,
                            // 弹出模态窗体
                            modal: 'true',
                            buttonAlign: "center",
                            bodyStyle: "padding:0 0 0 0",
                            items: [add_winForm],
                            buttons: [{
                                text: "保存",
                                minWidth: 70,
                                handler: function () {
                                    if (add_winForm.getForm().isValid()) {
                                        add_winForm.getForm().submit({
                                            url: _appctx + '/order/godown/upload.json',
                                            //等待时显示 等待
                                            waitTitle: '请稍等...',
                                            waitMsg: '正在提交信息...',
                                            params: {
                                                t: "add"
                                            },
                                            success: function (fp, o) {
                                                console.log(fp);
                                                console.log(o);
                                                // success函数，成功提交后，根据返回信息判断情况
                                                if (o.success == true) {
                                                    Ext.MessageBox.alert("信息提示", "保存成功!");
                                                    syswin.close(); //关闭窗口
                                                    // Store1.reload();
                                                } else {
                                                    Ext.Msg.alert('信息提示', '添加时出现异常！');
                                                }
                                            },
                                            failure: function (e) {
                                                console.log(e);
                                                syswin.close(); //关闭窗口
                                            }
                                        });
                                    }
                                }
                            }, {
                                text: "关闭",
                                minWidth: 70,
                                handler: function () {
                                    syswin.close();
                                }
                            }]
                        });
                        syswin.show();
                    }
                }, {}, {
                    icon: '../images/icons/fam/application_go.png',
                    tooltip: '导出入库单',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        Ext.Msg.confirm('完成？', '确实要导出入库单?', function (buttonId) {
                            if (buttonId == 'ok' || buttonId == 'yes') {
                                var win = window.open(_appctx + '/order/godown/export.xls?godownSid=' + rec.data.sid);
                                if (win == null) {
                                    alert('新窗口看起来是被一个弹出窗口拦截程序所阻挡。 如果想打开新窗口，我们建议您将本站点加入到这个拦截程序设定的允许弹出名单中。有的弹出窗口拦截程序允许在长按Ctrl键时可以打开新窗口。');
                                }
                            }
                        });
                    }
                }]
            }],
            listeners: {
                edit: function (editor, context, eOpts) {
                    godownOrderStore.sync({
                        success: function (batch, options) {
                            godownOrderStore.loadPage(1);
                        },
                        failure: function (batch, options) {
                            if (options.operations.create) {
                                Ext.Msg.alert("操作失败", "创建入库单失败");
                            } else if (options.operations.update) {
                                Ext.Msg.alert("操作失败", "更新入库单失败");
                            }
                            godownOrderStore.loadPage(1);
                        }
                    });
                },
                canceledit: function (editor, context, eOpts) {
                    godownOrderStore.reload();
                }
            },
            bbar: {
                xtype: 'pagingtoolbar',
                store: godownOrderStore,
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
            title: '入库单信息',
            layout: 'border',
            items: [godownOrderToolBar, {
                xtype: 'panel',
                region: 'center',
                layout: 'vbox',
                items: [godownOrderGrid]
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