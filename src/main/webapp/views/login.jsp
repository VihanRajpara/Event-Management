<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="UTF-8">
<%
String session_email = (String) session.getAttribute("email");
String session_type = (String) session.getAttribute("type");
%>
<%
if (session_email != null) {
	if (session_type.equals("admin")) {
		String redirectURL = "/CollageEvent/admin/dashboard";
		response.sendRedirect(redirectURL);
	} else if (session_type.equals("hod")) {
		String redirectURL = "/CollageEvent/hod/dashboard";
		response.sendRedirect(redirectURL);
	} else if (session_type.equals("faculty")) {
		String redirectURL = "/CollageEvent/faculty/dashboard";
		response.sendRedirect(redirectURL);
	} else if (session_type.equals("student")) {
		String redirectURL = "/CollageEvent/student/dashboard";
		response.sendRedirect(redirectURL);
	}
}
%>
<title>Login</title>
<style>
@import url(https://meyerweb.com/eric/tools/css/reset/reset.css);

/* ---------- FONTAWESOME ---------- */
[class*="fontawesome-"]:before {
	font-family: 'FontAwesome', sans-serif;
}

/* ---------- GENERAL ---------- */
body {
	color: #5a5656;
	font-family: 'Open Sans', sans-serif;
	line-height: 1.5;
	margin: 0;
	min-height: 100vh;
	place-items: center;
}

a {
	text-decoration: none;
}

h1 {
	font-size: 1em;
}

h1, p {
	margin-bottom: 10px;
}

strong {
	font-weight: bold;
}

.uppercase {
	text-transform: uppercase;
}

/* ---------- LOGIN ---------- */
#login {
	margin: 50px auto;
	width: 300px;
}

form fieldset input[type="text"], input[type="password"], input[type="email"]
	{
	appearance: none;
	background: #e5e5e5;
	border: none;
	border-radius: 3px;
	color: #5a5656;
	font-family: inherit;
	font-size: 14px;
	height: 50px;
	outline: none;
	padding: 0px 10px;
	width: 280px;
}

form fieldset input[type="submit"] {
	appearance: none;
	background-color: #4169E1;
	border: none;
	border-radius: 3px;
	color: #f4f4f4;
	cursor: pointer;
	font-family: inherit;
	height: 50px;
	text-transform: uppercase;
	width: 300px;
}

form fieldset input[type="submit"]:hover {
	background-color: #0047AB;
	color: #f4f4f4;
}
</style>
<body>

	<jsp:include page="components/userheader.jsp" />
	<div id="login">


		<form action="usercheck" method="post">
			<h1>
				<strong>Welcome.</strong> Please login as <select name="type"
					id="cars">
					<option value="admin">ADMIN</option>
					<option value="hod">HOD</option>
					<option value="faculty">FACULTY</option>
					<option value="student">STUDENT</option>
				</select> .
			</h1>
			<fieldset>
				<p>
					<input type="email" name="email" id="email" placeholder="Email">
				</p>

				<p>
					<input type="password" name="password" id="password"
						placeholder="Password">
				</p>
				<p>
					<input type="submit" value="Login">
				</p>
			</fieldset>
		</form>
	</div>
</body>
</html>