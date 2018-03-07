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

	<div title="角色列表" class="easyui-panel" style="padding:10px">
        <table id="s_data" >

        </table>
	</div>
	
	<div id="addPermission_div" class="easyui-dialog" style="width:700px;height:200px;padding:20px 20px;">
		<form id="addPermission_form" method="post">
            <table cellpadding="5">
                <tr>
                    <td>权限字符串 :</td>
                    <td><input style="width: 500px" id="addPermission_form_permission" name="permission"></input></td>
                </tr>
                <tr>
                    <td colspan="2">eg : AccountController,list,platform_type_id,用户列表;AccountController,exportAccount,platform_type_id,用户列表导出;</td>
                </tr>
            </table>
        </form>
	</div>

    <div id="addRole_div" class="easyui-dialog" style="width:1200px;height:700px;padding:20px 20px;">
        <span style="color: red">&nbsp;角色添加请联系技术开发</span><br>
        <form id="addRole_form" method="post">
            <table cellpadding="5">
                <tr>
                    <td>新角色:</td>
                    <td><input id="addRole_form_role" name="addRole_form_role"></input></td>
                </tr>
                <tr>
                    <td>描述:</td>
                    <td><input id="addRole_form_desc" name="addRole_form_desc"></input></td>
                </tr>
                <tr>
                    <td>部门:</td>
                    <td>
                        <select name="" id="addRole_form_department">
                            <#list departmentCode as dc >
                                <option value = "${dc.code}" >${dc.desc} </option>
                            </#list>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>资源分配:</td>
                    <td>
                        <table border="1">
                            <#list  permissionList as bl >
                                <tr class="resources">
                                    <td>
                                        ${bl.category}:
                                    </td>
                                    <td>
                                        <input type="checkbox" name="re_permission_all" onclick="checkAll(this)" ><span style="color: red">全选</span>
                                        <#list  bl.pes as b2 >
                                            <input type="checkbox" name="re_permission" value="${b2.id}"><span title="${b2.permission}">${b2.description}</span>
                                        </#list>
                                        <br>
                                    </td>
                                </tr>
                            </#list>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div id="copyRole_div">
        <select name="roleId" id="copyRole">
            <#list roleList as rl >
                <option value = "${rl.id}" >${rl.role} </option>
            </#list>
        </select>
        <br/>
        <input type="text" name="role" id="copyRoleName" >
    </div>

    <div id="editRole_div">

    </div>

    <input type="hidden" value="${rc.contextPath}" id="contextPath">

</div>

