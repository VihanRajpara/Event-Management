<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
String session_email = (String) session.getAttribute("email");
String session_type = (String) session.getAttribute("type");

if (session_email == null || !session_type.equals("admin")) {
	String redirectURL = "/CollageEvent/login";
	response.sendRedirect(redirectURL);
}
%>
<meta charset="ISO-8859-1">
<title>ADMIN Panel</title>
</head>
<style>
body {
	font-family: 'Open Sans', sans-serif;
}
</style>
<body>
	<jsp:include page="../components/userheader.jsp" />
	<div style="text-transform: uppercase;"><%=session_type%>
		DASHBOARD
	</div>
</body>
</html>