<div class="location">
    <div class="checkAll">
        <span class="fr">
            <input id="checkArea" type="checkbox" onclick="areaChooseAll()">
            <label for="">全选</label>
        </span>
    </div>
    <div class="capital">
    <#list areaKeywordsRegion?keys as key>
        <#assign x=(x+1)?number />
        <DIV class="chooseRegion">
            <input type="checkbox" id="${x}" onclick="checkValueRegion(this,'${x}')">
            <label for="">${key}<input type="hidden" value="${x}"></label>
        </DIV>
        <div class="counties">
            <#list areaKeywordsRegion[key] as values>
                <#if (values!=null&&""!=values)>
                    <span>
                            <input type="checkbox" id="${values.regionId}" onclick="checkValue('${values.regionAbbr}','${values.regionId}')">
                            <label for="">${values.regionAbbr}<input type="hidden" value="${values.regionId}"></label>
                    </span>
                </#if>
            </#list>
        </div>
    </#list>
    </div>
</div>