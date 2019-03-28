/**
 * 根据配置生成表单页面的HTML5文档
 * @auto dsj
 * @time 2019-03-27
 */

var Config = {};

Config.createHtml = function(div, conf)
{
	var html = [];
	var size = conf.length;
	for(var i = 0; i < size; i ++)
	{
		var element = conf[i];
		var type = element.type;
		switch(type)
		{
		case 'input':
			html.push('<input id="' + element.id + '" >');
			break;
		default:
			break;
		}
	}
	div.append(html.join(' '));
}