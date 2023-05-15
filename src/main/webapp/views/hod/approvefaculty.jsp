<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
	<br><br><center><strong><h2>Under Approve Faculty</h2></strong></center><br>
	<div class="container">
	<table class="center table table-striped" style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>STATUS</th>
				<th>ACTION</th>
			</thead>
			<jstlc:forEach var="faculty" items="${faculty_list}">
				<tr style="text-transform: uppercase;">
					<td>${faculty.id}</td>
					<td>${faculty.name}</td>
					<td style="text-transform: lowercase;">${faculty.email}</td>
					<td>${faculty.dep}</td>
					<td>${faculty.verify}</td>
					<td>
						<a style="text-transform: capitalize;" href="${pageContext.request.contextPath}/hod/facultyaccess?id=${faculty.id}" 
							onclick="javascript: return confirm('Are you sure you want to Give Access to this Faculty?');">Give Access</a>
					</td>
				</tr>
			</jstlc:forEach>
		</table></div>
		<br><br><br><center><strong><h2>Approve Faculty</h2></strong></center><br>
		<div class="container">
	<table class="table table-striped" style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>STATUS</th>
				<th>ACTION</th>
			</thead>
			<jstlc:forEach var="faculty" items="${afaculty_list}">
				<tr style="text-transform: uppercase;">
					<td>${faculty.id}</td>
					<td>${faculty.name}</td>
					<td style="text-transform: lowercase;">${faculty.email}</td>
					<td>${faculty.dep}</td>
					<td>${faculty.verify}</td>
					<td>
						<a style="text-transform: capitalize;" href="${pageContext.request.contextPath}/hod/facultyaccess?id=${faculty.id}" 
							onclick="javascript: return confirm('Are you sure you want to Remove Access?');">Remove Access</a>
					</td>
				</tr>
			</jstlc:forEach>
		</table></div>
</body>
</html>