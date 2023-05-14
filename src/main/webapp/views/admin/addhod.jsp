<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Admin Panel</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script>
    // Remove error message after 5 seconds
    setTimeout(function() {
        var errorMessageElement = document.getElementById("errorMessage");
        if (errorMessageElement != null) {
            errorMessageElement.parentNode.removeChild(errorMessageElement);
        }
    }, 2000); // Change the timeout value as needed (in milliseconds)
</script>
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

form fieldset input[type="text"], input[type="password"], input[type="email"],select
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
	width: 300px;
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
</head>
<body>
	<jsp:include page="../components/userheader.jsp" />
	<div id="login">
	<form action="addhodaction" method="post" modelAttribute="hod">
			<h1>
				<strong>Welcome.</strong> Add HOD.
			</h1>
			<fieldset>
			<p>
					<input type="text" name="name" id="name" placeholder="Your Name">
				</p>
				<p>
					<input type="email" name="email" id="email" placeholder="Email">
				</p>
				<p>
					<input type="text" name="contact" id="contact" placeholder="Contact">
				</p>
				<p>
					<select name="dep" id="dep">
					<option value="" selected>Select Department</option>
					<jstlc:forEach var="dep" items="${dep_list}">
						<option value="${dep.name}">${dep.name} - ${dep.fullname}</option>
						</jstlc:forEach>
					</select>
				</p>
				<p>
					<input type="password" name="pass" id="password"
						placeholder="Password">
				</p>
				<p>
					<input type="submit" onclick="javascript: return confirm('Are you sure you want to Add this User?');" value="Add">
				</p>
			</fieldset>
		</form>
		</div>
		<div class="container">
		<table class="table table-striped" style="text-align: center;">
			<thead class="thead-dark">
				<th>id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Dep</th>
				<th>Action</th>
			</thead>
			<jstlc:forEach var="hod" items="${hod_list}">
				<tr>
					<td>${hod.id}</td>
					<td>${hod.name}</td>
					<td>${hod.email}</td>
					<td>${hod.dep}</td>
					<td>
						<a href="${pageContext.request.contextPath}/admin/addhod/edit?id=${hod.id}">Edit</a> | 
						<a href="${pageContext.request.contextPath}/admin/addhod/delete?id=${hod.id}" 
							onclick="javascript: return confirm('Are you sure you want to delete this record?');">Delete</a>
					</td>
				</tr>
			</jstlc:forEach>
		</table>
	</div>
		
</body>
</html>