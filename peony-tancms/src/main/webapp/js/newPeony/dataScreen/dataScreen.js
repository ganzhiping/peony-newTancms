var pageContent;
var timeType;
var subjectid;

function makeDataScreen(){
    init();
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenAllNum"),
        data: {timeType:timeType},
        success: function (data) {
            $("#webAllData").html(data);
        }
    })
    chart0();
    chart1();
    chart3();
    chart4();
    chart5();
    //alert($(o).html())
    //alert($(this).html())

}

$(function(){
    pageContent = $("#pageContext").val();
    init();
    /*   chart0();
     chart1();
     chart3();
     chart4();
     chart5();*/
})

function init(){
    timeType = $(".activeA>input[name='timeType']").val();
}

/**
 * 全部专题（用户）统计页
 */
function initUserView(){
    $("#chooseTime>a").each(function(){
        $(this).mousedown(function(){
            $(this).addClass("activeA");
            $(this).siblings("a").removeClass("activeA");
        })
    })
    init();
    chart0();
    chart1();
    chart3();
    chart4();
    chart5();
}

/**
 * 单个专题初始化
 * @param id
 */
function initsubjectView(id){
    subjectid=id;
    //专题图1
    chart6();
    //专题图2
    chart7();
    //专题图3
    chart8();
    //专题图4
    chart9();
    //专题热词
    chart10();
}

function chart6(){
    // 基于准备好的dom，初始化echarts实例
    var main = echarts.init(document.getElementById('main6'));
    // 基于准备好的dom，初始化echarts实例
    var mediaData =[];
    var seriesVal = [];
    var placeHolderStyle = {
        normal : {
            color: 'rgba(0,0,0,0)',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: 'rgba(0,0,0,0)'
        }
    };
    var AllL = 200;
    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var option1 = {
        title: {
            text: '舆情数据',
            x: 'center',
            y: 'center',
            itemGap: 20,
            textStyle : {
                color : '#ff5b01',
                fontFamily : '微软雅黑',
                fontSize : 20,
                fontWeight : 'bolder'
            }
        },
        tooltip : {
            show: true,
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        // legend: {
        //     orient : 'vertical',
        //     x : 'center',
        //     y : 110,
        //     itemGap:12,
        //     data:mediaData,
        //     textStyle : {
        //         fontSize : 10,
        //         color : '#fff'
        //     }
        // },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                // dataView : {show: true, readOnly: false},
                // restore : {show: true},
                // saveAsImage : {show: true}
            }
        },
        series : seriesVal
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenMedia"),
        data: {timeType:3,subjectid:subjectid},
        success: function (data) {
            $.each(data,function(i,item){
                var fCnt = item.fCnt;
                var zCnt = item.zCnt;
                mediaData.push(item.mediaName);
                var mediaName = item.mediaName;
                var radius =[];
                radius.push(120-((i+1)*10));
                radius.push(120-(i*10));
                var value1 = {
                    name:item.mediaName,
                    type:'pie',
                    clockWise:false,
                    radius : [radius[0],radius[1]],
                    itemStyle : dataStyle,
                    data:[
                        {
                            value:fCnt,
                            name:"负面",
                            itemStyle : {
                                normal : {
                                    color: '#ff5b01',
                                    label: {show:false},
                                    labelLine: {show:false}
                                },
                                emphasis : {
                                    color: '#00f6ff'
                                }
                            }
                        },
                        {
                            value:zCnt,
                            name:'非负面',
                            itemStyle : {
                                normal : {
                                    color: '#8be28b',
                                    label: {show:false},
                                    labelLine: {show:false}
                                },
                                emphasis : {
                                    color: '#00f6ff'
                                }
                            }
                        }
                    ]
                }
                seriesVal.push(value1);
            });
            main.setOption(option1);
        }
    });
}

