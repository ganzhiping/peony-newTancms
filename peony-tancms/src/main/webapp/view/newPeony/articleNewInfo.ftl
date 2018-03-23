<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文章详情</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/articleDetails.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script>
        $(function(){
            //选中关注
            $(".attention2").each(function(){
                $(this).toggle(function(){
                    $(this).text("已关注").addClass('attention2-2').removeClass('attention2');
                },function(){
                    $(this).text("关注").addClass('attention2').removeClass('attention2-2');
                })
            })
        })
    </script>
</head>
<body>
<!-- 头部 -->
<div class="header">
    <div class="h-left">
        <div class="logo"></div>
    </div>
    <ul class="h-right">
        <li><span class="icon1"></span>企业名称：<span>用户名称</span></li>
        <li><span class="icon2"></span><a href="javascrpt:;">系统设置</a></li>
        <li><span class="icon3"></span><a href="javascrpt:;">帮助</a></li>
        <li><span class="icon4"></span><a href="javascrpt:;">关于</a></li>
        <li><span class="icon5"></span><a href="javascrpt:;">消息提醒</a></li>
    </ul>
</div>
<div class="wrapper">
    <div class="Nav"><a href="javascript:;">文章详情</a></div>
</div>

<div class="content">
    <div class="article">
        <div class="articleHead">
            <div>
                <h2 class="articleTitle">${newInfoManager.title}</h2>
                <#if (newInfoManager.is_concern==1)>
                    <a class="attention2" href="javascript:addConcern('${newInfoManager.id}','${newInfoManager.pageid}','${newInfoManager.subjectid}');">已关注</a>
                <#else>
                    <a class="attention2" href="javascript:addConcern('${newInfoManager.id}','${newInfoManager.pageid}','${newInfoManager.subjectid}');">关注</a>
                </#if>
            </div>
            <div class="aiticleHead-bottom">
                <a class="selected" href="javascript:;"><b class="icon-collection"></b>收藏</a>
                <a href="javascript:;"><b class="icon-mail"></b>邮箱</a>
                <a href="javascript:;"><b class="icon-export"></b>导出</a>
                <a href="javascript:;"><b class="icon-delete"></b>删除</a>
            </div>
        </div>
        <div class="articleSubsec">
            <div class="SubsecWrap">
                <div>
                    <span class="w460">来源：<i>${newInfoManager.website}</i></span>
                    <span>日期：${newInfoManager.publishdate?string("yyyy-MM-dd HH:mm:ss")}</i></span>
                    <span class="fr">作者：<i>${newInfoManager.author}</i></span>
                </div>
                <div>
                    <span>涉猎词语：${newInfoManager.hitKeywords}</span>
                </div>
                <div>
                    <span>原文链接：<a href="${newInfoManager.url}"><#if (newInfoManager.url?length gt 100)>${newInfoManager.url?substring(0,100)}...<#else>${newInfoManager.url}</#if></a></span>
                </div>
            </div>
        </div>
        <div class="articleCon">
            ${newInfoManager.content}
        </div>
    </div>
</div>


</body>
</html>