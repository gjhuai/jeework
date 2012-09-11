var defualtCheck2="";

function loc(context,resCode){
	$.ajax({
        type: "GET",
        url: context+"/menuloc?resCode="+resCode,
        async: false,
        success: function(data) {
        },
        error: function(e) {
            alert("系统错误，菜单资源无法定位。");
        }
    });
}

function ative_located_menu(){
	var codes = $("#resIdChain").val();
	var codeList = codes.replace(/[ \[\]]/g,'').split(',');

	//  定位菜单
	for (var i=0;i<codeList.length;i++){
		//alert("#"+codeList[i]+"#");
		//alert("#"+codeList[i].replace(' ','')+"#");
		$("#"+codeList[i]).addClass("active");
	};
	// 显示被定位的导航栏
	$("#"+codeList[0]).parent().children("div").show();
}

// 菜单栏效果
 $(document).ready(function () {
	 $(".top_menu_ul_li_a").click(function(){
		 $(".top_menu_ul_li_a").removeClass("active");
		 $(this).addClass("active");
			$(".nav_bar").hide();
			//$(this).children("div").show();
			$(this).parent().children("div").show();
		});
		
	 // 定位菜单
	 ative_located_menu();
	 
	 
	 /*----------------------------*/
	/* 此代码执行后的DOM完全加载 */
	
	/* 获取系统当前时间 */
	$('#down_right_timer').timer({ format: "yy\u5e74mm\u6708dd\u65e5 W HH:MM:ss" });
    
	/* 改变thedefault缓动效果 - 将影响到/ slideDown方法效果基本show： */
    $.easing.def = "easeOutBounce";
	// $.easing.def = "easeOutQuad";
            
	/* 单击事件处理程序绑定到链接： */       
	$('li.button a').click(function (e) {
		/* 下拉列表中找到对应于当前部分： */
        var dropDown = $(this).parent().next();
		
		/* 关闭所有其他降下来的部分，除了目前的 */
        $('.dropdown').not(dropDown).slideUp('slow');
        dropDown.slideToggle('slow');
		
		/* 防止违约事件（这将是导航到链接的地址浏览器） */
        e.preventDefault();
    });
	
	/* 页面加载时设置按钮提示框 */
	$("button").tooltip({
		left: 5,top:20
	});
	/* 页面加载时设置文本提示框 */
	$(":text").tooltip({
		left: 20,top:-10
	});
	$(":password").tooltip({
		left: 20,top:-10
	});
	
	$("select").tooltip({
		left: 20,top:-10
	});
	
	$("img").tooltip({
		left: 20,top:-10
	});
	
	$("button").each(function() {
		if($(this).text()=="ui-button"){
			$(this).css("width","20");
			$(this).css("height","20");
		}
	 });
	
	DisabledCSS();// 设置Disabled样式
	
	if($("#defualtCheck").val()!=null){
		defualtCheck2 = $("#defualtCheck").val();
		UserOrgCbx();
	}
	
	numeralInt($(".numberInt"),1,1,5);
});
 
 /* 页面局部刷新时设置按钮提示框 */
function showTooltip(){
	$("button").each(function() {
		if(this.tooltipText==null){
		// if(this.title!=null && this.title.length>0 &&
		// $(this).text()!="ui-button"){
		$(this).tooltip({
			left: 5,top:20
		});}
	 });
	$(":text").each(function() {
		if(this.tooltipText==null){
			$(this).tooltip({
			left: 20,top:-10
		});}
	 });
	$(":password").each(function() {
		if(this.tooltipText==null){
			$(this).tooltip({
			left: 20,top:-10
		});}
	 });
	$("select").each(function() {
		if(this.tooltipText==null){
			$(this).tooltip({
			left: 20,top:-10
		});}
	 });
	$("img").each(function() {
		if(this.tooltipText==null){
			$(this).tooltip({
			left: 20,top:-10
		});}
	 });
	
	$("button").each(function() {
		if($(this).text()=="ui-button"){
			$(this).css("width","20");
			$(this).css("height","20");
		}
	 });
	
	DisabledCSS();// 设置Disabled样式
	if($("#defualtCheck").val()!=null){
		$("#headerCheckbox").attr("checked","");
		UserOrgCbx();
	}
	numeralInt($(".numberInt"),1,1,5);
 }
        
