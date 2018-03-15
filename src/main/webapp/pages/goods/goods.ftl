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
        <div data-options="region:'north',title:'商品系统',split:true" style="height: 100px;">
            <br/>
            <div style="height: 60px;padding: 10px">
                <span>商品名称：</span>
                <span><input id="searchTitle" name="searchTitle"/></span>
                <span>是否可用：</span>
                <span>
					<select id="searchIsAvailable" name="searchIsAvailable">
                        <option value="-1">全部</option>
                        <option value="1">可用</option>
                        <option value="0">停用</option>
                    </select>
				</span>
                <span>
					<a id="searchBtn" onclick="searchGoods()" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&nbsp;
				</span>
            </div>
        </div>
        <div data-options="region:'center'" >
            <!--数据表格-->
            <table id="s_data" style=""></table>

            <!-- 新增 begin -->
            <div id="editDiv" class="easyui-dialog" style="width:550px;height:450px;padding:15px 20px;">
                <form id="editDiv_form" method="post">
                    <input id="editId" type="hidden" name="editId" value="0" >
                    <p>
                        <span>基础商品id：</span>
                        <span><input type="text" name="goodsBaseId" id="goodsBaseId" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>商品名称：</span>
                        <span><input type="text" name="name" id="name" value="" maxlength="64" style="width: 300px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>商品描述：</span>
                        <span><input type="text" name="desc" id="desc" value="" maxlength="500" style="width: 300px"/></span>
                        <font color="red">*不超过500字</font>
                    </p>
                    <p>
                        <span>图片1：</span>
                        <span><input type="text" name="image1" id="image1" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('image1')" href="javascript:;" class="easyui-linkbutton">上传图片</a><font color="red">*</font>
                    </p>
                    <p>
                        <span>图片2：</span>
                        <span><input type="text" name="image2" id="image2" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('image2')" href="javascript:;" class="easyui-linkbutton">上传图片</a>
                    </p>
                    <p>
                        <span>图片3：</span>
                        <span><input type="text" name="image3" id="image3" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('image3')" href="javascript:;" class="easyui-linkbutton">上传图片</a>
                    </p>
                    <p>
                        <span>图片4：</span>
                        <span><input type="text" name="image4" id="image4" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('image4')" href="javascript:;" class="easyui-linkbutton">上传图片</a>
                    </p>
                    <p>
                        <span>图片5：</span>
                        <span><input type="text" name="image5" id="image5" value="" maxlength="100" style="width: 300px;"/></span>
                        <a onclick="picDialogOpen('image5')" href="javascript:;" class="easyui-linkbutton">上传图片</a>
                    </p>
                    <p>
                        <span>供货价，分为单位：</span>
                        <span><input type="text" name="marketPrice" id="marketPrice" value="" maxlength="64" style="width: 100px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>销售价，分为单位：</span>
                        <span><input type="text" name="salesPrice" id="salesPrice" value="" maxlength="64" style="width: 100px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>收益，分为单位：</span>
                        <span><input type="text" name="income" id="income" value="" maxlength="64" style="width: 100px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>加入购物车限制：</span>
                        <span><input type="text" name="limit" id="limit" value="" maxlength="64" style="width: 100px;"/></span>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <span>类目id：</span>
                        <span><input type="text" name="categoryId" id="categoryId" value="" maxlength="32" style="width: 100px;"/></span>
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

<div id="picDia" class="easyui-dialog" icon="icon-save" align="center"
     style="padding: 5px; width: 300px; height: 150px;">
    <form id="picForm" method="post" enctype="multipart/form-data">
        <input id="picFile" type="file" name="picFile" />&nbsp;&nbsp;<br/><br/>
        <a href="javascript:;" onclick="picUpload()" class="easyui-linkbutton" iconCls='icon-reload'>提交图片</a>
    </form>
    <br><br>
</div>

