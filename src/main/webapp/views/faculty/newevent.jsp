<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<style>
body {
	font-family: 'Open Sans', sans-serif;
}

.buttons {
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

.section {
	margin: 0px;
	width: 100%;
	text-align: right;
	background: #dcdcdc;
	padding: 12px;
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
<body>
	<%
	if (session_verify.equals("Under Approval")) {
	%><section class="section">
		<a class="buttons"
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
	
	
	<br>
	<div id="login">
	<form action="addevent" method="post">
			<h1>
				<strong>Welcome .</strong> Add EVENT.
			</h1>
			<fieldset>
			<p>
					<input type="text" name="name" id="name" placeholder="Event Name">
				</p>
				<p>
					<input type="text" name="info" id="info" placeholder="Event Info">
				</p>
				<p>
					<input type="text" name="contact" id="contact" placeholder="Contact">
				</p><p>
					<input type="text" name="fee" id="fee"
						placeholder="Fee">
				</p>
				<p>
					<input type="submit" onclick="javascript: return confirm('Are you sure you want to Add this User?');" value="Add">
				</p>
			</fieldset>
		</form>
		</div>
		<br>
	<br>
	<center>
		<h3>
			<strong>Approve Event</strong>
		</h3>
	</center>
	<br>
	<div class="container">
		<table class="center table table-striped" id="student-list-table"
			style="text-align: center;">
			<thead class="thead-dark">
				<th>Id</th>
				<th>Name</th>
				<th>Info</th>
				<th>Fee</th>
				<th>Dep</th>
				<th>Contact</th>
				<th>Host</th>
				<th>Permission</th>
			</thead>

			<jstlc:forEach var="event" items="${newevent}">

				<tr>
					<td>${event.id}</td>
					<td>${event.name}</td>
					<td>${event.info}</td>
					<td>${event.fee}</td>
					<td>${event.type}</td>
					<td>${event.contact}</td>
					<td>${event.email} | ${event.mtype}</td>
					<td>${event.permission}</td>
				</tr>
			</jstlc:forEach>
		</table>
	</div>
		
	<br>
	<br>
	<center>
		<h3>
			<strong>ALL EVENT</strong>
		</h3>
	</center>
	<br>
	<div class="container">
		<table class="center table table-striped" id="student-list-table"
			style="text-align: center;">
			<thead class="thead-dark">
				<th>Id</th>
				<th>Name</th>
				<th>Info</th>
				<th>Fee</th>
				<th>Dep</th>
				<th>Contact</th>
				<th>Host</th>
				<th>Status</th>
				<th>Permission</th>
				<th>Action</th>
				<th>Detail</th>
			</thead>

			<jstlc:forEach var="event" items="${events}">

				<tr>
					<td>${event.id}</td>
					<td>${event.name}</td>
					<td>${event.info}</td>
					<td>${event.fee}</td>
					<td>${event.type}</td>
					<td>${event.contact}</td>
					<td>${event.email} | ${event.mtype}</td>
					<td>${event.status}</td>
					<td>${event.permission}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/eventstatus?id=${event.id}"
						onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">
							On & Off</a></td>
							<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/eventview?id=${event.id}"
						onclick="javascript: return confirm('Are you sure you want to View Registration Detail?');">
							View</a></td>
				</tr>
			</jstlc:forEach>
			<jstlc:forEach var="event" items="${eventsss}">

				<tr>
					<td>${event.id}</td>
					<td>${event.name}</td>
					<td>${event.info}</td>
					<td>${event.fee}</td>
					<td>${event.type}</td>
					<td>${event.contact}</td>
					<td>${event.email} | ${event.mtype}</td>
					<td>${event.status}</td>
					<td>${event.permission}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/eventstatus?id=${event.id}"
						onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">
							On & Off</a></td>
							<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/eventview?id=${event.id}"
						onclick="javascript: return confirm('Are you sure you want to View Registration Detail?');">
							View</a></td>
				</tr>
			</jstlc:forEach>
		</table>
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