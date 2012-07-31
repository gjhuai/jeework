var LODOP;

// 打印预览
function printPreview(print_id, title) {
	// document.getElementById("test").style.display = "none";
	
	if (print_id != null) {
		coreHtmlSetting(print_id, title);
		LODOP.PREVIEW();
	}
}

// 直接打印
function printPage(print_id, title) {
	if (print_id != null) {
		coreHtmlSetting(print_id, title);
		LODOP.PRINTA();
	}
}

// 打印html核心
function coreHtmlSetting(print_id, title) {
	hideAllButton();
	LODOP = getLodop(document.getElementById('LODOP'), document
			.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT("cmdpms");
	LODOP.SET_PRINT_STYLE("FontSize", 14);
	LODOP.SET_PRINT_STYLE("Bold", 1);

	// 设定纸张大小
	// 1.3表示纵向打印,且按内容的高度
	// 2.打印纸宽度
	// 3.页面底部空白
	LODOP.SET_PRINT_PAGESIZE(3, 2130, 47, '');

	// 页眉
	LODOP.ADD_PRINT_TEXT(12, 32, 100, 1, "丰益技术");

	// 增加纯文本项
	// 1.上
	// 2.打印字体左边距
	// 3.左
	// 4.右
	LODOP.ADD_PRINT_TEXT(42, 360, 500, 1, title);

	// 增加超文本项
	// 1.上
	// 2.打印内的左边距
	// 3.右
	// 4.下
	LODOP.ADD_PRINT_HTM(82, 32, 32, 1, getPrintHtml(print_id));

	// 页脚
	LODOP.ADD_PRINT_TEXT(250, 690, 100, 12, "丰益技术");
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

function getPrintHtml(print_id) {
	var printHtml = getBillingStyle()
			+ "<body>"
			+ document.getElementById(print_id).innerHTML + "</div>"
			+ "</body>";
	// alert(printHtml);
	return printHtml;

}

function getBillingStyle() {
	return "<style type='text/css'>* {	margin: 0;	padding: 0;}table {	border-collapse: collapse;}table tr td {	border: 1px solid #000;	height: 25px;	line-height: 25px;	text-align: center;}</style>";
}


function hideAllButton() {
	inputVar = document.getElementsByTagName("input");
	for ( var i = 0; i < inputVar.length; i++) {
		if(inputVar[i].type == "submit"){
			inputVar[i].style.display = "none";
		}
	}
	
}
function showAllButton() {
	inputVar = document.getElementsByTagName("input");
	for ( var i = 0; i < inputVar.length; i++) {
		if(inputVar[i].type == "button" || inputVar[i].type = "submit"){
			inputVar[i].style.display = "";
		}
	}
}
