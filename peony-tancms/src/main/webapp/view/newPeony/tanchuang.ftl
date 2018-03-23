<!-- 弹窗1 -->
<input id="subjectName" type="hidden" value="">
<input id="subjectId" type="hidden" value="">
<div class="tanchuang1" id="allSubject">
    <div class="tanchuang1_1">确认要删除该监测类别？</div>
    <div class="tanchuang1_2">注意：该类别下所有子类别和方案将一并删除！</div>
    <div class="tanchuang1_3">
        <input class="input-confirm" type="button" onclick="makeSureDel()" value="确认">
        <input class="input-cancel" type="button" value="取消">
    </div>
</div>
<!-- 弹窗2 -->
<div class="tanchuang2" id="oneSubject">
    <div class="tanchuang2_1">确认要删除该监测方案？</div>
    <div class="tanchuang2_3">
        <input class="input-confirm" type="button" onclick="makeSureDel()" value="确认">
        <input class="input-cancel" type="button" value="取消">
    </div>
</div>