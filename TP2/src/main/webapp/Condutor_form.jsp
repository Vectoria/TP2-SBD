<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="pojo.CartaConducao, pojo.Condutor, db.CartaConducaoDao, db.CondutorDao, java.time.LocalDate"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gerenciar Carta de Condução e Condutor</title>
<style>
table {
	margin: 20px;
	border-spacing: 0 10px;
}

td {
	padding: 5px;
}

input[type="text"], input[type="date"] {
	padding: 5px;
	width: 200px;
}

input[type="submit"] {
	margin-top: 20px;
	padding: 10px 20px;
}

h3 {
	color: #333;
	margin-top: 20px;
}
</style>
</head>
<body>
	<%
	// Get parameters and initialize DAOs
	String condutorNIF = request.getParameter("condutorNIF");
	String titulo = "";
	String accao = "";

	CartaConducaoDao cartaDao = new CartaConducaoDao();
	CondutorDao condutorDao = new CondutorDao();
	CartaConducao carta = null;
	Condutor condutor = null;

	// Fetch existing data if editing
	if (condutorNIF != null && !condutorNIF.isEmpty()) {
		try {
			condutor = condutorDao.getById(Integer.parseInt(condutorNIF));
			if (condutor != null) {
		carta = cartaDao.getById(condutor.getNumID());
		titulo = "Atualizar Condutor e Carta de Condução";
		accao = "EditServletCondutor";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	} else {
		titulo = "Adicionar Novo Condutor";
		accao = "SaveServletCondutor";
		carta = new CartaConducao();
		condutor = new Condutor();
	}
	%>

	<h1>
		<%=titulo%>&nbsp; <input title="Ver registros" type="button"
			value="Ver" onClick="javascript:window.open('index.jsp')" />&nbsp; <input
			title="Voltar" type="button" value="Voltar"
			onclick="javascript:window.history.back()" />
	</h1>

	<form action="<%=accao%>" method="post">
		<input type="hidden" name="from" value="form.jsp" />
		<table>
			<!-- Seção Carta de Condução -->
			<tr>
				<td colspan="2"><h3>Informações da Carta de Condução</h3></td>
			</tr>
			<tr>
				<td><label for="numID">Número da Carta:</label></td>
				<td><input type="text" id="numID" name="numID"
					value="<%=carta != null ? carta.getNumID() : ""%>"
					<%=condutorNIF != null ? "readonly" : ""%> required /></td>
			</tr>
			<tr>
				<td><label for="tipoHab">Tipo de Habilitação:</label></td>
				<td><input type="text" id="tipoHab" name="tipoHab"
					value="<%=carta != null ? carta.getTipoHab() : ""%>" required
					maxlength="20" /></td>
			</tr>
			<tr>
				<td><label for="dataEmissao">Data de Emissão:</label></td>
				<td><input type="date" id="dataEmissao" name="dataEmissao"
					value="<%=carta != null && carta.getDataEmissao() != null ? carta.getDataEmissao().toString() : ""%>"
					required /></td>
			</tr>
			<tr>
				<td><label for="dataValidade">Data de Validade:</label></td>
				<td><input type="date" id="dataValidade" name="dataValidade"
					value="<%=carta != null && carta.getDataValidade() != null ? carta.getDataValidade().toString() : ""%>"
					required /></td>
			</tr>

			<!-- Seção Condutor -->
			<tr>
				<td colspan="2"><h3>Informações do Condutor</h3></td>
			</tr>
			<tr>
				<td><label for="condutorNIF">NIF do Condutor:</label></td>
				<td><input type="text" id="condutorNIF" name="condutorNIF"
					value="<%=condutor != null ? condutor.getCondutorNIF() : ""%>"
					required maxlength="9" pattern="\d{9}"
					<%=condutorNIF != null ? "readonly" : ""%> /></td>
			</tr>
			<tr>
				<td><label for="dataNascimento">Data de Nascimento:</label></td>
				<td><input type="date" id="dataNascimento"
					name="dataNascimento"
					value="<%=condutor != null && condutor.getDataNascimento() != null ? condutor.getDataNascimento().toString() : ""%>"
					required /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="<%=condutorNIF != null ? "Atualizar Registros" : "Salvar Registros"%>" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>