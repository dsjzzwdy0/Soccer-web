/**
 * 公共Javascript函数
 */
//检测对象是否不为空
$.isNullOrEmpty = function (obj) {
    if ((typeof (obj) == "string" && obj == "") || obj == null || obj == undefined) {
        return true;
    }
    else {
        return false;
    }
}

//检测对象是否为空
$.isNotNullOrEmpty = function (obj) {
	return !$.isNullOrEmpty(obj);
}

//验证字符串是否是数字
$.checkNumber = function(theObj){
  var reg = /^[0-9]+.?[0-9]*$/;
  if (reg.test(theObj)) {
    return true;
  }
  return false;
}
//格式化日期
formatDate = function (v, format) {
    if (!v) return "";
    var d = v;
    if (typeof v === 'string') {
        if (v.indexOf("/Date(") > -1)
            d = new Date(parseInt(v.replace("/Date(", "").replace(")/", ""), 10));
        else
            d = new Date(Date.parse(v.replace(/-/g, "/").replace("T", " ").split(".")[0]));//.split(".")[0] 用来处理出现毫秒的情况，截取掉.xxx，否则会出错
    }
    var o = {
        "M+": d.getMonth() + 1,  //month
        "d+": d.getDate(),       //day
        "h+": d.getHours(),      //hour
        "m+": d.getMinutes(),    //minute
        "s+": d.getSeconds(),    //second
        "q+": Math.floor((d.getMonth() + 3) / 3),  //quarter
        "S": d.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

//盘口数据的处理
var OddsUtil = OddsUtil || {};
OddsUtil.NAME_YPS =
	[ "平手", "平/半", "半球", "半/一", "一球", "一/球半", "球半", "球半/两", "两球", "两/两半", "两半", "两半/三", "三球", 
	  "受平/半", "受半球", "受半/一","受一球", "受一/球半", "受球半", "受球半/两", "受两球", "受两/两半", "受两半", "受两半/三", "受三球" ];
OddsUtil.NAME_YPS_VALUES =
	[ 0.0, 0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0, 2.25, 2.5, 2.75, 3.0, -0.25, -0.50, -0.75, -1.0,
			-1.25, -1.5, -1.75, -2.0, -2.25, -2.5, -2.75, -3.0 ];
OddsUtil.DEFAUL_THRESHOLD =0.002;
OddsUtil.formatHandicap = function(value)
{
	var len = OddsUtil.NAME_YPS_VALUES.length;
	for(var i = 0; i < len ; i ++)
	{
		if(OddsUtil.withinErrorMargin(value, OddsUtil.NAME_YPS_VALUES[i], OddsUtil.DEFAUL_THRESHOLD))
		{
			return OddsUtil.NAME_YPS[i];
		}
	}
	return '';
}

if (!Array.isArray)
{
	Array.isArray = function(arg) {
		return Object.prototype.toString.call(arg) === '[object Array]';
	};
}

/**
 * 检测是否在误差范围内
 * @param left
 * @param right
 * @returns
 */
OddsUtil.withinErrorMargin = function(left, right)
{
	return $.withinErrorMargin(left, right, 0.003);
}
OddsUtil.withinErrorMargin = function(left, right, threshold)
{
	if($.isNullOrEmpty(threshold) || threshold <= 0)
	{
		threshold = DEFAUL_THRESHOLD;
	}
	return Math.abs(left - right) < threshold;
}

/**
 * 通过让球的名称获得让球数
 * @param name
 * @returns
 */
OddsUtil.getHandicapValue = function(name)
{
	var len = NAME_YPS.length;
	for(var i = 0; i < len ; i ++)
	{
		if(name == NAME_YPS[i])
		{
			return NAME_YPS_VALUES[i];
		}
	}
	return -100;
}


/**
 * 状态改变监听器
 * @returns
 */
function StateListeners()
{
	this.listeners = [];
	this.add = function(listener)
	{
		this.listeners.push(listener);
	},
	this.notify = function(state, source, conf)
	{
		var len = this.listeners.length;
		for(var i = 0; i < len; i ++)
		{
			this.listeners[i](state, source, conf);
		}
	}
}