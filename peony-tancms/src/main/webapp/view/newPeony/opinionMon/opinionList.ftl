<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>舆情监测</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link href="${request.getContextPath()}/css/newPeony/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/style.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/jbox/jbox.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox.src.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox-zh-CN.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/My97DatePicker/WdatePicker.js"></script>
    <script src="${request.getContextPath()}/js/utils/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">
        $(function(){
            //选中条件
            $(".ol-list>li").click(function(){
                $(this).addClass('activeA');
                $("#set_default").css({color:'#a1a1a1'});
                $(this).siblings('li').removeClass("activeA");
            })
            <#if (queryRequire!=null)>
                var source ="${queryRequire.source}";
                var id_source = "#source"+source;
                $(id_source).parent("li").addClass("activeA");
                $(id_source).attr("checked","checked");
                var polarity="${queryRequire.polarity}";
                id_source = "#polarity"+polarity;
                $(id_source).parent("li").addClass("activeA");
                $(id_source).attr("checked","checked");
                var weblevel ="${queryRequire.weblevel}";
                id_source = "#weblevel"+weblevel;
                $(id_source).parent("li").addClass("activeA");
                $(id_source).attr("checked","checked");
                var dataType = "${queryRequire.dataType}";
                if(dataType==""||null==dataType){
                    var startTime ="${queryRequire.startTime}";
                    if(startTime!=""&&null!=startTime){
                        id_source = "#dateBar";
                        $("#dateBar").show();
                    }
                }else{
                    id_source = "#dataType"+dataType;
                }
                $(id_source).parent("li").addClass("activeA");
                $(id_source).attr("checked","checked");
            </#if>
        <#if (queryRequire==null)>
            var source ="0";
            var id_source = "#source"+source;
            $(id_source).parent("li").addClass("activeA");
            $(id_source).attr("checked","checked");
            var polarity="0";
            id_source = "#polarity"+polarity;
            $(id_source).parent("li").addClass("activeA");
            $(id_source).attr("checked","checked");
            var weblevel ="0";
            id_source = "#weblevel"+weblevel;
            $(id_source).parent("li").addClass("activeA");
            $(id_source).attr("checked","checked");
            var dataType = "3";
            id_source = "#dataType"+dataType;
            $(id_source).parent("li").addClass("activeA");
            $(id_source).attr("checked","checked");
        </#if>

        //点击选中当前li
        $(".rankUl>li").mousedown(function(){
            $(this).addClass('indexLi').siblings('li').removeClass('indexLi');
        });
        //点击进行排序
        $(".rankUl>li").each(function(){
            $(this).toggle(function(){
                $(this).children('i.turnDown').show();
                $("#orderStr").val("down");
                $(this).children('i.turnUp').hide();
            },function(){
                $(this).children('i.turnDown').hide();
                $("#orderStr").val("up");
                $(this).children('i.turnUp').show();
            });
        })
        //自定义日期
        $("#showDate").mousedown(function(){
            $("#dateBar").show();
        })
        // 批量操作
        $("#operation").click(function(){alert("1")
            var gouxuan=$("#operation").is(':checked');
            if(gouxuan){
                $('.con3-title_check').attr("checked", true);
            }else{
                $('.con3-title_check').attr("checked", false);
            }

        })


        })
    </script>
