<!doctype html>
<html lang="en">
<head>
	<title>信息列表页</title>
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
	<div class="pt10">
        <div class="searchGroup">
            <div class="searchBox">
                <input id="searchText" class="searchText" type="text" id = "title" placeholder="作者、标题等关键字搜索">
                <input class="searchPic" type="button" value="" onclick="searchList()">
                <a href="javascript:referrer();" id="clearText" class="clearText">X</a>
            </div>
        </div>
		<!-- 数据为空时显示以下内容 -->


        <div id = "infoDiv">
            <#if smartParkList?exists>
                <#if (null==smartParkList)>
                    <p class="no-info">该用户目前还没有舆情信息……</p>
                </#if>
            </#if>
			<dl class="records">

				<#list smartParkList as list>
					<dd>
						<a href="javascript:gotoInfoPage('${list.id}');">
							<p class="personInfo">
								<b class="userName fs16">${list.title}</b>
								<span class="mr20 colorGray">${list.description}</span>
							</p>
							<p class="hospitalInfo">
								<span class="fl">${list.webSite}</span>
								<span class="fr colorGray">${list.publishDate}</span>
							</p>
						</a>
					</dd>
				</#list>
			</dl>
			<!-- ajax加载部分 -->
			<div class="content">
				<dl class="records lists">
					<!-- ajax通过json返回的数据页面 -->
				</dl>

			</div>
        </div>
	</div>
	<!-- 加载更多插件样式 -->
    <link rel="stylesheet" href="${request.getContextPath()}/js/smartpark/js/dist/dropload.css">
	<!-- jQuery1.7以上 或者 Zepto 二选一，不要同时都引用 <script src="js/dist/dropload.min.js"></script>-->
	<script src="${request.getContextPath()}/js/smartpark/js/jquery-1.9.1.min.js"></script>
    <script src="${request.getContextPath()}/js/smartpark/js/dist/dropload.min.js"></script>
	
	<script type="text/javascript">
		var pageContent = '${request.getContextPath()}';
        function gotoInfoPage(id) {
            window.open(encodeURI(pageContent + "/smartPark/newsInfo?id=" + id + "&timestamp=" + Date.parse(new Date())));
        }
        
        function searchList(){
            var title = $("#searchText").val();
            if(title==null||""==title|| title==undefined){
                alert("查询词语不能为空!");
                return;
            }
            $.ajax({
                type: 'GET',
                url: '/smartPark/searchList',
                dataType: 'json',
                data:{title:title},
                success: function(data){
                    $("#infoDiv").html("");
                    var result = '';
                    result +='<div class="content">'+
                    '<dl class="records lists">';
                    if(data.length<1){
                        result += '<p class="no-info">未查询到数据</p>';
                    }else{
                        for(var i = 0; i < data.length; i++){
                            var list = data[i];
                            result += '<dd>'+
                                    '<a href="javascript:gotoInfoPage('+"'"+list.id+"');"+'>'+
                                    '<p class="personInfo">'+
                                    '<b class="userName fs16">'+list.title+'</b>'+
                                    '<span class="mr20 colorGray">'+list.description+'</span>'+
                                    '</p>'+
                                    '<p class="hospitalInfo">'+
                                    '<span class="fl">'+list.webSite+'</span>'+
                                    '<span class="fr colorGray">'+list.publishDate+'</span>'+
                                    '</p>'+
                                    '</a>'+
                                    '</dd>';
                        }
                    }
                    result += '</dl></div>';
                    $('#infoDiv').html(result);
                },
                error: function(xhr, type){
                    alert('系统错误，请稍后再试!');
                }
            });
		}

		function referrer(){
            window.location.reload();
        }

	$(function(){
	    var counter = 2;
	    // 每页展示6个
	    var num = 10;
	    var pageStart = 0,pageEnd =0;
		var startTime='${startTime}';var endTime='${endTime}';
        var title = $("#searchText").val();
        if(title==""||title==undefined || title==null){
            title=null;
		}
	    // dropload
	    $('.content').dropload({
	        scrollArea : window,
	        loadDownFn : function(me){
                pageEnd = num * counter;
                pageStart = pageEnd - num;
	            $.ajax({
	                type: 'GET',
	                url: '/smartPark/ajaxAddList',
	                dataType: 'json',
					data:{pageStart:pageStart,pageEnd:num,startTime:startTime,endTime:endTime,title:title},
	                success: function(data){
	                    var result = '';
	                    counter++;
	                    for(var i = 0; i < data.length; i++){
						    var list = data[i];
						    result += '<dd>'+
                                            '<a href="javascript:gotoInfoPage('+"'"+list.id+"');"+'>'+
                                                '<p class="personInfo">'+
                                                    '<b class="userName fs16">'+list.title+'</b>'+
                                                    '<span class="mr20 colorGray">'+list.description+'</span>'+
                                                '</p>'+
                                                '<p class="hospitalInfo">'+
                                                    '<span class="fl">'+list.webSite+'</span>'+
                                                    '<span class="fr colorGray">'+list.publishDate+'</span>'+
                                                '</p>'+
                                            '</a>'+
                                        '</dd>';
	                        if((i + 1) >= data.length){
	                            // 锁定
	                            me.lock('up');
	                            // 无数据
	                            //me.noData();
	                            break;
	                        }
	                    }
	                    if(data.length<10||data==null){
                            me.noData();
                        }
	                    // 为了测试，延迟1秒加载
	                    //setTimeout(function(){
	                        $('.lists').append(result);
	                        // 每次数据加载完，必须重置
	                        me.resetload();
	                   // },500);
	                },
	                error: function(xhr, type){
	                    alert('系统错误，请稍后再试!');
	                    // 即使加载出错，也得重置
	                    me.resetload();
	                }
	            });
	        }
	    });
        //出现清除图标
        $("#searchText").keyup(function(){
            $("#clearText").show();
        })
        $("#clearText").click(function(){
            $("#searchText").val("")
            $(this).hide();
        })
	});
	</script>
</body>
</html>