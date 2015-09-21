/**
 * Created by liufl on 15-1-19.
 */
(function () {
    var Base64 = (function () {
        // private property
        var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

        // private method for UTF-8 encoding
        function utf8Encode(string) {
            string = string.replace(/\r\n/g, "\n");
            var utftext = "";
            for (var n = 0; n < string.length; n++) {
                var c = string.charCodeAt(n);
                if (c < 128) {
                    utftext += String.fromCharCode(c);
                }
                else if ((c > 127) && (c < 2048)) {
                    utftext += String.fromCharCode((c >> 6) | 192);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
                else {
                    utftext += String.fromCharCode((c >> 12) | 224);
                    utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                    utftext += String.fromCharCode((c & 63) | 128);
                }
            }
            return utftext;
        }

        // public method for encoding
        return {
            //encode : (typeof btoa == 'function') ? function(input) { return btoa(input); } : function (input) {
            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
                var i = 0;
                input = utf8Encode(input);
                while (i < input.length) {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output = output +
                    keyStr.charAt(enc1) + keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) + keyStr.charAt(enc4);
                }
                return output;
            }
        };
    })();
    var format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p) {
            return c[p];
        })
    };
    var tableToExcel = function (table, fileName) {
        var uri = 'data:application/vnd.ms-excel;base64,'
            , fileName = fileName || 'excelexport'
            , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office"' +
                ' xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">' +
                '<meta http-equiv="Content-Type" charset=utf-8"><head>' +
                '<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets>' +
                '<x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/>' +
                '</x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml>' +
                '<![endif]--></head><body>{table}</body></html>';

        var ctx = {worksheet: 'Worksheet', table: table};
        var a = document.createElement('a');
        document.body.appendChild(a);
        a.hreflang = 'zh';
        a.charset = 'utf8';
        a.type = "application/vnd.ms-excel";
        a.href = uri + Base64.encode(format(template, ctx));
        a.target = '_blank';
        a.download = fileName + '.xls';
        a.click();

    };
    window.grid2Excel = function (grid, fileName) {
        var columns = grid.initialConfig.columns || [],
            store = grid.getStore(),
            head = [],
            dataIndex = [],
            tableStr = '<table><thead>{thead}</thead><tbody>{tbody}</tbody></table>';

        columns.forEach(function (column) {
                column.colspan = 1;
                column.rowspan = 1;
                head.push(column);
                dataIndex.push(column);
        });
        var theadStr = '<tr>';
        head.forEach(function (head) {
            theadStr += '<th colspan = "' + head.colspan +
            '" rowspan="' + head.rowspan + '">' + head.text + '</th>';
        });
        theadStr += '</tr>';

        var tbodyStr = '',
            defRenderer = function (value) {
                return value;
            };

        store.each(function (r) {
            tbodyStr += '<tr>';
            dataIndex.forEach(function (c) {
                var renderere = c.renderer || defRenderer;
                tbodyStr += '<td>' + renderere.call(r, r.get(c.dataIndex)) + '</td>'
            });
            tbodyStr += '</tr>'
        });
        tableStr = format(tableStr, {
            thead: theadStr,
            tbody: tbodyStr
        });

        tableToExcel(tableStr, fileName);

    }
})();