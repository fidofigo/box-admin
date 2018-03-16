<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>box-后台管理</title>
    <link href="${rc.contextPath}/pages/js/jquery-easyui-1.4/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${rc.contextPath}/pages/js/jquery-easyui-1.4/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${rc.contextPath}/pages/js/jquery-easyui-1.4/jquery.min.js"></script>
    <script src="${rc.contextPath}/pages/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script src="${rc.contextPath}/pages/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
    <script src="${rc.contextPath}/pages/js/My97DatePicker/WdatePicker.js"></script>

</head>
<body class="easyui-layout">
<div data-options="region:'west',title:'menu',split:true" style="width:180px;">
    <input type="hidden" value="${nodes!0}" id="nowNode"/>
<#include "../common/menu.ftl" >
</div>

<div data-options="region:'center'" style="padding:5px;">
    <div id="cc" class="easyui-layout" data-options="fit:true" >
        <div data-options="region:'north',title:'商铺用户系统',split:true" style="height: 100px;">
            <br/>
            <div style="height: 60px;padding: 10px">
                <span>邮箱：</span>
                <span><input id="searchEmail" name="searchEmail"/></span>
                <span>是否可用：</span>
                <span>
					<select id="searchIsAvailable" name="searchIsAvailable">
                        <option value="-1">全部</option>
                        <option value="1">可用</option>
                        <option value="0">停用</option>
                    </select>
				</span>
                <span>
					<a id="searchBtn" onclick="searchShopAccount()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				</span>
            </div>
        </div>
        <div data-options="region:'center'" >
            <!--数据表格-->
            <table id="s_data" style=""></table>

            <!-- 新增 begin -->
            <div id="editDiv" class="easyui-dialog" style="width:550px;height:300px;padding:15px 20px;">
                <form id="editDiv_form" method="post">
                    <input id="editId" type="hidden" name="editId" value="0" >
                    <p>
                        <span>邮箱：</span>
                        <span><input type="text" name="email" id="email" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>密码：</span>
                        <span><input type="text" name="password" id="password" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>店铺id：</span>
                        <span><input type="text" name="shopId" id="shopId" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>手机号：</span>
                        <span><input type="text" name="mobileNumber" id="mobileNumber" value="" maxlength="32" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>可用状态：</span>
                        <span><input type="radio" name="isAvailable" id="isAvailable1" checked="checked" value="1"/>可用&nbsp;&nbsp;</span>
                        <span><input type="radio" name="isAvailable" id="isAvailable0" value="0"/>停用</span>
                    </p>
                </form>
            </div>
            <!-- 新增 end -->
        </div>
    </div>
</div>