// 鼠标停留效果
function LiStyleOver(obj) {
	obj.style.border = "1px solid #7cb9dd";
}

 // 鼠标离开效果
function LiStyleOut(obj) {
	obj.style.border = "1px solid #d5e8f6";
}
		
/*--获取网页传递的参数--*/
function request(paras){ 
	var url = location.href; 
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
	var paraObj = {};
 	for (i=0; j=paraString[i]; i++){ 
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
	var returnValue = paraObj[paras.toLowerCase()]; 
	if(typeof(returnValue)=="undefined"){ 
    	return ""; 
    }else{ 
        return returnValue; 
    } 
}

/*--菜单栏隐藏--*/
// var arrowpic1="/images/control_left.gif";//最好用服务器的绝对路径
// var arrowpic2="/images/control_right.gif";

// --------------------- 状态初始 ----------------------
// var MENU_SWITCH=1;

// ------------------ 点击时切换菜单栏状态 ---------------------
function enter_menu_ctrl()
{
	if(getCookie("MENU_SWITCH")==null){
		SetCookie("MENU_SWITCH","1");
	}
	if(getCookie("MENU_SWITCH")==0)
    {
		$("#menu_id").css("display","block");
		$("#control_id").css("left","172px");
		$("#main_id").css("left","180px");
        SetCookie("MENU_SWITCH","1");
        $("#menu_ctrl_img_left").css("display","block");
        $("#menu_ctrl_img_right").css("display","none");
		// document.getElementById("menu_ctrl_img_left").src=arrowpic1;
		// document.getElementById("menu_ctrl_img_right").src=arrowpic2;
		document.getElementById("menu_ctrl_tit").title="\u5173\u95ed\u83dc\u5355";
     }
     else
     {
		$("#menu_id").css("display","none");
		$("#control_id").css("left","0px");
		$("#main_id").css("left","8px");
       	SetCookie("MENU_SWITCH","0");
       	$("#menu_ctrl_img_left").css("display","none");
        $("#menu_ctrl_img_right").css("display","block");
       	// document.getElementById("menu_ctrl_img_left").src=arrowpic1;
		// document.getElementById("menu_ctrl_img_right").src=arrowpic2;
		document.getElementById("menu_ctrl_tit").title="\u6253\u5f00\u83dc\u5355";
     }
}

// ------------------ 默认时记忆中的菜单栏状态 ---------------------
function ready_menu_ctrl()
{
	if(getCookie("MENU_SWITCH")==null){
		SetCookie("MENU_SWITCH","1");
	}
	if(getCookie("MENU_SWITCH")==1)
    {
		$("#menu_id").css("display","block");
 		$("#control_id").css("left","172px");
 		$("#main_id").css("left","180px");
         $("#menu_ctrl_img_left").css("display","block");
         $("#menu_ctrl_img_right").css("display","none");
 		// document.getElementById("menu_ctrl_img_left").src=arrowpic1;
 		// document.getElementById("menu_ctrl_img_right").src=arrowpic2;
 		document.getElementById("menu_ctrl_tit").title="\u5173\u95ed\u83dc\u5355";
     }
     else
     {
    	$("#menu_id").css("display","none");
 		$("#control_id").css("left","0px");
 		$("#main_id").css("left","8px");
 		$("#menu_ctrl_img_left").css("display","none");
        $("#menu_ctrl_img_right").css("display","block");
        // document.getElementById("menu_ctrl_img_left").src=arrowpic1;
 		// document.getElementById("menu_ctrl_img_right").src=arrowpic2;
 		document.getElementById("menu_ctrl_tit").title="\u6253\u5f00\u83dc\u5355";
     }
}

/*
 * 关闭验证消息框 id 消息ID
 */
function closeMsg(id){
	$("#"+id).css("z-index","0");
	$("#"+id).css("position","absolute");
	// $("#"+id).css("float","right");
	$("#"+id).css("left","400px");
	$("#"+id).css("top","-10px");
	 setTimeout(function(){
		 $("#"+id).hide('slow');
		 $("#"+id).css("display","none");
		 }, 3000);
	// var intervalID =
	// window.setTimeout($("#"+id).css("display","none"),5000);
	// var intervalID2 =
	// window.setTimeout($("#"+id).css("display","block"),5000);
}

// 写cookies函数
function SetCookie(name, value)// 两个参数，一个是cookie的名子，一个是值
{
    delCookie(name);
    var Days = 30; // 此 cookie 将被保存 30 天
    var exp = new Date();    // new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)// 取cookies函数
{
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) return unescape(arr[2]); return "";

}
function delCookie(name)// 删除cookie
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

// ------------------------------------------------------------

// 源系统物料代码自动根据源代码赋值
function updateByMaterialCode(vars) {
	var sysCode = document.getElementById("material_addOriginalVendorNo");
	sysCode.value=vars.value;
}

// 关闭添加panel
function handleAddComplete(args) {
	if(args.validationFailed){
  } else {
  	addPanel.hide();
  }
}
// 关闭修改panel
function handleUpdateComplete(args) {
	if(args.validationFailed){
  } else {
  	updatePanel.hide();
  }
}
/*
 * 弹出层验证事件 panel 需要关闭的层 args
 */
function handleComplete(panel,args){
	 if(args.validationFailed){
	    } else {
	    	panel.hide();
	    }
}

/**
 * 弹出非Ajax数据提交等待层 用法：<h:form id="packingMaterialInputForm" prependId="false"
 * onsubmit="statusComplete();">
 */
function statusComplete(){
	 $("#submitDiv").css("display","");
}

function goMessage(){
	// $("#test1").val(parseInt($("#test1").val())+1);
	// $("#test2").val();

	/*
	 * $(".ui-message-error-icon").each(function (i) {
	 * if($('.ui-message-error-icon').get(i).parentNode.style.display!='none'){
	 * //$(".ui-message-error-icon").get(i).parentNode.remove() //var
	 * node=$(".ui-message-error-icon").get(i).parentNode;
	 * //$('.ui-message-error-icon').get(i).parentNode.style.display='none';
	 * //setTimeout("hideMessage()",5000);
	 * //$("#test2").val($('.ui-message-error-icon').get(i).parentNode.style.display);
	 * //$("#test1").val(parseInt($("#test1").val())+1); } });
	 */
	// $(".ui-messages").each(function (i) {
		if($('.ui-messages').get(0)!=null){
			setTimeout("$($('.ui-messages').get(0)).remove()",5000);
		}
	// });
	$(".ui-message-error").each(function (i) {
		if($('.ui-message-error').get(0)!=null){
			setTimeout("$($('.ui-message-error').get(0)).remove()",5000);
		}
	});
	$(".ui-message-info").each(function (i) {
		if($('.ui-message-info').get(0)!=null){
			setTimeout("$($('.ui-message-info').get(0)).remove()",5000);
		}
	});
	
}

function hideMessage(){
	$('.ui-message-error-icon').get(0).parentNode.style.display='none';
	
}


// 制保留2位小数，如：2，会在2后面补上00.即2.00
function toDecimal2(x){  
    var f = parseFloat(x);
    if(isNaN(f)){
        return "0.00";
    }
    var f = Math.round(x*100)/100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if(rs < 0){
        rs = s.length;  
        s += '.';  
    }
    while(s.length <= rs + 2){
        s += '0';
    }
    return s;
}

// 制保留3位小数，如：3，会在3后面补上000.即3.000
function toDecimal3(x){  
    var f = parseFloat(x);
    if(isNaN(f)){
        return "0.000";
    }
    var f = Math.round(x*1000)/1000;
    var s = f.toString();
    var rs = s.indexOf('.');
    if(rs < 0){
        rs = s.length;  
        s += '.';  
    }
    while(s.length <= rs + 3){
        s += '0';
    }
    return s;
}

// 制保留5位小数，如：5，会在3后面补上00000.即5.00000
function toDecimal5(x){
    var f = parseFloat(x);
    if(isNaN(f)){
        return "0.00000";
    }
    var f = Math.round(x*100000)/100000;
    var s = f.toString();
    var rs = s.indexOf('.');
    if(rs < 0){
        rs = s.length;  
        s += '.';  
    }
    while(s.length <= rs + 5){
        s += '0';
    }
    return s;
}

// 设置disabled样式
function DisabledCSS(){
	$("input[disabled='true']").css("color", "#666666");
	$("input[disabled='true']").css("background-color", "#FFFFFF");
	$("input[disabled='disabled']").css("color", "#666666");
	$("input[disabled='disabled']").css("background-color", "#FFFFFF");
	$("select[disabled='true']").css("color", "#666666");
	$("select[disabled='disabled']").css("color", "#666666");
	$("textarea[disabled='true']").css("color", "#666666");
	$("textarea[disabled='disabled']").css("color", "#666666");
	$("input[readonly='true']").css("color", "#666666");
	$("input[readonly='readonly']").css("color", "#666666");
	$("input[readonly='true']").css("background-color", "#FFFFFF");
	$("input[readonly='readonly']").css("background-color", "#FFFFFF");
	$("button[disabled='true']").css("color", "#666666");
	$("button[disabled='disabled']").css("color", "#666666");
	$("button[disabled='disabled']").css("opacity",0.3);
	$("button[disabled='disabled']").css("filter","alpha(opacity=30)");
	$("button[disabled='true']").css("opacity",0.3);
	$("button[disabled='true']").css("filter","alpha(opacity=30)");
}

function UserOrgCbx() {
	var ss =defualtCheck2;
	var arr=ss.split(",");
	// 默认选中事件
	for(var i=0; i < arr.length;i++){
	$("[name='userID']").each(function() {
			if($(this).val()==arr[i]){
				$(this).attr("checked","true");
			}
		 });
	}

	// 单选事件
	$("[name='userID']").click( function () {
		getCheckedId();
	   }); 
	
	}


// 全选，取消全选事件
function checkUser(){
   if($("#headerCheckbox").attr("checked")){
   		$("[name='userID']").attr("checked",'true');// 全选
   		
   }else{
	   $("[name='userID']").attr("checked",'');// 取消全选
	   
	}
   getCheckedId();

}

// 获取选中的cbx
function getCheckedId(){
	var arr="";
	var i=0;
	$("[name='userID']").each(function() {
		
			if($(this).attr("checked")){
					arr+=$(this).val()+",";
			}else{
				i=1;
				
			}
			if(i==0){
				$("#headerCheckbox").attr("checked","true");
			}else{
				$("#headerCheckbox").attr("checked","");
			}
		 });
		 if(arr!=null&&arr.length>0 ){
			 arr=arr.substring(0,arr.length-1);
		 }
	$("#defualtCheck").val(arr);
}

/**
 * 限制整数输入 @param obj：输入框对象 @param len: 长度限制 @param defVal: 默认值 @param maxVal: 最大值
 */
function numeralInt(obj,len,defVal,maxVal){
	var re = /^[1-9]+[0-9]*]*$/;// 不为0的整数
	// 按下键盘事件
	obj.bind("keypress",function(event) {
		if(obj.val().length < len){
			var k=window.event?event.keyCode:event.which;
			return (k >= 48 && k <= 57)||k==8||k==46||k==37||k==39||k==0;
		}
    });
	
	obj.bind("blur", function() {
        if (!re.test(obj.val()) || obj.val().length > len || obj.val() > maxVal) {
        	obj.val(defVal);
        }
        formatNumber(1);
        formatNumber(0);
    });
	
	obj.bind("paste", function() {
        var s = clipboardData.getData('text');
        if (!re.test(s) || obj.val().length > len || obj.val() > maxVal) {
        	obj.val(defVal);
        }
    });
	
    obj.bind("dragenter", function() {
        return false;
    });
    
    obj.bind("keyup", function() {
    	if (obj.val().length!=0 && (!re.test(obj.val()) || obj.val().length > len || obj.val() > maxVal)) {
        	obj.val(defVal);
        }
    });
}


