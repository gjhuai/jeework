//源系统物料代码自动根据源代码赋值
function updateByMaterialCode(vars) {
	var sysCode = document.getElementById("material_addOriginalVendorNo");
	sysCode.value = vars.value;
}

// 关闭添加panel
function handleAddComplete(args) {
	if (args.validationFailed) {
	} else {
		addPanel.hide();
	}
}
// 关闭修改panel
function handleUpdateComplete(args) {
	if (args.validationFailed) {
	} else {
		updatePanel.hide();
	}
}
/*
 * 弹出层验证事件 panel 需要关闭的层 args
 */
function handleComplete(panel, args) {
	if (args.validationFailed) {
	} else {
		panel.hide();
	}
}

/**
 * 弹出非Ajax数据提交等待层 用法：<h:form id="packingMaterialInputForm" prependId="false"
 * onsubmit="statusComplete();">
 */
function statusComplete() {
	jQuery("#submitDiv").css("display", "");
}

function alertMsg() {
	alert("dsfsdf");
	var msg= document.getElementById("resMsg")
	if (msg) {
		alert(msg);
	} 
}

function cleanText(var idName) {
	alert(idName);
}
