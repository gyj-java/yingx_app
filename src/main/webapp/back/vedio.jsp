<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<style>
    .ui-jqgrid tr.jqgrow td {
        white-space: normal !important;
        height: auto;
        word-wrap: break-word;
    }
</style>
<script>
    $(function () {
        $("#vedioInfo").jqGrid({
            //查询提交的url
            url: "${path}/video/showVideoByPage",
            //编辑提交的url
            editurl: "${path}/video/videoEdit",
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
            pager: "#vedioPage",
            rowNum: 4,
            rowList: [4, 5, 8],
            viewrecords: true,

            toolbar: [true, "top"],
            //正常展示使用
            colNames: ['ID', '标题', '描述', '视频', '上传时间', '用户Id', '类别id', '分组id'],
            colModel: [
                {name: 'id', width: 55},
                {name: 'title', editable: true, width: 90},
                {name: 'brief', editable: true, width: 100},
                {
                    name: 'videoPath', width: 400, align: "center", edittype: "file",editable: true,
                    formatter: function (cellvalue, options, rowObject) {
                        //远程地址访问
                        return "<video id='media' src='" + cellvalue + "' controls width='400px' heigt='800px' poster='" + rowObject.coverImg + "'/>";

                        //本地访问路径
                        //return "<video id='media' src='${path}" + cellvalue + "' controls width='400px' heigt='800px' poster='${path}" + rowObject.coverImg + "'/>";
                    }
                },
                {name: 'publishDate', width: 150, align: "right"},
                {name: 'userId', width: 80, align: "right"},
                {
                    name: 'categoryId', width: 80, align: "center",
                    // editable: true,
                    // edittype: 'select',
                    <%--editoptions: {dataUrl: "${path}/group/showAllGroup"}--%>
                },
                {name: 'groupId', width: 80,  align: "center"}
            ]
        }).jqGrid("navGrid", "#vedioPage",
            {edit: true, add: true, del: true, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                //修改用的
                closeAfterEdit: true,
                beforeShowForm :function(obj){
                    obj.find("#videoPath").attr("disabled",true);//禁用input
                }
            },
            {
                //添加用的
                closeAfterAdd: true,
                //添加完成之后，异步上传照片，且更新数据库图片地址
                afterSubmit: function (response) {
                    $.ajaxFileUpload({
                        url: "${path}/video/videoUpload",// 上传处理程序地址。
                        fileElementId: "videoPath",//需要上传的文件域的ID，即的ID。
                        type: "post",
                        data: {"id": response.responseText},
                        success: function (data) {
                            $("#vedioInfo").trigger("reloadGrid");
                        }
                    });
                    return "true";
                }
            },
            {
                closeAfterdel: true,
                //删除用的
                afterSubmit: function (data) {
                    //向警告框中追加错误信息
                    $("#showMsg").html(data.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function () {
                        $("#deleteMsg").hide();
                    }, 3000);
                    return "true";
                }
            }
        );
        //表格工具栏添加按钮
        var div = $("<div id='deleteMsg' class='alert alert-danger' style='height: 50px;width: 250px;display: none' align='center'>\n" +
            "            <span id='showMsg'/>\n" +
            "        </div>");
        $("#t_vedioInfo").append(div);
    })
</script>

<div class="panel panel-warning">
    <div class="panel-heading">
        <h4>视频展示</h4>
    </div>
    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">视频管理</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="vedioInfo"></table>
                    <div id="vedioPage"></div>
                </div>
            </div>

        </div>
    </div>
</div>