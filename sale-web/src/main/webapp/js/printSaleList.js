function timeStamp2String(time) {
    var datetime = new Date();
    if (time) {
        datetime.setTime(time);
    }
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

function printGoodsList() {
    var oPop = window.open('', 'oPop');
    var str = '<!DOCTYPE html>';
    str += '<html>';
    str += '<head>';
    str += '<meta charset="utf-8">';
    str += '<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">';
    str += '<style>';
    str += '#oDiv2 div{border:1px solid #c50000;}';
    str += '</style>';
    str += '</head>';
    str += '<body>';
    str += '<h1 style="font-family: 幼圆;font-size: 30px;font-weight: bold; color: transparent; -webkit-text-stroke: 1px black;letter-spacing: 0.04em;">诺格曼莎</h1>'
    str += '<h1 style="font-family: 幼圆;font-size: 40px;font-weight: bold; color: transparent; -webkit-text-stroke: 1px black;letter-spacing: 0.04em;">nogemasa</h1>'
    str += "<div id='oDiv2'>";
    str += '<p>禹城市人民路店</p>';
    str += '<p>时间:' + timeStamp2String() + '</p>';
    if ($("#employeeSelect").find("option:selected").val() != '0') {
        str += '<p>营业员:' + $("#employeeSelect").find("option:selected").text() + '</p>';
    }
    str += '<p>----------------------</p>';
    str += '<table>';
    str += '        <thead><tr class="firstRow">';
    str += '            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="71">商品</td>';
    str += '            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="50">单价</td>'
    str += '            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="37">数量</td>'
    str += '            <td valign="top" colspan="1" rowspan="1" style="word-break: break-all;" width="50">小计</td>'
    str += '        </tr></thead>';
    str += '        <tbody>';
    var list = $('#goodsList').val();
    var arr = [];
    var total = 0;
    var countType;
    var count = 0;
    var proportion = 1;
    var method = $("#promotionSelect").find("option:selected").val();
    if (method) {
        var m = method.split("!#!");
        if (m[1] == '2') {
            countType = m[1];
            proportion = parseFloat(m[0]);
        }
    }
    if (list) {
        arr = list.split("\n");
        for (var i = 0; i < arr.length; i++) {
            var l = arr[i].split("\t");
            str += '        <tr>';
            str += '            <td width="191" valign="top" style="word-break: break-all;">' + l[0] + '</td>';
            str += '            <td width="76" valign="top" style="word-break: break-all;">' + l[3] + '</td>';
            total = (Number(total) + Number(l[3])).toFixed(2);
            str += '            <td width="52" valign="top" style="word-break: break-all;">1</td>';
            if (countType == '2') {
                count = proportion * parseFloat(l[3]);
            } else {
                count = l[3];
            }
            str += '            <td width="63" valign="top" style="word-break: break-all;">' + count + '</td>';
            str += '        </tr>';
        }
    }
    str += '        </tbody>';
    str += '</table>';
    str += '<p>----------------------</p>';
    str += '<p>数量:' + arr.length + '</p>';
    str += '<p>金额:' + (Number(total)).toFixed(2) + '</p>';
    var totalCost = $("#totalCost").val();
    var discount = Number(total) - totalCost;
    str += '<p>优惠:' + (Number(discount)).toFixed(2) + '</p>';
    str += '<p>实收:' + totalCost + '</p>';
    str += '<p>----------------------</p>';
    var memberCardNo = $("#cardNo").val();
    str += '<p>会员:' + memberCardNo + '</p>';
    str += '<p>地址:人民路市政府向北150米路西</p>';
    str += '<p>电话:7777060</p>';
    str += "</div>";
    str += '</body>';
    str += '</html>';
    oPop.document.write(str);
    oPop.print();
    oPop.close();
}