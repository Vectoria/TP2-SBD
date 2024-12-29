<%@page import="usr.*"%>
<%@page import="db.ClienteDao"%>
<%@page import="pojo.Cliente"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
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

		if (cliente != null) {
	// Obter a avaliação do cliente
	double reputacao = cliente.getAvaliacaoCliente();
	// Formatar a string com a reputação
	String reputacaoMd = String.format(
    "## Sua Reputação\n\n**Avaliação**: %.1f/5.0\n\n*Esta avaliação é baseada no histórico de alugueres*",
    reputacao);

%> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Perfil</title>
</head>
<body>
 	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%></h2> 
	<div style="font-family: verdana; white-space: pre-wrap;">
		<%=reputacaoMd%> 
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
