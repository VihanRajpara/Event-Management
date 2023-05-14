<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
String session_email = (String) session.getAttribute("email");
String session_type = (String) session.getAttribute("type");
String session_verify = (String) session.getAttribute("verify");
%>
<meta charset="ISO-8859-1">
<title><%=session_type%> Panel</title>
</head>
<style>
body {
	font-family: 'Open Sans', sans-serif;
}

.button {
	background-color: #4169E1;
	border: none;
	border-radius: 6px;
	color: white;
	padding: 12px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 10px;
	cursor: pointer;
}

section {
margin:0px;
	width: 100%;
	text-align: right;
	background: #dcdcdc;
	padding: 12px;
}
</style>
<body>
	<%
	if (session_verify.equals("Under Approval")) {
	%><section>
		<a class="button"
			href='${pageContext.request.contextPath}/faculty/logout'>LOGOUT</a>
	</section>
	<br>
	<br>
	<center>
		<strong><h1><%=session_verify%></h1></strong>
	</center>
	<%
	} else {
	%>
	<jsp:include page="../components/userheader.jsp" />
	<div style="text-transform: uppercase;"><%=session_type%>
		DASHBOARD
	</div>

	<%
	}
	%>
	<%
	if (session_email == null) {
		String redirectURL = "/CollageEvent/login";
		response.sendRedirect(redirectURL);
	}
	%>
</body>
</html>