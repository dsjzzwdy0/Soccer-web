<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap Table Examples</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="An extended Bootstrap table with radio, checkbox, sort, pagination, and other added features.">
  <meta name="keywords" content="table, bootstrap, bootstrap plugin, bootstrap resources, bootstrap table, jQuery plugin">
  <meta name="author" content="Zhixin Wen, and Bootstrap table contributors">
  <!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->

  <!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
  <link rel="stylesheet" href="${ctxPath }/content/plugins/bootstrap-4.2.1/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${ctxPath }/content/fonts/css/all.css" />
  <link rel="stylesheet" href="${ctxPath }/content/scripts/mapping/css/default.min.css"/>
  <link rel="stylesheet" href="${ctxPath }/content/scripts/mapping/css/hint.min.css"/>
  <link href="${ctxPath }/content/scripts/mapping/css/docs.min.css" rel="stylesheet"/>
  <link href="${ctxPath }/content/scripts/mapping/css/template.css?v=VERSION" rel="stylesheet">
  
  <link href="${ctxPath }/content/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
  

  <script src="${ctxPath }/content/plugins/jquery/jquery-3.3.1.min.js" ></script>
  <script src="${ctxPath }/content/plugins/popper/popper.min.js" ></script>
  <script src="${ctxPath }/content/plugins/bootstrap-4.2.1/js/bootstrap.min.js" ></script>
  <script src="${ctxPath }/content/plugins/bootstrap-table/bootstrap-table.min.js"></script>
  <script src="${ctxPath }/content/scripts/mapping/js/template.js?v=VERSION"></script>
</head>
<body>
  <div class="row">
    <div class="col-md-8 title-desc">
      <h1 class="bd-title" id="content">
        <span></span>
      </h1>
      <p class="bd-lead"></p>
    </div>

    <div class="col-md-4">
      <div id="codefund"><!-- fallback content --></div>
     
    </div>
  </div>

  <div id="example"></div>
  <pre class="source-pre"><code id="source" class="html"></code></pre>
</body>
</html>