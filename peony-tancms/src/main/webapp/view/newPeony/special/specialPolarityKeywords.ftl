<!-- 弹出层2 -->
<div id="specialArea" class="mask1">
    <!--在弹出层里面放一个DIV-->
    <div class="div1">
        <!--放大的地图-->
        <div>
            <div class="div1_title"><span class="circle">●</span>事件词库<span class="circle">●</span></div>
            <div class="div_con">
                <div>
                    <div class="div_conLeft">事件类别</div>
                    <div class="div_conRight">
                        <div class="eventClass">
                        <#list polarityName as list>
                            <a href="javascript:changePolarityContent('${list.id}');">${list.name}</a>
                        </#list>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="div_conLeft">关键词</div>
                    <div id="polarity_div"></div>
                </div>
            </div>
            <div class="btn-group">
                <input type="button" onclick="makeSurePolaritywords()" value="确认">
                <input  class="index_input" type="button" onclick="closeAreaDiv()" value="取消">
            </div>
        </div>
    </div>
</div>