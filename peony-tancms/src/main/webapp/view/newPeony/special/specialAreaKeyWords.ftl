
<!-- 弹出层1 -->
<div id="specialArea" class="mask1">
    <!--在弹出层里面放一个DIV-->
    <div class="div1">
        <!--放大的地图-->
        <div>
            <div class="div1_title"><span class="circle">●</span>地域词库<span class="circle">●</span></div>
            <div class="div_con">
                <div>
                    <div class="div_conLeft">已选择</div>
                    <div class="div_conRight">
                        <div class="selected" id='iframeKeywords'>

                        </div>
                    </div>
                </div>
                <div>
                    <div class="div_conLeft">省/直辖市</div>
                    <div class="div_conRight">
                        <div class="local">
                        <#list areaKeywords as list>
                            <a id="${list.regionId}" href="javascript:changeDivConRight('${list.regionId}');">${list.regionAbbr}</a>
                        </#list>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="div_conLeft">具体县市</div>
                    <div class="div_conRight" id="div_conRight">

                    </div>
                </div>
                <div class="btn-group">
                    <input type="button" onclick="makeSureKeywords()" value="确认">
                    <input  class="index_input" type="button" onclick="closeAreaDiv()" value="取消">
                </div>
            </div>
        </div>
    </div>
</div>