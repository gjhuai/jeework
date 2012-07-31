package com.wcs.base.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtils {

	/**
	 * 
	 * <p>
	 * Description: 错误提示信息！
	 * </p>
	 * 
	 * @param clientId
	 *            组建ID
	 * @param summary
	 *            消息
	 */
	public static void addErrorMessage(String clientId, String summary) {
		MessageUtils.addMessage(clientId, summary, FacesMessage.SEVERITY_ERROR);
	}
	
	/**
	 * 添加错误信息
	 * @param widgetId 空间ID
	 * @param key 资源文件中的KEY值
	 * @param values 可变的参数
	 */
	public static void addErrorMessage(String widgetId, String key,String ... values) {
		String mess = MessageUtils.getMessage(key, values);
		MessageUtils.addMessage(widgetId, mess, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * 
	 * <p>
	 * Description: 成功提示信息！
	 * </p>
	 * 
	 * @param clientId
	 *            组建ID
	 * @param summary
	 *            消息
	 */
	public static void addSuccessMessage(String clientId, String summary) {
		MessageUtils.addMessage(clientId, summary, FacesMessage.SEVERITY_INFO);
	}
	
	/**
	 * 添加成功信息
	 * @param widgetId 空间ID
	 * @param key 资源文件中的KEY值
	 * @param values 可变的参数
	 */
	public static void addSuccessMessage(String widgetId, String key,String ... values) {
		String mess = MessageUtils.getMessage(key, values);
		MessageUtils.addMessage(widgetId, mess, FacesMessage.SEVERITY_INFO);
	}

	/**
	 * 
	 * <p>
	 * Description: 警告提示信息！
	 * </p>
	 * 
	 * @param clientId
	 *            组建ID
	 * @param summary
	 *            消息
	 */
	public static void addWarnMessage(String clientId, String summary) {
		MessageUtils.addMessage(clientId, summary, FacesMessage.SEVERITY_WARN);
	}
	
	/**
	 * 添加警告信息
	 * @param widgetId 空间ID
	 * @param key 资源文件中的KEY值
	 * @param values 可变的参数
	 */
	public static void addWarnMessage(String widgetId, String key,String ... values) {
		String mess = MessageUtils.getMessage(key, values);
		MessageUtils.addMessage(widgetId, mess, FacesMessage.SEVERITY_WARN);
	}

	public static void addMessage(String clientId, String summary) {
		MessageUtils.addMessage(clientId, summary, FacesMessage.SEVERITY_WARN);
	}

	public static void addMessage(String clientId, String summary,
			FacesMessage.Severity servertity) {
		MessageUtils.addMessage(clientId, summary, null, servertity);
	}

	/**
	 * 当逻辑验证不通过时，通过addMessage方法，可以提交到前台，其中clientId用于定位验证组件的位置，detail用于显示验证信息，
	 * 默认的验证级别为warn级别
	 * 验证级别共有4种，分别为SEVERITY_INFO。SEVERITY_FATAL。SEVERITY_ERROR。SEVERITY_WARN
	 * 
	 * 例如，当需要收购组织下必须有产地信息时 if(orgPlaceList.size()==0) {
	 * this.addMessage("orgList",
	 * "该组织下的产地为空，请先添加产地",FacesMessage.SEVERITY_ERROR); }
	 * 
	 * <p>
	 * Description:验证信息
	 * </p>
	 * 
	 * @param clientId
	 *            验证组件id号
	 * @param summary
	 *            提示信息
	 * @param detail
	 *            详细提示信息
	 * @param servertity
	 *            验证级别
	 */
	public static void addMessage(String clientId, String summary,
			String detail, FacesMessage.Severity servertity) {

		FacesContext context = FacesContext.getCurrentInstance();

		FacesMessage message = new FacesMessage();
		message.setSeverity(servertity);
		message.setDetail(detail);
		message.setSummary(summary);
		if (servertity == FacesMessage.SEVERITY_ERROR) {
			context.validationFailed();
		}
		context.addMessage(clientId, message);
	}

	/**
	 * 获取国际化资源
	 * @param key 
	 * @param values 可替换"{1}{2}"等
	 * @return
	 */
	public static String getMessage(String key,String ... values) {
		FacesContext context = FacesContext.getCurrentInstance();
//		String bundleName = context.getApplication().getMessageBundle();
		ResourceBundle rb = ResourceBundle.getBundle("messages",
				context.getViewRoot().getLocale());
		MessageFormat mf = new MessageFormat(rb.getString(key));
		
		String mess = mf.format(values);
		
		return mess;
	}

    /**
     *
     * @param paramName
     * @param methodName
     * @return
     *
     */
    public static String buildAssertMessage(String paramName, String methodName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Parameter '");
        sb.append(paramName);
        sb.append("' canot null, When do ");
        sb.append(methodName);
        sb.append(".");
        return sb.toString();
    }


    /**
     *
     * @param methodName
     * @param t
     * @return
     *
     */
    public static String buildExceptionMessage(String methodName, Throwable t) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hit Exception: Can not ");
        sb.append(methodName);
        sb.append(" ");
        sb.append(t.getMessage());
        sb.append(".");


        return sb.toString();
    }
}
