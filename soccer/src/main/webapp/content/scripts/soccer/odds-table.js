/**
 * 
 */
OddsTable = function(element, options)
{
	this.$table = $(element);
	this.options = {
		showHeader: true,
		showOrdinary: true,
		theadClasses: 'test',
		url: ''
	};
	$.extend(this.options, options);
}

OddsTable.prototype = {
	constructor: OddsTable,
	
	init: function()
	{
		this.$header = this.$table.find('>thead')
		if (!this.$header.length) {
			this.$header = $('<thead class="' + this.options.theadClasses + '"></thead>')
				.appendTo(this.$table)
		} else if (this.options.theadClasses) {
			this.$header.addClass(this.options.theadClasses)
		}
		layer.msg('Test')
	},
	create: function()
	{
		layer.msg(this.options.url);
	}
};

/**
//创建表格头部
function createTableHeader(header, columns)
{
	var top = [];
	var center = [];
	var bottom = [];
	var size = columns.length;
	var showFirst = true;
	var showLast = true;
	for(var i = 0; i < size; i ++)
	{
		c = columns[i];
		if(c.type == 'odds')
		{
			//初始值不显示与即时值也不显示，则不显示该列
			if(!showFirst && !showLast)
			{
				continue;
			}
			
			colspan = 0;
			if(showFirst) colspan += 3;
			if(showLast) colspan += 3;
			top.push('<th colspan="' + colspan + '"><div class="th-wrap">');
			top.push(c.field);
			top.push('</div></th>');
			
			if(showFirst)
			{
				center.push('<th colspan="3"><div class="th-wrap">');
				center.push('初盘');
				center.push('</div></th>');
				
				bottom.push(formatOddsHeader(c.field, true));
			}
			if(showLast)
			{
				center.push('<th colspan="3"><div class="th-wrap">');
				center.push('即时');
				center.push('</div></th>');
				
				bottom.push(formatOddsHeader(c.field, false));
			}
		}
		else
		{
			var name = c.name;
			if($.isNullOrEmpty(name)) name = c.field;
			
			top.push('<th rowspan="3"><div class="th-wrap">');
			if($.isNullOrEmpty(c.name))
				top.push(c.field);
			else
				top.push(c.name);
			
			if(c.sortable === true)
			{
				top.push('<div class="sorting-action" data-field="' + c.field + '">';
				top.push('<i class="sa-icon sa-up iconfont icon-sanjiao2"></i><i class="sa-icon sa-down iconfont icon-sanjiao1"></i></div>');
			}
			top.push('</div></th>');
		}
	};
	
	formatOddsHeader = function(corpname, isFirst)
	{
	};
	
	formatBasicHeader = function(rowspan, sortable, name, field, index)
	{
		var html = [];
		html.push('<th' + ($.isNotNullOrEmpty(rowspan) ? ' rowspan="' + rowspan + '"') + '>');
		html.push('<div class="th-wrap">');
		html.push(name);
		if(sortalbe == true)
		{
			html.push('<div class="sorting-action" data-field="' + field + '"';
			if($.isNotNullOrEmpty(index)) html.push(' data-index=' + index + '"');
			html.push('>');
			html.push('<i class="sa-icon sa-up iconfont icon-sanjiao2"></i><i class="sa-icon sa-down iconfont icon-sanjiao1"></i></div>');
		}
		html.push('</div></th>');
		return html;
	}
};**/