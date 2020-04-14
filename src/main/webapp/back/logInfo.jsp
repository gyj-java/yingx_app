<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        $("#logInfo").jqGrid({
            //查询提交的url
            url: "${path}/log/showAllLog",
            //编辑提交的url
            editurl: "${path}/log/editLog",
            //设置使用bootstrap风格的表格
            styleUI: "Bootstrap",
            //自适应宽度
            autowidth: true,
            height: "auto",
            //返回值（若无声明，则可能无数据）
            datatype: "json",
            //复选框
            multiselect: true,
            //分页使用
            pager: "#logPage",
            rowNum: 4,
            rowList: [4, 5, 8],
            viewrecords: true,

            toolbar: [true, "top"],
            //正常展示使用
            colNames: ["ID", "用户名", "时间", "操作", "结果"],
            colModel: [
                {name: "id"},
                {name: "adminName",width:"50"},
                {name: "data", width:"100"},
                {name: "operation", editable: true},
                {name: "status", width:"60"}
            ]
        }).jqGrid("navGrid", "#logPage",
            {edit: false, add: false, del: true, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                //修改用的
                closeAfterEdit: true,
            },
            {
                //添加用的
                closeAfterAdd: true,
            },
            {
                //删除用的
            }
        );
        //表格工具栏添加按钮
        var div = $("<div class='btn btn-primary'>导入用户信息</div>");
        $("#t_logInfo").append(div);
    });
</script>


<div class="panel panel-danger">
    <div class="panel-heading">
        <h4>日志信息</h4>
    </div>
    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">日志管理</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="logInfo"></table>
                    <div id="logPage"></div>
                </div>
                <div role="tabpanel" class="tab-pane" id="profile">...</div>
                <div role="tabpanel" class="tab-pane" id="messages">s*************</div>
                <div role="tabpanel" class="tab-pane" id="settings">...</div>
            </div>

        </div>
    </div>
</div>