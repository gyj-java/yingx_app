<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    /*延迟加载*/
    $(function(){
        //初始化表格的方法
        pageInit();
    });

    //初始化表格方法
    function pageInit(){

        //父表格
        $("#categoryInfo").jqGrid({
            url : "${path}/category/showPrentCategoryByPage",
            editurl:"${path}/category/categoryEdit",
            datatype : "json",
            rowNum : 8,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 8, 10, 20, 30 ],
            pager : '#categoryPage',
            viewrecords : true,
            colNames : [ 'Id', '类别名', '级别'],
            colModel : [
                {name : 'id',index : 'id', width : 55},
                {name : 'cateName',index : 'cateName', editable: true,width : 90},
                {name : 'levels',index : 'levels', width : 100}
            ],
            subGrid : true,  //是否开启子表格
            //1.subgrid_id   点击一行时会在父表格中创建一个div，用来容纳子表格，subgrid_id就是div的id
            //2.row_id   点击行的id
            subGridRowExpanded : function(subgridId, rowId) {  //设置子表格相关的属性
                //复制子表格内容
                addSubGrid(subgridId, rowId);
            }
        });

        //父表格分页工具栏
        $("#categoryInfo").jqGrid('navGrid', '#categoryPage',
            {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
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
                closeAfterdel:true,  //关闭对话框
                //提交之后的操作
                afterSubmit:function(response){
                    //向警告框中写入内容
                    $("#delMessage").html(response.responseJSON.message);
                    //展示警告框
                    $("#alertMessage").show();

                    //自动关闭
                    setTimeout(function(){
                        //关闭提示框
                        $("#alertMessage").hide();
                    },3000);
                    return "true";
                }

            });
    }

    //子表格
    function addSubGrid(subgridId, rowId){

        var subgridTableId= subgridId+"Table";  //定义子表格 Table的id
        var pagerId= subgridId+"Page";   //定义子表格工具栏id

        //在子表格容器中创建子表格和子表格分页工具栏
        $("#" + subgridId).html("<table id='"+subgridTableId+"' /> <div id='"+pagerId+"'>");


        //子表格
        $("#" + subgridTableId).jqGrid({
            //url:"/category/queryByTwoCategory&id="+rowId,
            url:"${path}/category/showChildCategoryByPage?parentId="+rowId,
            editurl:"${path}/category/categoryEdit",
            datatype : "json",
            rowNum : 8,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 8, 10, 20, 30 ],
            pager : "#"+pagerId,
            viewrecords : true,
            colNames : [ 'Id', '类别名', '级别','上级类别id'],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'cateName', editable: true,width : 90},
                {name : 'levels',index : 'levels', width : 100},
                {name : 'parentId',index : 'parentId', editable: true,width : 100,
                    editable:true,edittype:'select',editoptions: {dataUrl:"${path}/category/getParentCategory"}
                }
            ]
        });

        //子表格分页工具栏
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
            {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
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
                closeAfterdel:true,  //关闭对话框
                //提交之后的操作
                afterSubmit:function(response){
                    //向警告框中写入内容
                    $("#delMessage").html(response.responseJSON.message);
                    //展示警告框
                    $("#alertMessage").show();

                    //自动关闭
                    setTimeout(function(){
                        //关闭提示框
                        $("#alertMessage").hide();
                    },3000);
                    return "true";
                }
            });
    }

</script>

<div class="panel panel-info">
    <div class="panel-heading">
        <h4>用户信息</h4>
    </div>
    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">类别管理</a></li>

            </ul>
            <div class="alert alert-danger" id="alertMessage" role="alert" style="display: none">
                <span id="delMessage"></span>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="categoryInfo"></table>
                    <div id="categoryPage"></div>
                </div>
            </div>

        </div>
    </div>
</div>