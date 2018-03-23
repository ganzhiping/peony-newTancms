<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${pageType_}</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/scheme.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/jbox.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/special.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox.src.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
    $(function(){
        showMask();
    })
    function showMask(){
        $("#showMask").click(function(){
            $("#mask1").show();
        });
        $("#btn_close").click(function(){
            $("#mask1").hide();
        });
        $("#showMask2").click(function(){
            $("#mask2").show();
        });
        $("#btn_close2").click(function(){
            $("#mask2").hide();
        });
    }
</script>
</head>
<body id="fbody">
<!-- 头部 -->
<input type="hidden" id="pageContext" value="${request.getContextPath()}">

<!-- 新增专题页面 -->
<div class="schemeSec">
    <form class="schemeForm" action="#" id="specialForm"  onsubmit="return false;" >
        <input type="hidden" value="${pageType}" name="pageType">
        <ul>
            <li>
                <label for="">专题名称：</label>
                <input type="hidden" value="${special.subjectid}" name="pid" id="pid">
                <input type="text" required="required"  id="name" name="name" value="${subjectInfo.name}">
            </li>
            <li>
                <label for="">地域名称：</label>
                <input type="hidden" name="area" value="area" id="area">
                <input type="hidden" name="areaId" value="${special.areaId}">
                <input type="text" name="areaStr" id="areaStr" placeholder="i 地域名称中间用“空格”隔开，词语之间为“或”的关系" value="${special.areaStr}">
                <a class="regionThesaurus" href="javascript:areaLib();">地域词库</a>
            </li>
            <li>
                <label for="">主关键词：</label>
                <input type="hidden" name="main" value="main">
                <input type="hidden" name="mainId" value="${special.mainId}">
                <input type="text" required="required" id="mainStr"  name="mainStr" VALUE="${special.mainStr}" placeholder="i 主关键词中间用“空格”隔开，词语之间为“或”的关系">
            </li>
            <li>
                <label for="">倾向性词：</label>
                <input name="Qxx" type="hidden" value="qxx">
                <input type="hidden" name="qxxId" value="${special.qxxId}">
                <input type="text" name="qxxStr" id="qxxStr" value="${special.qxxStr}" placeholder="i 倾向性词中间用“空格”隔开，词语之间为“或”的关系            i 倾向性词和上面主关键词为“与”的关系">
                <a class="emotionThesaurus" href="javascript:feeLib();">情感词库</a>
            </li>
            <li>
                <label for="">过滤词：</label>
                <input type="hidden" name="glc" value="glc">
                <input type="hidden" name="glcId" value="${special.glcId}">
                <input type="text" name="glcStr" value="${special.glcStr}" id="glcStr" placeholder="i 过滤词中间用“空格”隔开，词语之间为“或”的关系            i 包含过滤列表中任一词语的文章即被排除掉">
            </li>
            <li class="textCenter">
                <input class="input-Btn" type="button" onclick="submitForm()" value="提 交">
            </li>
        </ul>
    </form>
</div>

</body>
<script type="text/javascript" src="${request.getContextPath()}/js/newPeony/special/special.js"></script>
</html>