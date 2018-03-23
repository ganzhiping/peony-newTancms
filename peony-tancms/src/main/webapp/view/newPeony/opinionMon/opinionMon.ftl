<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>舆情监测</title>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <!-- <link href="${request.getContextPath()}/css/newPeony/ztree/zTreeStyle.css" rel="stylesheet" type="text/css" /> -->
    <link href="${request.getContextPath()}/css/newPeony/ztree/awesomeStyle/font-awesome.min.css" rel="stylesheet">
    <link href="${request.getContextPath()}/css/newPeony/ztree/awesomeStyle/awesome.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/style.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/special.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/jbox.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox.src.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox-zh-CN.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/My97DatePicker/WdatePicker.js"></script>
    <script src="${request.getContextPath()}/js/utils/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/opinionMonAll.js"></script>
    <script>
        $(function () {
            var flag =${flag};
            if(!flag){
                $("#iframe").attr("src","/special/specialSubject?pageType=1&subjectid="+$("#currentNodeId").val());
            }else{
                $("#iframe").attr("src","/opinionMon/opinionList");
            }
        })
        function changeIframe(type,o){
            var id = $("#currentNodeId").val();
            var isParent = $("#isParent").val();
            if(type=='1'){
                $("#titleV1").addClass("index");
                $("#titleV1").siblings('li').removeClass('index');
                $("#iframe").attr("src","/opinionMon/opinionList?subjectid="+$("#currentNodeId").val());
                $("#viewType").val('1');
            }else if(type=="2"){
                if(id==null||id==""||id=="null" || isParent=='true' || isParent== true ){
                    alert("请选择专题后查看!");
                    return;
                }
                $("#titleV2").addClass("index");
                $("#titleV2").siblings('li').removeClass('index');
                $("#iframe").attr("src","/opinionMon/opinionRouteReport?subjectid="+$("#currentNodeId").val());
                $("#viewType").val('2');
            }else if(type=="3"){
                $("#titleV3").addClass("index");
                $("#titleV3").siblings('li').removeClass('index');
                if(id!=""&&id!=null && isParent=="true"){
                    //pageType:1：新增  2：修改
                    $("#iframe").attr("src","/special/specialSubject?pageType=1&isParent=true&subjectid="+$("#currentNodeId").val());
                    $("#viewType").val('3');
                }else if(id!=""&&id!=null && isParent=="false"){
                    $("#iframe").attr("src","/special/specialSubject?pageType=2&isParent=false&subjectid="+$("#currentNodeId").val());
                    $("#viewType").val('3');
                }else if(id==""||id==null|| id==undefined||id=="1"){
                    alert("请选择舆情分类或专题！");
                    return;
                }
            }
        }

    $(function(){
        $("#dbhide").click(function(){
            $('#sidebar').hide('slow');
            $("#main").animate({width:'100%'},500,function(){
                $('#dbshow').show();
                $('#dbhide').hide();
            })
        })
        $("#dbshow").click(function(){
            $('#sidebar').show('slow');
            $("#main").animate({width:'78%'},500,function(){
                $('#dbhide').show();
                $('#dbshow').hide();
            })
        })
    })

    </script>
</head>
<body>
<!-- 头部 -->
<#include "newPeony/top/newPeonyTop.ftl"/>
<input type="hidden" id="pageContext" value="${request.getContextPath()}">
<input type="hidden" id="currentNodeId" value="${subjectid}">
<input type="hidden" id="isParent" value="">
<input type="hidden" id="subjectNameValue" value="">
<input type="hidden" id="viewType" value="0">
<div class="content">
       <div id="sidebar" class="sidebar">
            <a href="javascript:;" class="dbhide" id="dbhide"></a>
            <div class="addGroup">
                <div class="addClassify" onclick="addUserAllView()">添加分类</div>
                <div class="addSpecial" onclick="addSubjectView()">添加专题</div>
            </div>
           <div class="treeTd" width="160" valign="top">
               <ul class="tree_ul ztree" id="subjectTree"></ul>
           </div>
        </div>

    <div id="main" class="main">
        <a href="javascript:;" class="dbshow" id="dbshow" style="display:none;"></a>
        <div class="subjectTitle" id="opinionClass">舆情监测(所属类别:<span id="typeValueName">全部专题</span>)</div>
        <ul  id="TabNav" class="TabNav">
            <li class="index" id="titleV1"><a href="javascript:changeIframe('1');">舆情列表</a></li>
            <li class="" id="titleV2"><a href="javascript:changeIframe('2');">传播路线</a></li>
            <li class="" id="titleV3"><a href="javascript:changeIframe('3');"><span id="pageType"> 新增专题</span></a></li>
        </ul>
        <!-- 搜索页面 -->
        <iframe src="" id="iframe" name="iframe" style="border:none"  scrolling="no" width="100%" height="715px"> </iframe>
    </div>
