Ext.onReady(function () {
    var add_winForm = Ext.create('Ext.form.Panel', {
        frame: true,   //frame属性
        //title: 'Form Fields',
        width: 340,
        bodyPadding: 5,
        //renderTo:"panel21",
        fieldDefaults: {
            labelAlign: 'left',
            labelWidth: 90,
            anchor: '100%'
        },
        items: [{
            //隐藏的文本框
            xtype: 'hiddenfield', //1
            name: 'hiddenfield1',
            value: '隐藏的文本框'
        }, {
            //显示文本框，相当于label
            xtype: 'displayfield', //2
            name: 'displayfield1',
            fieldLabel: 'Display field',
            value: '显示文本框'

        }, {
            //输入文本框
            xtype: 'textfield', //3
            name: 'textfield1',
            fieldLabel: 'Text field',
            //value: '输入文本框',
            allowBlank: false,
            emptyText: '陈建强',
            blankText: "提示"
        }, {
            //输入密码的文本框，输入的字符都会展现为.
            xtype: 'textfield', //4
            name: 'password1',
            inputType: 'password',
            fieldLabel: 'Password field'
        }, {
            //多行文本输入框
            xtype: 'textareafield', //5
            name: 'textarea1',
            fieldLabel: 'TextArea',
            id: "areaid",
            value: '啦啦啦，我是卖报的小行家'
        }, {
            //上传文件文本框
            xtype: 'filefield', //6
            name: 'file1',
            fieldLabel: 'File upload'
        }, {
            //时间文本框
            xtype: 'timefield', //7
            name: 'time1',
            fieldLabel: 'Time Field',
            minValue: '8:00 AM',
            maxValue: '5:00 PM',
            increment: 30
        }, {
            //日期文本框
            xtype: 'datefield', //8
            name: 'date1',
            fieldLabel: 'Date Field',
            value: new Date()
        }, {
            //下拉列表框
            xtype: 'combobox', //9
            fieldLabel: 'Combobox',
            displayField: 'name',
            store: Ext.create('Ext.data.Store', {
                fields: [
                    {type: 'string', name: 'name'}
                ],
                data: [
                    {"name": "Alabama"},
                    {"name": "Alaska"},
                    {"name": "Arizona"},
                    {"name": "Arkansas"},
                    {"name": "California"}
                ]
            }),
            queryMode: 'local',
            typeAhead: true
        }, {
            //只能输入数字的文本框
            xtype: 'numberfield',
            name: 'numberfield1', //10
            fieldLabel: 'Number field',
            value: 20,
            minValue: 0,
            maxValue: 50
        }, {
            //复选框
            xtype: 'checkboxfield', //11
            name: 'checkbox1',
            fieldLabel: 'Checkbox',
            boxLabel: '复选框'
        }, {
            //单选框，注意name和下面的单选框相同
            xtype: 'radiofield', //12
            name: 'radio1',
            value: 'radiovalue1',
            fieldLabel: 'Radio buttons',
            boxLabel: 'radio 1'
        }, {
            //单选框，注意name和上面的单选框相同
            xtype: 'radiofield', //13
            name: 'radio1',
            value: 'radiovalue2',
            fieldLabel: '',
            labelSeparator: '',
            hideEmptyLabel: false,
            boxLabel: 'radio 2'
        }, {
            //拖动组件
            xtype: 'multislider', //14
            fieldLabel: 'Multi Slider',
            values: [25, 50, 75],
            increment: 5,
            minValue: 0,
            maxValue: 100
        }, {
            //拖动组件
            xtype: 'sliderfield', //15
            fieldLabel: 'Single Slider',
            value: 50,
            increment: 10,
            minValue: 0,
            maxValue: 100
        }]
    });

    //创建window面板，表单面板是依托window面板显示的
    var syswin = Ext.create('Ext.window.Window', {
        title: "新建属性",
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
                        url: 'testshowform.jsp',
                        //等待时显示 等待
                        waitTitle: '请稍等...',
                        waitMsg: '正在提交信息...',
                        params: {
                            t: "add"
                        },
                        success: function (fp, o) {
                            //alert(o);success函数，成功提交后，根据返回信息判断情况
                            if (o.result == true) {
                                Ext.MessageBox.alert("信息提示", "保存成功!");
                                syswin.close(); //关闭窗口
                                // Store1.reload();
                            } else {
                                msg('信息提示', '添加时出现异常！');
                            }
                        },
                        failure: function () {
                            msg('信息提示', '添加失败！');
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
});