<%@page import="usr.*"%>
<%
User x = Check.login(request, response, 0);
try {
	if (x != null) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Importar Veículo XML</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

.button {
	padding: 10px 20px;
	background-color: #4CAF50;
	color: white;
	border: none;
	text-decoration: none;
	border-radius: 5px;
}

.button:hover {
	background-color: #45a049;
}
</style>
</head>
<body>
	<h1>Importar Veículo XML</h1>
	<form action="ImportarVeiculoXMLServlet" method="post"
		enctype="multipart/form-data">
		<label>Selecionar Arquivo XML: <input type="file"
			name="xmlFile" accept=".xml" required>
		</label>
		<button type="submit" class="button">Importar</button>
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