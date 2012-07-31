var LODOP;

// 打印预览
function printPreview(printId,printType,title,pageHeader,pageFooter) {
	if (printId != null) {
		coreHtmlSetting(printId,printType,title,pageHeader,pageFooter);
		LODOP.PREVIEW();
	}
}

// 直接打印
function printPage(printId,printType,title,pageHeader,pageFooter) {
	if (printId != null) {
		coreHtmlSetting(printId,printType,title,pageHeader,pageFooter);
		LODOP.PRINTA();
	}
}

// 打印html核心
function coreHtmlSetting(printId,printType,title,pageHeader,pageFooter) {
	LODOP = getLodop(document.getElementById('LODOP'), document
			.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT("cmdpms");
	LODOP.SET_PRINT_STYLE("FontSize", 14);
	LODOP.SET_PRINT_STYLE("Bold", 1);
	
	// 隐藏按钮
	hideAllButton();
	
	// 设置纸张大小
	setPageSize(printType);

	// 设置标题
	getPagetTitle(title,printType)
	
	// 设置页眉
	setPageHeader(pageHeader,printType)
	
	// 设置页脚
	setPageFooter(pageFooter,printType)

	// 设置打印内容
	setPrintHtml(printId,printType)

	// 显示按钮
	showAllButton();
}

// 打印图片核心
function coreImageSetting(imageUrl) {
	LODOP = getLodop(document.getElementById('LODOP'), document
			.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT("图片打印");
	LODOP.ADD_PRINT_IMAGE(30, 150, 260, 150, "<img border='0' src='" + imageUrl
			+ "'/>");

}

// 设置打印内容
function setPrintHtml(printId,printType){
	if(printType == 'billing'){
		// 1.上
		// 2.打印内的左边距
		// 3.右
		// 4.下
		LODOP.ADD_PRINT_HTM(82, 32, 32, 1, getPrintHtml(printId,printType));
	}else if(printType == 'weight'){
		LODOP.ADD_PRINT_HTM(120, 23, 722, 746, getPrintHtml(printId,printType));
	}else if(printType == 'purchaseDoc'){
		LODOP.ADD_PRINT_HTM(90, 37, 722, 746, getPrintHtml(printId,printType));
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
	}
}

// 设置标题
function getPagetTitle(title,printType){
	if(printType == 'billing'){
		// 1.上
		// 2.打印字体左边距
		// 3.右
		// 4.下
		LODOP.ADD_PRINT_TEXT(42, 460, 500, 1, title);
	}else if(printType == 'weight'){
		LODOP.ADD_PRINT_TEXT(70, 251, 500, 1, title);
	}else if(printType == 'purchaseDoc'){
		LODOP.ADD_PRINT_TEXT(70, 360, 500, 1, title);
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
	}
}

// 设置页眉
function setPageHeader(pageHeader,printType){
	if(printType == 'billing'){
		LODOP.ADD_PRINT_TEXT(12, 32, 100, 1,pageHeader);
	}else if(printType == 'weight'){
		LODOP.ADD_PRINT_TEXT(12, 32, 100, 1,pageHeader);
	}else if(printType == 'purchaseDoc'){
		LODOP.ADD_PRINT_TEXT(12, 32, 100, 1,pageHeader);
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
	}
}

// 设置页脚
function setPageFooter(pageFooter,printType){
	if(printType == 'billing'){
		LODOP.ADD_PRINT_TEXT(1400, 1000, 100, 12,pageFooter);
	}else if(printType == 'weight'){
		LODOP.ADD_PRINT_TEXT(1000, 650, 100, 1,pageFooter);
	}else if(printType == 'purchaseDoc'){
		LODOP.ADD_PRINT_TEXT(1070, 720, 100, 12,pageFooter);
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
	}
}

// 设置打印纸的样式
function setPageSize(printType){
	if(printType == 'billing'){
		// 设定纸张大小
		// 1.3表示纵向打印,且按内容的高度
		// 2.打印纸宽度
		// 3.页面底部空白
		LODOP.SET_PRINT_PAGESIZE(1, 2800, 47, '');
	}else if(printType == 'weight'){
		LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	}else if(printType == 'purchaseDoc'){
		LODOP.SET_PRINT_PAGESIZE(1, 0, 0, 'A4');
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
	}
}

// 获取打印样式
function getStyle(printType) {
	if(printType == 'billing'){
		return "<style type='text/css'>* {	margin: 0;	padding: 0;}table {	border-collapse: collapse;}table tr td {	border: 1px solid #000;	height: 25px;	line-height: 25px;	text-align: center;}</style>";
	}else if(printType == 'weight'){
		return "<style type='text/css'>* {	margin: 0;	padding: 0;}table {background-color:#FFF;width:100%;border-collapse:collapse;align:center;}.borderSet td{border:1px solid #000000;}</style>";
	}else if(printType == 'purchaseDoc'){
		return "<style type='text/css'>*  { margin: 0;	padding: 0;} table {	border-collapse: collapse;}table tr td {	border: 1px solid #000;	height: 25px;	line-height: 25px;	text-align: center;}body,td,th {	font-size: 14px;}</style>";
//		return "<style type='text/css'>* {	margin: 0;	padding: 0;}table {background-color:#FFF;width:100%;border-collapse:collapse;align:center;}.borderSet td{border:1px solid #000000;}</style>";
	}else if(printType == ''){
		
	}else if(printType == ''){
		
	}else{
		return '';
	}
	
}


//获取打印内容
function getPrintHtml(printId,printType) {
	var printHtml = getStyle(printType)
			+ "<body>"
			+ document.getElementById(printId).innerHTML
			+ "</body>";
	return printHtml;

}

// 隐藏页面所有按钮
function hideAllButton() {
	inputVar = document.getElementsByTagName("input");
	for ( var i = 0; i < inputVar.length; i++) {
		if(inputVar[i].type == "button" || inputVar[i].type == "submit"){
			inputVar[i].style.display = "none";
		}
	}
	
}

// 显示页面所有按钮
function showAllButton() {
	inputVar = document.getElementsByTagName("input");
	for ( var i = 0; i < inputVar.length; i++) {
		if(inputVar[i].type == "button" || inputVar[i].type == "submit"){
			inputVar[i].style.display = "";
		}
	}
}
