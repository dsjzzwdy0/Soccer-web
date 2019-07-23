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

<div id="content" class="container_wrapper">
	
	<div id="main" class="main_wrapper">
		<div id="toolbar">
            <div class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加记录</div>
        </div>
            
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
$(function() {
    //根据窗口调整表格高度
    $(window).resize(function() {
        $('#gridTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    })

    $('#gridTable').bootstrapTable({
        url: "getLeagues",		//数据源
        dataField: "rows",		//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
        height: tableHeight(),	//高度调整
        search: true,			//是否搜索
        pagination: true,		//是否分页
        pageSize: 20,			//单页记录数
        pageList: [5, 10, 20, 50],//分页步进值
        sidePagination: "server",//服务端分页
        contentType: "application/x-www-form-urlencoded",//请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
        dataType: "json",//期待返回数据类型
        method: "post",//请求方式
        searchAlign: "left",//查询框对齐方式
        queryParamsType: "limit",//查询参数组织方式
        queryParams: function getParams(params) {
            //params obj
            params.other = "otherInfo";
            return params;
        },
        searchOnEnterKey: false,//回车搜索
        showRefresh: false,//刷新按钮
        showColumns: true,//列选择按钮
        buttonsAlign: "left",//按钮对齐方式
        toolbar: "#toolbar",//指定工具栏
        toolbarAlign: "right",//工具栏对齐方式
        columns: [
            {
                title: "全选",
                field: "select",
                checkbox: true,
                width: 20,//宽度
                align: "center",//水平
                valign: "middle"//垂直
            },
            {
                title: "ID",//标题
                field: "id",//键名
                sortable: true,//是否可排序
                order: "desc"//默认排序方式
            },
            {
                field: "sourcename",
                title: "Okooo名称",
                sortable: true,
                titleTooltip: "this is name"
            },
            {
                field: "sourcefrom",
                title: "数据来源",
                sortable: true,
            },
            {
                field: "destname",
                title: "联赛名称",
                sortable: true,
                titleTooltip: "this is name"
            },
            {
                field: "sourcedest",
                title: "数据来源",
                sortable: true,
            },
            {
                field: "info",
                title: "数据操作",
                formatter: 'infoFormatter',		//对本列数据做格式化
            }
        ],
        onClickRow: function(row, $element) {
            //$element是当前tr的jquery对象
            $element.css("background-color", "green");
        },			//单击row事件
        locale: "zh-CN", //中文支持
        detailView: false, //是否显示详情折叠
        detailFormatter: function(index, row, element)
        {
            var html = '';
            $.each(row, function(key, val){
                html += "<p>" + key + ":" + val +  "</p>"
            });
            return html;
        }
    });

    $("#addRecord").click(function(){
        alert("name:" + $("#name").val() + " age:" +$("#age").val());
    });
    
    function tableHeight() {
        return $(window).height() - 30;
    }
});
</script>