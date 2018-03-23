<div class="header">
    <div class="h-left">
        <div class="logo"></div>
    </div>
    <ul class="h-right">
        <li><span class="icon1"></span>企业名称：<span>${userfront.name}</span></li>
        <li><span class="icon2"></span><a href="javascrpt:;">系统设置</a></li>
        <li><span class="icon3"></span><a href="javascrpt:;">帮助</a></li>
        <li><span class="icon4"></span><a href="javascrpt:;">关于</a></li>
        <li><span class="icon5"></span><a href="javascrpt:;">消息提醒</a></li>
    </ul>
</div>
<div class="wrapper">
    <!-- 导航条 -->
    <ul class="Tabnav">
    <#list menuSet_new as menu>
        <li> <a id="${menu.menuId}" href="${request.getContextPath()}${menu.url}">${menu.name}</a></li>
    </#list>
       <#-- <li><a href="javascript:;">总览</a></li>
        <li><a href="javascript:;">舆情监测</a></li>
        <li><a href="javascript:;">数据大屏</a></li>-->
    </ul>
</div>
<script>
    function gotoDataScreen(){
        window.open ( "${request.getContextPath()}/dataScreen/dataScreenMain", "_blank" ) ;
    }

</script>