<script type="application/javascript">

    function searchShopAccount() {
        $('#s_data').datagrid('load', {
            email : $("#searchEmail").val(),
            isAvailable : $("#searchIsAvailable").val()
        });
    }

    function editIt(index){
        var arr=$("#s_data").datagrid("getData");
        $("input[name='editId']").val(arr.rows[index].id);
        if(arr.rows[index].isAvailable == 1) {
            $("#isAvailable1").prop("checked", "checked");//prop
        } else {
            $("#isAvailable0").prop("checked", "checked");//prop
        }
        $("input[name='email']").val(arr.rows[index].email);
        $("input[name='password']").val(arr.rows[index].pwd);
        $("input[name='shopId']").val(arr.rows[index].shopId);
        $("input[name='mobileNumber']").val(arr.rows[index].mobileNumber);
        $('#editDiv').dialog('open');
    }

    function editIsDisplay(id,isAvailable) {
        $.ajax({
            url: '${rc.contextPath}/shopAccount/updateShopAccount',
            type:"POST",
            data: {id:id,isAvailable:isAvailable},
            success: function(data) {
                if(data.status == 1){
                    $('#s_data').datagrid('load');
                    return
                } else{
                    $.messager.alert('响应信息',data.msg,'error');
                }
            }
        });
    }

    function deleteIt(id){
        $.messager.confirm("提示信息","确定删除么？",function(re){
            if(re){
                $.messager.progress();
                $.post("${rc.contextPath}/shopAccount/deleteShopAccount",{id:id},function(data){
                    $.messager.progress('close');
                    if(data.status == 1){
                        $.messager.alert('响应信息',"删除成功...",'info',function(){
                            $('#s_data').datagrid('reload');
                            return
                        });
                    } else{
                        $.messager.alert('响应信息',data.msg,'error',function(){
                            return
                        });
                    }
                })
            }
        })
    }

    $(function(){

        $('#editDiv').dialog({
            title:'商铺用户系统',
            collapsible: true,
            closed: true,
            modal: true,
            buttons: [
                {
                    text: '保存',
                    iconCls: 'icon-ok',
                    handler: function(){
                        var params = {};
                        params.id = $("input[name='editId']").val();
                        params.isAvailable = $("input[name='isAvailable']:checked").val();
                        params.mobileNumber = $("input[name='mobileNumber']").val();
                        params.shopId = $("input[name='shopId']").val();
                        params.email = $("input[name='email']").val();
                        params.pwd = $("input[name='password']").val();
                        if(params.mobileNumber == '' || params.shopId == '' || params.email == '' || params.pwd == '') {
                            $.messager.alert("error","请填写完整信息","error");
                            return false;
                        } else {
                            $.messager.progress();
                            $.ajax({
                                url: '${rc.contextPath}/shopAccount/saveOrUpdateShopAccount',
                                type: 'post',
                                dataType: 'json',
                                data: params,
                                success: function(data){
                                    $.messager.progress('close');
                                    if(data.status == 1){
                                        $('#s_data').datagrid('load');
                                        $('#editDiv').dialog('close');
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
                    }
                },
                {
                    text:'取消',
                    align:'left',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#editDiv').dialog('close');
                    }
                }]
        });

        $('#s_data').datagrid({
            nowrap: false,
            striped: true,
            collapsible:true,
            idField:'id',
            url:'${rc.contextPath}/shopAccount/jsonShopAccount',
            loadMsg:'正在装载数据...',
            singleSelect:true,
            fitColumns:true,
            remoteSort: true,
            pageSize:50,
            pageList:[50,60],
            columns:[[
                {field:'id',    title:'ID', width:20, align:'center'},
                {field:'email', title:'邮箱', width:50, align:'center'},
                {field:'pwd', title:'密码', width:50, align:'center'},
                {field:'shopId', title:'店铺id', width:50, align:'center'},
                {field:'mobileNumber', title:'手机号', width:50, align:'center'},
                {field:'isAvailable',    title:'可用状态', width:30, align:'center',
                    formatter:function(value,row,index){
                        if(row.isAvailable == 1){
                            return '可用';
                        }else{
                            return '停用';
                        }
                    }
                },
                {field:'hidden',  title:'操作', width:50,align:'center',
                    formatter:function(value,row,index){
                        var a = '<a href="javascript:;" onclick="editIt(' + index + ')">编辑</a> | ';
                        var c = '';
                        if (row.isAvailable == 1)
                            c = '<a href="javascript:void(0);" onclick="editIsDisplay(' + row.id + ',' + 0 + ')">停用</a> | ';
                        if (row.isAvailable == 0)
                            c = '<a href="javascript:void(0);" onclick="editIsDisplay(' + row.id + ',' + 1 + ')">可用</a> | ';
                        var e = '<a href="javascript:;" onclick="deleteIt(' + row.id + ')">删除</a> | ';
                        return a + c + e;
                    }
                }
            ]],
            toolbar:[{
                id:'_add',
                text:'新增商铺用户',
                iconCls:'icon-add',
                handler:function(){
                    $("input[name='editId']").val("0");
                    $("#isAvailable1").prop("checked", "checked");
                    $("input[name='email']").val("");
                    $("input[name='password']").val("");
                    $("input[name='shopId']").val("");
                    $("input[name='mobileNumber']").val("");
                    $('#editDiv').dialog('open');
                }
            }],
            pagination:true
        });
    });

</script>

</body>
</html>
