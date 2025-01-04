<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pojo.Cliente, pojo.Morada, db.ClienteDao, db.MoradaDao"%>
<%@page import="usr.*"%>
<%
User x = Check.login(request, response, 0);
try {
	if (x != null) {
%>
<!DOCTYPE html>
<html>
<head>
<title>Formulário de Cliente</title>
<link rel="stylesheet" type="text/css" href="css/forms.css">
</head>
<body>
	<%
	String clienteNIF = request.getParameter("clienteNIF");
	String titulo = clienteNIF != null ? "Editar Cliente" : "Adicionar Novo Cliente";

	
	Cliente cliente = null;
	Morada morada = null;
	if (clienteNIF != null) {
		ClienteDao clienteDao = new ClienteDao();
		cliente = clienteDao.getById(Integer.parseInt(clienteNIF));
		if (cliente != null) {
			MoradaDao moradaDao = new MoradaDao();
			morada = moradaDao.getById(cliente.getRua(), cliente.getCodigoPostalP1(), cliente.getCodigoPostalP2(),
			cliente.getNumeroPorta());
		}
	}
	%>
	<h1><%=titulo%></h1>

	<form
		action="<%=clienteNIF != null ? "EditServletCliente" : "SaveServletCliente"%>"
		method="post">
		<!-- Dados do Cliente -->
			<label for="clienteNIF">NIF do Cliente:</label> <input type="text"
				id="clienteNIF" name="clienteNIF"
				value="<%=cliente != null ? cliente.getClienteNIF() : ""%>" required
				maxlength="9" pattern="\d{9}"
				<%=clienteNIF != null ? "readonly" : ""%> /><br> <label
				for="moedaPref">Moeda Preferida:</label> <select id="moedaPref"
				name="moedaPref" required>
				<option value="">Selecione</option>
				<option value="Eur"
					<%=cliente != null && "Eur".equals(cliente.getMoedaPref()) ? "selected" : ""%>>Euro</option>
				<option value="Dol"
					<%=cliente != null && "Dol".equals(cliente.getMoedaPref()) ? "selected" : ""%>>Dólar</option>
				<option value="Lib"
					<%=cliente != null && "Lib".equals(cliente.getMoedaPref()) ? "selected" : ""%>>Libra</option>
			</select><br> <label for="prefLingCult">Língua e Cultura
				Preferida:</label> <select id="prefLingCult" name="prefLingCult" required>
				<option value="">Selecione</option>
				<option value="Portugues"
					<%=cliente != null && "Portugues".equals(cliente.getPrefLingCult()) ? "selected" : ""%>>Português</option>
				<option value="English"
					<%=cliente != null && "English".equals(cliente.getPrefLingCult()) ? "selected" : ""%>>Inglês</option>
				<option value="Espanhol"
					<%=cliente != null && "Espanhol".equals(cliente.getPrefLingCult()) ? "selected" : ""%>>Espanhol</option>
			</select><br> <label for="contactoTel">Telefone:</label> <input
				type="text" id="contactoTel" name="contactoTel"
				value="<%=cliente != null ? cliente.getContactoTel() : ""%>"
				required maxlength="9" pattern="\d{9}" /><br> <label
				for="email">E-mail:</label> <input type="email" id="email"
				name="email" value="<%=cliente != null ? cliente.getEmail() : ""%>"
				required maxlength="100" /><br> <label for="nome">Nome:</label>
			<input type="text" id="nome" name="nome"
				value="<%=cliente != null ? cliente.getNome() : ""%>" required
				maxlength="200" pattern="[a-zA-Z '-]+" /><br> <label
				for="condutorNIF">NIF do Condutor:</label> <input type="text"
				id="condutorNIF" name="condutorNIF"
				value="<%=cliente != null ? cliente.getCondutorNIF() : ""%>"
				required maxlength="9" pattern="\d{9}" /><br>

		<!-- Dados da Morada -->
			<label for="rua">Rua:</label> <input type="text" id="rua" name="rua"
				value="<%=morada != null ? morada.getRua() : ""%>" required
				maxlength="200" /><br> <label for="codigoPostalP1">Código
				Postal Parte 1:</label> <input type="text" id="codigoPostalP1"
				name="codigoPostalP1"
				value="<%=morada != null ? morada.getCodigoPostalP1() : ""%>"
				required maxlength="4" pattern="\d{4}" /><br> <label
				for="codigoPostalP2">Código Postal Parte 2:</label> <input
				type="text" id="codigoPostalP2" name="codigoPostalP2"
				value="<%=morada != null ? morada.getCodigoPostalP2() : ""%>"
				required maxlength="3" pattern="\d{3}" /><br> <label
				for="numeroPorta">Número da Porta:</label> <input type="text"
				id="numeroPorta" name="numeroPorta"
				value="<%=morada != null ? morada.getNumeroPorta() : ""%>" required
				maxlength="3" pattern="\d{1,3}" /><br> <label
				for="nomeFreguesia">Freguesia:</label> <input type="text"
				id="nomeFreguesia" name="nomeFreguesia"
				value="<%=morada != null ? morada.getNomeFreguesia() : ""%>"
				maxlength="30" /><br> <label for="nomeConcelho">Concelho:</label>
			<input type="text" id="nomeConcelho" name="nomeConcelho"
				value="<%=morada != null ? morada.getNomeConcelho() : ""%>"
				maxlength="30" /><br> <label for="nomeDistrito">Distrito:</label>
			<input type="text" id="nomeDistrito" name="nomeDistrito"
				value="<%=morada != null ? morada.getNomeDistrito() : ""%>"
				maxlength="20" /><br>

		<input type="submit"
			value="<%=clienteNIF != null ? "Salvar Alterações" : "Adicionar Cliente"%>" />
		<input type="button" value="Voltar" onclick="history.back()"
			 />
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