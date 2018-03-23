<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>总览</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/style.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox.src.js"></script>
    <script type="text/javascript" src="${request.getContextPath()}/js/utils/jbox/jquery.jBox-zh-CN.js"></script>

</head>
<body class="bg1">
<!-- 头部 -->
<#include "newPeony/top/newPeonyTop.ftl"/>
<!-- 主内容区 -->
<div class="content">
    <input type="hidden" id="pageContext" value="${request.getContextPath()}">
    <div class="zonglanSec">
        <!-- 列表滚动效果 -->
        <div class="info-dl">
            <h2 class="info-dl-title"><span class="icon6"></span>舆情概述</h2>
            <div id="box">正在加载...</div>
        </div>
        <div class="info-dl2">
            <div class="info-dl2-1" >
                <h2 class="info-dl-title"><span class="icon7"></span>舆情智库</h2>
                <div id="box2" class="box2" onmouseover="showList()">正在加载...</div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/overView/overView.js"></script>
</div>
</body>
</html>