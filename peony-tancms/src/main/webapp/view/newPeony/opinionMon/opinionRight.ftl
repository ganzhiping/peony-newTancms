<div class="bar2">
    <div class="bar2-left">
        <label><input id="operation" onclick="piliang()" type="checkbox">批量操作：</label>
        <a class="" href="javascript:showAttention();"><b class="icon-collection"></b>收藏</a>
        <a href="javascript:toSendMail();"><b class="icon-mail"></b>邮箱</a>
        <a href="javascript:exportInfo('1');"><b class="icon-export"></b>导出</a>
        <a href="javascript:deleteInfo('true');"><b class="icon-delete"></b>删除</a>
    </div>
    <div class="bar2-right">
        <span>共${opinionPage.infoCount}条信息</span>
        <a href="javascript:exportInfo('2');">全部导出</a>
        <span>
          <#if (opinionPage.pageNum>1)>
              <a href="javascript:changePageNo('${((opinionPage.pageNum)?number-1)}');">&lt;</a>
          </#if>
          <#if (opinionPage.pageNum==1)>
              <a href="javascript:;" title="当前为首页">&lt;</a>
          </#if>
              <b>${opinionPage.pageNum}</b><i>/</i><b>${opinionPage.totalPage}</b>
          <#if (opinionPage.pageNum>0)&&((opinionPage.pageNum)?number &lt;
          (opinionPage.totalPage)?number)>
              <a href="javascript:changePageNo('${(opinionPage.pageNum)?number+1}');">&gt;</a>
          </#if>
          <#if ((opinionPage.pageNum)?number==(opinionPage.totalPage)?number)>
              <a href="javascript:;" title="当前为尾页">&gt;</a>
          </#if>
        </span>
    </div>
</div>
<#if (opinionCondition?size==0)>
<div class="noFind">未查询到结果</div>
</#if>
<#if (opinionCondition?size!=0)>
<ul class="con3" id="opinionCondition_li">
    <#if ("true"==sendManagerRole||true == sendManagerRole)>
        <#list opinionCondition as list>
            <li>
                <div class="con3-title">
                    <input type="hidden" id="polarity${list.id}" value="${list.polarity}">
                    <input class="con3-title_check" type="checkbox" name="chk_list" value="${list.id}">
                    <a href="javascript:gotoInfoPage('${list.id}');">${list.title}</a>
                    <span class="feeling" >
                        <a href="javascript:changePolarity('${list.pageid}','${list.subjectid}','${list.id}');">
                    <#if (0==list.polarity)>
                        <span id="pol${list.id}">非负面</span>
                    </#if>
                            <#if (1==list.polarity)>
                                <span id="pol${list.id}">非负面</span>
                            </#if>
                            <#if (-1==list.polarity)>
                                <span id="pol${list.id}">负面</span>
                            </#if></a>
                    </span>
                    <#if (list.isConcern!=null)>
                        <a class="attention" href="javascript:addConcern('${list.id}','${list.pageid}','${list.subjectid}');"><span id="isConcern${list.id}">已关注</span></a>
                    <#else>
                        <a class="attention" href="javascript:addConcern('${list.id}','${list.pageid}','${list.subjectid}');"><span id="isConcern${list.id}">关注</span></a>
                    </#if>
                </div>
            <#if (list.highligiht!=null)>
                <div class="con3-sec">${list.highligiht}</div>
            <#else>
                <div class="con3-sec">${list.summary}</div>
            </#if>
                <div class="con3-bottom">
                    <div class="bottom-left">
                        <span>${list.publishdate?string("yyyy-MM-dd HH:mm:ss")}</span>
                        <span>${list.website}</span>
                        <span class="similar">共${list.groupcount}篇相似</span>
                    </div>
                    <div class="bottom-right">
                        <a class="" href="javascript:showAttention('${list.pageid}');"><b class="icon-collection"></b>收藏</a>
                        <a href="javascript:toSendMail('1','${list.id}');"><b class="icon-mail"></b>邮箱</a>
                        <a href="javascript:sendManager('${list.id}');"><b class="icon-export"></b>短信</a>
                        <a href="javascript:deleteInfo('${list.id}');"><b class="icon-delete"></b>删除</a>
                    </div>
                </div>
            </li>
        </#list>
    <#else>
        <#list opinionCondition as list>
            <li>
                <div class="con3-title">
                    <input class="con3-title_check" type="checkbox" name="chk_list" value="${list.id}">
                    <a href="javascript:gotoInfoPage('${list.id}');">${list.title}</a>
                    <span class="feeling">
                <#if (0==list.polarity)>
                    非负面
                </#if>
                        <#if (1==list.polarity)>
                            非负面
                        </#if>
                        <#if (-1==list.polarity)>
                            负面
                        </#if>
                </span>
                    <#if (list.isConcern!=null)>
                        <a class="attention" href="javascript:addConcern('${list.id}','${list.pageid}','${list.subjectid}');">已关注</a>
                    <#else>
                        <a class="attention" href="javascript:addConcern('${list.id}','${list.pageid}','${list.subjectid}');">关注</a>
                    </#if>
                </div>
                <#if (list.highligiht!=null)>
                    <div class="con3-sec">${list.highligiht}</div>
                <#else>
                    <div class="con3-sec">${list.summary}</div>
                </#if>
                <div class="con3-bottom">
                    <div class="bottom-left">
                        <span>${list.publishdate?string("yyyy-MM-dd HH:mm:ss")}</span>
                        <span>${list.website}</span>
                        <span class="similar">共${list.groupcount}篇相似</span>
                    </div>
                    <div class="bottom-right">
                        <a class="selected" href="javascript:showAttention('${list.pageid}');"><b class="icon-collection"></b>收藏</a>
                        <a href="javascript:toSendMail('${list.id}','1');"><b class="icon-mail"></b>邮箱</a>
                        <a href="javascript:deleteInfo('${list.id}');"><b class="icon-delete"></b>删除</a>
                    </div>
                </div>
            </li>
        </#list>
    </#if>

</ul>
    <#if (null!=opinionPage.ret)>
    ${opinionPage.ret}
    <#else>
    <span>未查询到信息!</span>
    </#if>
</#if>