<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pojo.Veiculo, db.VeiculoDao"%>
<%@page import="usr.*"%>
<%
// Lógica de Backend antes da saída HTML
User x = Check.login(request, response, 0);
try {
	if (x != null) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulário de Veículo</title>
</head>
<body>
	<%
	String matricula = request.getParameter("matricula");
	String titulo = matricula != null ? "Editar Veículo" : "Adicionar Novo Veículo";
	VeiculoDao veiculoDao = new VeiculoDao();
	Veiculo veiculo = matricula != null ? veiculoDao.getById(matricula) : new Veiculo();
	%>
	<h1><%=titulo%></h1>
	<form
		action="<%=matricula != null ? "EditServletVeiculo" : "SaveServletVeiculo"%>"
		method="post">
		<!-- Dados do Veículo -->
		<fieldset>
			<legend>Dados do Veículo</legend>
			<label for="matricula">Matrícula:</label> <input type="text"
				id="matricula" name="matricula"
				value="<%=veiculo.getMatricula() != null ? veiculo.getMatricula() : ""%>"
				required maxlength="6" pattern="[A-Z0-9]{6}"
				<%=matricula != null ? "readonly" : ""%> /> <br> <label
				for="cor">Cor:</label> <input type="text" id="cor" name="cor"
				value="<%=veiculo.getCor() != null ? veiculo.getCor() : ""%>"
				required maxlength="30" /> <br> <label for="nomeMod">Nome
				do Modelo:</label> <input type="text" id="nomeMod" name="nomeMod"
				value="<%=veiculo.getNomeMod() != null ? veiculo.getNomeMod() : ""%>"
				required maxlength="100" /> <br>
		</fieldset>
		<input type="submit"
			value="<%=matricula != null ? "Salvar Alterações" : "Adicionar Veículo"%>" />
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
