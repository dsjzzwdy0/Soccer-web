<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tigis.geoqs.security.ShiroKit" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="qs" uri="/WEB-INF/taglib/function.tld"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div class="ibox float-e-margins">
	<div class="ibox-content">
		<div class="form-horizontal" id="menuInfoForm">
		
			<input type="hidden" id="id" value="">

			<div class="row">
				<div class="col-sm-6 b-r">
					<fns:input id="name" name="名称" underline="true" />
					<fns:input id="code" name="菜单编号" underline="true" />
					<fns:input id="pcodeName" name="父级编号" underline="true"
							hidden="pcode" readonly="readonly"
							clickFun="MenuInfoDlg.showMenuSelectTree(); return false;"
							style="background-color: fns:ffffff !important;"
							selectFlag="true" selectId="pcodeTreeDiv" selectTreeId="pcodeTree" selectStyle="width:244px !important;"/>
					<fns:select id="ismenu" name="是否是菜单">
						<option value="1">是</option>
						<option value="0">不是</option>
					</fns:select>
				</div>
				<div class="col-sm-6">
					<fns:input id="url" name="请求地址" underline="true" />
					<fns:input id="num" name="排序" underline="true" />
					<fns:input id="icon" name="图标" underline="false" />
				</div>
			</div>

			<div class="row btn-group-m-t">
				<div class="col-sm-10">
					<fns:button btnCss="info" name="提交" id="ensure" icon="fa-check" clickFun="MenuInfoDlg.addSubmit()"/>
					<fns:button btnCss="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="MenuInfoDlg.close()"/>
				</div>
			</div>
		</div>

	</div>
</div>
<script src="${ctxPath}/content/modular/system/menu/menu_info.js"></script>
