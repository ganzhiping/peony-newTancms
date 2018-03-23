<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>文章详细</title>
<#include "/common/global_css.ftl">
</head>
<body>
<!----头部--->
<#include "/common/top.ftl">
<!----头部-结束--->
<div class="bg">
    <div class="zhanwei color_hongse_n">
        <strong>当前位置：</strong>&nbsp;首页&nbsp;&gt;&nbsp;舆情专报&nbsp;&gt;&nbsp;文章详细 </div>
</div>
<div class="bg">
    <div style="background:#f6f6f6;font-size:20px;">
        <div class="wenzhangxiangxi_t" style="border-bottom:8px solid #fff;">
            <div><p style="font-weight:bold;font-size:40px;">${popular.title}</p></div>

            <p class="color_huise">作者：<span style="margin-right:20px;">${popular.writer}</span>      发布时间：<span>${popular.uploadTime}</span> </p>
            <p class="color_huise">
                <span>出处：${popular.descript}</span>
            </p>
        </div>
        <div style="border:2px solid #00cccc;">
		${popular.content}
        </div>
    </div>
    <!----底部-开始--->
<#include "/common/bottom.ftl">
    <!----底部-结束--->
</body>
</html>
