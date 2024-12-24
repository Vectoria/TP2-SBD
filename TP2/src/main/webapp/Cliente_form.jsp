<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pojo.Cliente, pojo.Morada, db.ClienteDao, db.MoradaDao"%>
<!DOCTYPE html>
<html>
<head>
<title>Formulário de Cliente</title>
</head>
<body>
	<h1>
		<%
		String clienteNIF = request.getParameter("clienteNIF");
		String titulo = clienteNIF != null ? "Editar Cliente" : "Adicionar Novo Cliente";
		out.print(titulo);
		%>
	</h1>
	<form
		action="<%=clienteNIF != null ? "EditServletCliente" : "SaveServletCliente"%>"
		method="post">
		<!-- Dados do Cliente -->
		<fieldset>
			<legend>Dados do Cliente</legend>
			<label for="clienteNIF">NIF do Cliente:</label> <input type="text"
				id="clienteNIF" name="clienteNIF"
				value="<%=clienteNIF != null ? clienteNIF : ""%>" required
				maxlength="9" pattern="\d{9}" /> <br> <label for="moedaPref">Moeda
				Preferida:</label> <select id="moedaPref" name="moedaPref" required>
				<option value="">Selecione</option>
				<option value="Eur">Euro</option>
				<option value="Dol">Dólar</option>
				<option value="Lib">Libra</option>
			</select> <br> <label for="prefLingCult">Língua e Cultura
				Preferida:</label> <select id="prefLingCult" name="prefLingCult" required>
				<option value="">Selecione</option>
				<option value="Portugues">Português</option>
				<option value="English">Inglês</option>
				<option value="Espanhol">Espanhol</option>
			</select> <br> <label for="contactoTel">Telefone:</label> <input
				type="text" id="contactoTel" name="contactoTel" required
				maxlength="9" pattern="\d{9}" /> <br> <label for="email">E-mail:</label>
			<input type="email" id="email" name="email" required maxlength="100" />
			<br> <label for="nome">Nome:</label> <input type="text"
				id="nome" name="nome" required maxlength="200"
				pattern="[a-zA-Z '-]+" /> <br> <label for="condutorNIF">NIF
				do Condutor:</label> <input type="text" id="condutorNIF" name="condutorNIF"
				required maxlength="9" pattern="\d{9}" /> <br>
		</fieldset>

		<!-- Dados da Morada -->
		<fieldset>
			<legend>Morada</legend>
			<label for="rua">Rua:</label> <input type="text" id="rua" name="rua"
				required maxlength="200" /> <br> <label for="codigoPostalP1">Código
				Postal Parte 1:</label> <input type="text" id="codigoPostalP1"
				name="codigoPostalP1" required maxlength="4" pattern="\d{4}" /> <br>

			<label for="codigoPostalP2">Código Postal Parte 2:</label> <input
				type="text" id="codigoPostalP2" name="codigoPostalP2" required
				maxlength="3" pattern="\d{3}" /> <br> <label for="numeroPorta">Número
				da Porta:</label> <input type="text" id="numeroPorta" name="numeroPorta"
				required maxlength="3" pattern="\d{1,3}" /> <br> <label
				for="nomeFreguesia">Freguesia:</label> <input type="text"
				id="nomeFreguesia" name="nomeFreguesia" maxlength="30" /> <br>

			<label for="nomeConcelho">Concelho:</label> <input type="text"
				id="nomeConcelho" name="nomeConcelho" maxlength="30" /> <br> <label
				for="nomeDistrito">Distrito:</label> <input type="text"
				id="nomeDistrito" name="nomeDistrito" maxlength="20" /> <br>
		</fieldset>

		<input type="submit"
			value="<%=clienteNIF != null ? "Salvar Alterações" : "Adicionar Cliente"%>" />
	</form>
</body>
</html>
