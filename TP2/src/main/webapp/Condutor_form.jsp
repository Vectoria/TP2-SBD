<!DOCTYPE html>
<html>
<%@ page
	import="pojo.CartaConducao, pojo.Condutor, db.CartaConducaoDao, db.CondutorDao, java.time.LocalDate"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gerenciar Carta de Condução e Condutor</title>
</head>
<body>
	<%
	String numID = request.getParameter("numID");
	String titulo = "";
	String accao = "";

	CartaConducaoDao cartaDao = new CartaConducaoDao();
	CondutorDao condutorDao = new CondutorDao();
	CartaConducao carta = new CartaConducao();
	Condutor condutor = new Condutor();

	if (numID != null && !numID.isBlank()) {
		try {
			int id = Integer.parseInt(numID);
			carta = cartaDao.getById(id);
			if (carta != null) {
		// Get the corresponding condutor using the numID
		for (Condutor c : condutorDao.getAll()) {
			if (c.getNumID() == id) {
				condutor = c;
				break;
			}
		}
		titulo = "Update Driver's License and Driver";
		accao = "EditServletCondutor";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	} else {
		titulo = "Add New Driver's License and Driver";
		accao = "SaveServletCondutor";
	}
	%>

	<h1><%=titulo%>&nbsp; <input title="View records" type="button"
			value="View" onClick="javascript:window.open('index.jsp')" /> &nbsp;<input
			title="Go back" type="button" value="Back"
			onclick="javascript:window.history.back()" />
	</h1>

	<form action="<%=accao%>" method="post">
		<input type="hidden" name="from" value="form.jsp" />
		<table>
			<!-- Driver's License Section -->
			<tr>
				<td colspan="2"><h3>Driver's License Information</h3></td>
			</tr>
			<tr>
				<td><label for="numID">License Number:</label></td>
				<td><input type="text" id="numID" name="numID"
					value="<%=carta.getNumID()%>" required /></td>
			</tr>
			<tr>
				<td><label for="tipoHab">License Type:</label></td>
				<td><input type="text" id="tipoHab" name="tipoHab"
					value="<%=carta.getTipoHab()%>" required maxlength="20" /></td>
			</tr>
			<tr>
				<td><label for="dataEmissao">Issue Date:</label></td>
				<td><input type="date" id="dataEmissao" name="dataEmissao"
					value="<%=carta.getDataEmissao() != null ? carta.getDataEmissao().toString() : ""%>"
					required /></td>
			</tr>
			<tr>
				<td><label for="dataValidade">Expiry Date:</label></td>
				<td><input type="date" id="dataValidade" name="dataValidade"
					value="<%=carta.getDataValidade() != null ? carta.getDataValidade().toString() : ""%>"
					required /></td>
			</tr>

			<!-- Driver Section -->
			<tr>
				<td colspan="2"><h3>Driver Information</h3></td>
			</tr>
			<tr>
				<td><label for="condutorNIF">Driver NIF:</label></td>
				<td><input type="text" id="condutorNIF" name="condutorNIF"
					value="<%=condutor.getCondutorNIF()%>" required /></td>
			</tr>
			<tr>
				<td><label for="dataNascimento">Birth Date:</label></td>
				<td><input type="date" id="dataNascimento"
					name="dataNascimento"
					value="<%=condutor.getDataNascimento() != null ? condutor.getDataNascimento().toString() : ""%>"
					required /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save Records" /></td>
			</tr>
		</table>
	</form>

</body>
</html>