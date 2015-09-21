Ext.define('SearchDesktop.App', {
    extend: 'Ext.ux.desktop.App',
    requires: ['Ext.window.MessageBox',
        'Ext.ux.desktop.ShortcutModel',
        'SearchDesktop.Settings'],
    init: function () {
        this.callParent();
    },
    getModules: function () {
        return this.modules;
    },
    getDesktopConfig: function () {
        var me = this, ret = me.callParent();
        var shotcutIcons = [];
        for (i in this.getModules()) {
            var module = this.getModules()[i];
            shotcutIcons.push({
                name: module.launcher.text,
                iconCls: module.iconCls,
                module: module.id
            });
        }
        return Ext.apply(ret, {
            contextMenuItems: [{
                text: '设置',
                handler: me.onSettings,
                scope: me
            }],
            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',
                data: shotcutIcons
            }),
            wallpaper: _appctx + '/wallpapers/Blue-Sencha.jpg',
            wallpaperStretch: true
        });
    },
    // config for the start menu
    getStartConfig: function () {
        var me = this, ret = me.callParent();
        return Ext.apply(ret, {
            title: username,
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [{
                    text: '设置',
                    iconCls: 'settings',
                    handler: me.onSettings,
                    scope: me
                }, '-', {
                    text: '退出',
                    iconCls: 'logout',
                    handler: me.onLogout,
                    scope: me
                }]
            }
        });
    },
    getTaskbarConfig: function () {
        var ret = this.callParent();
        return Ext.apply(ret, {
            quickStart: [],
            trayItems: [{
                xtype: 'trayclock',
                flex: 1
            }]
        });
    },
    onLogout: function () {
        Ext.Msg.confirm('退出', '确实要退出本系统？', function (buttonId) {
            if (buttonId == 'ok' || buttonId == 'yes') {
                window.location = _appctx + "/logout.html";
            }
        });
    },
    onSettings: function () {
        var dlg = new SearchDesktop.Settings({
            desktop: this.desktop
        });
        dlg.show();
    }
});
