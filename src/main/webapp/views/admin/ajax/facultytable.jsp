<%@ taglib prefix="jstlc" uri="http://java.sun.com/jsp/jstl/core"%>
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