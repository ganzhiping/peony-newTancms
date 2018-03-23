var pageContext;
$(function () {
    pageContext = $("#pageContext").val();
    $(window.parent.document).find("#iframe").height(715);
});
/**
 * 树形结构改变页面存值
 * @param id
 */
function refresh(id) {
    var isParent = window.parent.$("#isParent").val();
    if(isParent=='true'||isParent==true){
        window.parent.$("#iframe").attr("src",pageContext+"/special/specialSubject?pageType=2&isParent=true&subjectid=" + id);
    }else{
        window.parent.$("#iframe").attr("src",pageContext+"/special/specialSubject?pageType=2&isParent=false&subjectid=" + id);
    }
}

/**
 * 保存或修改内容
 */
function submitForm() {
    $.ajax({
        type: "POST",
        dataType: "text",
        url: pageContext + "/special/editDoSubmit",
        data: $("#specialForm").serialize(),
        success: function (data) {
           if("1" == data ){
               window.setTimeout(function () {
                   window.parent.$.jBox.tip("修改成功!", 'success', 1000);
               }, 100);
           }
            if("2" == data ){
                window.setTimeout(function () {
                    window.parent.$.jBox.tip("添加成功!", 'success', 1000);
                }, 100);
            }

        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

/**
 * 情感词库
 */
function feeLib(){
    $.ajax({
        type: "POST",
        dataType: "html",
        url:  "/special/specialAreaKeywords?viewType=polarity",
        data: null,
        success: function (data) {
            window.parent.document.getElementById("specialView").innerHTML = data;
            $.ajax({
                type: "POST",
                dataType: "html",
                url:  "/special/specialPolarity?polarityId=240",
                data: null,
                success: function (data) {
                    window.parent.document.getElementById("polarity_div").innerHTML = data;
                    window.parent.$("#specialArea").show();
                },
                error: function (data) {
                    alert("error:" + data.responseText);
                }
            });
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

/**
 * 地域词库
 */

function areaLib(){
    $.ajax({
        type: "POST",
        dataType: "html",
        url:  "/special/specialAreaKeywords?viewType=area",
        data: null,
        success: function (data) {
            window.parent.document.getElementById("specialView").innerHTML = data;
            $.ajax({
                type: "POST",
                dataType: "html",
                url:  pageContext+"/special/specialArea?regionId=11",
                data: null,
                success: function (data) {
                    window.parent.document.getElementById("div_conRight").innerHTML = data;
                    window.parent.$("#specialArea").show();
                },
                error: function (data) {
                    alert("error:" + data.responseText);
                }
            });
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

function changeDivConRight(regionid){
    var data = {
        regionId:regionid
    }
    $.ajax({
        type: "POST",
        dataType: "html",
        url:  "/special/specialArea",
        data: data,
        success: function (data) {
            window.parent.document.getElementById("div_conRight").innerHTML = data;
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}