<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp"%>
<%@page import="usr.*, java.math.BigDecimal"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%User x=Check.login(request, response);%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Users">
<meta name="description" content="Edição de Utilizadores">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="07dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>Edit Users</title>
<!-- Exemplo vulgarmente designado por: CRUD 
												Create					INSERT
												Read (Retrieve)			SELECT
												Update					UPDATE
												Delete (Destroy)		DELETE 
-->

<!-- 
		ABCD: Add, Browse, Change and Delete
	 	BREAD: Browse, Read, Edit, Add and Delete
	 	VADE(R): View, Add, Delete, Edit (e Restore, para sistemas com processos transacionais)
	 	VEIA: Visualizar, Excluir, Inserir, Alterar 
-->
</head>
<body>
<%-- 	<%
String old_username = request.getParameter("old_username");
if(old_username==null || old_username.isBlank())
	old_username = request.getParameter("username");
String old_userid = request.getParameter("userid");
String titulo="Update User";
String accao="EditServlet";
User u = null;
if(old_username!=null && !old_username.isBlank())
	u = UserDao.getByName(old_username);
else 
	if(old_userid!=null && !old_userid.isBlank()) {
		u = UserDao.getById(new BigDecimal(old_userid)); 
		if(u!=null)
			old_username = u.getUsername();
	}
if(u==null) {
	old_username="";
	titulo="Add New User";
	accao="SaveServlet";
	u = new User();
	u.setUsername("");
	u.setPassword("");
	u.setFirstname("");
	u.setLastname("");
	u.setEmail("");
}
%>
	<h1><%=titulo%></h1>
	<%
	if(u.getUserid()==null) {
		%>New record!<%
	}
	else {
		%>Updated at
	<%=u.getUpdated()%>
	<%
	}
%>
	<form id="search" name="search" method="get">
		<input type="hidden" id="param" />
	</form>
	<script>
function go(name, to) {
	if(name==='gender') {
		document.getElementById('param').value='X';
		if(document.getElementById('Female').checked)
			document.getElementById('param').value='F';
		else if(document.getElementById('Male').checked)
				document.getElementById('param').value='M';
	}
	else
		document.getElementById('param').value=document.getElementById(name).value;
	document.getElementById('param').name=name; 
	document.getElementById('search').action=to; 
	document.getElementById('search').submit();
}
</script>
	<form action="<%=accao%>" method="post" autocomplete="off">
		<input value="<%=old_username%>" type="hidden" id="old_username"
			name="old_username" />
		<table>
			<tr>
				<td><label for="userid">User ID:</label></td>
				<td><input value="<%=u.getUserid()%>" type="number" min='1'
					id="userid" name="userid" maxlength="12" size="10"
					title="User ID (2^63 - 1), auto incremented..." />&nbsp;&nbsp;
					&nbsp;<input type="button" value="Search"
					onclick="go('userid','');" /></td>
			</tr>
			
			<tr>
				<td><label for="profile">Profile:</label></td>
				<td><select required id="profile" name="profile"
					title="Profile level...">
						<option value="0">Administrador</option>
						<option value="1">Cliente</option>
						<option value="2">Condutor</option>
						<option value="3">Funcionário</option>
						<option value="4">Gerente</option>
				</select> <script>
				function selectItemByValue(elmnt, value){
				  for(var i=0; i < elmnt.options.length; i++)
				  {
				    if(elmnt.options[i].value === value) {
				      elmnt.selectedIndex = i;
				      break;
				    }
				  }
				}
				selectItemByValue(document.getElementById("profile"),'<%=u.getProfile()%>');
			</script> &nbsp;&nbsp;<input type="button" value="Search"
					onclick="go('profile','view.jsp')" /></td>
			</tr>
			<tr>
				<td><label for="username">Name:</label></td>
				<td><input autocomplete="off" autofocus tabindex="1" required
					value="<%=u.getUsername()%>" type="text" id="username"
					name="username" maxlength="10" size="30"
					pattern="[0-9a-zA-Z]{4,10}" title="User name (4..10)" />
					&nbsp;&nbsp;<input type="button" value="Search"
					onclick="go('username','')" /></td>
			</tr>
			<tr>
				<td><label for="password">Password:</label></td>
				<td><script>
		 		function myFunction() {
		 		 	 var x = document.getElementById("password");
		 		  	if (x.type === "password") {
		 		   	 x.type = "text";
		 		 	 } else {
		 		   	 x.type = "password";
		 		  }
		 		}
		 	</script> <input autocomplete="off" tabindex="2" type="password" value=""
					id="password" name="password" maxlength="20" size="30"
					title="Password..." />&nbsp; <input tabindex="20" type="checkbox"
					onclick="myFunction()"
					title="Toggle between password visibility...">Show Password
				</td>
			</tr>
			<tr>
				<td><label for="firstname">First Name:</label></td>
				<td><input tabindex="3" required type="text"
					value="<%=u.getFirstname()%>" id="firstname" name="firstname"
					maxlength="60" size="30"
					pattern="[a-zA-Z _ÁÉÍÓÚàáãâéêíóõôúç']{2,60}"
					title="First Name (2..60)" /> &nbsp;&nbsp;<input type="button"
					value="Search" onclick="go('firstname','view.jsp')" /></td>
			</tr>
			<tr>
				<td><label for="lastname">Last Name:</label></td>
				<td><input tabindex="4" required type="text"
					value="<%=u.getLastname()%>" id="lastname" name="lastname"
					maxlength="60" size="30"
					pattern="[a-zA-Z _ÁÉÍÓÚàáãâéêíóõôúç']{2,60}"
					title="Last Name (2..60)" /> &nbsp;&nbsp;<input type="button"
					value="Search" onclick="go('lastname','view.jsp')" /></td>
			</tr>
			<tr>
				<td><label for="email">Email:</label></td>
				<td><input tabindex="5" type="email" value="<%=u.getEmail()%>"
					id="email" name="email" maxlength="45" size="30" title="Email..." />
					&nbsp;&nbsp;<input type="button" value="Search"
					onclick="go('email','view.jsp')" /></td>
			</tr>
			<tr>
				<td colspan="2"><br /> <input title="Save data" type="submit"
					value="Save" />&nbsp; <input title="View users" type="button"
					value="View" onClick="javascript:window.open('view.jsp')" />&nbsp;
					<input title="Go back" type="button" value="Back"
					onClick="javascript:window.history.back()" /></td>
			</tr>
		</table>
	</form> --%>

</body>
</html>