function chart7(){
    // 基于准备好的dom，初始化echarts实例
    var dataVal =[];
    var fCntVal=[];
    var zCntVal=[];
    var main7 = echarts.init(document.getElementById('main7'));
    // 指定图表的配置项和数据
    var optionW = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['负面', '非负面'],
            textStyle : {
                color : '#fff'
            },
            y:'15px'
        },
        textStyle : {
            color : '#fff'
        },
        toolbox: {
            show : true,
            feature : {
                // mark : {show: true},
                // dataView : {show: true, readOnly: false},
                // magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                // restore : {show: true},
                // saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            x: 30,
            x2:100
        },
        xAxis : [
            {
                type : 'value'
            }
        ],
        yAxis : [
            {
                type : 'category',
                data : []
            },
            {
                type : 'category',
                data : dataVal
            }
        ],
        series : [
            {
                name:'负面',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:fCntVal
            },
            {
                name:'非负面',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:zCntVal
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenWebsite"),
        data: {timeType:3,subjectid:subjectid},
        success: function (data) {
            var num=data.length;
            $.each(data,function(i,item){
                var k = num-1;
                dataVal[k] = item.siteName;
                fCntVal[k]=item.fCnt;
                zCntVal[k]=item.zCnt-item.fCnt;
                num--;
            });
            main7.setOption(optionW);
        }
    });
}
function chart8(){
    var dateV = [];
    // 基于准备好的dom，初始化echarts实例
    var main8 = echarts.init(document.getElementById('main8'));
    // 指定图表的配置项和数
    option = {
        title : {
            // text : '时间坐标折线图',
            // subtext : 'dataZoom支持',
            textStyle:{
                color : '#fff'
            }
        },
        textStyle : {
            color : '#fff'
        },
        tooltip : {
            trigger: 'item',
            formatter : function (params) {
                var date = new Date(params.value[0]);
                data = date.getFullYear() + '-'
                    + (date.getMonth() + 1) + '-'
                    + date.getDate() + ' '
                    + date.getHours() + ':0'
                    + date.getMinutes();
                return data + '<br/>总数:'
                    + params.value[1] + ', 负面:'
                    + params.value[2];
            }
        },
        toolbox: {
            show : true,
            textStyle:{
                color : '#fff'
            }
        },
        dataZoom: {
            show: true,
            start : 0
        },
        legend : {
            data : ['series1'],
            textStyle:{
                color : '#fff'
            }
        },
        grid: {
            y2: 80
        },
        xAxis : [
            {
                type : 'time',
                splitNumber:7
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name: '',
                type: 'line',
                showAllSymbol: true,
                symbolSize: 6,
                data:dateV
            }
        ]
    };
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenDevelopRoute"),
        data: {timeType:3,subjectid:subjectid},
        success: function (data) {
            var num=data.length;
            $.each(data,function(i,item){
                var time = item.getTime;
                var negativeNo = item.negativeNo;
                if(negativeNo == null || negativeNo==""){
                    negativeNo=0;
                }
                dateV.push([new Date(time),(item.cnt==null?0:item.cnt),negativeNo]);
            });
            main8.setOption(option);
        }
    });
}

