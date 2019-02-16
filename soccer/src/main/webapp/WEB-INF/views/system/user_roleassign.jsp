<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fns" tagdir="/WEB-INF/tags" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    $(function () {

        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

        $("#btn_close").bind("click", function () {
            parent.layer.close(index);
        });

        $("#btn_save").bind("click", function () {
            var ids = Feng.zTreeCheckedNodes("zTree");
            var ajax = new $ax(Feng.ctxPath + "/mgr/setRole", function (data) {
                Feng.success("分配角色成功!");
                window.parent.MgrUser.table.refresh();
                parent.layer.close(index);
            }, function (data) {
                Feng.error("分配角色失败!" + data.responseJSON.message + "!");
            });
            ajax.set("roleIds", ids);
            ajax.set("userId", "${userId}");
            ajax.start();
        });

        initZtree();
    });

    function initZtree() {
        var setting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "",
                    "N": ""
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var ztree = new $ZTree("zTree", "/role/roleTreeListByUserId/${userId}");
        ztree.setSettings(setting);
        ztree.init();
    }
</script>


<!-- 配置grid -->
<div class="container"
     style="padding:  0px 10px !important; margin-top: -10px; text-align: center !important;">
    <div class="row">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>${userAccount}</h5>
            </div>
            <div class="ibox-content">
                <ul id="zTree" class="ztree"></ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <button class="btn btn-sm btn-info" type="button" id="btn_save">
                <i class="ace-icon fa fa-check bigger-110"></i> 保存
            </button>
            &nbsp;
            <button class="btn btn-sm btn-danger" type="button" id="btn_close">
                <i class="ace-icon fa fa-close bigger-110"></i> 关闭
            </button>
        </div>
    </div>
</div>

