//0-否 ,  1-是
function getYesOrNo(status) {
    if (!status) {
        return '否';
    } else if (status == 1) {
        return '是';
    }
}

//根据状态获取上架或下架字符串
function getOnlineStatusString(status) {
    if (!status) {
        return '下架';
    } else if (status == 1) {
        return '上架';
    }
}

//获取处理状态
function getHandleStatusString(status) {
    if (!status) {
        return '未处理';
    } else if (status == 1) {
        return '已经处理';
    }
}

//获取订单冻结状态
function getIsFreezeString(status) {
    if (!status) {
        return '未冻结';
    } else if (status == 1) {
        return '已经处理';
    } else if (status == 2) {
        return '已解冻';
    }
}

//获取处理状态
function getConfirmStatus(status) {
    if (!status) {
        return '确认失败';
    } else if (status == 1) {
        return '确认成功';
    }
}

//获取推送状态
function getPushStatus(status) {
    if (!status) {
        return '推送失败';
    } else if (status == 1) {
        return '推送成功';
    }
}

//获取考拉大状态
function getGorderStatus(status) {
    if (!status) {
        return '未支付';
    } else if (status == 1) {
        return '已支付';
    } else if (status == 5) {
        return '未支付已取消订单';
    }
}


//获取考拉订单状态
function getKlOrderStatus(status) {
    if (!status) {
        return '订单同步失败';
    } else if (status == 1) {
        return '订单同步成功（等待支付）';
    } else if (status == 2) {
        return '订单支付成功（等待发货）';
    } else if (status == 3) {
        return '订单支付失败';
    } else if (status == 4) {
        return '订单已发货';
    } else if (status == 5) {
        return '交易成功';
    } else if (status == 6) {
        return '订单交易失败（用户支付后不能发货）';
    } else if (status == 7) {
        return '订单关闭';
    } else if (status == 8) {
        return '退款成功';
    } else if (status == 9) {
        return '退款失败';
    }
}

//创建一个dataGrid field
function createDataGridFiled(prefix, value) {
    var result = "";
    if (prefix) {
        result = prefix;
    }
    if (value) {
        result += value;
    }
    result += "<br/>";
    return result;
}

//创建格格家订单编号超链接 , url的值为 ${rc.contextPath}
function createGgjOrderNumberHref(url,prefix, orderId, orderNumber) {
    var ggjOrderNumber = createDataGridFiled(prefix, orderNumber);
    if (orderNumber) {
        if (orderNumber.indexOf("GGJHB") == -1 && orderId) {
            ggjOrderNumber = "<a href='" + url + "/order/detail/" + orderId + "' target='_blank' >" + createDataGridFiled(prefix, orderNumber) + "</a>";
        }
    }
    return ggjOrderNumber;
}

//格式化时间戳
function formatTimeToString(timeLong) {
    if (timeLong) {
        var time = new Date(timeLong);
        var y = time.getFullYear();//年
        var m = time.getMonth() + 1;//月
        m = preAppendTime(m);
        var d = time.getDate();//日
        d = preAppendTime(d);
        var h = time.getHours();//时
        h = preAppendTime(h);
        var mm = time.getMinutes();//分
        mm = preAppendTime(mm);
        var s = time.getSeconds();//秒
        s = preAppendTime(s);
        return y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + s;
    }
}

//在时间字符串前面补0
function preAppendTime(str) {
    str += "";
    if(str && str.length == 1) {
        return "0" + str;
    }
    return str;
}

//获取格格家订单状态字符串
function getGgjOrderStatusStr(status) {
    if(status == 1) {
        return '未付款';
    }
    if(status == 2) {
        return '待发货';
    }
    if(status == 3) {
        return '已发货';
    }
    if(status == 4) {
        return '交易成功';
    }
    if(status == 5) {
        return '用户取消（待退款团购）';
    }
    if(status == 6) {
        return '超时取消（已退款团购）';
    }
    if(status == 7) {
        return '团购进行中(团购)';
    }
    return '';
}