function chart9(){
    var formatter1="";
    var negativeNo=0;
    var cnt=0;
    var dataVal=[];
    // 基于准备好的dom，初始化echarts实例
    var main9 = echarts.init(document.getElementById('main9'));
    // 指定图表的配置项和数据
    var option1 = {
        tooltip : {
            formatter:formatter1
        },
        toolbox: {
            show : true,
            feature : {
                // mark : {show: true},
                // restore : {show: true},
                // saveAsImage : {show: true}
            }
        },
        series : [
            {
                name:'业务指标',
                type:'gauge',
                splitNumber: 10,       // 分割段数，默认为5
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: [[0.2, '#8be28b'],[0.8, '#48b'],[1, '#ff4500']],
                        width: 8
                    }
                },
                axisTick: {            // 坐标轴小标记
                    splitNumber: 10,   // 每份split细分多少段
                    length :12,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    show: true,        // 默认显示，属性show控制显示与否
                    length :30,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                pointer : {
                    width : 5
                },
                title : {
                    show : true,
                    offsetCenter: [0, '-40%'],       // x, y，单位px
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    }
                },
                detail : {
                    formatter:'{value}%',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        color: '#fff'
                    }
                },
                data:dataVal
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenSentiment"),
        data: {timeType:3,subjectid:subjectid},
        success: function (data) {
            cnt = data.cnt;
            negativeNo = data.negativeNo;
            var percent = (negativeNo/cnt)*100+'.00';
            //  var a2 = percent.substring(0,percent.indexOf(".")+1)+percent.substr(percent.indexOf(".")+1,2);
            //var aa = percent.substr(0,5);
            var a2=0;
            if(percent.indexOf(".")==-1){
                a2=percent;
            }else{
                a2 = percent.substring(0,percent.indexOf(".")+1)+percent.substr(percent.indexOf(".")+1,2);
            }
            if(negativeNo == 0){
                a2=0.00
            }
            if(negativeNo == 0){
                a2=0.00
            }
            option1.tooltip.formatter="总数："+cnt+"<br>负面："+negativeNo;
            var value1 = {
                value:a2,
                name:"负面"
            }
            dataVal.push(value1);
            main9.setOption(option1);
        }
    });
}

function chart4(){
    // 基于准备好的dom，初始化echarts实例
    var main4 = echarts.init(document.getElementById('main4'));
    var xAxisVal=[];
    var seriesVal=[];
    var legendVal=[];
    // 指定图表的配置项和数据
    var option4 = {
        // title : {
        //     text: '未来一周气温变化',
        //     subtext: '纯属虚构',
        //     textStyle : {
        //         color : '#fff'
        //     }
        // },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            // orient: 'vertical',
            // x: 'left',
            // y: 'top',
            data:legendVal,
            textStyle : {
                color : '#fff'
            }
        },
        textStyle : {
            color : '#fff'
        },
        toolbox: {
            show : true,
            // feature : {
            //     mark : {show: true},
            //     dataView : {show: true, readOnly: false},
            //     magicType : {show: true, type: ['line', 'bar']},
            //     restore : {show: true},
            //     saveAsImage : {show: true}
            // }
        },
        dataZoom: [
            {
                type: 'slider',
                show: false,
                xAxisIndex: [0],
                handleSize: 20,//滑动条的 左右2个滑动条的大小
                height: 8,//组件高度
                left: 30, //左边的距离
                right: 40,//右边的距离
                bottom: 30,//右边的距离
                handleColor: '#ddd',//h滑动图标的颜色
                handleStyle: {
                    borderColor: "#cacaca",
                    borderWidth: "1",
                    shadowBlur: 2,
                    background: "#ddd",
                    shadowColor: "#ddd",
                },
                fillerColor: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                    //给颜色设置渐变色 前面4个参数，给第一个设置1，第四个设置0 ，就是水平渐变
                    //给第一个设置0，第四个设置1，就是垂直渐变
                    offset: 0,
                    color: '#1eb5e5'
                }, {
                    offset: 1,
                    color: '#5ccbb1'
                }]),
                backgroundColor: '#ddd',//两边未选中的滑动条区域的颜色
                showDataShadow: false,//是否显示数据阴影 默认auto
                showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
                handleIcon: 'M-292,322.2c-3.2,0-6.4-0.6-9.3-1.9c-2.9-1.2-5.4-2.9-7.6-5.1s-3.9-4.8-5.1-7.6c-1.3-3-1.9-6.1-1.9-9.3c0-3.2,0.6-6.4,1.9-9.3c1.2-2.9,2.9-5.4,5.1-7.6s4.8-3.9,7.6-5.1c3-1.3,6.1-1.9,9.3-1.9c3.2,0,6.4,0.6,9.3,1.9c2.9,1.2,5.4,2.9,7.6,5.1s3.9,4.8,5.1,7.6c1.3,3,1.9,6.1,1.9,9.3c0,3.2-0.6,6.4-1.9,9.3c-1.2,2.9-2.9,5.4-5.1,7.6s-4.8,3.9-7.6,5.1C-285.6,321.5-288.8,322.2-292,322.2z',
                filterMode: 'filter',
            },
            //下面这个属性是里面拖到
            {
                type: 'inside',
                show: true,
                xAxisIndex: [0],
                start: 1,
                end: 100
            }
        ],
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : xAxisVal
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '
                }
            }
        ],
        series :seriesVal
    };
    main4.on('dataZoom',function(){
    });
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenSubject"),
        data: {timeType:timeType},
        success: function (data) {
            var m=0;
            var xAxisVal_1 =[];;
            for(var key in data){
                var name = key.split("&$&")[0];
                legendVal.push(name);
                var dataVal = data[key];
                var v1_1 = [];
                for(var a =0;a<dataVal.length;a++) {
                    var item = dataVal[a];
                    v1_1.push(item.cnt);
                    if (m == 0) {
                        xAxisVal.push(item.reTime);
                    }
                }
                m++;
                var v1 = {
                    name:name,
                    type:'line',
                    data:v1_1,
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                }
                seriesVal.push(v1);
            }
            main4.setOption(option4);
        }
    });
}

