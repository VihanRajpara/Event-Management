<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Signup</title>
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

form fieldset div select {
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
<script>

</script>
<body>

	<jsp:include page="components/userheader.jsp" />
	<div id="login">
		<form action="student/signupaction" method="post">
			<h1>
				<strong>Welcome.</strong> Please Sign up as <select name="type"
					id="type">
					<option value="student">STUDENT</option>
					<option value="faculty">FACULTY</option>

				</select>
			</h1>
			<fieldset>
				<div id="student-fields" style="display: block;">
					<p>
						 <select name="dep" id="dep"
							style="text-transform: uppercase;"dep"><option style="text-transform: uppercase;" value="" selected>Select Your Department</option>
							<jstlc:forEach var="dep" items="${dep_list}">
								<option style="text-transform: uppercase;" value="${dep.name}">${dep.name}</option>
							</jstlc:forEach>
						</select>
					<div>
						 <select name="faculty" id="faculty">
						<option style="text-transform: uppercase;" value="" selected>Select Your Faculty</option>
							<jstlc:forEach var="faculty" items="${fac_list}">
								<option style="text-transform: capitalize;"
									value="${faculty.id}">${faculty.dep} - ${faculty.name}</option>
							</jstlc:forEach>
						</select>
					</div>
					</p>
					<p>
						<input type="text" name="name" id="name"
							placeholder="Student Name">
					</p>
					<p>
						<input type="email" name="email" id="email"
							placeholder="Student Email">
					</p>
					<p>
						<input type="text" name="contact" id="contact"
							placeholder="Student contact">
					</p>
					<p>
						<input type="password" name="password" id="password"
							placeholder="Student Password">
					</p>

				</div>
		</form>

		<form action="faculty/signupaction" method="post">
			<div id="faculty-fields" style="display: none;">
				<p>
				<div>
					<select name="hod" id="hod" style="text-transform: uppercase;">
						<option style="text-transform: capitalize;" value="" selected>Select
							Your Hod</option>
						<jstlc:forEach var="hod" items="${hod_list}">
							<option value="${hod.id}">${hod.id}- ${hod.name}</option>
						</jstlc:forEach>
					</select>
				</div>
				</p>
				<p>
					<input type="text" name="name" id="name" placeholder="Faculty Name">
				</p>
				<p>
					<input type="email" name="email" id="email"
						placeholder="Faculty Email">
				</p>
				<p>
					<input type="text" name="contact" id="contact"
						placeholder="Faculty contact">
				</p>
				<p>
					<input type="password" name="password" id="password"
						placeholder="Faculty Password">
				</p>
			</div>



			<p>
				<input type="submit" value="Sign Up">
			</p>
			</fieldset>
		</form>
	</div>
	<script>
    const signupTypeSelect = document.getElementById("type");
    const facultyFieldsDiv = document.getElementById("faculty-fields");
    const studentFieldsDiv = document.getElementById("student-fields");
    signupTypeSelect.addEventListener("change", (event) => {
      const selectedValue = event.target.value;
      if (selectedValue === "faculty") {
        facultyFieldsDiv.style.display = "block";
        studentFieldsDiv.style.display = "none";
      } else if (selectedValue === "student") {
        facultyFieldsDiv.style.display = "none";
        studentFieldsDiv.style.display = "block";
      }
    });
  </script>
</body>
</html>