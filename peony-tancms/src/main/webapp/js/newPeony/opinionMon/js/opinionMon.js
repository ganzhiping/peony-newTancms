var currentNode;
var pageContent;
function initNode(data) {
    if (data == null || data == undefined || data == "") {
        currentNode = {
            id: 1
        }
    } else {
        currentNode = {
            id: data
        }
    }
}

/**
 * 发送短信
 * @param id
 */
function sendManager(id) {
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/sendManger?id=" + id + "&timestamp=" + Date.parse(new Date())),
        dataType: "text",
        data: null,
        success: function (data) {
            window.setTimeout(function () {
                window.parent.$.jBox.tip(data, 'success', 1000);
            }, 100);
        }
    });
}

/**
 * 保存默认查询条件
 */
function setAsDefault() {
    $.ajax({
        type: "POST",
        dataType: "html",
        url: pageContent + "/opinionMon/setAsDefault",
        data: $("#form1").serialize(),
        success: function (data) {
            if ("success" == data) {
                window.setTimeout(function () {
                    window.parent.$.jBox.tip("保存成功！", 'success', 1000);
                }, 100);
            } else {

            }
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

/**
 * 查询
 */
function searchByQuery() {
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    var subjectid=window.parent.$("#currentNodeId").val();
    if(subjectid==null || subjectid==undefined ||subjectid==""){
        subjectid = "";
    }
    $.ajax({
            type: "POST",
            dataType: "html",
            url: pageContent + "/opinionMon/opinionMonList?subjectid="+subjectid,
            data: $("#form1").serialize(),
            success: function (data) {
                document.getElementById("opinionMonRight").innerHTML = data;
                var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
                $(window.parent.document).find("#iframe").height(height1 - 337);
            },
            error: function (data) {
                alert("error:" + data.responseText);
            }
        });
}
/**
 * 查询
 */
function searchByContent() {

    $("ul.rankUl").removeAttr('id');
    rank();

    var searchContent = $("#searchContent").val();
    var dataType=$(".activeA>input[name='dataType']").val();
    var showDate = $("#showDate").parent(".activeA").html();
    var data;
    if(showDate!=null||showDate != undefined){
        var startTime = $("$startTime").val();
        var endTime = $("$endTime").val();
        data ={
            searchContent:searchContent,
            startTime:startTime,
            endTime:endTime,
            searchType:1
        }
    }else{
        data={
            searchContent:searchContent,
            dataType:dataType,
            searchType:1
        }
    }
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        dataType: "html",
        url: encodeURI(pageContent + "/opinionMon/searchForResult?searchType=1&searchContent="+searchContent),
        data: $("#form1").serialize(),
        success: function (data) {
            document.getElementById("opinionMonRight").innerHTML = data;
            var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
            $(window.parent.document).find("#iframe").height(height1 - 337);
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });

}

function orderType(o){
    var orderStr = o;
    var str = $("#orderStr").val();
    var orderB = {
        orderStr:orderStr,
        downOrUp : str
    };
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/opinionMonList?subjectid=" + currentNode.id + "&downOrUp="+str+"&orderStr="+orderStr+"&timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: $("#form1").serialize(),
        success: function (data) {
            document.getElementById("opinionMonRight").innerHTML = data;
            var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
            $(window.parent.document).find("#iframe").height(height1 - 337);

        }
    });
}

/**
 * 查询舆情列表
 */
function loadRightView() {
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/opinionMonList?subjectid=" + currentNode.id + "&timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: $("#form1").serialize(),
        success: function (data) {
            document.getElementById("opinionMonRight").innerHTML = data;
            var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
            $(window.parent.document).find("#iframe").height(height1 - 337);

        }
    });
}

$(function () {
    pageContent = $("#pageContext").val();
    initNode(null)
    loadRightView();
    rank();
});

/**
 * 树形机构刷新舆情列表
 * @param id
 */
