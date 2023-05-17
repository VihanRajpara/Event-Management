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
body {
	font-family: 'Open Sans', sans-serif;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
	$('#semDropdown, #searchInput').change(function() {
		var selectedSem = $('#semDropdown').val();
		var searchQuery = $('#searchInput').val();
		updateStudentList(searchQuery, selectedSem);
	});
});


	function updateStudentList(searchQuery,selectedSem) {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/newallstudent",
			type : "GET",
			data : {
				sem : selectedSem,
				search : searchQuery
			},
			success : function(response) {
				$('#student-list-table').html(response);
				console.log(response);
			},
			error : function(xhr, status, error) {
				console.error(xhr.responseText);
			}
		});
	}
</script>
</head>
<body>
	<jsp:include page="../components/userheader.jsp" />
	<br>
	<center>
		<h2>
			<strong>Approval Student</strong>
		</h2>
	</center>
	<br>
	<br>
	<div class="container">
		<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="center table table-striped" 
			style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>SEM</th>
				<th>TYPE</th>
				<th>FACULTY</th>
				<th>HOD</th>
				<th>STATUS</th>
				<th>ACTION</th>
			</thead>
			<jstlc:forEach var="student" items="${student_new}">
				<tr style="text-transform: uppercase;">
					<td>${student.id}</td>
					<td>${student.name}</td>
					<td style="text-transform: lowercase;">${student.email}</td>
					<td>${student.dep}</td>
					<td>${student.sem}</td>
					<td>${student.type}</td>
					<td>${student.faculty.name}</td>
					<td>${student.hod.name}</td>
					<td>${student.verify}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentaccess?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Remove Access to this Student?');">Give
							Access</a> | <a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentcr?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">
							Remove CR</a></td>
				</tr>

			</jstlc:forEach>
		</table>
	</div>
	<br>
	<br>
	<center>
		<h2>
			<strong>CR Student</strong>
		</h2>
	</center>
	<br>
	<br>
	<div class="container">
		<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="center table table-striped" 
			style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>SEM</th>
				<th>TYPE</th>
				<th>FACULTY</th>
				<th>HOD</th>
				<th>STATUS</th>
				<th>ACTION</th>
			</thead>
			<jstlc:forEach var="student" items="${student_cr}">
				<tr style="text-transform: uppercase;">
					<td>${student.id}</td>
					<td>${student.name}</td>
					<td style="text-transform: lowercase;">${student.email}</td>
					<td>${student.dep}</td>
					<td>${student.sem}</td>
					<td>${student.type}</td>
					<td>${student.faculty.name}</td>
					<td>${student.hod.name}</td>
					<td>${student.verify}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentaccess?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Remove Access to this Student?');">Remove
							Access</a> | <a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentcr?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">
							Remove CR</a></td>
				</tr>

			</jstlc:forEach>
		</table>
	</div>
	<br>
	<br>
	<center>
		<h2>
			<strong>All Student</strong>
		</h2>
	</center>
	<br>Select Sem <select
				id="semDropdown">
				<option value="">All</option>
				<jstlc:forEach var="i" begin="1" end="8">
					<option value="${i}">${i}</option>
				</jstlc:forEach>
			</select><span> </span> Enter Name You want Search : <input type="text"
				id="searchInput" placeholder="Search by name"> <br>
	<br>
	<br>

	<div class="container">
		<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="center table table-striped" id="student-list-table"
			style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>SEM</th>
				<th>TYPE</th>
				<th>FACULTY</th>
				<th>HOD</th>
				<th>STATUS</th>
				<th>ACTION</th>
			</thead>
			<jstlc:forEach var="student" items="${student_list}">
				<tr style="text-transform: uppercase;">
					<td>${student.id}</td>
					<td>${student.name}</td>
					<td style="text-transform: lowercase;">${student.email}</td>
					<td>${student.dep}</td>
					<td>${student.sem}</td>
					<td>${student.type}</td>
					<td>${student.faculty.name}</td>
					<td>${student.hod.name}</td>
					<td>${student.verify}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentaccess?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Remove Access to this Student?');">Remove
							Access</a> | <a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/admin/studentcr?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">
							CR Action</a></td>
				</tr>

			</jstlc:forEach>
		</table>
	</div>

</body>
</html>