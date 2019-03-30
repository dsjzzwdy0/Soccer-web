/**
 * 根据配置生成表单页面的HTML5文档
 * @auto dsj
 * @time 2019-03-27
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

var Config = {};

Config.getElementClass = function (element)
{
	if($.isNullOrEmpty(element.classType))
		return 'class="' + ClassValue[element.type] + '"';
	else
		return 'class="' + element.classType + '"';
}

Config.getBaseAttr = function(element)
{
	var text = 'name="' + element.id + '" class=' + this.getElementClass(element);
	return text;
}

Config.getElementHtml = function(element, disabled)
{
	var html = [];
	var type = element.type;
	var value = element.value;
	
	if($.isNullOrEmpty(value)) value = '';
	switch(type)
	{
	case 'input':
		html.push('<input ' + this.getBaseAttr(element) + (disabled ? ' readonly="true"' : '') + ' value="' + value + '"/>');
		break;
	case 'checkbox':
		hmtl.push('<input type="checkbox"' + this.getBaseAttr(element) + (disabled ? ' disabled="true"' : '') + ' />');
		break;
	case 'select':
		html.push('<select ' + this.getBaseAttr(element) + (disabled ? ' disabled="disabled"' : '') +  '>');
		var list = element.options;
		if($.isNotNullOrEmpty(list))
		{
			var size = list.length;
			var opt;
			for(var i = 0; i < size; i ++)
			{
				opt = list[i];
				html.push('<option ' + (value == opt.value ? 'selected' : '') + '>' + opt.name + '</option>');
			}
		}
		html.push('</select>');
		break;
	default:
		html.push('<div ' + getElementClass(element) + '>' + value + '</div>');
		html.push('<input type="hidden" value="' + value + '" />');
		break;
	}
	return html.join(' ');
}

Config.createHtml = function(div, conf, disabled)
{
	var html = [];
	var size = conf.length;
	
	html.push('<table class="gridTable"><tbody>');
	for(var i = 0; i < size; i ++)
	{
		var element = conf[i];
		html.push('<tr>');
		html.push('<td class="colname">' + element.name + '</td>');
		html.push('<td>')
		html.push(this.getElementHtml(element, disabled));
		html.push('</td>');
		html.push('</tr>');
	}
	html.push('</tbody></table>')
	div.append(html.join(' '));
}

var ClassValue = {};
ClassValue['input'] = 'newinput';
ClassValue['div'] = 'newdiv';
ClassValue['checkbox'] = 'checkboxcls';
ClassValue['select'] = 'newselect';