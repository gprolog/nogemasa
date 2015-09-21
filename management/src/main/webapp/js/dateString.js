/**
 * Created by liufl on 15-1-26.
 */
function dateBeginString(date) {
    if (!date)
        return '';
    var str = '';
    str += date.getUTCFullYear();
    str += '-';
    var m = date.getMonth() + 1;
    str += m < 10 ? '0' + m : m;
    str += '-';
    str += date.getDate();
    str += ' 00:00:00';
    return str;
}

function dateEndString(date) {
    if (!date)
        return '';
    var str = '';
    str += date.getUTCFullYear();
    str += '-';
    var m = date.getMonth() + 1;
    str += m < 10 ? '0' + m : m;
    str += '-';
    str += date.getDate();
    str += ' 23:59:59';
    return str;
}