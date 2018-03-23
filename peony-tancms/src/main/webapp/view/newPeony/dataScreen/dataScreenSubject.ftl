<div class="con_main">
    <div class="left">
        <div class="s_bg8-wrap">
            <div class="s_bg3"></div>
            <div class="sec">
                <div class="secTitle">
                    <div class="secTitle-bg1">
                        <p>舆情数据渠道分布</p>
                    </div>
                </div>
                <div class="secCon">
                    <div id="main6" style="float:right;width:100%;min-height:370px;"></div>
                </div>
            </div>
        </div>
        <div class="s_bg9-wrap">
            <div class="s_bg4"></div>
            <div class="sec">
                <div class="secTitle">
                    <div class="secTitle-bg1">
                        <p>舆情数据媒体势力图</p>
                    </div>
                </div>
                <div class="secCon">
                    <div id="main7" style="float:right;width:100%;min-height:370px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="center">
        <div class="s_bg10-wrap">
            <div class="s_bg5"></div>
            <div class="sec dataInfo">
                <div class="dataInfo1">全网舆情数据信息共 ${subjectWebNum} 条</div>
                <div class="dataInfo2">
                    <span class="dataInfo2-span1"><span id="yearTime"></span>&nbsp;
                    <span class="dataInfo2-span2"><span id="hourTime"></span></span>
                </div>
            </div>
        </div>
        <div class="s_bg11-wrap">
            <div class="s_bg6"></div>
            <div class="sec">
                <div class="secTitle">
                    <div class="secTitle-bg1">
                        <p>舆情时间发展路线</p>
                    </div>
                </div>
                <div class="secCon">
                    <div id="main8" style="float:right;width:100%;min-height:562px;"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="right">
        <div class="s_bg11-wrap">
            <div class="s_bg6"></div>
            <div class="sec">
                <div class="secTitle">
                    <div class="secTitle-bg1">
                        <p>舆情热词分布</p>
                    </div>
                </div>
                <div class="secCon">
                    <div style="float:right;width:100%;min-height:370px;">
                        <div id="tagbox" style="position:relative;">
                            <#list dataScreenHotWords as list>
                                <a class="${list.colorView}" href="#">${list.hotwords}</a>
                            </#list>
<#--
                            <a class="red" href="#">大气</a>
                            <a class="green" href="#">医务</a>
                            <a class="green" href="#">扶贫</a>
                            <a class="blue" href="#">医疗</a>
                            <a class="green" href="#">污染</a>
                            <a class="red" href="#">环保</a>
                            <a class="yellow" href="#">健康</a>
                            <a class="green" href="#">报销</a>
                            <a class="red" href="#">医疗</a>
                            <a class="green" href="#">报销</a>
                            <a class="blue" href="#">儿科</a>
                            <a class="blue" href="#">收费</a>
                            <a class="red" href="#">环境</a>
                            <a class="yellow" href="#">脱贫</a>
                            <a class="yellow" href="#">返乡</a>
                            <a class="blue" href="#">女职工</a>
                            <a class="red" href="#">环保</a>
                            <a class="yellow" href="#">健康</a>
                            <a class="green" href="#">报销</a>
                            <a class="red" href="#">医疗</a>
                            <a class="green" href="#">报销</a>
                            <a class="blue" href="#">儿科</a>
                            <a class="yellow" href="#">报销</a>
                            <a class="green" href="#">健康</a>-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="s_bg11-wrap">
            <div class="s_bg6"></div>
            <div class="sec">
                <div class="secTitle">
                    <div class="secTitle-bg1">
                        <p>舆情形象负面指数</p>
                    </div>
                </div>
                <div class="secCon">
                    <div id="main9" style="float:right;width:100%;min-height:370px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>