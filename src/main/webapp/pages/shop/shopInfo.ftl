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
                <span>店铺名称：</span>
                <span><input id="searchName" name="searchName"/></span>
                <span>是否可用：</span>
                <span>
					<select id="searchIsAvailable" name="searchIsAvailable">
                        <option value="-1">全部</option>
                        <option value="1">可用</option>
                        <option value="0">停用</option>
                    </select>
				</span>
                <span>
					<a id="searchBtn" onclick="searchShopInfo()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
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
                        <span>名称：</span>
                        <span><input type="text" name="name" id="name" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>详细地址：</span>
                        <span><input type="text" name="detailAddress" id="detailAddress" value="" maxlength="200" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>地址编码：</span>
                        <span><input type="text" name="code" id="code" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>手机号：</span>
                        <span><input type="text" name="mobileNumber" id="mobileNumber" value="" maxlength="32" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>头像：</span>
                        <span><input type="text" name="head" id="head" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('head')" href="javascript:;" class="easyui-linkbutton">上传图片</a><font color="red">*</font>
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

<div id="picDia" class="easyui-dialog" icon="icon-save" align="center"
     style="padding: 5px; width: 300px; height: 150px;">
    <form id="picForm" method="post" enctype="multipart/form-data">
        <input id="picFile" type="file" name="picFile" />&nbsp;&nbsp;<br/><br/>
        <a href="javascript:;" onclick="picUpload()" class="easyui-linkbutton" iconCls='icon-reload'>提交图片</a>
    </form>
    <br><br>
</div>

<script type="application/javascript">

    function searchShopInfo() {
        $('#s_data').datagrid('load', {
            name : $("#searchName").val(),
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
        $("input[name='name']").val(arr.rows[index].name);
        $("input[name='detailAddress']").val(arr.rows[index].detailAddress);
        $("input[name='code']").val(arr.rows[index].code);
        $("input[name='mobileNumber']").val(arr.rows[index].mobileNumber);
        $("input[name='head']").val(arr.rows[index].head);
        $('#editDiv').dialog('open');
    }

    function editIsDisplay(id,isAvailable) {
        $.ajax({
            url: '${rc.contextPath}/shopInfo/updateShopInfo',
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
                $.post("${rc.contextPath}/shopInfo/deleteShopInfo",{id:id},function(data){
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
                        params.name = $("input[name='name']").val();
                        params.detailAddress = $("input[name='detailAddress']").val();
                        params.code = $("input[name='code']").val();
                        params.head = $("input[name='head']").val();
                        if(params.mobileNumber == '' || params.name == '' || params.detailAddress == '' || params.code == '' || params.head == '') {
                            $.messager.alert("error","请填写完整信息","error");
                            return false;
                        } else {
                            $.messager.progress();
                            $.ajax({
                                url: '${rc.contextPath}/shopInfo/saveOrUpdateShopInfo',
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
            url:'${rc.contextPath}/shopInfo/jsonShopInfo',
            loadMsg:'正在装载数据...',
            singleSelect:true,
            fitColumns:true,
            remoteSort: true,
            pageSize:50,
            pageList:[50,60],
            columns:[[
                {field:'id',    title:'ID', width:20, align:'center'},
                {field:'name', title:'名称', width:50, align:'center'},
                {field:'detailAddress', title:'详细地址', width:50, align:'center'},
                {field:'code', title:'地址编码', width:50, align:'center'},
                {field:'mobileNumber', title:'手机号', width:50, align:'center'},
                {field:'income', title:'总收益', width:50, align:'center'},
                {field:'withdraw', title:'总收入', width:50, align:'center'},
                {field:'isAvailable',    title:'可用状态', width:30, align:'center',
                    formatter:function(value,row,index){
                        if(row.isAvailable == 1){
                            return '可用';
                        }else{
                            return '停用';
                        }
                    }
                },
                {field:'isOpen', title:'是否开店', width:30, align:'center',
                    formatter:function(value,row,index){
                        if(row.isAvailable == 1){
                            return '开店';
                        }else{
                            return '关店';
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
                    $("input[name='name']").val("");
                    $("input[name='code']").val("");
                    $("input[name='detailAddress']").val("");
                    $("input[name='mobileNumber']").val("");
                    $("input[name='head']").val("");
                    $('#editDiv').dialog('open');
                }
            }],
            pagination:true
        });
    });

    $(function(){
        $('#picDia').dialog({
            title:'图片上传窗口',
            collapsible:true,
            closed:true,
            modal:true
        });
    });

    var inputId;
    function picDialogOpen($inputId) {
        inputId = $inputId;
        $("#picDia").dialog("open");
        $("#yun_div").css('display','none');
    }

    function picDialogClose() {
        $("#picDia").dialog("close");
    }

    function picUpload() {
        $('#picForm').form('submit',{
            url:"${rc.contextPath}/pic/fileUpLoad",
            success:function(data){
                var res = eval("("+data+")")
                if(res.status == 1){
                    $.messager.alert('响应信息',"上传成功...",'info',function(){
                        $("#picDia").dialog("close");
                        if(inputId) {
                            $("#"+inputId).val(res.url);
                            $("#picFile").val("");
                        }
                        return
                    });
                } else{
                    $.messager.alert('响应信息',res.msg,'error',function(){
                        return ;
                    });
                }
            }
        })
    }

</script>

</body>
</html>
