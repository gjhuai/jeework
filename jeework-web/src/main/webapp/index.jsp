<%
if (session.getAttribute("currentUser")==null) {
	response.sendRedirect(request.getContextPath()+"/faces/login.xhtml");
} else {
	response.sendRedirect(request.getContextPath()+"/faces/main.xhtml");
}
%>