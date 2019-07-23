<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<div id="login_bg"></div>
<div class="Newheader_box">
	<div class="Newheader_content clearfix">
		<div class="float_r">
			<div class="navlist_box ">
				<span>足彩网站 <em class="navIcon icon_down_sm"></em></span>
				<div class="tc_xiala top_short">
					<em class="navIcon icon_jiantou"></em>
					<a href="http://www.zgzcw.com/" target="_blank">中国足彩网</a>
					<a href="http://www.okooo.com/" target="_blank">澳客网</a>
					<a href="http://www.500.com/" target="_blank">500彩票网</a>
				</div>
			</div>
			<div class="navlist_box ">
				<span>比分直播 <em class="navIcon icon_down_sm"></em></span>
				<div class="tc_xiala top_short">
					<em class="navIcon icon_jiantou"></em> 
					<a href="http://www.okooo.com/livecenter/football/">足球直播</a> 
					<a href="http://www.okooo.com/livecenter/jingcai/">竞彩直播</a>
					<a href="http://www.okooo.com/livecenter/danchang/">单场直播</a>
				</div>
			</div>
			<div class="navlist_box">
				<span><a href="http://www.okooo.com/store/">投注站</a></span>
			</div>
			<div class="navlist_box">
				<span><a href="../download/admin">数据下载</a></span>
			</div>
			
			<c:if test="${not empty user}">
				<div class="navlist_box bdrn">
					<span><em style="color: red">欢迎您,${user.username }</em>&nbsp;&nbsp;<a href="../user/change">更改密码</a>&nbsp;&nbsp;<a href="../user/logout">退出</a></span>
				</div>
			</c:if>
			<c:if test="${empty user}">
				<div class="navlist_box bdrn">
					<span><a href="../user/login">登录</a></span>
				</div>
			</c:if>
		</div>
	</div>
</div>