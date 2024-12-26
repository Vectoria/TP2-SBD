<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp"%>
<%@ page import="pojo.Cliente, pojo.Condutor, db.ClienteDao, db.CondutorDao, java.util.List"%>
<%@ page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<meta name="keywords" content="ISEL, DEETC, JSP, Users">
<meta name="description" content="Edição de Utilizadores">
<meta name="owner" content="ISEL/DEETC - Doutor Porfírio Filipe">
<meta name="copyright" content="ISEL/DEETC/2022">
<meta name="createdate" content="20nov2022">
<meta name="lastupdate" content="11dec2023">
<meta http-equiv="Pragma" content="no-cache">
<title>Example</title>
<style>
@font-face {
	font-family: ChristmasFont;
	src: url(fonts/MountainsofChristmas-Regular.ttf);
}

p {
	font-size: 2em;
	font-family: 'ChristmasFont', serif;
	margin: 5px;
}

.table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.table th, .table td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: left;
}

.table th {
	background-color: #f2f2f2;
}

.search-bar {
	width: 100%;
	padding: 8px;
	margin-bottom: 20px;
}

button {
	padding: 8px 15px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 5px;
}

button:hover {
	background-color: #45a049;
}
</style>
<script>
function searchClient() {
    const input = document.getElementById('clientSearch').value.toLowerCase();
    const rows = document.querySelectorAll('#clientTable tbody tr');
    rows.forEach(row => {
        const name = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
        row.style.display = name.includes(input) ? '' : 'none';
    });
}
</script>
</head>
<body>
	<br />
	<p>Congratulations on taking the next big step in your career. Best
		wishes for your time at [new company] — they're lucky to have you.</p>
	<p style="font-family: verdana">
		Funcionário<br /> 1 - Atribuir veículos às reservas pendentes.<br />
		2 - Localizar um determinado veículo.<br /> 3 - Registar intervenção
		no registo cronológico de um determinado veículo.<br /> 4 - Procurar
		dados/fichas dos clientes indicando o seu nome num controle
		autocomplete.<br /> 5 – Avaliar a reputação de um cliente/condutor e
		atribuir, caso julgue conveniente, um desconto.<br /> 6 – Identificar
		o condutor de um veículo numa determinada data.<br />
	</p>

	<!-- Client Table with Search -->
	<h2>Clientes</h2>
	<input type="text" id="clientSearch" class="search-bar"
		placeholder="Procurar cliente pelo nome..." onkeyup="searchClient()" />
	<table id="clientTable" class="table">
		<thead>
			<tr>
				<th>NIF</th>
				<th>Nome</th>
				<th>Email</th>
				<th>Telefone</th>
				<th>Avaliação</th>
				<th>Editar</th>
			</tr>
		</thead>
		<tbody>
			<%
			ClienteDao clienteDao = new ClienteDao();
			List<Cliente> clientes = clienteDao.getAll();

			for (Cliente cliente : clientes) {
			%>
			<tr>
				<td><%=cliente.getClienteNIF()%></td>
				<td><%=cliente.getNome()%></td>
				<td><%=cliente.getEmail()%></td>
				<td><%=cliente.getContactoTel()%></td>
				<td><%=cliente.getAvaliacaoCliente()%></td>
				<td>
					<form method="get" action="Cliente_funcionario_form.jsp">
						<input type="hidden" name="clienteNIF"
							value="<%=cliente.getClienteNIF()%>" />
						<button type="submit">Editar</button>
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Driver Table -->
	<h2>Condutores</h2>
	<table id="driverTable" class="table">
		<thead>
			<tr>
				<th>NIF</th>
				<th>Avaliação</th>
				<th>Editar</th>
			</tr>
		</thead>
		<tbody>
			<%
			CondutorDao condutorDao = new CondutorDao(); 
			List<Condutor> condutores = condutorDao.getAll();
			for (Condutor condutor : condutores) {
			%>
			<tr>
				<td><%=condutor.getCondutorNIF()%></td>
				<td><%=condutor.getReputacao() != null ? condutor.getReputacao() : "N/A"%></td>
				<td>
					<form method="get" action="Condutor_funcionario_form.jsp">
						<input type="hidden" name="condutorNIF"
							value="<%=condutor.getCondutorNIF()%>" />
						<button type="submit">Editar</button>
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Button to Intervencao Form -->
	<div style="margin-top: 20px;">
		<button onclick="location.href='Intervencao_form.jsp'">
			Registrar Intervenção
		</button>
	</div>

	<br />
	<input title="Go back" type="button" value="Back"
		onClick="javascript:window.history.back()" />
</body>
</html>
