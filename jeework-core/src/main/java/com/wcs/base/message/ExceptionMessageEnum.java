package com.wcs.base.message;


import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

/**
 * Created by IntelliJ IDEA.
 * User: Chris
 * Date: 11-8-11
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
@BaseName("messages.exception")
@LocaleData({
   @Locale("zh_CN")
})
public enum ExceptionMessageEnum {
	// WELD-001408
    @MessageId("E0001") GOOD_MAN_ZHANGSAN,
    @MessageId("E0002") NOT_FOUND_PAGE_404,
    
    @MessageId("RPTFILE_01") RPTFILE_MSTRID, //报表主数据Id不存在
    @MessageId("RPTFILE_02") RPTFILE_SAVE //上传保存文件异常
}