function refresh(id){
    initNode(id)
    loadRightView();
}
/**
 * 舆情列表换页
 * @param o
 */
function changePageNo(o) {
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/opinionMonList?subjectid=" + currentNode.id + "&pageNo=" + o + "&timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: $("#form1").serialize(),
        success: function (data) {
            document.getElementById("opinionMonRight").innerHTML = data;
            var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
            $(window.parent.document).find("#iframe").height(height1 - 337);
        }
    });
}
/**
 * 舆情搜索列表换页
 * @param o
 */
function changePageESNo(o) {
    $("#opinionMonRight").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContent+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/searchForResult?subjectid=" + currentNode.id + "&pageNo=" + o + "&searchType=2&timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: $("#form1").serialize(),
        success: function (data) {
            document.getElementById("opinionMonRight").innerHTML = data;
            var height1 = document.getElementById("opinionMonRight").scrollHeight + $("#fbody").height();
            $(window.parent.document).find("#iframe").height(height1 - 337);
        }
    });
}




/**
 * 跳转新闻详情页
 * @param id
 */
function gotoInfoPage(id) {
    window.open(encodeURI(pageContent + "/opinionMon/newsInfo?id=" + id + "&timestamp=" + Date.parse(new Date())));
}

/**
 * 发送邮件
 */
function toSendMail(val_, id) {
    var ids = "";
    if ("1" == val_) {
        ids = id;
    } else {
        ids = getCheckVal();
    }
    if (ids.length == 0) {
        window.parent.$.jBox.info('请选择发送项!', '提示');
    } else {
        window.parent.jBox("iframe:" + pageContent + "/subject/toWriteEmail", {
            title: "填写邮箱",
            width: 400,
            height: 190,
            top: '15%',
            id: 'toWriteEmail',
            buttons: {}
        });
    }
}
/**
 * 添加收藏
 */
function showAttention(pageId) {
    window.parent.jBox("iframe:" + pageContent + "/subject/showAttention/" + pageId, {
        title: "请选择要添加的收藏夹",
        width: 600,
        height: 276,
        top: '15%',
        id: 'addAttention',
        buttons: {}
    });
}

/**
 * 执行删除操作
 * @return
 */
function deleteInfo(id) {
    if (getCheckVal() == "" && id == "true") {
        window.parent.$.jBox.info("请先选择要删除的信息", '提示');
        return;
    }
    var arrayID = new Array();
    if (id == "true") {
        arrayID = getCheckVal().split(",");
    } else {
        arrayID[0] = id;
    }
    var arrayIdLength = arrayID.length;
    var params = {};
    params.ids = new Array();
    for (i = 0; i < arrayIdLength; i++) {
        params.ids.push(arrayID[i]);
    }
    var submit = function (v, h, f) {
        if (v == 'ok') {
            $.ajax({
                type: "post",
                url: encodeURI(pageContent + "/opinionMon/deleteOpinionCondition"),
                dataType: "json",
                data: params,
                success: function (data) {
                    alert(data)
                    if (data > 0) {
                        window.setTimeout(function () {
                            window.parent.$.jBox.tip("信息删除成功", 'success', 1000);
                        }, 100);
                        window.setTimeout(loadRightView, 1000);
                    } else {
                        window.setTimeout(function () {
                            window.parent.$.jBox.tip("信息删除失败", 'error', 1000);
                        }, 100);
                    }
                }
            });
        }
        else if (v == 'cancel') {
        }
        return true;
    };
    window.parent.jBox.confirm("您确定要删除以下" + arrayIdLength + "条信息？", "提示", submit);
}

/**
 * 导出execle
 */

