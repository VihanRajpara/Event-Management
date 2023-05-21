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
<title>ADMIN Panel</title>
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
</style>
<body>
	<jsp:include page="../components/userheader.jsp" />
	<br>
	<br>
	<center>
		<h3>
			<strong>Event Registration Detail</strong>
		</h3>
	</center>
	<br>
	<div class="container">
		<table class="center table table-striped" id="student-list-table"
			style="text-align: center;">
			<thead class="thead-dark">
				<th>No.</th>
				<th>Id</th>
				<th>Event Name</th>
				<th>Student Name</th>
				<th>Student Email</th>
				<th>Student Contact</th>
				<th>Fee</th>
				<th>Action</th>
			</thead>

			<jstlc:forEach var="e" items="${events}" varStatus="status">

				<tr>
					<td>${status.index + 1}</td>
					<td>${e.id}</td>
					<td>${e.event.name}</td>
					<td>${e.student.name}</td>
					<td>${e.student.email}</td>
					<td>${e.student.contact}</td>
					<td>${e.fee}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/eventfeestatus?id=${e.id}&eid=${eid}"
						onclick="javascript: return confirm('Are you sure you want to Change Fee Status?');">
							Paid</a></td>
				</tr>
			</jstlc:forEach>
		</table>
	</div>
</body>
</html>