/*********************组织下的指标数据************************/
function indNumber() {
	rangeLower();
	rangeLimit();
	
	$("#orgMaterialInd_containLower").change(function(){
		formatNumber(1);
	});
	$("#orgMaterialInd_containLimit").change(function(){
		formatNumber(1);
	});
}

function rangeLower(){

	$("#orgMaterialInd_rangeLower").keypress(function(event){
		var k=window.event?event.keyCode:event.which;  
		return (k >= 48 && k <= 57)||k==8||k==46||k==37||k==39||k==0;
	}).blur(function(){
		formatNumber(1);
	}).bind("paste", function() {
		formatNumber(1);
    }).bind("dragenter", function() {
        return false;
    });
}

function rangeLimit(){
	$("#orgMaterialInd_rangeLimit").keypress(function(event){
		var k=window.event?event.keyCode:event.which;  
		return (k >= 48 && k <= 57)||k==8||k==46||k==37||k==39||k==0;
	}).blur(function(){
		formatNumber(0);
	}).bind("paste", function() {
		formatNumber(0);
    }).bind("dragenter", function() {
        return false;
    });
}




/**
 * 取零
 * @param preObjVal 小数位数
 */
function preText(preObjVal) {
	var pre =".";
	for(var i=0;i<preObjVal;i++){
		pre += "0";
	}
	return pre;
}


