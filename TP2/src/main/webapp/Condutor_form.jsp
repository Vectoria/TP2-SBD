<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="pojo.CartaConducao, pojo.Condutor, db.CartaConducaoDao, db.CondutorDao"%>
<%@page import="usr.*"%>
<%
User x = Check.login(request, response, 0);
try {
	if (x != null) {
%>
<!DOCTYPE html>
<html>
<head>
<title>Formulário de Condutor</title>
<link rel="stylesheet" type="text/css" href="css/forms.css">
</head>
<body>
	<%
	String condutorNIF = request.getParameter("condutorNIF");
	String titulo = condutorNIF != null ? "Editar Condutor" : "Adicionar Novo Condutor";

	CartaConducaoDao cartaDao = new CartaConducaoDao();
	CondutorDao condutorDao = new CondutorDao();
	CartaConducao carta = null;
	Condutor condutor = null;

	if (condutorNIF != null) {
		try {
			condutor = condutorDao.getById(Integer.parseInt(condutorNIF));
			if (condutor != null) {
		carta = cartaDao.getById(condutor.getNumID());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	%>
	<h1><%=titulo%></h1>

	<form
		action="<%=condutorNIF != null ? "EditServletCondutor" : "SaveServletCondutor"%>"
		method="post">
		<!-- Informações do Condutor -->
		<label for="condutorNIF">NIF do Condutor:</label> <input type="text"
			id="condutorNIF" name="condutorNIF"
			value="<%=condutor != null ? condutor.getCondutorNIF() : ""%>"
			required maxlength="9" pattern="\d{9}"
			<%=condutorNIF != null ? "readonly" : ""%> /><br> <label
			for="dataNascimento">Data de Nascimento:</label> <input type="date"
			id="dataNascimento" name="dataNascimento"
			value="<%=condutor != null && condutor.getDataNascimento() != null ? condutor.getDataNascimento().toString() : ""%>"
			required /><br>

		<!-- Informações da Carta de Condução -->
		<h2>Informações da Carta de Condução</h2>

		<label for="numID">Número da Carta:</label> <input type="text"
			id="numID" name="numID"
			value="<%=carta != null ? carta.getNumID() : ""%>" required
			maxlength="20" <%=condutorNIF != null ? "readonly" : ""%> /><br>

		<label for="tipoHab">Tipo de Habilitação:</label> <input type="text"
			id="tipoHab" name="tipoHab"
			value="<%=carta != null ? carta.getTipoHab() : ""%>" required
			maxlength="20" /><br> <label for="dataEmissao">Data de
			Emissão:</label> <input type="date" id="dataEmissao" name="dataEmissao"
			value="<%=carta != null && carta.getDataEmissao() != null ? carta.getDataEmissao().toString() : ""%>"
			required /><br> <label for="dataValidade">Data de
			Validade:</label> <input type="date" id="dataValidade" name="dataValidade"
			value="<%=carta != null && carta.getDataValidade() != null ? carta.getDataValidade().toString() : ""%>"
			required /><br>

		<!-- Botões de Ação -->
		<input type="submit"
			value="<%=condutorNIF != null ? "Salvar Alterações" : "Adicionar Condutor"%>" />
		<input type="button" value="Voltar" onclick="history.back()" />
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
