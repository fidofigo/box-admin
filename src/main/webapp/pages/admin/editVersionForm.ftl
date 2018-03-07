<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>格格家-后台管理</title>
</head>
<body>

<div id="">

    <form>
        <input type="hidden" id="ajax_id" value="${id!'0'}" />
            <table style="width:100%">
                <tr>
                    <td style="width:20%;text-align: right;"> APP网址：</td>
                    <td style="width:80%"><input type="text" id="ajax_version_url" value="${url!''}" /></td>
                </tr>
                <tr>
                    <td style="width:20%;text-align: right;"> 版本号：</td>
                    <td><input type="text" id="ajax_version_version" value="${version!''}" />例如:2.7、2.71，非法:2.7.1</td>
                </tr>
                <tr>
                    <td style="width:20%;text-align: right;">备注：</td>
                    <td><textarea style="width:90%;height: 100px;" id="ajax_version_remark">${remark!''}</textarea>各行之间用 | 分隔</td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;"><input style="width: 150px; height: 30px" type="button" id="save" value="保存"></td>
                </tr>
            </table>
        <br>

    </form>

</div>
<script>
    $(function(){
        $("#save").click(function(){
            var id = $("#ajax_id").val();
            var ajax_version_url = $("#ajax_version_url").val();
            var ajax_version_version = $("#ajax_version_version").val();
            var ajax_version_remark = $("#ajax_version_remark").val();
            $.ajax({
                url: '${rc.contextPath}/admin/updateVersion',
                type: 'post',
                dataType: 'json',
                data: {'id':id,'url':ajax_version_url,'version':ajax_version_version,'remark':ajax_version_remark},
                success: function(data){
                    $.messager.progress('close');
                    if(data.status == 1){
                        $.messager.alert("提示","保存成功","info",function(){
                            window.location.href = window.location.href;
                        });
                    }else{
                        $.messager.alert("提示",data.msg,"error");
                    }
                },
                error: function(xhr){
                    $.messager.progress('close');
                    $.messager.alert("提示",'服务器忙，请稍后再试。('+xhr.status+')',"info");
                }
            });
        })
    })
</script>
</body>
</html>