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
<script type="text/javascript" src="${ctxPath}/content/scripts/soccer/odds-table.js"></script>

<div id="content" class="container_wrapper">
	<%@include file="./analysis/anatoolbar.jsp"%>
	
	<div id="main" class="main_wrapper">
		<table id="gridTable" class="gridTable table-hover"
			data-pagination="true" data-show-refresh="true">
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

$(document).ready(function() {
	var table = new OddsTable($('#gridTable'), {
		url: 'getMatchOdds'
	});
	table.init();
	
	$('#btnRefresh').on('click', function(){
		table.create();
	});
});
</script>