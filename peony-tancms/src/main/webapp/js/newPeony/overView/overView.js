var pageContext;
var currentNode;
function loadTopView() {
    $("#box").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContext+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContext + "/overView/overViewListTop?timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: null,
        success: function (data) {
            document.getElementById("box").innerHTML = data;
            gundong();
        }
    });
}

function gundong(){
     var area = document.getElementById('box');
     var con1 = document.getElementById('con1');
     var con2 = document.getElementById('con2');
     var speed = 50;
     area.scrollTop = 0;
     con2.innerHTML = con1.innerHTML;
     var  scrollUp = function(){
         if(area.scrollTop >= con1.scrollHeight) {
             area.scrollTop = 0;
             }else{
               area.scrollTop ++;
             }
    }
    var myScroll = setInterval(scrollUp,speed);
    area.onmouseover = function(){
         clearInterval(myScroll);
        }
    area.onmouseout = function(){
         myScroll = setInterval(scrollUp,speed);
        }
}

function loadBottomView() {
    $("#box2").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContext+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContext + "/overView/overViewListBottom?timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: null,
        success: function (data) {
            document.getElementById("box2").innerHTML = data;
        }
    });
}

$(function () {
    pageContext = $("#pageContext").val();
    $.ajaxSetup({
        cache: false //关闭AJAX相应的缓存
    });
    jBox.setDefaults({
        defaults: {
            border: 0
        }
    })
    loadTopView();
    loadBottomView();
});

// // 显示内容摘要JS
// function showList() {
//     $("#box2>ul>li").hover(function () {
//         $(this).find('a').css({color: '#ff8500'});
//         // $(this).children('div').slideDown();
//         $(this).children('div').show();
//     }, function () {
//         $(this).find('a').css({color: '#000'});
//         // $(this).children('div').slideUp('slow');
//         $(this).children('div').hide();
//     });
// }


function gotoNewInfo(o) {
    window.open(encodeURI(pageContext + "/overView/overViewTopInfo?id="+o+"&timestamp=" + Date.parse(new Date())));
}


function changePageNo(o) {
    $("#box2").html('<div class="x_jiazai"><div class="tuxing_dengdai"><img src='+pageContext+'/css/newPeony/img/load.gif></div></div>');
    $.ajax({
        type: "post",
        url: encodeURI(pageContext + "/overView/overViewListBottom?pageNo=" + o + "&timestamp=" + Date.parse(new Date())),
        dataType: "html",
        data: null,
        success: function (data) {
            document.getElementById("box2").innerHTML = data;
        }
    });
}

function gotoInfoPage(id) {
    window.open(encodeURI(pageContext + "/opinionMon/newsInfo?id=" + id + "&timestamp=" + Date.parse(new Date())));
}
