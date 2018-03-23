<div style="width:100%;height:100%;position:fixed;top:0;z-index:999;background:rgba(0,0,0,0.7);background: #555\9;filter:alpha(opacity=92)\9;">
<!-- 添加分类1 -->
    <div class="classification" id="add_userAll">
        <div class="classification_1">添加分类</div>
        <div class="classification_2">
            <div class="addScheme_2-sec2">
                <div class="treeTd" width="160" valign="top">
                    <ul class="tree_ul ztree" id="subjectTreeAddUser"></ul>
                </div>
            </div>
            <input class="input-name" type="text" id="spaecialNameSave" placeholder="请输入类别名称">
            <div class="sureAndcancle">
                <input class="input-confirm" type="button" onclick="saveSpecialName()" value="确定">
                <input class="input-cancel" type="button" onclick="cancelClose()" value="取消">
            </div>
        </div>
    </div>
    <!-- 添加方案2 -->
    <div class="addScheme" id="add_subject">
        <div class="addScheme_1">添加专题</div>
        <div class="addScheme_2">
            <div class="addScheme_2-sec">
                <div class="addScheme_2-sec1">请选择方案所属类别</div>
                <div class="addScheme_2-sec2">
                    <div class="treeTd" width="160" valign="top">
                        <ul class="tree_ul ztree" id="subjectTreeAdd"></ul>
                    </div>
                </div>
                <div class="addScheme_2-sec3">
                    <input class="input-confirm" type="button" onclick="savaSubject()" value="确认">
                    <input class="input-cancel" type="button" onclick="cancelClose()" value="取消">
                </div>
            </div>
        </div>
    </div>
</div>