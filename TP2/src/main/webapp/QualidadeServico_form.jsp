<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="usr.*"%>
<%
// Lógica de Backend antes da saída HTML
User x = Check.login(request, response, 2);
try {
	if (x != null ) {
%>
<head>
<meta charset="UTF-8">
<title>Registrar Qualidade de Serviço</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

form {
	max-width: 400px;
	margin: 0 auto;
}

label {
	display: block;
	margin-top: 10px;
}

input, textarea, button {
	width: 100%;
	padding: 8px;
	margin-top: 5px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

textarea {
	resize: vertical;
}

button {
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
}

button:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<h1>Registrar Qualidade de Serviço</h1>
	<form action="SaveQualidadeServicoServlet" method="post">
		<label for="clienteNIF">NIF do Cliente:</label> <input type="number"
			id="clienteNIF" name="clienteNIF" required min="1" /> <label
			for="avaliacao">Avaliação (0 a 10):</label> <input type="number"
			id="avaliacao" name="avaliacao" required min="0" max="10" /> <label
			for="comentario">Comentário:</label>
		<textarea id="comentario" name="comentario" rows="4" maxlength="255"
			placeholder="Insira seu comentário (opcional)"></textarea>

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
