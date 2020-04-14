<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        $("#feedBackInfo").jqGrid({
            //查询提交的url
            url: "${path}/feedBack/showAllFeedBack",
            //编辑提交的url
            editurl: "${path}/feedBack/editFeedBack",
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
            pager: "#feedBackPage",
            rowNum: 4,
            rowList: [4, 5, 8],
            viewrecords: true,

            toolbar: [true, "top"],
            //正常展示使用
            colNames: ["ID", "标题", "内容", "用户ID", "反馈时间"],
            colModel: [
                {name: "id"},
                {name: "title",width:"50"},
                {name: "content", width:"100"},
                {name: "userId", editable: true},
                {name: "saveDate", width:"60",sortable:true,sortdesc:true}
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
        $("#t_feedBackInfo").append(div);
    });
</script>


<div class="panel panel-danger">
    <div class="panel-heading">
        <h4>反馈信息</h4>
    </div>
    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">反馈管理</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="feedBackInfo"></table>
                    <div id="feedBackPage"></div>
                </div>
                <div role="tabpanel" class="tab-pane" id="profile">...</div>
                <div role="tabpanel" class="tab-pane" id="messages">s*************</div>
                <div role="tabpanel" class="tab-pane" id="settings">...</div>
            </div>

        </div>
    </div>
</div>