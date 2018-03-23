var pageContent;
function zTreeOnAsyncSuccess() {
    var treeObj = $.fn.zTree.getZTreeObj("subjectTree")
    var node = treeObj.getNodeByParam("id", 1);
    treeObj.expandNode(node, true, false, true, false);
}
function zTreeOnAsyncSuccess1() {
    var treeObj = $.fn.zTree.getZTreeObj("subjectTreeAdd")
    var node = treeObj.getNodeByParam("id", 1);
    treeObj.expandNode(node, true, false, true, false);
}
function zTreeOnAsyncSuccess2() {
    var treeObj = $.fn.zTree.getZTreeObj("subjectTreeAddUser")
    var node = treeObj.getNodeByParam("id", 1);
    treeObj.expandNode(node, true, false, true, false);
}
var name;
$(function () {
    pageContent = $("#pageContext").val();
    $.ajaxSetup({
        cache: true //关闭AJAX相应的缓存
    });
    jBox.setDefaults({
        defaults: {
            border: 0
        }
    });

    initZttree();
});

function initZttree(){
    function configTree() {
        setting = {
            edit: {
                enable: true
            },
            async: {
                enable: true,
                url: pageContent + "/system/findChildren",
                autoParam: ["id"]
            },
            view: {
                dblClickExpand: false,
                expandSpeed: "",
                showLine: false,
                showIcon: false,
                selectedMulti: false,
                dblClickExpand: false,
                addDiyDom: addDiyDom
            },
            callback: {
                onAsyncSuccess: zTreeOnAsyncSuccess,
                onClick: function (event, treeId, treeNode) {
                    currentNode = treeNode;
                    $("#isParent").val(currentNode.isParent);
                    if (currentNode.isParent) {
                        var treeObj = $.fn.zTree.getZTreeObj("subjectTree");
                        treeObj.expandNode(treeNode);
                    }
                    if (!currentNode.isParent) {
                        parent.frames["iframe"].window.refresh(currentNode.id);
                        $("#subjectNameValue").val(currentNode.name);
                        $("#pageType").html("修改专题");
                    } else {
                        var viewType=$("#viewType").val();
                        if(viewType=='3'){
                            parent.frames["iframe"].window.refresh(currentNode.id);
                        }
                        $("#pageType").html("新增专题");
                    }
                    $("#currentNodeId").val(currentNode.id);

                    name="";
                    treeCheckParents(currentNode);
                    var val_1 = name.split(",");
                    var val_2="";
                    var len = val_1.length;
                    for(var i =val_1.length-1;i>=0;i--){
                        val_2 += val_1[i]+"--";
                    }
                    $("#typeValueName").html(val_2.substring(2,val_2.length) + currentNode.name);
                },
                onRemove: function (event, treeId, treeNode)  {
                    var id = treeNode.id;
                    var data ={
                        subjectid:id,
                        isParent:treeNode.isParent
                    }
                    var va = "";
                    if(treeNode.isParent){
                        va = "确定删除<span color='red'>" + treeNode.name +"</span> 以及专题？"
                    }else{
                        va = "确定删除<span color='red'>" + treeNode.name +"</span>专题？"
                    }
                    if(window.confirm(va)){
                        $.ajax({
                            type: "POST",
                            dataType: "html",
                            url:  "/special/removeSubject",
                            data: data,
                            success: function (data) {
                                window.setTimeout(function () {
                                    window.$.jBox.tip("删除成功", 'error', 1000);
                                }, 100);
                            },
                            error: function (data) {
                                alert("error:" + data.responseText);
                            }
                        })
                    }else{
                        return null;
                    }
                },
                onRename: function (event, treeId, treeNode)  {
                    var id = treeNode.id;
                    var name = treeNode.name;
                    var data ={
                        subjectid:id,
                        name:name
                    }
                    if(window.confirm("确定修改为"+treeNode.name)){
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            url:  "/special/renameSubject",
                            data: data,
                            success: function (data) {
                                window.setTimeout(function () {
                                    window.$.jBox.tip("修改成功", 'error', 1000);
                                }, 100);
                            },
                            error: function (data) {
                                alert("error:" + data.responseText);
                            }
                        })
                    }else{
                        return null;
                    }
                }
            }
        };
        return setting;
    }
    $.fn.zTree.init($("#subjectTree"), configTree());
}
var i =1;
function addDiyDom(treeId, treeNode) {
        var spaceWidth = 5;
        var a1 = "#subjectTree_" + i + "_switch";
        var switchObj = $(a1),
        icoObj = $("#subjectTree_" + i + "_ico");
        switchObj.remove();
        icoObj.before(switchObj);
        if (treeNode.level > 1) {
            var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
            switchObj.before(spaceStr);
        }
        var spantxt=$("#subjectTree_" + i + "_span").html();
        if(spantxt!=undefined&&spantxt.length>8){
            spantxt=spantxt.substring(0,8)+"...";
            $("#subjectTree_" + i + "_span").html(spantxt);
        }

        i++;
    }


