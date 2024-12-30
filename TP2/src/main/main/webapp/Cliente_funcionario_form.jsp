<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Formulário de Avaliação do Cliente</title>
</head>
<body>
	<h1>Avaliação do Cliente</h1>
	<form action="SaveServletClienteFuncionario" method="post">
		<!-- NIF do Cliente -->
		<fieldset>
			<legend>Identificação do Cliente</legend>
			<label for="clienteNIF">NIF do Cliente:</label> <input type="text"
				id="clienteNIF" name="clienteNIF" required maxlength="9"
				pattern="\d{9}" /> <br />
		</fieldset>

		<!-- Avaliação do Cliente -->
		<fieldset>
			<legend>Avaliação</legend>
			<label for="avaliacaoCliente">Avaliação (0 a 10):</label> <input
				type="number" id="avaliacaoCliente" name="avaliacaoCliente" required
				min="0" max="10" step="0.01" /> <br />
		</fieldset>

		<input type="submit" value="Salvar Avaliação" />
	</form>
</body>
</html>
