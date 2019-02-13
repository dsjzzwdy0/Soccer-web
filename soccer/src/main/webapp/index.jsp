<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>head</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="content/bootstrap-4.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="content/flat-ui/css/flat-ui.css">
    <script src="content/scripts/jquery/jquery-3.3.1.min.js"></script>
    <script src="content/scripts/jquery/jquery-session.js"></script>
    <script src="content/bootstrap-4.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Title</a>
            
            <a href="#" class="btn btn-large btn-block btn-info">Info Button</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="index.jsp">Main_page</a></li>
                <li class="active"><a href="next_page.jsp">next_page</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right" id="user_sst">
            </ul>
        </div>
    </div>
    
    <div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a href="#"> Menu Item <span class="navbar-unread">1</span>
						</a></li>
						<li class="active"><a href="#"> Messages <span
								class="navbar-unread">1</span>
						</a>
							<ul>
								<li><a href="#">Element One</a></li>
								<li><a href="#">Sub menu</a>
									<ul>
										<li><a href="#">Element One</a></li>
										<li><a href="#">Element Two</a></li>
										<li><a href="#">Element Three</a></li>
									</ul> <!-- /Sub menu --></li>
								<li><a href="#">Element Three</a></li>
							</ul> <!-- /Sub menu --></li>
						<li><a href="#"> About Us <span class="navbar-unread">1</span>
						</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
</nav>
	

	<div class="modal fade" id="modal-container-2" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel2" align="center">
                    登入
                </h4>
            </div>
            <div class="modal-body">
                <form name="log_form">
                    <div class="row form-group">
                        <div class="col-lg-offset-3 col-lg-6" id="log_st">
                            <!--<p class="alert-success" align="center">gg?</p>-->
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="form-group" id="log_id">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-3" align="right">
                                <label class="control-label" for="log_id_">用户名</label>
                            </div>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" id="log_id_"
                                       placeholder="请在此输入用户名">
                            </div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="form-group" id="log_pas">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-3" align="right">
                                <label class="control-label" for="log_pas_">密码</label>
                            </div>
                            <div class="col-lg-6">
                                <input type="password" class="form-control" id="log_pas_"
                                       placeholder="请在此输入密码">
                            </div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-lg-2"></div>
                        <div class="col-lg-4">
                            <button type="reset" class="btn btn-default btn-wide">重置</button>
                        </div>
                        <div class="col-lg-4">
                            <button type="submit" class="btn btn-primary btn-wide" id="log_sub" onclick="return false">
                                登入
                            </button>
                        </div>
                        <div class="col-lg-2"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-container-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel" align="center">
                    注册
                </h4>
            </div>
            <div class="modal-body">
                <form name="reg_form">
                    <div class="row form-group">
                        <div class="col-lg-offset-3 col-lg-6" id="reg_st">
                            <!--<p class="alert-success" align="center">gg?</p>-->
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="form-group" id="reg_id">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-3" align="right">
                                <label class="control-label" for="reg_id_">用户名</label>
                            </div>
                            <div class="col-lg-6">
                                <input type="text" class="form-control" id="reg_id_"
                                       placeholder="请在此输入用户名">
                            </div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="form-group" id="reg_pas1">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-3" align="right">
                                <label class="control-label" for="reg_pas1_">密码</label>
                            </div>
                            <div class="col-lg-6">
                                <input type="password" class="form-control" id="reg_pas1_"
                                       placeholder="创建你的密码">
                            </div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="form-group" id="reg_pas2">
                            <div class="col-lg-1"></div>
                            <div class="col-lg-3" align="right">
                                <label class="control-label" for="reg_pas2_">验证密码</label>
                            </div>
                            <div class="col-lg-6">
                                <input type="password" class="form-control" id="reg_pas2_"
                                       placeholder="请再次输入密码">
                            </div>
                            <div class="col-lg-2"></div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="col-lg-2"></div>
                        <div class="col-lg-4">
                            <button type="reset" class="btn btn-default btn-wide">重置</button>
                        </div>
                        <div class="col-lg-4">
                            <button type="submit" class="btn btn-primary btn-wide" id="reg_sub" onclick="return false">
                                提交
                            </button>
                        </div>
                        <div class="col-lg-2"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-container-3" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel3" align="center">
                    确定退出吗？
                </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-2"></div>
                    <div class="col-lg-4">
                        <button type="button" class="btn btn-default btn-wide" data-dismiss="modal"
                                aria-hidden="true">取消
                        </button>
                    </div>
                    <div class="col-lg-4">
                        <button class="btn btn-primary btn-wide" id="logout">退出</button>
                    </div>
                    <div class="col-lg-2"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
