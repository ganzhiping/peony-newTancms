$(function(){
    // ssq add 2016.11.2
    move();
})
    function move(){
        var area=document.getElementById("box");
        var iLiHeight=40;
        area.innerHTML+=area.innerHTML;
        area.scrollTop=0;
        var speed=50;
        var delay=2000;
        var time;
        function startMove(){
            area.scrollTop++;
            time=setInterval("scrollUp()",speed);
        }
        function scrollUp(){
            // area.scrollTop++;jjjj
            if(area.scrollTop%iLiHeight==0){
                clearInterval(time);
                setTimeout("startMove()",delay);
            }else{
                area.scrollTop++;
                if(area.scrollTop>=area.scrollHeight/2){
                    area.scrollTop=0;
                }
            }
        }
        setTimeout("startMove()",delay);//初始化
    }