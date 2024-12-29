<%@page import="usr.*"%>
<%@page import="db.ClienteDao"%>
<%@page import="pojo.Cliente"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page errorPage="error.jsp"%>
<%
// Verifique se o usuário está logado e se a sessão não foi comprometida
User x = Check.login(request, response, 1);
%>

<%
// Caso o usuário esteja logado, prossegue com a execução
try {
	if (x != null && x.getNif() != 0) {
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = clienteDao.getById(x.getNif());
%>

<!DOCTYPE html>
<html>
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
<title>Perfil</title>
<style>
@font-face {
	font-family: ChristmasFont;
	src: url(fonts/MountainsofChristmas-Regular.ttf);
}

p {
	font-size: 2em;
	font-family: 'ChristmasFont', serif;
	margin: 5px;
}
</style>
</head>
<body>
	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%>
	</h2>


	<br />
	<p style="font-family: verdana">
		Cliente<br /> 1 - Reservar veículo, seleciona tipo ou modelo, o
		parque de levantamento e o período do aluguer. Após validação,
		apresenta o custo final previsto para o aluguer, expresso na moeda de
		preferência do cliente, ao qual pode ser aplicado um desconto
		indicando o respetivo código.<br /> 2 - Consultar estado das
		reservas, avaliações, custo final previsto e efetivo expresso na moeda
		de preferência.<br /> 3 - Consultar reputação e descontos.<br />
	</p>

	<div style="font-family: verdana; white-space: pre-wrap;">
		<%
		if (cliente != null) {
			// Obter a avaliação do cliente
			double reputacao = cliente.getAvaliacaoCliente();

			// Exibir o número de telefone de contato
			String contactoTel = "Número de telefone: " + cliente.getContactoTel();
		%>
		<h2>Reputação</h2>
		<%=reputacao%>/10,0
	</div>
	<div style="font-family: verdana;">
		<%=contactoTel%>
	</div>

	<input type="button" value="Voltar"
		onClick="javascript:window.history.back()" />
</body>
</html>

<%
} else {
%>
<div style="font-family: verdana; color: red;"># Cliente não
	encontrado *Não foi possível encontrar suas informações de cliente.*</div>
<%
}
} else {
%>
<div style="font-family: verdana; color: red;"># NIF inválido ou
	não logado.</div>
<%
}
} catch (Exception e) {
e.printStackTrace();
}
%>
