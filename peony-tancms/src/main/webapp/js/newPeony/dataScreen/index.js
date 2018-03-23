    //页面加载完毕后触发
    $(function(){
        turnTop();
        hover1();
    })

    function hover1(){
        $(".countTable tr:not(:first-child)").hover(function() {
            $(this).css({background:'#b1c8fa',fontWeight:'bold',color:'#fff'});
        }, function() {
            $(this).css({background:'white',fontWeight:'normal',color:'#000'});
        });

        $(".rank dd p:not(:first-child)").hover(function() {
            $(this).addClass("rankHover");
        }, function() {
            $(this).removeClass("rankHover");
        });

        $(".billboard dd p:not(:first-child)").hover(function() {
            $(this).addClass("rankHover");
        }, function() {
            $(this).removeClass("rankHover");
        });

        $(".sec-one span").hover(function() {
            $(this).next('i').show();
        }, function() {
            $(this).next('i').hide();
        });

    }

    function turnTop(){
        var obtn=document.getElementById('btn');
        //用来获取页面可视区的高度的
        var clientHeight=document.documentElement.clientHeight;
        var timer=null;
        var isTop=true;
        //滚动条滚动时触发该事件
        window.onscroll=function(){
            var osTop=document.documentElement.scrollTop||document.body.scrollTop;
            if(osTop>=200){
                obtn.style.display="block";
            }else{
                obtn.style.display="none";
            }
            if(!isTop){
                clearInterval(timer);
            }
            isTop=false;
        }
        obtn.onclick=function(){
            //设置定时器
            timer=setInterval(function(){
                //IE浏览器下与chrome浏览器下的兼容，获取滚动条距离顶部的高度
                var osTop=document.documentElement.scrollTop||document.body.scrollTop;
                var ispeed=Math.floor(-osTop/9);//此方法实现由快到慢的效果，加-号的原因不是特别理解，但是它可以解决运算出的结果不为0，到达不了顶部没办法清除定时器问题。
                isTop=true;
                document.documentElement.scrollTop=osTop+ispeed;
                document.body.scrollTop=osTop+ispeed;
                //到达顶部后清除定时器
                if(osTop==0){
                    clearInterval(timer);
                }
            },30)
        }
    }