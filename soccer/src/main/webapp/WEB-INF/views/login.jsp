<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<html>
<body>
	<h2>这里是登录主页面</h2>
	${user }

	<form action="${ctxPath}/user/submit">
		<fieldset>
			<legend>表单属性</legend>
			<label for=""> email: <input type="email" name="email"> </label> 
			<label for=""> tel: <input type="tel" name="tel"> <!-- 本身不验证,自己添加验证 -->
			</label> 
			<label for=""> url: <input type="url" name="url">
			</label> <label for=""> number: <input type="number" name="number"
				step="5"> <!--输入的是步长的整倍数-->
			</label> <label for=""> search: <input type="search" name="search">
				<!--移动端出现的小键盘右下角是搜索按钮-->
			</label> 
			<label for=""> range: <input type="range" name="range" value="100" min="0" max="300"> <!--默认最小0 最大100--></label> 
			<label for=""> 
			<label for=""> color: 
			<input type="color" name="color">
			</label> time: <input type="time" name="time"> </label> 
			<label for=""> date: <input type="date" name="date"> </label> 
			<label for=""> month: <input type="month" name="month"> </label> 
			<label for=""> week: <input type="week" name="week"> </label> 
			<label for=""> datetime: <input type="datetime" name="datetime">
			</label> 
			
			<input type="submit" value="提交">
		</fieldset>
	</form>
</body>
</html>
