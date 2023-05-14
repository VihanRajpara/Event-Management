<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
	String session_email = (String) session.getAttribute("email");
	String session_type = (String) session.getAttribute("type");
%>
<meta charset="ISO-8859-1">
<title>HOD Panel</title>
</head>
<style>
body {
	font-family: 'Open Sans', sans-serif;
}
</style>
<body>
	<%
	if (session_email == null || ! session_type.equals("hod")) {
		String redirectURL = "/CollageEvent/login";
		response.sendRedirect(redirectURL);
	}
	%><jsp:include page="../components/userheader.jsp" />
	<div style="text-transform: uppercase;"><%=session_email %> hod DASHBOARD</div>
</body>
</html>