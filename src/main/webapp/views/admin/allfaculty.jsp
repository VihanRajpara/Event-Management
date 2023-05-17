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
body {
	font-family: 'Open Sans', sans-serif;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
	$('#searchInput').change(function() {
		var searchQuery = $('#searchInput').val();
		updateStudentList(searchQuery);
	});
});


	function updateStudentList(searchQuery) {
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/newallfaculty",
			type : "GET",
			data : {
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
	<center><h2><strong>Approval Faculty</strong></h2></center>
	<br><br>
		<div class="container">
		<table class="table table-striped" style="text-align: center;">
			<thead class="thead-dark">
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Dep</th>
				<th>Contact</th>
				<th>HOD</th>
				<th>Verify</th>
				<th>Action</th>
			</thead>
			<jstlc:forEach var="faculty" items="${faculty_new}">
				<tr>
					<td>${faculty.id}</td>
					<td>${faculty.name}</td>
					<td>${faculty.email}</td>
					<td>${faculty.dep}</td>
					<td>${faculty.contact}</td>
					<td>${faculty.hod.name}</td>
					<td>${faculty.verify}</td>
					<td>
						<a style="text-transform: capitalize;" href="${pageContext.request.contextPath}/admin/facultyaccess?id=${faculty.id}" 
							onclick="javascript: return confirm('Are you sure you want to Remove Access?');">Give Access</a>
					</td>
				</tr>
			</jstlc:forEach>
		</table>
	</div>
	<br>
	<center><h2><strong>All Faculty</strong></h2></center><br>
	Enter Name You want Search : <input type="text"
				id="searchInput" placeholder="Search by name"> <br>
	<br><br>
		<div class="container">
		<table class="table table-striped" id="student-list-table" style="text-align: center;">
			<thead class="thead-dark">
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Dep</th>
				<th>Contact</th>
				<th>HOD</th>
				<th>Verify</th>
				<th>Action</th>
			</thead>
			<jstlc:forEach var="faculty" items="${faculty_list}">
				<tr>
					<td>${faculty.id}</td>
					<td>${faculty.name}</td>
					<td>${faculty.email}</td>
					<td>${faculty.dep}</td>
					<td>${faculty.contact}</td>
					<td>${faculty.hod.name}</td>
					<td>${faculty.verify}</td>
					<td>
						<a style="text-transform: capitalize;" href="${pageContext.request.contextPath}/admin/facultyaccess?id=${faculty.id}" 
							onclick="javascript: return confirm('Are you sure you want to Remove Access?');">Remove Access</a>
					</td>
				</tr>
			</jstlc:forEach>
		</table>
	</div>
		
</body>
</html>