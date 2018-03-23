<div class="div_conRight">
    <div class="keyWord">
        <div class="allChoose">
			<span>
				<input type="checkbox" id="checkPolarity" onclick="polarityChooseAll()">
				<label for="">全选</label>
			</span>
        </div>
        <div class="keywordCon" id="polarityContent">
        <#list polarityContent as list>
            <span>
					<input type="checkbox" id="${list.id}" onclick="checkValue('${list.content}','${list.id}')">
					<label for="">${list.content}</label>
				</span>
        </#list>
        </div>
    </div>
</div>