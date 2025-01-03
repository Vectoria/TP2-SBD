<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="db.ClienteDao, pojo.Cliente"%>
<%@page import="usr.*"%>
<%
// Lógica de Backend antes da saída HTML
User x = Check.login(request, response, 3);
try {
	if (x != null) {
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/forms.css">
<title>Formulário de Avaliação do Cliente</title>
</head>
<body>
	<%
	String clienteNIFStr = request.getParameter("clienteNIF");
	Cliente cliente = null;

	if (clienteNIFStr != null) {
		int clienteNIF = Integer.parseInt(clienteNIFStr);
		ClienteDao clienteDao = new ClienteDao();
		cliente = clienteDao.getById(clienteNIF);
	}
	%>
	<h1>Avaliação do Cliente</h1>
	<form action="SaveServletClienteFuncionario" method="post">
		<!-- NIF do Cliente -->
		<fieldset>
			<legend>Identificação do Cliente</legend>
			<label for="clienteNIF">NIF do Cliente:</label> <input type="text"
				id="clienteNIF" name="clienteNIF" readonly
				value="<%=cliente != null ? cliente.getClienteNIF() : ""%>" /> <br />
		</fieldset>

		<!-- Avaliação do Cliente -->
		<fieldset>
			<legend>Avaliação</legend>
			<label for="avaliacaoCliente">Avaliação (0 a 10):</label> <input
				type="number" id="avaliacaoCliente" name="avaliacaoCliente" required
				min="0" max="10" step="0.01"
				value="<%=cliente != null ? cliente.getAvaliacaoCliente() : ""%>" />
			<br />
		</fieldset>

		<input type="submit" value="Salvar Avaliação" />
	</form>
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