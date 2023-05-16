<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core" %>
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