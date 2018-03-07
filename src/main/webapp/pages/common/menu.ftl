<script src="${rc.contextPath}/js/lib/pinyin.js" xmlns="http://www.w3.org/1999/html"></script>
<div id="head_title">
    <h2>BOX管理</h2>
</div>
<div id="menuSearchDiv">
    <input type="text" id="menuSearchTextId" style="width: 130px;" placeholder="搜索" >
</div>
<ul id="menu">

</ul>
<div>
<form id="lgout" action="${rc.contextPath}/auth/lgout" type="post">
    <input type="hidden" value="${rc.contextPath}" id="rcContextPath">
	<input type="hidden" value="0"/>
    用户名：${menuUserName!''}</br>
	<a href="javascript:;"  style="text-decoration: none;" onclick="lgout()" >退出</a></br>
	<a href="javascript:;" style="text-decoration: none;" onclick="updatePWD()" >修改密码</a></br>
    <a href="${rc.contextPath}/common/index" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">返回首页</a>
    <span></span>
</form>
</div>
<!-- 右键菜单定义如下： -->
<input type="hidden" id="tempUrl" value="" />
<div id="mm" class="easyui-menu" style="width:120px;">
	<div onclick="oopen()">新标签页打开</div>
</div>
<div id="menupwdDialog" class="easyui-dialog">
    <form id="pwdForm" method="post">
		<p>原密码： <input type="password" id = "oldpassword" name="oldpwd"/></p>
		<p>新密码： <input type="password" id = "newpassword1" name="pwd"/></p>
		<p>确认新密码：<input type="password" id = "newpassword2" name="pwd1"/></p>
	</form>
</div>
<script>
function oopen(){
	var url = '${rc.contextPath}'+$('#tempUrl').val();
	window.open(url,'_blank');
}

