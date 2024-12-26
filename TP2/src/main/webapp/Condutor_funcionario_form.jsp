<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Formulário de Avaliação do Condutor</title>
</head>
<body>
	<h1>Avaliação do Condutor</h1>
	<form action="SaveServletCondutorFuncionario" method="post">
		<!-- NIF do Condutor -->
		<fieldset>
			<legend>Identificação do Condutor</legend>
			<label for="condutorNIF">NIF do Condutor:</label> <input type="text"
				id="condutorNIF" name="condutorNIF" required maxlength="9"
				pattern="\d{9}" /> <br />
		</fieldset>

		<!-- Avaliação do Condutor -->
		<fieldset>
			<legend>Avaliação</legend>
			<label for="avaliacaoCondutor">Avaliação (0 a 10):</label> <input
				type="number" id="avaliacaoCondutor" name="avaliacaoCondutor"
				required min="0" max="10" step="0.01" /> <br />
		</fieldset>

		<input type="submit" value="Salvar Avaliação" />
	</form>
</body>
</html>
