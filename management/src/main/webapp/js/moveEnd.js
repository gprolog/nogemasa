/**
 * Created by liufl on 15-1-25.
 */
function moveEnd(textarea){
    textarea.focus();
    var len = textarea.value.length;
    if (document.selection) {
        var sel = textarea.createTextRange();
        sel.moveStart('character',len);
        sel.collapse();
        sel.select();
    } else if (typeof textarea.selectionStart == 'number' && typeof textarea.selectionEnd == 'number') {
        textarea.selectionStart = textarea.selectionEnd = len;
    }
}