/**
 * 公共Javascript函数
 */

$.isNullOrEmpty = function (obj) {
    if ((typeof (obj) == "string" && obj == "") || obj == null || obj == undefined) {
        return true;
    }
    else {
        return false;
    }
}
$.isNotNullOrEmpty = function (obj) {
	return !$.isNullOrEmpty(obj);
}