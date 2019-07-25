<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="ibox float-e-margins">
	<div class="ibox-content">
		<div class="form-horizontal" id="userInfoForm">
			<div class="row">
				
				<div class="col-sm-6 b-r">
					<h2>澳客网数据</h2>
					<fns:input id="sourceid" name="编号" underline="true"/>
					<fns:input id="sourcecountry" name="国家" underline="true" />
					<fns:select id="sourcename" name="名称" underline="true" />
				</div>
				<div class="col-sm-6">
					<h2>足彩网数据</h2>
					<fns:input id="destid" name="编号" underline="true"/>					
					<fns:input id="destcountry" name="国家" underline="true" />
					<fns:select id="destname" name="名称" underline="true" />
				</div>
			</div>

			<!-- 这是部门选择的下拉框 -->
			<div id="menuContent" class="menuContent"
				style="display: none; position: absolute; z-index: 200;">
				<ul id="treeDemo" class="ztree tree-box" style="width: 249px !important;"></ul>
			</div>

			<div class="row btn-group-m-t">
				<div class="col-sm-12" style="text-align: right;">
					<fns:button btnCss="info" name="提交" id="ensure" btnType="primary" icon="fa-check" clickFun="UserInfoDlg.addSubmit()"/>
					<fns:button btnCss="danger" name="取消" id="cancel" btnType="primary" icon="fa-eraser" clickFun="UserInfoDlg.close()"/>
				</div>
			</div>
		</div>
	</div>
</div>
<script>

var okoooList = null;
var list = null;

$(function() {
	var url = "getLeagues";
	$.ajax({
        type: "post",
        url: url,
        data: null,
        dataType: "json",
        async:false,
        success:function(data) {
        	okoooList = data['okooo'];
        	list = data['zgzcw']; 
        	
        	init($('#sourcename'), okoooList);
        	init($('#destname'), list);
        }
	});
	
	function init(element, list)
	{
		var html = '<option value="">请选择</option>';
		var size = list.length;
		for (var i = 0; i < size; i++) {
			var rec = list[i];
			html += '<option value="' + rec.lid+'">' + rec.name + '</option>';
		}
		$(element).html(html);
		
		$(element).on('change', function()
		{
			var type = $(this).attr('id');
			var lid = $(this).children('option:selected').val();

			if(type == 'sourcename')
			{
				if(okoooList == null) return;
				var size = okoooList.length;
				var exist = false;
				for(var i = 0; i < size; i ++)
				{
					var rec = okoooList[i];
					if(rec.lid == lid)
					{
						$('#sourceid').val(rec.lid);
						$('#sourcecountry').val(rec.country)
						exist = true;
					}
				}
				if(!exist)
				{
					$('#sourceid').val('');
					$('#sourcecountry').val('')
				}
			}
			else if(type == 'destname')
			{
				if(list == null) return;
				var size = list.length;
				var exist = false;
				for(var i = 0; i < size; i ++)
				{
					var rec = list[i];
					if(rec.lid == lid)
					{
						$('#destid').val(rec.lid);
						$('#destcountry').val(rec.country)
						exist = true;
					}
				}
				if(!exist)
				{
					$('#destid').val('');
					$('#destcountry').val('')
				}
			}
		});
	}
});
</script>
