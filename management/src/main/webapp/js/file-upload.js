Ext.onReady(function () {
    Ext.create('Ext.form.Panel', {
        title: '文件上传',
        width: 400,
        bodyPadding: 10,
        frame: true,
        renderTo: 'fi-form',
        items: [{
            xtype: 'filefield',
            name: 'file',
            fieldLabel: 'File',
            labelWidth: 50,
            msgTarget: 'side',
            allowBlank: false,
            anchor: '100%',
            buttonText: '请选择文件...'
        }],

        buttons: [{
            text: '上传',
            handler: function () {
                var form = this.up('form').getForm();
                if (form.isValid()) {
                    form.submit({
                        url: 'upload.action',
                        waitMsg: '正在上传文件...',
                        success: function (fp, o) {
                            Ext.Msg.alert('上传成功', '文件已经保存到服务器！');
                        }
                    });
                }
            }
        }]
    });
});