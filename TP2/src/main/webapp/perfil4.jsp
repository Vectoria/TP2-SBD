<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp" %> 
<%@page import="usr.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%User x=Check.login(request, response, 4);%> 
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
<h2>(<%=x.getProfile()%>) <%=x.welcome()%></h2>
<br/>
<p>
Congratulations on taking the next big step in your career. Best wishes for your time at [new company] — they're lucky to have you.
</p>
<p style="font-family:verdana">
Gerente<br/>
1 – Apresentar histórico de um determinado veículo, avaliações e intervenções existentes no registo cronológico.<br/>
2 – Exibir o ranking das 3 marcas de veículos que geraram menor lucro.<br/>
3 – Exibir o ranking dos 5 modelos dos veículos com melhor avaliação na semana passada.<br/>
4 – Exibir o ranking dos 10 veículos que percorreram menos quilómetros no último trimestre.<br/>
5 – Exibir o ranking dos 100 clientes ordenados por reputação e filtrados por freguesia de morada.<br/>

</p>
<br/>
<input title="Go back" type="button" value="Back" onClick="javascript:window.history.back()"/>
</body>
</html>