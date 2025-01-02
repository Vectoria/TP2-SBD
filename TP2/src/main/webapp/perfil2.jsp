<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp" %> 
<%@page import="usr.*"%>
<%@page
	import="db.CondutorDao, db.AluguerDao, db.DescontoDao, db.QualidadeServicoDao, db.LugarVeiculoDao, db.VeiculoDao"%>
<%@page
	import="pojo.Condutor, pojo.Aluguer, pojo.Desconto, pojo.QualidadeServico, pojo.Veiculo"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%User x=Check.login(request, response,2);%> 
<%
try {
	if (x != null && x.getNif() != 0) {
		CondutorDao condutorDao = new CondutorDao();
		Condutor condutor = condutorDao.getById(x.getNif());
%>
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
If you want your colleague to leave in stitches, we have a few funny congratulations messages you can use. (We don't recommend these messages for your boss, though — keep it 100% professional there.)
</p>
<p style="font-family:verdana">
Condutor<br/>
1 - Levantar veículo, visualiza dados do veículo atribuído à reserva e respetivo lugar de estacionamento.<br/>
2 - Entregar veículo, procura lugares de estacionamento vagos no parque mais perto do local onde se encontra e indica qual foi o lugar onde estacionou. Opcionalmente, realiza a avaliação do aluguer/serviço e faz comentário.<br/>
</p>
<br/>
<input title="Go back" type="button" value="Back" onClick="javascript:window.history.back()"/>
</body>
</html>
<%
} else {
out.println("<div style='color: red;'># NIF inválido ou não logado.</div>");
}
} catch (Exception e) {
e.printStackTrace();
out.println("<div style='color: red;'>Ocorreu um erro ao processar a solicitação.</div>");
}
%>