/**
 * 指标数据逻辑处理 
 * @param preObj：小数限制对象 
 * @param conLowObj: 下限 
 * @param ranLowObj: 下限值 
 * @param conLimObj: 上限 
 * @param ranLimObj：上限值
 * @param thisObj：当前聚焦对象
 */
function formatNumber(lowOrLim){
	
	var msg = "";//错误提示信息
	
	var preObj = $("#orgMaterialInd_precision");
	var conLowObj = $("#orgMaterialInd_containLower");
	var ranLowObj = $("#orgMaterialInd_rangeLower");
	var conLimObj = $("#orgMaterialInd_containLimit");
	var ranLimObj = $("#orgMaterialInd_rangeLimit");
	var thisObj=ranLimObj;//默认当前对象为上限值
	
	var preObjVal = parseInt(preObj.val());
	var conLowObjVal = parseInt(conLowObj.val());
	var ranLowObjVal = parseFloat(ranLowObj.val());
	var conLimObjVal = parseInt(conLimObj.val());
	var ranLimObjVal = parseFloat(ranLimObj.val());
	
	if(lowOrLim==1){
		thisObj=ranLowObj;//设置当前对象为下限值
	}
	
	var the_Val = parseFloat(thisObj.val());
	
	if(!isNaN(the_Val)){
		if(!/\./.test(thisObj.val())){
			thisObj.val(the_Val+preText(preObjVal));
		}
		else{
			thisObj.val(the_Val.toFixed(preObjVal));
		}
	}else{
		msg="提示：非法数字输入！";
		thisObj.val("0"+preText(preObjVal));
	}
	
	if((conLowObjVal == 1 && conLimObjVal == 1 && ranLowObjVal == ranLimObjVal) || ranLowObjVal < ranLimObjVal ){
		//正确
		msg = "数据填写正确！";
		$("#orgMaterialIndUpDateSaveId").attr('disabled', false);
		$("#orgMaterialIndUpDateSaveId").css("color","#2779AA");
		$("#errorMsg").css("color","green");
	}else{
		//错误
		if(conLowObjVal == 1 && conLimObjVal == 1 ){
			msg = "提示：下限值必须小于等于上限值！";
		}else{
			msg = "提示：下限值必须小于上限值！";
		}
		$("#orgMaterialIndUpDateSaveId").attr('disabled', 'disabled');
		$("#orgMaterialIndUpDateSaveId").css("color","#CCCCCC");
		$("#errorMsg").css("color","Red");
	}
	$("#errorMsg").html(msg);

}

