<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据大屏</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/allScheme.css">
    <!-- 引入 echarts.js -->
    <script src="${request.getContextPath()}/js/newPeony/dataScreen/echarts.min.js"></script>
    <script src="${request.getContextPath()}/js/newPeony/dataScreen/china.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script src="${request.getContextPath()}/js/newPeony/dataScreen/cloudKeywords.js"></script>
     <script src="${request.getContextPath()}/js/newPeony/dataScreen/dataScreen.js"></script>
</head>
<body class="big-bg">
<input type="hidden" id="pageContext" value="${request.getContextPath()}">
<div class="wrapper">
    <!-- 第一模块 -->
    <div class="header">
        <div id="dataScreenFirst"></div>
    </div>
    <!-- 第二模块 -->
    <div class="content">
        <div id="sidebarNav" class="sidebarNav">
            <a class="" href="javascript:;" onclick="changeDataScreenView('null')"><div class="mt27">全部方案</div></a>
            <a id="dbshow" href="javascript:;"><div class="mt27">单个方案</div></a>
            <div id="dbmove" class="schemeFrame">
                <div class="sanjiao-big"></div>
                <ul class="schemeFrame_ul">
                <#list subjectValues as list>
                    <li><a class="" id="subject${list.id}" href="javascript:changeDataScreenView('${list.id}','${list.name}');">${list.name}</a></li>
                </#list>

                </ul>
            </div>
        </div>
        <div id="dataScreenView"></div>
    </div>
</div>

    <script type="text/javascript">
        var path ;
        var a;
        $(function () {
            path=$("#pageContext").val();
            changeDataScreenView('null');
            // 单个方案点击出现弹框
            a="1";
            $('#dbshow').click(function(){
                if(a=='1'){
                    $('#dbmove').show('show');
                    $(this).addClass("activeA");
                    $(this).prev("a").removeClass("activeA");
                    a="2";
                }else{
                    $('#dbmove').hide('show');
                    $(this).addClass("activeA");
                    $(this).prev("a").removeClass("activeA");
                    a="1";
                }
            })
        });
        function changeDataScreenView(id,name){
            //点击切换选中效果
            if(name==undefined){
                $('#dbshow').siblings("a").addClass("activeA");
                $('#dbshow').removeClass("activeA");
                $('#dbmove').hide('show');
            }else{
                $('#dbshow').siblings("a").removeClass("activeA");
                $('#dbshow').addClass("activeA");
                $('#dbmove').hide('show');
            }
            if(id!=null&&id!='null'){
                $("#subject"+id).addClass("IndexA");
                $("#subject"+id).parent("li").siblings("li").children("a").removeClass("IndexA");
            }

            a="1";
            document.getElementById("dataScreenFirst").innerHTML="";
            document.getElementById("dataScreenView").innerHTML = "";
            var url ="";
            var flag = true;
            if(id=="null"||id==null||id==""){
                url= encodeURI(path + "/dataScreen/dataScreenMainView");
            }else{
                url=encodeURI(path + "/dataScreen/dataScreenSubjectView?subjectid="+id);
                flag=false;
            }
            $.ajax({
                type: "post",
                dataType: "html",
                url: encodeURI(path + "/dataScreen/dataScreenFirstView"),
                data:{flagVal:flag,timeType:4},
                success: function (data) {
                    document.getElementById("dataScreenFirst").innerHTML = data;
                    $("#subjectName").html(name);
                    getViewBottom(url,flag,id);
                }
            });
        }
        function getViewBottom(url,flag,id){
            setInterval(function(){
                var time = new Date();
                var nowTime = dateFtt('yyyy年MM月dd日 HH:mm:ss',time);
                var time = nowTime.split(" ");
                var year = time[0];
                var hour = time[1];
                $("#yearTime").html(year);
                $("#hourTime").html(hour);
            },1000);
            $.ajax({
                type: "post",
                dataType: "html",
                url:url ,
                data:null,
                success: function (data) {
                    document.getElementById("dataScreenView").innerHTML = data;
                    if(flag){
                        initUserView();
                    }else{
                        initsubjectView(id);
                    }
                }
            });
        }

        function dateFtt(fmt,date)
        { //author: meizz
            var o = {
                "M+" : date.getMonth()+1,                 //月份
                "d+" : date.getDate(),                    //日
                "H+" : date.getHours(),                   //小时
                "m+" : date.getMinutes(),                 //分
                "s+" : date.getSeconds(),                 //秒
                "q+" : Math.floor((date.getMonth()+3)/3), //季度
                "S"  : date.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }

    </script>


    <script type="text/javascript">
        // 单个方案点击出现弹框
        $(function(){
            // $('#dbshow').toggle(function(){
            //     $('#dbmove').show('show');
            //     $(this).addClass("activeA");
            //     $(this).prev("a").removeClass("activeA");
            // },function(){
            //     $('#dbmove').hide('show');
            //     $(this).addClass("activeA");
            //     $(this).prev("a").removeClass("activeA");
            // });

            // $('#dbshow').click(function(){
            //     if(a=='1'){
            //         $('#dbmove').show('show');
            //         $(this).addClass("activeA");
            //         $(this).prev("a").removeClass("activeA");
            //         a="2";
            //         alert(a);
            //     }else{
            //         $('#dbmove').hide('show');
            //         $(this).addClass("activeA");
            //         $(this).prev("a").removeClass("activeA");
            //         a="1";
            //         alert(a);
            //     }
            // })
        })
    </script>
    <!-- JS end -->
    <!-- 弹出层 -->
    <div id="mask1" class="mask1">
        <!--在弹出层里面放一个DIV-->
        <div class="div1">
            <!--关闭按钮-->
            <a href="javascript:void(0);" id="btn_close" class="btn_close"></a>
            <!--放大的地图-->
            <div id="main11" style="float:left;width:60%;min-height:80%;margin-top:50px;"></div>
            <div id="main12" style="float:left;width:40%;min-height:80%;margin-top:50px"></div>
        </div>
    </div>
</body>
</html>