//媒体类型
function chart0(){
    // 基于准备好的dom，初始化echarts实例
    var mediaData =[];
    var seriesVal = [];
    var main = echarts.init(document.getElementById('main'));
    var placeHolderStyle = {
        normal : {
            color: 'rgba(0,0,0,0)',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: 'rgba(0,0,0,0)'
        }
    };
    var AllL = 200;
    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var option1 = {
        title: {
            text: '舆情数据',
            x: 'center',
            y: 'center',
            itemGap: 20,
            textStyle : {
                color : '#ff5b01',
                fontFamily : '微软雅黑',
                fontSize : 25,
                fontWeight : 'bolder'
            }
        },
        tooltip : {
            show: true,
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        // legend: {
        //     orient : 'vertical',
        //     x : 'center',
        //     y : 100,
        //     itemGap:12,
        //     data:mediaData,
        //     textStyle : {
        //         color : '#fff',
        //         fontSize : 10
        //     }
        // },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                // dataView : {show: true, readOnly: false},
                // restore : {show: true},
                // saveAsImage : {show: true}
            }
        },
        series : seriesVal
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenMedia"),
        data: {timeType:timeType},
        success: function (data) {
            $.each(data,function(i,item){
                var fCnt = item.fCnt;
                var zCnt = item.zCnt;
                mediaData.push(item.mediaName);
                var mediaName = item.mediaName;
                var radius =[];
                radius.push(140-((i+1)*12));
                radius.push(140-(i*12));
                var value1 = {
                    name:item.mediaName,
                    type:'pie',
                    clockWise:false,
                    radius : [radius[0],radius[1]],
                    itemStyle : dataStyle,
                    data:[
                        {
                            value:fCnt,
                            name:"负面",
                            itemStyle : {
                                normal : {
                                    color: '#ff5b01',
                                    label: {show:false},
                                    labelLine: {show:false}
                                },
                                emphasis : {
                                    color: '#00f6ff'
                                }
                            }
                        },
                        {
                            value:zCnt,
                            name:'非负面',
                            itemStyle : {
                                normal : {
                                    color: '#8be28b',
                                    label: {show:false},
                                    labelLine: {show:false}
                                },
                                emphasis : {
                                    color: '#00f6ff'
                                }
                            }
                        }
                    ]
                }
                seriesVal.push(value1);
            });
            main.setOption(option1);
        }
    });
}

