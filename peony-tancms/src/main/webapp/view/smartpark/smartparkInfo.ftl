<!doctype html>
<html lang="en">
<head>
	<title>舆情详细内容</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Cache-Control" content="no-siteapp">
	<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<!--开启对web app的支持-->
    <meta name="apple-mobile-web-app-capable" content="yes" />
	<!--主要是正对苹果手机将数字自动识别为号码及iPhone手机全屏显示和状态栏功能-->
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile=web-app-status-bar-style" content="black" />
    <!-- 针对浏览器将字体自动调大 -->
    <meta name="wap-font-scale" content="no">
    <link rel="stylesheet" href="${request.getContextPath()}/css/smartpark/css/mobile.css">
</head>
<body class="mhome bg1">
    <div class="fixedTop">
        <span>牡丹舆情</span>
    </div>
	<div class="bgW">
		<div class="letterSec">
			<p class="letterTitle">${newInfoManager.title}</p>
            <div style="padding:10px;border-radius:8px;background:#eee;">
                <p>
                    <span>来源：<i>${newInfoManager.website}</i></span>
                    <span>日期：${newInfoManager.publishDate}</span>
                    <span>作者：<i>${newInfoManager.author}</i></span>
                </p>
                <p>涉猎词语：${newInfoManager.hitKeywords}</p>
                <p>原文链接：<a href="${newInfoManager.url}"><#if (newInfoManager.url?length gt 100)>${newInfoManager.url?substring(0,100)}...<#else>${newInfoManager.url}</#if></a></p>
            </div>
			<div class="letterCon">
              ${newInfoManager.content}
      		</div>
		</div>
		<div class="btnGroup">
			<div>
				<a class="btn-agree" href="javascript:;">返回</a>
			</div>
		</div>
	</div>
</body>
</html>