<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.loris.soccer.model.League"%>
<%@page import="com.loris.soccer.util.IssueMatchUtil" %>
<%@page import="org.apache.commons.lang3.StringUtils" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<%
    String issue = request.getParameter("issue");
	String sid = request.getParameter("sid");		//配置编号
	String type = request.getParameter("type");
	if(StringUtils.isEmpty(issue))
	{
		issue = IssueMatchUtil.getCurrentIssue();
	}
	if(StringUtils.isEmpty(sid))
	{
		sid = "1011";
	}
	if(StringUtils.isEmpty(type))
	{
		type = "bd";
	}
%>
<link rel="stylesheet" type="text/css" href="${ctxPath}/content/css/soccer/datacenter.css" />
<link rel="stylesheet" type="text/css" href="${ctxPath}/content/scripts/soccer/soccer-table.css" />
<script type="text/javascript" src="${ctxPath}/content/scripts/soccer/soccer-table.js"></script>

<div id="content" class="container_wrapper">
	<%@include file="./analysis/anatoolbar.jsp"%>
	
	<div id="main" class="main_wrapper">
		<table id="gridTable" class="gridTable table-hover"
			data-pagination="true" data-show-refresh="true">
			<thead id="tableHeader">
			</thead>
			<tbody id="tableContent">
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript">

var url = "${ctxPath}/odds/getMatchesOdds";
//系统参数
var issue = "<%=issue%>";
var type = "<%=type%>";
var sid = "<%=sid%>";


function createColumns(comps)
{
	var columns = [
		{
			field: 'ordinary',
			name: '序号',
			sortable: true
		},
		{
			field: 'lid',
			name: '联赛',
			sortable: true
		},
		{
			field: '',
			name: '比赛',
			formatter: formatMatchInfo
		}
	];
	
	var size = comps.length;
	for(var i = 0; i < size; i ++)
	{
		var comp = comps[i];
		var c = {
			field: comp.name,
			type: 'odds'
		}
		columns.push(c);
	}
	colums.push({
		field: '',
		name: '详细信息',
		formatter: formatDetail
	});
}

function createTable(matchdocs)
{
	
}

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
			
			if(c.sortable == true)
			{
				top.push('<div class="sorting-action" data-field="' + c.field + '">';
				top.push('<i class="sa-icon sa-up iconfont icon-sanjiao2"></i><i class="sa-icon sa-down iconfont icon-sanjiao1"></i></div>');
			}
			top.push('</div></th>');
		}
	}
	
	formatOddsHeader = function(corpname, isFirst)
	{
		
	}
	
	formatBasicHeader = function(rowspan, sortable,  name, field, index)
	{
		var html = [];
		html.push('<th' + ($.isNotNullOrEmpty(rowspan) ? ' rowspan="' + rowspan + '"') + '>');
		html.push('<div class="th-wrap">');
		html.push(name);
		if(sortalbe)
		{
			html.push('<div class="sorting-action" data-field="' + field + '"';
			if($.isNotNullOrEmpty(index)) html.push(' data-index=' + index + '"');
			html.push('>');
			html.push('<i class="sa-icon sa-up iconfont icon-sanjiao2"></i><i class="sa-icon sa-down iconfont icon-sanjiao1"></i></div>');
		}
		html.push('</div></th>');
		return html;
	}
}

$(document).ready(function() {
	showNewToolBar();
	showSettingSel();
	showOddsType();
	
	if($.isNotNullOrEmpty(sid))
	{
		$('#settingSel').val(sid);
	}
	if($.isNotNullOrEmpty(issue))
	{
		$('#issueSel').val(issue);
	}

	var conf = getConfValue();
	createMatchOddsTable(conf);	
	stateListeners.add(stateChange);
	
	$('#btnRefresh').on('click', function(){
		$('#gridTable').soccerTable('destroy');
	});

	$('#hideChosen').on('click', function(){
		//layer.msg('Recovery');
		sorter.asc = !sorter.asc;
		table.options.sorter = sorter;
		$('#gridTable').soccerTable(table);
	});
});
</script>