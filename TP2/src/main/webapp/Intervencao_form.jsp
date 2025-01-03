<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="usr.*"%>
<%
// Lógica de Backend antes da saída HTML
User x = Check.login(request, response, 3);
try {
	if (x != null) {
%>
<head>
<meta charset="UTF-8">
<title>Registar Intervenção</title>
<link rel="stylesheet" type="text/css" href="css/forms.css">
</head>

<body>
	<h1>Registar Intervenção</h1>
	<form action="SaveServletIntervencao" method="post">
		<label for="numKM">Número de Quilômetros:</label> <input type="number"
			id="numKM" name="numKM" required min="1" /> <label for="matricula">Matrícula
			do Veículo:</label> <input type="text" id="matricula" name="matricula"
			required maxlength="6" pattern="[A-Za-z0-9]{1,6}" /> <label
			for="dhRegisto">Data e Hora do Registo:</label> <input
			type="datetime-local" id="dhRegisto" name="dhRegisto" required /> <label
			for="tipoInt">Tipo de Intervenção:</label> <input type="text"
			id="tipoInt" name="tipoInt" required maxlength="50" /> <label
			for="custoInt">Custo da Intervenção:</label> <input type="number"
			id="custoInt" name="custoInt" required step="0.01" min="0" />

		<button type="submit">Salvar</button>
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