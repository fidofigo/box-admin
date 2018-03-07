<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>格格家-后台管理</title>
<link href="${rc.contextPath}/js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/js/uploadify/jquery-1.4.2.min.js"></script>
<script src="${rc.contextPath}/js/uploadify/swfobject.js"></script>
<script src="${rc.contextPath}/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>

</head>
<body>

<div id="fileQueue"></div>
<br/>

<input type="file" name="uploadify" id="uploadify" />
<br/>
<p>
    <a href="javascript: jQuery('#uploadify').uploadifyUpload()">开始上传</a>&nbsp;
    <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
</p>
<p>统计上传总数计数：<li id="total"></li></p>
<p>统计上传成功计数：<li id="count"></li></p>
<p>上传失败文件名称：<li id="errorMSG"></li></p>
<br/><br/>


<script type="text/javascript">
    var total=0;
    var count=0;
    var errorMSG="";
    $(document).ready(function(){
        $("#uploadify").uploadify({
            'uploader'	:	"${rc.contextPath}/js/uploadify/uploadify.swf",
            'script'    :	"${rc.contextPath}/idCard/batchIDCardFileUpLoad",
            'cancelImg' :	"${rc.contextPath}/js/uploadify/cancel.png",
            'queueId'	:	"fileQueue",
            'queueSizeLimit'	:	20000,//限制上传文件的数量
            'fileExt'	:	"*.jpg,*.png,*.gif,*.bmp,*.jpeg"  , //"*.RAR,*.rar",
            'auto'		:	false,
            'multi'		:	true,//是否允许多文件上传
            'simUploadLimit':	10,//同时运行上传的进程数量
            'fileSizeLimit':'500KB',
            'buttonText':	"files",
            'method'	:	"POST",
            'onComplete': function(_event,queueId,fileObj,response,data) {
                var jsonData = eval("("+response+")");
                if(jsonData.status){
                    count=count+1;
                }else{
                    if(errorMSG==""){
                        errorMSG=jsonData.fileName;
                    }else{
                        errorMSG=","+jsonData.fileName;
                    }
                }
                total=total+1;
                $('#count').html(count);
                $('#total').html(total);
                $('#errorMSG').append(errorMSG);
            }
        });

    });
</script>

</body>
</html>