function chart1(){
    var series1=[];
    // 基于准备好的dom，初始化echarts实例
    var main1 = echarts.init(document.getElementById('main1'));
    // 指定图表的配置项和数据
    var  option1 = {
        tooltip : {
            trigger: 'item'
        },
        dataRange: {
            min: 100,
            max: 10000,
            x: '30px',
            y: '30px',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true,
            color: ['#365562','#e0ffff'],
            textStyle:{
                color : '#fff'
            }
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            x: 'right',
            y: 'center'
        },
        roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
                'china': true
            }
        },
        series : [
            {
                name: '舆情数量',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                layoutCenter: ['60%', '45%'],
                layoutSize: 350,
                data:series1
            }
        ]
    };
    var main2 = echarts.init(document.getElementById('main2'));
    // 指定图表的配置项和数据
    var yAxis =[];
    var series2=[];
    var option2 = {
        title: {
            text: '省份排行',
            textStyle:{
                color : '#00fdff'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        textStyle:{
            color : '#00fdff'
        },
        // legend: {
        //     data: ['2011年', '2012年']
        // },
        grid: {
            left: '3%',
            right: '14%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: yAxis
        },
        series: [
            {
                name:'产量',
                type:'bar',
                itemStyle:{
                    normal: {color: '#122c3b',borderColor:'#00fdff',borderWidth: 1 }
                },
                data: series2
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenRegion"),
        data: {timeType:timeType},
        success: function (data) {
            var len =data.length;
            var num=1;
            $.each(data,function(i,item){
                var id = item.regionid;
                var regionName = item.regionName;
                var v1 = regionName.substring(0,regionName.length-1);
                var v2 = regionName.substring(regionName.length-1,regionName.length);
                if(v2=="省"||v2=="市"){
                    regionName=v1;
                }
                var cnt = item.cnt;
                var value1 = {
                    name:regionName,
                    value:cnt
                };
                series1.push(value1);
                var a = len-num;
                if(a>=0){
                    yAxis[a] =regionName;
                    series2[a]=cnt;
                }
                num++;
            });
            main1.setOption(option1);
            main2.setOption(option2);
        }
    });
}

function chart5(){
    var formatter1="";
    var negativeNo=0;
    var cnt=0;
    var dataVal=[];
    // 基于准备好的dom，初始化echarts实例
    var main5 = echarts.init(document.getElementById('main5'));
    // 指定图表的配置项和数据
    var option1 = {
        tooltip : {
            formatter:formatter1
        },
        toolbox: {
            show : true,
            textStyle:{
                color : '#fff'
            }
        },
        series : [
            {
                name:'舆情负面比重',
                type:'gauge',
                splitNumber: 10,       // 分割段数，默认为5
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: [[0.2, '#8be28b'],[0.8, '#48b'],[1, '#ff4500']],
                        width: 8
                    }
                },
                axisTick: {            // 坐标轴小标记
                    splitNumber: 10,   // 每份split细分多少段
                    length :12,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    show: true,        // 默认显示，属性show控制显示与否
                    length :30,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                pointer : {
                    width : 5
                },
                title : {
                    show : true,
                    offsetCenter: [0, '-40%'],       // x, y，单位px
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        color:"#fff"
                    }
                },
                detail : {
                    formatter:'{value}%',
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        color: 'auto',
                        fontWeight: 'bolder'
                    }
                },
                data:dataVal
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenSentiment"),
        data: {timeType:timeType},
        success: function (data) {
            cnt = data.cnt;
            negativeNo = data.negativeNo;
            var percent = (negativeNo/cnt)*100+'00';
            //var aa = percent.substr(0,5);
            var a2=0;
            if(percent.indexOf(".")==-1){
                a2=percent;
            }else{
                a2 = percent.substring(0,percent.indexOf(".")+1)+percent.substr(percent.indexOf(".")+1,2);
            }
            if(negativeNo == 0){
                a2=0.00
            }
            option1.tooltip.formatter="总数："+cnt+"<br>负面："+negativeNo;
            var value1 = {
                value:a2,
                name:"负面"
            }
            dataVal.push(value1);
            main5.setOption(option1);
        }
    });

    //点击圆点触发函数
}

