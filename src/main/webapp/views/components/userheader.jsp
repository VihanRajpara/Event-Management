<style>
* {
	padding: 0;
	margin: 0;
}

.body {
	display: flex;
	justify-content: center;
	align-items: center;
	position: relative;
}

section {
	background: #dcdcdc;
}

nav {
	width: 100%;
	text-align: center;
	background: #dcdcdc;
	padding: 25px;
}

nav p {
	text-align: right;
	text-transform: uppercase;
}

.menuItems {
	list-style: none;
	display: flex;
	justify-content: center;
}

.menuItems li {
	margin: 0 20px;
}

.menuItems li a {
	text-decoration: none;
	color: #696969;
	font-size: 18px;
	font-weight: 400;
	position: relative;
	text-transform: uppercase;
}

.button {
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

.menuItems li a:hover {
	width: 100%;
	transition: all 0.5s ease-in-out;
	color: #4169E1;
}

.menuItems li a.active {
	color: #4169E1;
	font-weight: bold;
}
</style>
<%
String session_email = (String) session.getAttribute("email");
String session_type = (String) session.getAttribute("type");
String urlPath = request.getRequestURI().substring(request.getContextPath().length());
%>
<%
if (session_email == null) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/'
				<% if (urlPath.equals("/")) { %> class="active" <% } %>>Home</a></li>
			<li><a href='${pageContext.request.contextPath}/login'
				<% if (urlPath.equals("/views/login.jsp")) { %> class="active"
				<% } %>>LOGIN</a></li>
			<li><a href='${pageContext.request.contextPath}/signup'
				<% if (urlPath.equals("/views/signup.jsp")) { %> class="active"
				<% } %>>SIGN UP</a></li>
		</ul>
	</nav>
</section>
<%
} else if (session_type.equals("admin")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/admin/dashboard'
				<% if (urlPath.equals("/views/admin/dashboard.jsp")) { %>
				class="active" <% } %>>HOME</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/addhod'
				<% if (urlPath.equals("/views/admin/addhod.jsp")) { %>
				class="active" <% } %>>ADD HOD</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/adddep'
				<% if (urlPath.equals("/views/admin/adddep.jsp")) { %>
				class="active" <% } %>>ADD DEP</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/faculty'
				<% if (urlPath.equals("/views/admin/allfaculty.jsp")) { %>
				class="active" <% } %>>FAculty</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/student'
				<% if (urlPath.equals("/views/admin/allstudent.jsp")) { %>
				class="active" <% } %>>Student</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/newevent'
				<% if (urlPath.equals("/views/admin/newevent.jsp")) { %>
				class="active" <% } %>>NEW EVENT</a></li>
		</ul>

	</nav>
	<a class="button"
		href='${pageContext.request.contextPath}/admin/logout'>LOGOUT</a>
</section>
<%
} else if (session_type.equals("hod")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/hod/dashboard'
				<% if (urlPath.equals("/views/hod/dashboard.jsp")) { %>
				class="active" <% } %>>HOME</a></li>
			<li><a
				href='${pageContext.request.contextPath}/hod/approvalfaculty'
				<% if (urlPath.equals("/views/hod/approvefaculty.jsp")) { %>
				class="active" <% } %>> Faculty</a></li>
			<li><a
				href='${pageContext.request.contextPath}/hod/approvestudent'
				<% if (urlPath.equals("/views/hod/approvalstudent.jsp")) { %>
				class="active" <% } %>>New Student</a></li>
			<li><a href='${pageContext.request.contextPath}/hod/allstudent'
				<% if (urlPath.equals("/views/hod/allstudent.jsp")) { %>
				class="active" <% } %>> Student</a></li>
		</ul>

	</nav>
	<a class="button" href='${pageContext.request.contextPath}/hod/logout'>LOGOUT</a>
</section>
<%
} else if (session_type.equals("faculty")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a
				href='${pageContext.request.contextPath}/faculty/dashboard'
				<% if (urlPath.equals("/views/faculty/dashboard.jsp")) { %>
				class="active" <% } %>>HOME</a></li>
			<li><a
				href='${pageContext.request.contextPath}/faculty/approvalstudent'
				<% if (urlPath.equals("/views/faculty/approvestudent.jsp")) { %>
				class="active" <% } %>>Add Student</a></li>
			<li><a
				href='${pageContext.request.contextPath}/faculty/allstudent'
				<% if (urlPath.equals("/views/faculty/allstudent.jsp")) { %>
				class="active" <% } %>>All Student</a></li>
		</ul>

	</nav>
	<a class="button"
		href='${pageContext.request.contextPath}/faculty/logout'>LOGOUT</a>
</section>
<%
} else if (session_type.equals("student")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a
				href='${pageContext.request.contextPath}/student/dashboard'
				<% if (urlPath.equals("/views/student/dashboard.jsp")) { %>
				class="active" <% } %>>HOME</a></li>
		</ul>

	</nav>
	<a class="button"
		href='${pageContext.request.contextPath}/student/logout'>LOGOUT</a>
</section>
<%
}
%>