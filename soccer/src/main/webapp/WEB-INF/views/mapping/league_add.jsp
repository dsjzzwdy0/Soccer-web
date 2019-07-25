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
					<fns:select id="sourcename" name="名称" underline="true">
						<option value="1">男</option>
						<option value="2">女</option>
					</fns:select>
				</div>
				<div class="col-sm-6">
					<h2>足彩网数据</h2>
					<fns:input id="destid" name="编号" underline="true"/>
					<fns:select id="destname" name="名称" underline="true">
						<option value="1">男</option>
						<option value="2">女</option>
					</fns:select>
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

</script>