function chart3(){
    // 基于准备好的dom，初始化echarts实例
    var dataVal =[];
    var fCntVal=[];
    var zCntVal=[];
    var main3 = echarts.init(document.getElementById('main3'));
    // 指定图表的配置项和数据
    var optionW = {
        tooltip : {
            trigger: 'item',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['负面', '非负面'],
            textStyle : {
                color : '#fff'
            },
            y:'15px'
        },
        textStyle : {
            color : '#fff'
        },
        toolbox: {
            show : true,
            feature : {
                // mark : {show: true},
                // dataView : {show: true, readOnly: false},
                // magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                // restore : {show: true},
                // saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            x: 30,
            x2:100
        },
        xAxis : [
            {
                type : 'value'
            }
        ],
        yAxis : [
            {
                type : 'category',
                data : []
            },{
                type : 'category',
                data : dataVal
            }
        ],
        series : [
            {
                name:'负面',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:fCntVal
            },
            {
                name:'非负面',
                type:'bar',
                stack: '总量',
                itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
                data:zCntVal
            }

        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenWebsite"),
        data: {timeType:timeType},
        success: function (data) {
            var num=data.length;
            $.each(data,function(i,item){
                var k = num-1;
                dataVal[k] = item.siteName;
                fCntVal[k]=item.fCnt;
                zCntVal[k]=item.zCnt-item.fCnt;
                num--;
            });
            main3.setOption(optionW);
        }
    });
}

function chart11(){
    var series1=[];
    // 基于准备好的dom，初始化echarts实例
    var main11 = echarts.init(document.getElementById('main11'));
    // 指定图表的配置项和数据
    var  option1 = {
        tooltip : {
            trigger: 'item'
        },
        dataRange: {
            min: 100,
            max: 10000,
            x: '30px',
            y: '30px',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true,
            color: ['#365562','#e0ffff'],
            textStyle:{
                color : '#fff'
            }
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            x: 'right',
            y: 'center'
        },
        roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
                'china': true
            }
        },
        series : [
            {
                name: '舆情数量',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                layoutCenter: ['55%', '45%'],
                layoutSize: 600,
                data:series1
            }
        ]
    };
    var main12 = echarts.init(document.getElementById('main12'));
    // 指定图表的配置项和数据
    var yAxis =[];
    var series2=[];
    var option2 = {
        title: {
            text: '省份排行',
            textStyle:{
                color : '#00fdff'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        textStyle:{
            color : '#00fdff'
        },
        // legend: {
        //     data: ['2011年', '2012年']
        // },
        grid: {
            left: '3%',
            right: '14%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: yAxis
        },
        series: [
            {
                name:'产量',
                type:'bar',
                itemStyle:{
                    normal: {color: '#122c3b',borderColor:'#00fdff',borderWidth: 1 }
                },
                data: series2
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    $.ajax({
        type: "post",
        dataType: "json",
        url: encodeURI(pageContent + "/dataScreen/dataScreenRegion"),
        data: {timeType:timeType},
        success: function (data) {
            var len =data.length;
            var num=1;
            $.each(data,function(i,item){
                var id = item.regionid;
                var regionName = item.regionName;
                var v1 = regionName.substring(0,regionName.length-1);
                var v2 = regionName.substring(regionName.length-1,regionName.length);
                if(v2=="省"||v2=="市"){
                    regionName=v1;
                }
                var cnt = item.cnt;
                var value1 = {
                    name:regionName,
                    value:cnt
                };
                series1.push(value1);
                var a = len-num;
                if(a>=0){
                    yAxis[a] =regionName;
                    series2[a]=cnt;
                }
                num++;
            });
            main11.setOption(option1);
            main12.setOption(option2);
        }
    });
}
// 放大JS
function showMask(){
    $("#mask1").show();
    chart11();
    $("#btn_close").click(function(){
        $("#mask1").hide();
        chart11();
    });
}