/*******************************指标主数据***********************************************************************/


function indMasterNumber() {
	rangeMasterLower();
	rangeMasterLimit();
	
	$("#indMaster_containLower").change(function(){
		formatMasterNumber(1);
	});
	$("#indMaster_containLimit").change(function(){
		formatMasterNumber(1);
	});
}

function rangeMasterLower(){

	$("#indMaster_rangeLower").keypress(function(event){
		var k=window.event?event.keyCode:event.which;  
		return (k >= 48 && k <= 57)||k==8||k==46||k==37||k==39||k==0;
	}).blur(function(){
		formatMasterNumber(1);
	}).bind("paste", function() {
		formatMasterNumber(1);
    }).bind("dragenter", function() {
        return false;
    });
}

function rangeMasterLimit(){
	$("#indMaster_rangeLimit").keypress(function(event){
		var k=window.event?event.keyCode:event.which;
		return (k >= 48 && k <= 57)||k==8||k==46||k==37||k==39||k==0;
	}).blur(function(){
		formatMasterNumber(0);
	}).bind("paste", function() {
		formatMasterNumber(0);
    }).bind("dragenter", function() {
        return false;
    });
}


/**
 * 指标数据逻辑处理 
 * @param preObj：小数限制对象 
 * @param conLowObj: 下限 
 * @param ranLowObj: 下限值 
 * @param conLimObj: 上限 
 * @param ranLimObj：上限值
 * @param thisObj：当前聚焦对象
 */
