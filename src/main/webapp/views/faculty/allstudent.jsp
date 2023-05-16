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
}
</style>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#semDropdown').change(function() {
            var selectedSem = $(this).val();
            updateStudentList(selectedSem);
        });
    });

    function updateStudentList(selectedSem) {
        $.ajax({
            url: "${pageContext.request.contextPath}/faculty/newallstudent",
            type: "GET",
            data: {
                sem: selectedSem
            },
            success: function(response) {
            	$('#student-list-table').html(response);
                console.log(response);
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    }
</script>


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
	<br>
	<select id="semDropdown">
		<option value="">All</option>
		<jstlc:forEach var="i" begin="1" end="8">
			<option value="${i}">${i}</option>
		</jstlc:forEach>
	</select>
	<br>
	<br>
	<center>
		<strong><h2>CR Student</h2></strong>
	</center>
	<br>
	<div class="container">
		<table class="center table table-striped" 
			style="text-align: center;">
			<thead class="thead-dark">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>DEP</th>
				<th>SEM</th>
				<th>TYPE</th>
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
					<td>${student.verify}</td>
					<td><a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/studentaccess?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Remove Access to this Student?');">Remove
							Access </a> | <a style="text-transform: capitalize;"
						href="${pageContext.request.contextPath}/faculty/studentcr?id=${student.id}"
						onclick="javascript: return confirm('Are you sure you want to Remove CR to this Student?');">Remove
							CR</a></td>
				</tr>
			</jstlc:forEach>
		</table>
		<br> <br>
		<center>
			<strong><h2>All Student</h2></strong>
		</center>
		<br>
		<div class="container">
			
			<table class="center table table-striped" id="student-list-table"
				style="text-align: center;">
				<thead class="thead-dark">
					<th>ID</th>
					<th>NAME</th>
					<th>EMAIL</th>
					<th>DEP</th>
					<th>SEM</th>
					<th>TYPE</th>
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
							<td>${student.verify}</td>
							<td><a style="text-transform: capitalize;"
								href="${pageContext.request.contextPath}/faculty/studentaccess?id=${student.id}"
								onclick="javascript: return confirm('Are you sure you want to Remove Access to this Student?');">Remove
									Access</a> | <a style="text-transform: capitalize;"
								href="${pageContext.request.contextPath}/faculty/studentcr?id=${student.id}"
								onclick="javascript: return confirm('Are you sure you want to Make CR to this Student?');">Make
									CR</a></td>
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