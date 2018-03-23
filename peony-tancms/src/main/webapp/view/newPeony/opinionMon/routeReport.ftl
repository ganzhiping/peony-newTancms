<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>舆情监测路线图</title>
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/reset.css">
    <link rel="stylesheet" href="${request.getContextPath()}/css/newPeony/css/luxiantu2.css">
    <script type="text/javascript" src="${request.getContextPath()}/js/newPeony/opinionMon/js/jquery-1.8.3.min.js"></script>
    <script>
      /*  $(function(){
             $("#subjectNameValue").val(currentNode.name);
            //点击选中当前效果
            $("#TabChange>li").click(function(){
                $(this).addClass('active');
                $(this).siblings('li').removeClass("active");
                $('#Tabcon>div:eq('+$(this).index()+')').show().siblings().hide();
            })
        })*/
      function refresh(id) {
          var pageContext = $("#pageContext").val();
          window.parent.$("#iframe").attr("src","/opinionMon/opinionRouteReport?subjectid=" + id);
      }
    </script>
</head>
<body id="fbody">
<input type="hidden" id="pageContext" value="${request.getContextPath()}">
<div class="luxianCon">
    <div class="Tab">
        <!-- tab切换 -->
        <ul id="TabChange" class="TabChange">
         <#--   <li>地域路线</li>-->
            <li class="active">媒体路线</li>
        </ul>
        <div id="Tabcon">
            <!-- 地域路线 -->
            <div>
                <div class="luxianTitle"><span id="mediaName"></span>媒体传播路线图（近五天）</div>
                <div class="luxianTime">
                    <div class="luxianPic">
                        <#list routeReports?keys as key>
                            <ul class="luxianPic_ul1 ">
                                <span class="luxianTime_1">${key}</span>
                            <#list routeReports[key] as values>
                                <#if (values!=null&&""!=values)>
                                    <li><div>${values.website}<span>(${values.report})</span></div></li>
                                </#if>
                            </#list>
                            </ul>
                        </#list>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script>
      $(function() {
          var name = window.parent.$("#subjectNameValue").val();
          $("#mediaName").html(name);
          var height1 =$("#fbody").height();
          $(window.parent.document).find("#iframe").height(height1);
      })
    </script>
 </html>