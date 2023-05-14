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
</style>
<%
String session_email = (String) session.getAttribute("email");
String session_type = (String) session.getAttribute("type");
%>
<%
if (session_email == null) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/'>Home</a></li>
			<li><a href='${pageContext.request.contextPath}/login'>LOGIN</a></li>
			<li><a href='${pageContext.request.contextPath}/signup'>SIGN
					UP</a></li>
		</ul>
	</nav>
</section>
<%
} else if (session_type.equals("admin")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/admin/dashboard'>HOME</a></li>
			<li><a href='${pageContext.request.contextPath}/admin/addhod'>ADD
					HOD</a></li>
					<li><a href='${pageContext.request.contextPath}/admin/adddep'>ADD
					DEP</a></li>
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
			<li><a href='${pageContext.request.contextPath}/hod/dashboard'>HOME</a></li>
			<li><a href='${pageContext.request.contextPath}/hod/approvalfaculty'>
					Faculty</a></li>
		</ul>

	</nav>
	<a class="button" href='${pageContext.request.contextPath}/hod/logout'>LOGOUT</a>
</section>
<%
} else if(session_type.equals("faculty")) {
%>
<section class="body">
	<nav>
		<ul class="menuItems">
			<li><a href='${pageContext.request.contextPath}/faculty/dashboard'>HOME</a></li>
			<li><a href='${pageContext.request.contextPath}/faculty/addfac'>ADD
					Faculty</a></li>
		</ul>

	</nav>
	<a class="button" href='${pageContext.request.contextPath}/faculty/logout'>LOGOUT</a>
</section>
<%
}
%>