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
function createTableHeader(header, columns, showFirst, showLast)
{
	var top = [];
	var bottom = [];
	var size = columns.length;
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
			top.push('<th><div class="th-wrap">');
			top.push(c.field);
			top.push('</div></th>');
			
			bottom.push('<th class="oddsvalue">');
			bottom.push('')
		}
		else
		{
			top.push('<th rowspan="2"><div class="th-wrap">');
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