function treeCheckParents(currentNode){
    var tree =currentNode.getParentNode();
    if(tree.id==1||tree.id=='1'){
        return;
    }else{
        name =name+ tree.name+",";
        treeCheckParents(tree);
    }
}

var userViewNode;
var subjectViewNode;
function addUserAllView(){
    $.ajax({
        type: "POST",
        dataType: "html",
        url:  "/special/addSubject",
        data: null,
        success: function (data) {
            document.getElementById("specialView").innerHTML = data;
            $("#add_subject").hide();
            pageContent = $("#pageContext").val();
            $.ajaxSetup({
                cache: false //关闭AJAX相应的缓存
            });
            jBox.setDefaults({
                defaults: {
                    border: 0
                }
            });
            function configTree() {
                setting = {
                    edit: {
                        enable: false
                    },
                    async: {
                        enable: true,
                        url: pageContent + "/system/findChildren",
                        autoParam: ["id"]
                    },
                    view: {
                        expandSpeed: "",
                        dblClickExpand: false,
                        showLine: false,
                        showIcon: false,
                        selectedMulti: false
                    },
                    callback: {
                        onAsyncSuccess: zTreeOnAsyncSuccess2,
                        onClick: function (event, treeId, treeNode) {
                            userViewNode = treeNode;
                            if (userViewNode.isParent) {
                                var treeObj = $.fn.zTree.getZTreeObj("subjectTreeAddUser");
                                treeObj.expandNode(treeNode);
                            }
                        }
                    }
                };
                return setting;
            }
            $.fn.zTree.init($("#subjectTreeAddUser"), configTree());
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    })
}
function addSubjectView(){
    $.ajax({
        type: "POST",
        dataType: "html",
        url:  "/special/addSubject",
        data: null,
        success: function (data) {
            document.getElementById("specialView").innerHTML = data;
            $("#add_userAll").hide();
            pageContent = $("#pageContext").val();
            $.ajaxSetup({
                cache: false //关闭AJAX相应的缓存
            });
            jBox.setDefaults({
                defaults: {
                    border: 0
                }
            });

            function configTree() {
                setting = {
                    edit: {
                        enable: false
                    },
                    async: {
                        enable: true,
                        url: pageContent + "/system/findChildren",
                        autoParam: ["id"]
                    },
                    view: {
                        expandSpeed: "",
                        dblClickExpand: false,
                        showLine: false,
                        showIcon: false,
                        selectedMulti: false
                    },
                    callback: {
                        onAsyncSuccess: zTreeOnAsyncSuccess1,
                        onClick: function (event, treeId, treeNode) {
                            subjectViewNode = treeNode;
                            if (subjectViewNode.isParent) {
                                var treeObj = $.fn.zTree.getZTreeObj("subjectTreeAdd");
                                treeObj.expandNode(treeNode);
                            }
                        }
                    }
                };
                return setting;
            }
            $.fn.zTree.init($("#subjectTreeAdd"), configTree());
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    })
}

function saveSpecialName(){
    if(userViewNode==undefined){
        alert("请选择舆情分类，无法在专题下添加分类!");
        return;
    }
    var name1 = $("#spaecialNameSave").val();
    var data = {
        parentId:  userViewNode.id,
        name:name1
    };
    $.ajax({
        type: "POST",
        dataType: "text",
        url:  "/special/saveUserView",
        data: data,
        success: function (data) {
            if("error" ==data){
                alert("请选择分类，专题不能添加下级分类!")
            }else if("success" == data){
                cancelClose();
                initZttree();
                window.setTimeout(function () {
                    window.$.jBox.tip("添加成功", 'error', 1000);
                }, 100);
            }

        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });
}

function savaSubject() {
    if (subjectViewNode.id == null || subjectViewNode.id == undefined ) {
        alert("请选择分类!");
        return;
    }
    if(subjectViewNode.isParent){
        $("#iframe").attr("src",pageContent+"/special/specialSubject?pageType=1&isParent=true&subjectid=" + subjectViewNode.id);
    }else{
        $("#iframe").attr("src",pageContent+"/special/specialSubject?pageType=2&isParent=false&subjectid=" + subjectViewNode.id);
    }
    $("#titleV3").addClass("index");
    $("#titleV3").siblings('li').removeClass('index');
    cancelClose();
}
function cancelClose(){
    document.getElementById("specialView").innerHTML = "";
}