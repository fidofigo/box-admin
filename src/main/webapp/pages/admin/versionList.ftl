<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>格格家-后台管理</title>
<link href="${rc.contextPath}/js/jquery-easyui-1.4/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${rc.contextPath}/js/jquery-easyui-1.4/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="${rc.contextPath}/js/jquery-easyui-1.4/jquery.min.js"></script>
<script src="${rc.contextPath}/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script src="${rc.contextPath}/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>

</head>
<body class="easyui-layout">

<div data-options="region:'west',title:'menu',split:true" style="width:180px;">
<input type="hidden" value="${nodes!0}" id="nowNode"/>
	<#include "../common/menu.ftl" >
</div>

<div data-options="region:'center'" style="padding:5px;">

	<div title="版本列表" class="easyui-panel" style="padding:10px">
        <table id="s_data" ></table>
	</div>
	<div id="editVersion_div">

    </div>
    <input type="hidden" value="${rc.contextPath}" id="contextPath">

</div>

<script>
    function updateVersion(id){
        var url = $("#contextPath").val() + "/admin/editVersionForm?versionId=" + id;
        $('#editVersion_div').dialog('refresh', url);
        $('#editVersion_div').dialog('open');
    }

	function controlVersion(id,value){
        $.ajax({
            url: '${rc.contextPath}/admin/controlVersion',
            type: 'post',
            dataType: 'json',
            data: {'id':id,'available':value},
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
	}

	$(function(){

        $('#editVersion_div').dialog({
            title: '版本更新',
            width: 500,
            height: 300,
            closed: true,
            href: '${rc.contextPath}/admin/editVersionForm',
            buttons:[{
                text:'关闭    ',
                align:'left',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#editVersion_div').dialog("close");
                }
            }]
        });

        function formatTime(num){
            if(num<10){
                return "0"+num;
            }else{
                return num;
            }
        }

		$('#s_data').datagrid({
            nowrap: false,
            striped: true,
            collapsible:true,
            idField:'id',
            url:'${rc.contextPath}/admin/versionInfo',
            loadMsg:'正在装载数据...',
            fitColumns:true,
            remoteSort: true,
            singleSelect:true,
            pageSize:50,
            pageList:[50,100],
            columns:[[
//                {field:'id',    title:'ID', width:50, align:'center'},
                {field:'channelName',    title:'名称', width:30, align:'center'},
                {field:'updateTime',     title:'最近更新时间',  width:100,   align:'center' ,formatter:function(value,row){
                    var date = new Date(row.updateTime);
                    Y = date.getFullYear() + '-';
                    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                    D = date.getDate() + ' ';
                    h = formatTime(date.getHours()) + ':';
                    m = formatTime(date.getMinutes()) + ':';
                    s = formatTime(date.getSeconds());
                    return Y+M+D+h+m+s;
                }},
                {field:'version',     title:'版本号',  width:100,   align:'center' },
                {field:'hidden',  title:'操作', width:80,align:'center',
                    formatter:function(value,row){
                        var a ="";
                        a += '<a href="javaScript:;" onclick="updateVersion(' + row.id + ')">更新</a>&nbsp;';
                        if(row.available==1){
                            a += '<a href="javaScript:;" onclick="controlVersion(' + row.id + ',0)">暂停</a>';
                        }else{
                            a += '<a href="javaScript:;" onclick="controlVersion(' + row.id + ',1)">启用</a>';
                        }
                        return a;
                    }
                }
            ]],
            pagination:true
        });
	})
</script>

</body>
</html>