</head>
<body id="fbody">
<input type="hidden" id="pageContext" value="${request.getContextPath()}">
<input type="hidden" id="orderStr" value="">
<div class="searchSec">
    <form action="" id="form1" method="post">
        <ul class="searchTabCon">
            <li>
                <label for="">文章来源：</label>
                <ol class="ol-list">
                    <li><input type="radio" value="0"  id="source0"  name="source" style="display:none;"><label for="source0">全部</label></li>
                    <li><input type="radio" value="1"  id="source1"  name="source" style="display:none;"><label for="source1">网站</label></li>
                    <li><input type="radio" value="2"  id="source2"  name="source" style="display:none;"><label for="source2">博客</label></li>
                    <li><input type="radio" value="3"  id="source3"  name="source" style="display:none;"><label for="source3">微博</label></li>
                    <li><input type="radio" value="4"  id="source4"  name="source" style="display:none;"><label for="source4">问答</label></li>
                    <li><input type="radio" value="5"  id="source5"  name="source" style="display:none;"><label for="source5">视频</label></li>
                    <li><input type="radio" value="6"  id="source6"  name="source" style="display:none;"><label for="source6">学术</label></li>
                    <li><input type="radio" value="7"  id="source7"  name="source" style="display:none;"><label for="source7">微信</label></li>
                    <li><input type="radio" value="8"  id="source8"  name="source" style="display:none;"><label for="source8">推特</label></li>
                    <li><input type="radio" value="9"  id="source9"  name="source" style="display:none;"><label for="source9">电子报刊</label></li>
                    <li><input type="radio" value="10" id="source10" name="source" style="display:none;"><label for="source10">论坛</label></li>
                    <li><input type="radio" value="11" id="source11" name="source" style="display:none;"><label for="source11">政务</label></li>
                    <li><input type="radio" value="12" id="source12" name="source" style="display:none;"><label for="source12">客户端</label></li>
                    <li><input type="radio" value="13" id="source13" name="source" style="display:none;"><label for="source13">脸书</label></li>
                    <li><input type="radio" value="14" id="source14" name="source" style="display:none;"><label for="source14">领英</label></li>
                </ol>
            </li>
            <li>
                <label for="">情感属性：</label>
                <ol class="ol-list">
                    <li><input type="radio" value="0" id="polarity0" name="polarity" style="display:none;"><label for="polarity0">全部</label></li>
                    <li><input type="radio" value="1" id="polarity1" name="polarity" style="display:none;"><label for="polarity1">负面</label></li>
                    <li><input type="radio" value="2" id="polarity2" name="polarity" style="display:none;"><label for="polarity2">非负面</label></li>
                </ol>
            </li>
            <li>
                <label for="">网站等级：</label>
                <ol class="ol-list">
                    <li><input type="radio" value="0"  id="weblevel0"  name="weblevel" style="display:none;"><label for="weblevel0">全部</label></li>
                    <li><input type="radio" value="1"  id="weblevel1"  name="weblevel" style="display:none;"><label for="weblevel1">非常重要</label></li>
                    <li><input type="radio" value="2"  id="weblevel2"  name="weblevel" style="display:none;"><label for="weblevel2">重要</label></li>
                    <li><input type="radio" value="3"  id="weblevel3"  name="weblevel" style="display:none;"><label for="weblevel3">一般</label></li>
                </ol>
            </li>
            <li>
                <label for="">日期范围：</label>
                <ol class="ol-list">
                    <li><input type="radio" value="0"  id="dataType0"  name="dataType" style="display:none;"><label for="dataType0">全部</label></li>
                    <li><input type="radio" value="1"  id="dataType1"  name="dataType" style="display:none;"><label for="dataType1">今天</label></li>
                    <li><input type="radio" value="2"  id="dataType2"  name="dataType" style="display:none;"><label for="dataType2">24小时</label></li>
                    <li><input type="radio" value="3"  id="dataType3"  name="dataType" style="display:none;"><label for="dataType3">2天</label></li>
                    <li><input type="radio" value="4"  id="dataType4"  name="dataType" style="display:none;"><label for="dataType4">一周</label></li>
                    <li><input type="radio" value="5"  id="dataType5"  name="dataType" style="display:none;"><label for="dataType5">一个月</label></li>
                    <li>
                        <b id="showDate"> 自定义</b>
                        <span id="dateBar">
                                        <input class="customDate" type="text" id="startTime" name="startTime" onclick="WdatePicker()" value="${queryRequire.startTime}">
                                        <i>到</i>
                                        <input class="customDate" type="text" id="endTime" name="endTime" onclick="WdatePicker()" value="${queryRequire.endTime}">
                                    </span>
                    </li>
                </ol>
            </li>
            <li>
                <div class="searchBtn">
                    <input class="active-input" type="button" value="查询" onclick="searchByQuery()"/>
                </div>
            </li>
            <li>
                <input id="set_default" class="set_default" type="button" onclick="setAsDefault()" value="设为默认条件">
            </li>
        </ul>
    </form>
</div>
<div class="searchCon">
    <div class="bar1">
        <ul class="rankUl" id="rankUl">
            <li class="indexLi" onclick="orderType('0')"> <input name="order" type="hidden" value="0">综合</li>
            <li  onclick="orderType('1')">时间<input name="order" type="hidden" value="1"><i style="display:inline-block;" class="turnDown">↓</i><i class="turnUp">↑</i></li>
            <li  onclick="orderType('2')">关注度<input name="order" type="hidden" value="2"><i class="turnDown">↓</i><i class="turnUp">↑</i></li>
            <li  onclick="orderType('3')">等级<input name="order" type="hidden" value="3"><i class="turnDown">↓</i><i class="turnUp">↑</i></li>
        </ul>
        <div class="searchForm">
            <input class="input_text" type="text" id="searchContent" value="" placeholder="在结果中搜索">
            <input class="input_btn" type="button" onclick="searchByContent()" value="搜索">
        </div>
    </div>
    <div id="opinionMonRight"></div>
</div>
<script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/opinionMon.js"></script>

</body>
</html>