<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script src="${path}/js/goeasy-1.0.5.js"></script>
<script src="${path}/js/jquery-3.4.1.min.js"></script>
<script src="${path}/js/echarts.min.js"></script>

<script>
    /*
    * 客户端与GoEasy建立连接
    * 一个独立网页、一个小程序或APP只需要创建一个goeasy对象
    * */
    // 在main.js里初始化全局的GoEasy对象
    var goEasy = new GoEasy({
        host: "hangzhou.goeasy.io",//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
        appkey: "BC-20905462639143a98df5fdbd74a82c9e",//替换为您的应用appkey
        onConnected: function () {
            console.log('连接成功！')
        },
        onDisconnected: function () {
            console.log('连接断开！')
        },
        onConnectFailed: function (error) {
            console.log('连接失败或错误！')
        }
    });


    //订阅消息（接收）
    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        //$.get("/echarts/queryUserNum",function (data) {
        //接收消息
        goEasy.subscribe({
            channel: "yingxChannel-gyj", //替换为您自己的channel
            onMessage: function (message) {
                //alert("Channel:" + message.channel + " content:" + message.content);

                //获取GoEasy数据
                var datas=message.content;

                //将json字符串转为javascript对象
                var data=JSON.parse(datas);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户月注册统计',  //标题
                        subtext:"来源网络",
                        link:"${path}/main/main.jsp",
                        target:"self"
                    },
                    tooltip: {},  //鼠标提示
                    legend: {
                        data:['小男孩','小姑娘']  //选项卡
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},  //自适应
                    series: [{
                        name: '小男孩',
                        type: 'bar',
                        data: data.boys
                    },{
                        name: '小姑娘',
                        type: 'bar',
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
        //},"json");
    });

    //发送消息
    // this.$goEasy.publish({
    //     channel: "my_channel", //替换为您自己的channel
    //     message: "Hello, GoEasy!" //替换为您想要发送的消息内容
    // });
</script>

<div id="main" style="width: 600px;height:400px;"></div>