<script type="application/javascript">

    function searchGoods() {
        $('#s_data').datagrid('load', {
            categoryId : $("#searchCategoryId").val(),
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
        $("input[name='goodsBaseId']").val(arr.rows[index].goodsBaseId);
        $("input[name='name']").val(arr.rows[index].name);
        $("input[name='categoryId']").val(arr.rows[index].categoryId);
        $("input[name='goodsId']").val(arr.rows[index].goodsId);
        $("input[name='image1']").val(arr.rows[index].image1);
        $("input[name='image2']").val(arr.rows[index].image2);
        $("input[name='image3']").val(arr.rows[index].image3);
        $("input[name='image4']").val(arr.rows[index].image4);
        $("input[name='image5']").val(arr.rows[index].image5);
        $("input[name='desc']").val(arr.rows[index].desc);
        $("input[name='income']").val(arr.rows[index].income);
        $("input[name='marketPrice']").val(arr.rows[index].marketPrice);
        $("input[name='limit']").val(arr.rows[index].limit);
        $("input[name='salesPrice']").val(arr.rows[index].salesPrice);
        $('#editDiv').dialog('open');
    }

    function editIsDisplay(id,isAvailable) {
        $.ajax({
            url: '${rc.contextPath}/goods/updateGoods',
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
                $.post("${rc.contextPath}/goods/deleteGoods",{id:id},function(data){
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
            title:'商品系统',
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
                        params.name = $("input[name='name']").val();
                        params.goodsBaseId = $("input[name='goodsBaseId']").val();
                        params.image1 = $("input[name='image1']").val();
                        params.image2 = $("input[name='image2']").val();
                        params.image3 = $("input[name='image3']").val();
                        params.image4 = $("input[name='image4']").val();
                        params.image5 = $("input[name='image5']").val();
                        params.desc = $("input[name='desc']").val();
                        params.income = $("input[name='income']").val();
                        params.limit = $("input[name='limit']").val();
                        params.marketPrice = $("input[name='marketPrice']").val();
                        params.salesPrice = $("input[name='salesPrice']").val();
                        params.categoryId = $("input[name='categoryId']").val();
                        if(params.name == '' || params.goodsBaseId == '' || params.image1 == '' || params.desc == '' ||
                           params.income == '' || params.limit == '' || params.marketPrice == '' || params.salesPrice == '' || params.categoryId == '') {
                            $.messager.alert("error","请填写完整信息","error");
                            return false;
                        } else {
                            $.messager.progress();
                            $.ajax({
                                url: '${rc.contextPath}/goods/saveOrUpdateGoods',
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
            url:'${rc.contextPath}/goods/jsonGoods',
            loadMsg:'正在装载数据...',
            singleSelect:true,
            fitColumns:true,
            remoteSort: true,
            pageSize:50,
            pageList:[50,60],
            columns:[[
                {field:'id',    title:'ID', width:20, align:'center'},
                {field:'name', title:'商品名称', width:50, align:'center'},
                {field:'goodsBaseId', title:'基础商品id', width:50, align:'center'},
                {field:'marketPrice', title:'供货价', width:50, align:'center'},
                {field:'salesPrice', title:'销售价', width:50, align:'center'},
                {field:'income', title:'收益', width:50, align:'center'},
                {field:'limit', title:'加入购物车限制', width:50, align:'center'},
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
                text:'新增商品',
                iconCls:'icon-add',
                handler:function(){
                    $("input[name='editId']").val("0");
                    $("#isAvailable1").prop("checked", "checked");
                    $("input[name='goodsBaseId']").val("");
                    $("input[name='image1']").val("");
                    $("input[name='image2']").val("");
                    $("input[name='image3']").val("");
                    $("input[name='image4']").val("");
                    $("input[name='image5']").val("");
                    $("input[name='name']").val("");
                    $("input[name='desc']").val("");
                    $("input[name='income']").val("");
                    $("input[name='limit']").val("");
                    $("input[name='marketPrice']").val("");
                    $("input[name='salesPrice']").val("");
                    $("input[name='categoryId']").val("");
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
