<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    function turnStatus(id,data){
        $.ajax({
            url:"${path}/user/changUserStatus",
            type:"post",
            dateType:"jsom",
            data:{"id":id,"status":data},
            success:function () {
                $("#userInfo").trigger("reloadGrid")
            }
        })
    }

    $(function () {
        $("#userInfo").jqGrid({
            //查询提交的url
            url: "${path}/user/showAllUser",
            //编辑提交的url
            editurl: "${path}/user/editUser",
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
            pager: "#userPage",
            rowNum: 4,
            rowList: [4, 5, 8],
            viewrecords: true,

            toolbar: [true, "top"],
            //正常展示使用
            colNames: ["ID", "头像", "名字", "手机号", "注册时间", "微信", "状态", "签名"],
            colModel: [
                {name: "id"},
                {
                    name: "headImg", editable: true, edittype: "file",
                    //cellvalue当前列的值
                    //options操作
                    //rowObject行对象，整一行的数据
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}"+cellvalue+"' style='height:50px;width:50px'>";
                    }
                },
                {name: "username", editable: true},
                {name: "phonenumber", editable: true},
                {name: "createDate", editable: true, edittype: "date"},
                {name: "wechat", editable: true},
                {name: "status", editable: true,edittype:'select',
                    editoptions: {value:'0:冻结;1:正常'},
                    formatter: function (cellvalue, options, rowObject) {
                        if(cellvalue==1)
                            return "<div onclick='turnStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-success'>正常</div>";
                        else
                            return "<div onclick='turnStatus(\""+rowObject.id+"\",\""+cellvalue+"\")' class='btn btn-danger'>冻结</div>";
                    }
                },
                {name: "sign", editable: true}
            ]
        }).jqGrid("navGrid", "#userPage",
            {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                //修改用的
                closeAfterEdit: true,
                afterSubmit:function (response) {
                    //修改文件上传头像

                }
            },
            {
                //添加用的
                closeAfterAdd: true,

                //添加完成之后，异步上传照片，且更新数据库图片地址
                afterSubmit: function (response) {

                    $.ajaxFileUpload({
                        url:"${path}/user/userImgUpload",// 上传处理程序地址。
                        dataType:"json",// 服务器返回的数据类型
                        fileElementId:"headImg",//需要上传的文件域的ID，即的ID。
                        type:"post",
                        data:{"id":response.responseText},
                        success:function (data) {
                            alert("****************"+data);
                            $("#userInfo").trigger("reloadGrid");
                        }
                    })
                    return "true";
                }
            },
            {
                //删除用的
            }
        );
        //表格工具栏添加按钮
        var div = $("<div class='btn btn-primary'>导入用户信息</div>");
        $("#t_userInfo").append(div);
    });
</script>


<div class="panel panel-danger">
    <div class="panel-heading">
        <h4>用户信息</h4>
    </div>
    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">用户管理</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="userInfo"></table>
                    <div id="userPage"></div>
                </div>
                <div role="tabpanel" class="tab-pane" id="profile">...</div>
                <div role="tabpanel" class="tab-pane" id="messages">s*************</div>
                <div role="tabpanel" class="tab-pane" id="settings">...</div>
            </div>

        </div>
    </div>
</div>