<script>

	function refreshPermission(){
		$.messager.progress();
		$.ajax({
            url: '${rc.contextPath}/admin/refreshPermission',
            type: 'post',
            dataType: 'json',
            success: function(data){
                $.messager.progress('close');
                if(data.status == 1){
                    $.messager.alert("提示","刷新成功","info",function(){
                        window.location.href=window.location.href;
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
    function checkAll(obj){
        if($(obj).is(':checked')){
            $(obj).parent().find("input[name='re_permission']").prop("checked",true);
        }else{
            $(obj).parent().find("input[name='re_permission']").prop("checked",false);
        }
    }

	function editRole(id){
        var url = $("#contextPath").val() + "/admin/editRoleForm?roleId=" + id;
        $('#editRole_div').dialog('open');
        $('#editRole_div').dialog('refresh', url);
	}

	$(function(){
        $('#editRole_div').dialog({
            title: '权限修改',
            width: 1100,
            height: 700,
            closed: true,
            <#--href: '${rc.contextPath}/admin/editRoleForm',-->
            buttons:[{
                text:'关闭    ',
                align:'left',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#editRole_div').dialog("close");
                }
            }]
        });

        $('#addPermission_div').dialog({
            title:'新增权限',
            collapsible:true,
            closed:true,
            modal:true,
            buttons:[{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var permission=$("#addPermission_form_permission").val();
                    $.ajax({
                        url: '${rc.contextPath}/admin/addPermission',
                        type: 'post',
                        dataType: 'json',
                        data: {'permission':permission},
                        success: function(data){
                            $.messager.progress('close');
                            if(data.status == 1){
                                $.messager.alert("提示","修改成功","info",function(){
                                    $('#addPermission_div').dialog('close');
                                    window.location.href=window.location.href;
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
            },{
                text:'取消',
                align:'left',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#addPermission_div').dialog('close');
                }
            }]
        });
	
		$('#addRole_div').dialog({
            title:'新增角色',
            collapsible:true,
            closed:true,
            modal:true,
            buttons:[{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                	$.messager.progress();
                	var addRole_form_role=$("#addRole_form_role").val();
                    var addRole_form_desc=$("#addRole_form_desc").val();
                    var addRole_form_department=$("#addRole_form_department").val();
                	var permissionStr="";
                	$(".resources").each(function(){
                        var resources="";
                        $(this).find("td").eq(1).find("input[name='re_permission']:checked").each(function(){
                            resources+=$(this).val()+",";
                        });
                        permissionStr+=resources;
                	});
                	$.ajax({
    	                url: '${rc.contextPath}/admin/updateRole',
    	                type: 'post',
    	                dataType: 'json',
    	                data: {'role':addRole_form_role,'resource':permissionStr,'desc':addRole_form_desc,'departmentId':addRole_form_department},
    	                success: function(data){
    	                	$.messager.progress('close');
    	                    if(data.status == 1){
    	                    	$('#s_data').datagrid('load');
    	                    	$('#addRole_div').dialog('close');
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
            },{
                text:'取消',
                align:'left',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#addRole_div').dialog('close');
                }
            }]
        });

        $('#copyRole_div').dialog({
            title:'复制角色',
            collapsible:true,
            closed:true,
            modal:true,
            buttons:[{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                    $.messager.progress();
                    var roleId=$("#copyRole").val();
                    var roleName= $.trim($("#copyRoleName").val());
                    if(roleId == '' || roleId == 0) {
                        alert("请选择要复制的角色！");$.messager.progress('close');return;
                    }
                    if(roleName == '') {
                        alert("请填写复制角色的名称！");$.messager.progress('close');return;
                    }
                    $.ajax({
                        url: '${rc.contextPath}/admin/copyRole',
                        type: 'post',
                        dataType: 'json',
                        data: {'roleId':roleId,'roleName':roleName},
                        success: function(data){
                            $.messager.progress('close');
                            if(data.status == 1){
                                $('#s_data').datagrid('load');
                                $('#copyRole_div').dialog('close');
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
            },{
                text:'取消',
                align:'left',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#copyRole_div').dialog('close');
                }
            }]
        });
	
		$('#s_data').datagrid({
            nowrap: false,
            striped: true,
            collapsible:true,
            idField:'id',
            url:'${rc.contextPath}/admin/roleInfo',
            loadMsg:'正在装载数据...',
            fitColumns:true,
            remoteSort: true,
            singleSelect:true,
            columns:[[
                {field:'id',    title:'ID', width:50, align:'center'},
                {field:'role',    title:'名称', width:30, align:'center'},
                {field:'description',     title:'角色描述',  width:100,   align:'center' },
                {field:'department',     title:'部门',  width:40,   align:'center' },
                {field:'hidden',  title:'操作', width:80,align:'center',
                    formatter:function(value,row,index){
                        var a = '<a href="javaScript:;" onclick="editRole(' + row.id + ')">修改</a>';
                        return a;
                    }
                }
            ]],
            toolbar:[{
                    iconCls: 'icon-add',
                    text:'新增权限',
                    handler: function(){
                    	$("#addPermission_form_permission").val("");
                    	$('#addPermission_div').dialog('open');
                    }
                },
                {
                    iconCls: 'icon-add',
                    text:'新增角色',
                    handler: function(){
                        $("#addRole_form_desc").val("");
                        $("#addRole_form_role").val("");
                        $("#addRole_form_department").val("");
                        $("input[name='re_permission']:checked").each(function(){
                            $(this).prop("checked",false);
                        });
                        $("input[name='re_permission_all']:checked").each(function(){
                            $(this).prop("checked",false);
                        });
                        $('#addRole_div').dialog('open');
                    }
                },
                {
                    iconCls: 'icon-reload',
                    text:'刷新权限',
                    handler: function(){
                    	refreshPermission();
                    }
                },
                {
                    iconCls: 'icon-add',
                    text:'复制权限',
                    handler: function(){
                        $("#copyRole").find("option:selected").attr("selected", false);
                        $("#copyRoleName").val('');
                        $('#copyRole_div').dialog('open');
                    }
                }
            ]
        });
	})
</script>

</body>
</html>