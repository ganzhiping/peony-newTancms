
<div class="s_bg1"></div>
<div class="s_bg2"></div>
<div class="header-sec1">全网舆情数据信息共<span id="webAllData">${allWebData.webData}</span>条</div>
<div class="header-sec2">
    <p class="header-sec2-p1">今日舆情信息条数
        <span>
             <#if (allWebData.todayData!=null)>
             ${allWebData.todayData}
             </#if>
             <#if (allWebData.todayData==null)>
                 0
             </#if>
            </span>
    </p>
    <p class="header-sec2-p2"><span id="yearTime"></span>&nbsp;&nbsp;<span id="hourTime"></span></p>
</div>
<div id="chooseTime" class="header-sec3">
    <a  href="javascript:makeDataScreen(this);"><input type="hidden"value="1" name="timeType">今&nbsp;&nbsp;&nbsp;日</a>
    <a href="javascript:makeDataScreen(this);"><input type="hidden"value="2" name="timeType">近3天</a>
    <a href="javascript:makeDataScreen(this);"><input type="hidden"value="3" name="timeType">近一周</a>
    <a class="activeA" href="javascript:makeDataScreen(this);"><input type="hidden"value="4" name="timeType">近一月</a>
</div>