$(function(){
    $('#menuSearchTextId').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            menuSearch();
        }
    });

    $('#menupwdDialog').dialog({
        title:'编辑密码',
        collapsible:true,
        closed:true,
        modal:true,
        buttons:[{
            text:'保存',
            iconCls:'icon-ok',
            handler:function(){
				var oldpwd = $("#oldpassword").val();
				var pwd = $("#newpassword1").val();
				var pwd1 = $("#newpassword2").val();
				if(oldpwd == '') {
                    $.messager.alert("提示","请输入原密码","error");
                    return false;
				}
                if(pwd == ''){
                    $.messager.alert("提示","请输入新密码","error");
                    return false;
                }else if(pwd1 == ''){
                    $.messager.alert("提示","请输入确认密码","error");
                    return false;
                }else if(pwd != pwd1){
                    $.messager.alert("提示","两次密码不一致","error");
                    return false;
                }
                $.ajax({
                    url:'${rc.contextPath}/common/updateOwnPWD',
                    type:'post',
                    dataType:'json',
                    data:{'pwd':pwd,"pwd1":pwd1, "oldpwd":oldpwd},
                    success:function(data){
                        $.messager.progress('close');
                        if(data.status == 1){
                            $.messager.alert('响应信息',"保存成功",'info');
                            $('#menupwdDialog').dialog('close');
                            setTimeout("lgout()",1500)
                        }else{
                            $.messager.alert('响应信息',data.msg,'error');
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
                $('#menupwdDialog').dialog('close');
            }
        }]
    });


	$('#menu').tree({
		url:'${rc.contextPath}/common/menu?nodes='+$("#nowNode").val(),
		lines:true,
		animate:true,
		width:400,
		height:300,
		onClick:function(node){
			var url = node.attributes.url;
			var menus = $('#menu').tree('getRoots');
			var menusStr = "";
			
			$.each(menus,function(i){
				var cm = menus[i];
				if(cm.state == 'open'){
					menusStr+=cm.id+"-";
				}
			});
			if(url != ""){
				if(url.indexOf("?") > 0 )
				{
					window.location.href = "${rc.contextPath}"+url+"&nodes="+menusStr;
				}else{
					window.location.href = "${rc.contextPath}"+url+"?nodes="+menusStr;
				}
			}else{
				if(node.state == 'closed'){
					$(this).tree('expand',node.target);
				}else{
					$(this).tree('collapse',node.target);
				}
			}
		},
		onContextMenu: function(e, node){
			e.preventDefault();
			var url = node.attributes.url;
			if(url != ""){//是链接
				// 查找节点
				$('#menu').tree('select', node.target);
				$('#tempUrl').val(node.attributes.url);
				// 显示快捷菜单
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
		}

	});
})
function lgout() {
    location.href='${rc.contextPath}/auth/tlogin';
//    $("#lgout").submit();
}

function updatePWD() {
    $("#newpassword1").val('');
    $("#newpassword2").val('');
    $("#menupwdDialog").dialog('open');
}


function menuSearch() {
    $('#menu').tree('collapseAll')
    var search_content = $.trim($('#menuSearchTextId').val()).replace(/\s+/g,""); //得到搜索的文件
    if (search_content == '') {
//        $('#menu').tree('expandAll'); //展开所有
    } else {
        var roots = $('#menu').tree('getRoots'); //得到tree顶级node
        searchTree(roots, search_content);
    }
}

var searchNodes;
function searchTree(parentNode, searchCon)
{
    searchNodes = [];
    var children;
    var pinyin = /[a-zA-Z]/.test(searchCon);
    for (var i = 0; i < parentNode.length; i++) {
		if(parentNode[i].text.indexOf(searchCon) >= 0 || (pinyin && window.Pinyin.t(parentNode[i].text).replace(/\s+/g,"").indexOf(searchCon)>=0) )
		{
            searchNodes.push(parentNode[i]);
            selectNode(parentNode[i]);
            expandParent(parentNode[i]);
		}
        children = $('#menu').tree('getChildren', parentNode[i].target);
        if (children) {
            for (var j = 0; j < children.length; j++) {
                if ($('#menu').tree('isLeaf', children[j].target)) {
                    if (children[j].text.indexOf(searchCon) >= 0 || (pinyin && window.Pinyin.t(children[j].text).replace(/\s+/g,"").indexOf(searchCon)>=0) ) {
						searchNodes.push(children[j]);
                        selectNode(children[j]);
                        expandParent(children[j]);
                    }
                }else {
                    searchSubMenuNode(children[j], searchCon, searchNodes);
                }
            }
        }
    }

    for (var i=0; i< searchNodes.length; i++)
    {
        searchNodes[i].target.className = "tree-node tree-node-selected";
    }
    if(searchNodes.length > 0) {
        $('#menu').tree('scrollTo', searchNodes[searchNodes.length-1].target);
    }
}

function searchSubMenuNode(menuNode, searchCon, searchNodes) {
    var pinyin = /[a-zA-Z]/.test(searchCon);
    if (menuNode.text.indexOf(searchCon) >= 0 || (pinyin && window.Pinyin.t(menuNode.text).replace(/\s+/g,"").indexOf(searchCon)>=0) ) {
        searchNodes.push(menuNode);
        selectNode(menuNode);
        expandParent(menuNode);
    }
    var children = $('#menu').tree('getChildren', menuNode.target);//获取顶级node下所有子节点
    if (children) { //如果有子节点
        for (var j = 0; j < children.length; j++) { //循环所有子节点
            if ($('#menu').tree('isLeaf', children[j].target)) { //判断子级是否为叶子节点,即不是父节点
                if (children[j].text.indexOf(searchCon) >= 0 || (pinyin && window.Pinyin.t(children[j].text).replace(/\s+/g,"").indexOf(searchCon)>=0) ) { //判断节点text是否包含搜索文本
                    searchNodes.push(children[j]);
                    selectNode(children[j]);
                    expandParent(children[j]);
                }
            } else {
                searchSubMenuNode(children[j], searchCon, searchNodes);
            }
        }
    }
}



function selectNode(node) {
    $('#menu').tree('select', node.target);
}

function expandParent(node) {
    var parent = node;
    var t = true;
    do {
        parent = $('#menu').tree('getParent', parent.target); //获取此节点父节点
        if (parent) { //如果存在
            t = true;
            $('#menu').tree('expand', parent.target);
        } else {
            t = false;
        }
    } while (t);
}
</script>