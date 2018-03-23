
    <ul class="ol">
    <#list pagination.list as thinkInfo>
        <li>
            <p>
                <a class="" href="javascript:gotoNewInfo('${thinkInfo.pubinfoId}');">${thinkInfo.title}</a>
                <span class="source2">${thinkInfo.author}</span>
                <span class="time2">${thinkInfo.timeStr}</span>
            </p>
            <div class="info-details">
                <div>
                    <img src="" alt="">
                    <a href="javascript:gotoNewInfo('${thinkInfo.pubinfoId}');">${thinkInfo.summary}</a>
                </div>
            </div>
        </li>
    </#list>
    </ul>

        ${pageStr}