function formatMasterNumber(lowOrLim){
	
	var msg = "";//错误提示信息
	
	var conLowObj = $("#indMaster_containLower");
	var ranLowObj = $("#indMaster_rangeLower");
	var conLimObj = $("#indMaster_containLimit");
	var ranLimObj = $("#indMaster_rangeLimit");
	var thisObj=ranLimObj;//默认当前对象为上限值
	
	var preObjVal = parseInt(5);
	var conLowObjVal = parseInt(conLowObj.val());
	var ranLowObjVal = parseFloat(ranLowObj.val());
	var conLimObjVal = parseInt(conLimObj.val());
	var ranLimObjVal = parseFloat(ranLimObj.val());
	
	if(lowOrLim==1){
		thisObj=ranLowObj;//设置当前对象为下限值
	}
	
	var the_Val = parseFloat(thisObj.val());
	
	if(!isNaN(the_Val)){
		if(!/\./.test(thisObj.val())){
			thisObj.val(the_Val+preText(preObjVal));
		}
		else{
			thisObj.val(the_Val.toFixed(preObjVal));
		}
	}else{
		msg="提示：非法数字输入！";
		thisObj.val("0"+preText(preObjVal));
	}
	
	if((conLowObjVal == 1 && conLimObjVal == 1 && ranLowObjVal == ranLimObjVal) || ranLowObjVal < ranLimObjVal ){
		//正确
		msg = "数据填写正确！";
		$("#btn_indMaster_Save").attr('disabled', false);
		$("#btn_indMaster_Save").css("color","#2779AA");
		$("#errorMsg").css("color","green");
	}else{
		//错误
		if(conLowObjVal == 1 && conLimObjVal == 1 ){
			msg = "提示：下限值必须小于等于上限值！";
		}else{
			msg = "提示：下限值必须小于上限值！";
		}
		$("#btn_indMaster_Save").attr('disabled', 'disabled');
		$("#btn_indMaster_Save").css("color","#CCCCCC");
		$("#errorMsg").css("color","Red");
	}
	$("#errorMsg").html(msg);

}

/**
 * Float数据格式化
 * @param inputId:输入框ID
 * @param n：保留小数位数
 */
function toDecimal(inputId,n){
	var thisObj = $("#"+inputId);
	getParseFloat(thisObj,n);
	thisObj.keypress(function(event){
		var k=window.event?event.keyCode:event.which;
		return (k >= 48 && k <= 57)||k==8||k==46||k==45||k==37||k==39||k==0;
	}).blur(function(){
		getParseFloat(thisObj,n);
	}).bind("paste", function() {
		getParseFloat(thisObj,n);
    }).bind("dragenter", function() {
        return false;
    });
}

function getParseFloat(thisObj,n){
	var the_Val = parseFloat(thisObj.val());
	if(!isNaN(the_Val)){
		if(!/\./.test(thisObj.val())){
			thisObj.val(the_Val+preText(n));
		}
		else{
			thisObj.val(the_Val.toFixed(n));
		}
	}else{
		thisObj.val("0"+preText(n));
	}
}


function BrowseFolder(){  
	$("#vendor_fileUpload_id").trigger("click");
}
function BrowseFolderB(){  
	alert("B");
}