</div>
<div id="specialView"></div>
</body>
<script>
    function closeAreaDiv(){
        $("#specialArea").hide();
    }

    function changeDivConRight(regionid){
        var data = {
            regionId:regionid
        }
        $.ajax({
            type: "POST",
            dataType: "html",
            url:  "/special/specialArea",
            data: data,
            success: function (data) {
                window.parent.document.getElementById("div_conRight").innerHTML = data;
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });
    }

    function checkValue(val,id){
        var a1 = "#"+id;
        var flag = $(a1).is(':checked');
        if(flag){
            var values = " "+val;
            var val_ =  '<span  onmouseover="del(this)" class="id_'+id+'" onmouseout="moveX(this)">'+values +'<i onmousedown="del2(this,'+"'"+id+"'"+')"  class="del_pic">X</i></span>';
            $("#iframeKeywords").append(val_);
        }else{
            var id_ = ".id_"+id;
            $(id_).remove();
            //var a2 = $("#iframeKeywords").html();
            //$("#iframeKeywords").html(a2.replace(val,""));
        }
    }

    function checkValueRegion(o,idR){
        var flag = $(o).is(':checked');
        //$(o).parent('div').next('div').children('span').children('input').attr("checked","checked");
        if(flag){
            var aa = $(o).parent('div').next('div').children('span').children('label');
            var a1='<span  onmouseover="del(this)" class="id_'+idR+'" onmouseout="moveX(this)"> '+$(o).next("label")[0].innerText+' <i onmousedown="del2(this,+'+"'"+idR+"'"+')"  class="del_pic">X</i></span>';
            for(var i =0;i<aa.length;i++){
                var id = aa[i].getElementsByTagName("input")[0].value;
                var id_ = "#"+id;
                var is_checked = $(id_).is(':checked');
                if(is_checked){
                    continue;
                }else{
                    $(id_).attr("checked","checked");
                    var val_ =  '<span  onmouseover="del(this)" class="id_'+id+'" onmouseout="moveX(this)"> '+aa[i].innerText+' <i onmousedown="del2(this,'+"'"+id+"'"+')"  class="del_pic">X</i></span>';
                    a1 += val_;
                }
            }
            $("#iframeKeywords").append(a1);
        }else{
            $(o).parent('div').next('div').children('span').children('input').removeAttr("checked");
            var aa = $(o).parent('div').next('div').children('span').children('label');
            for(var i =0;i<aa.length;i++){
                var id = aa[i].getElementsByTagName("input")[0].value;
                var id_ = ".id_"+id;
                $(id_).remove();
            }
            $(".id_"+idR).remove();
        }
    }

    function areaChooseAll(){
        var val = "";
        if($("#checkArea").is(":checked")){
            $(".chooseRegion").children("input").each(function(){
                $(this).attr("checked","checked");
                var text = $(this).next("label");
                var v_input = text.children("input").val();
                var text_ = text[0].innerText;
                var val_ =  '<span  onmouseover="del(this)" class="id_'+v_input+'" onmouseout="moveX(this)"> '+text_+' <i onmousedown="del2(this,'+"'"+v_input+"'"+')"  class="del_pic">X</i></span>';
                val+=val_;
            });
            $(".counties").children("span").children("input").each(function(){
                $(this).attr("checked","checked");
                var text = $(this).next("label");
                var v_input = text.children("input").val();
                var text_ = text[0].innerText;
                var val_ =  '<span  onmouseover="del(this)" class="id_'+v_input+'" onmouseout="moveX(this)"> '+text_+' <i onmousedown="del2(this,'+"'"+v_input+"'"+')"  class="del_pic">X</i></span>';
                val+=val_;
            });
            $("#iframeKeywords").append(val);
        }else{
            $(".chooseRegion").children("input").each(function(){
                $(this).removeAttr("checked");
                var text = $(this).next("label");
                var v_input = text.children("input").val();
                $(".id_"+v_input).remove();
            });
            $(".counties").children("span").children("input").each(function(){
                $(this).removeAttr("checked");
                var text = $(this).next("label");
                var v_input = text.children("input").val();
                $(".id_"+v_input).remove();
            });

        }

    }

    function makeSureKeywords(){
        //$("#iframeKeywords").html()
        var a2 = "";
        var span_vs = $("#iframeKeywords").children("span");
        for(var i = 0;i<span_vs.length;i++){
            var span_v = span_vs[i].innerText;
            a2 += " "+span_v;
        }
        var a3 =window.parent.frames["iframe"].$("#areaStr").val();
        window. parent.frames["iframe"].$("#areaStr").val($.trim(a2) +" "+a3);
        $("#specialArea").hide();
    }


    function del(e){
        $(e).addClass("del_state");
        $(e).children('i').show();
    }

    function moveX(e){
        $(e).removeClass("del_state");
        $(e).children('i').hide();
    }

    function del2(e,id){
        $("#"+id).removeAttr("checked");
        $(e).parent('span').remove();

    }

    function changePolarityContent(id){
        var data = {
            polarityId:id
        }
        $.ajax({
            type: "POST",
            dataType: "html",
            url:  "/special/specialPolarity",
            data: data,
            success: function (data) {
                window.parent.document.getElementById("polarity_div").innerHTML = data;
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });
    }


    function makeSurePolaritywords(){
        var ret = "";
        $("#polarityContent").children("span").children("input").each(function(){
            var flag = $(this).is(":checked");
            if(flag){
                var text = $(this).next("label");
                var text_ = text[0].innerText;
                ret += text_ + " ";
            }
        });
        var iframe_str =window.parent.frames["iframe"].$("#qxxStr").val();
        window. parent.frames["iframe"].$("#qxxStr").val($.trim(ret) +" "+iframe_str);
        $("#specialArea").hide();
    }

    function polarityChooseAll(){
        var flag = $("#checkPolarity").is(":checked");
        if(flag){
            $("#polarityContent").children("span").children("input").each(function(){
                $(this).attr("checked","checked");
            });
        }else{
            $("#polarityContent").children("span").children("input").each(function(){
                $(this).removeAttr("checked");
            });
        }

    }

</script>
</html>