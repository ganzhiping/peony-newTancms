    <ul id="con1">
        <#list overViewTopList as list>
            <li>
                <div class="con-left">
                    <span class="label1">负面</span>
                    <span><a href="javascript:gotoInfoPage('${list.id}');">${list.title}</a></span>
                </div>
                <div class="con-right">
                    <span class="source">${list.website}</span>
                    <span class="time">${list.topTime}</span>
                </div>
            </li>
        </#list>
    </ul>
    <ul id="con2"></ul>
