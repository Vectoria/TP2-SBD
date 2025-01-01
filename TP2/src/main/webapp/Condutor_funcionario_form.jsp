<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="db.CondutorDao, pojo.Condutor"%>
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
<title>Formulário de Avaliação do Condutor</title>
</head>
<body>
	<%
	String condutorNIFStr = request.getParameter("condutorNIF");
	Condutor condutor = null;

	if (condutorNIFStr != null) {
		int condutorNIF = Integer.parseInt(condutorNIFStr);
		CondutorDao condutorDao = new CondutorDao();
		condutor = condutorDao.getById(condutorNIF);
	}
	%>
	<h1>Avaliação do Condutor</h1>
	<form action="SaveServletCondutorFuncionario" method="post">
		<!-- NIF do Condutor -->
		<fieldset>
			<legend>Identificação do Condutor</legend>
			<label for="condutorNIF">NIF do Condutor:</label> <input type="text"
				id="condutorNIF" name="condutorNIF" readonly
				value="<%=condutor != null ? condutor.getCondutorNIF() : ""%>" />
			<br />
		</fieldset>

		<!-- Avaliação do Condutor -->
		<fieldset>
			<legend>Avaliação</legend>
			<label for="avaliacaoCondutor">Avaliação (0 a 10):</label> <input
				type="number" id="avaliacaoCondutor" name="avaliacaoCondutor"
				required min="0" max="10" step="0.01"
				value="<%=condutor != null ? condutor.getReputacao() : ""%>" /> <br />
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