function exportInfo(o) {
    var arrayID = getCheckVal();
    var type=null;
    var str = "";
    $("input[name='dataType']").each(function () {
        if($(this).is(":checked")){
            type = $(this).val();
            str = "&dataType="+type;
        }
    })
    if(type==null){
        str = "&startTime"+$("#startTime").val()+"&endTime="+$("#endTime").val();
    }

    $("input[name='source']").each(function () {
        if($(this).is(":checked")){
            str +="&source="+ $(this).val();
        }
    });

    var polarity=$("input[name='polarity']").each(function () {
        if($(this).is(":checked")){
            str +="&polarity="+  $(this).val();
        }
    });
    var weblevel=$("input[name='weblevel']").each(function () {
        if($(this).is(":checked")){
            str +="&weblevel="+   $(this).val();
        }
    })

    var submit = function () {
        location.href = encodeURI(pageContent + "/opinionMon/exportExecle?ids=" + arrayID +str+ "&timestamp=" + Date.parse(new Date()))
    }
    if (o=='2') {
        window.parent.jBox.confirm("您确定要导出所有符合条件的信息？", "提示", submit);
    } else {
        if(arrayID==undefined||arrayID==null||arrayID==""){
            window.setTimeout(function () {
                window.parent.$.jBox.tip("未选择导出项", 'error',2000);
            }, 100);
            return;
        }
        var num = arrayID.split(",").length;
        window.parent.$.jBox.confirm("您确定要导出以下 " + num + " 条的信息？", "提示", submit);
    }
}

/**
 * 情感值
 * @param poV
 * @param id
 */
function changePolarity(pageid,subjectid,id) {
    var val2 = "#polarity"+id;
    var pol=$(val2).val();
    var data = {
        pol:pol,
        pageid:pageid,
        subjectid:subjectid
    };
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/opinionProlarity?timestamp=" + Date.parse(new Date())),
        dataType: "json",
        data: data,
        success: function (data) {
            var val1 = "#pol"+id;
            $(val2).val(data);
            if(data=="-1"){
                $(val1).html("负面");
            }else{
                $(val1).html("非负面");
            }
            window.setTimeout(function () {
                window.parent.$.jBox.tip("极性修改成功", 'success', 1000);
            }, 100);
        }
    });
}

/**
 * 关注
 */
function addConcern(id,pageid,subjectid) {
    var dataV = {
        id:id,
        pageid:pageid,
        subjectid:subjectid
    };
    var con ="#isConcern"+ id;
    $.ajax({
        type: "post",
        url: encodeURI(pageContent + "/opinionMon/opinionConcern?timestamp=" + Date.parse(new Date())),
        dataType: "text",
        data: dataV,
        success: function (data) {
            if (data == "insert") {
                $(con).html("已关注");
                window.setTimeout(function () {
                    window.parent.$.jBox.tip("信息关注成功", 'success', 1000);
                }, 100);
            }else if(data == "delete"){
                $(con).html("关注");
                window.setTimeout(function () {
                    window.parent.$.jBox.tip("取消关注成功", 'success', 1000);
                }, 100);
            }
        }
    });
}
/**
 * 得到checkebox值
 * @return
 */
function getCheckVal() {
    var v = "";
    $("input[name='chk_list']:checked").each(function () {
        if (v == "") {
            v += this.value
        } else {
            v += "," + this.value
        }
    });
    return v;
}

function piliang(){
    // 批量操作
    var flag = $("#operation").is(":checked");
    if(flag){
            $('.con3-title_check').attr("checked", true);
    }else{
        $('.con3-title_check').attr("checked", false);
    }
}

function rank(){
    //点击选中当前li
    $("#rankUl>li").mousedown(function(){
        $(this).addClass('indexLi').siblings('li').removeClass('indexLi');
    });
    //点击进行排序
    $("#rankUl>li").each(function(){
        $(this).toggle(function(){
            $(this).children('i.turnDown').show();
            $("#orderStr").val("down");
            $(this).children('i.turnUp').hide();
        },function(){
            $(this).children('i.turnDown').hide();
            $("#orderStr").val("up");
            $(this).children('i.turnUp').show();
        });
    })
}
