<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp" %> 
<%-- <%@page import="usr.*"%> --%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%-- <%User x=Check.login(request, response,0);%>  --%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Users">
<meta name="description" content="Edição de Utilizadores">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="11dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>Example</title>
<style>
@font-face {
    font-family: ChristmasFont;
    src: url(fonts/MountainsofChristmas-Regular.ttf);
}
p{
	font-size: 2em;
	font-family: 'ChristmasFont', serif;
	margin: 5px;
}
</style>
</head>
<body>
<%-- <h2>(<%=x.getProfile()%>) <%=x.welcome()%></h2> --%>
<br/>
<p>
Best wishes for this exciting new job! We'll always remember you as a great boss.
</p>
<br>
<p style="font-family:verdana">
Administrador<br/>
1 – Criar/Atualizar dados/fichas dos clientes e dos respetivos condutores.<br/>
2 – Criar/Atualizar dados dos veículos incluído conteúdos multimédia.<br/>
3 – Exportar para um documento XML/JSON dados de um veículo incluindo o registo cronológico.<br/>
4 – Importar de um documento XML/JSON dados de um veículo incluindo o registo cronológico.<br/>
<a href="view.jsp">Gestão de Perfis dos Utilizadores</a><br/>
</p>
<br/>
<input title="Go back" type="button" value="Back" onClick="javascript:window.history.back()"/>
</body>
</html>