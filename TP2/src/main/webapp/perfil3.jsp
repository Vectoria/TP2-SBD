<!DOCTYPE html>
<html>
<%@page import="usr.*"%>
<%@page errorPage="error.jsp"%>
<%@ page
	import="pojo.Cliente, pojo.Condutor, db.ClienteDao, db.CondutorDao, java.util.*"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="db.AluguerDao"%>
<%@ page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
User x = Check.login(request, response, 3);
%>
<%
try {
	if (x != null) {
%>
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

	<h1>Localizar Veículo</h1>
	<form method="post" action="perfil3.jsp">
		<label for="matriculaLocalizar">Matrícula do Veículo:</label> <input
			type="text" id="matriculaLocalizar" name="matriculaLocalizar"
			required maxlength="6" pattern="[A-Za-z0-9]{1,6}" />
		<button type="submit">Localizar</button>
	</form>

	<%-- Lógica para localizar o veículo --%>
	<%
	String matriculaLocalizar = request.getParameter("matriculaLocalizar");
	if (matriculaLocalizar != null) {
		try {
			db.LugarVeiculoDao lugarVeiculoDao = new db.LugarVeiculoDao();
			pojo.LugarVeiculo lugar = lugarVeiculoDao.getByMatricula(matriculaLocalizar);

			if (lugar != null) {
	%>
	<div class="message success">
		O veículo com matrícula
		<%=matriculaLocalizar%>
		está localizado no lugar: <br /> Localidade:
		<%=lugar.getLocalidade()%>, Piso:
		<%=lugar.getPiso()%>, Fila:
		<%=lugar.getFila()%>, Posição na Fila:
		<%=lugar.getPosFila()%>
	</div>
	<%
	} else {
	%>
	<div class="message info">
		O veículo com matrícula
		<%=matriculaLocalizar%>
		está alugado no momento.
	</div>
	<%
	}
	} catch (Exception e) {
	%>
	<div class="message error">Erro ao processar a solicitação.
		Verifique os dados e tente novamente.</div>
	<%
	e.printStackTrace();
	}
	}
	%>
	
	
	<!-- Button to Intervencao Form -->
	<div style="margin-top: 20px;">
		<button onclick="location.href='Intervencao_form.jsp'">
			Registrar Intervenção</button>
	</div>

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

	<h1>Gerar Desconto Aleatório</h1>
	<form method="post" action="GerarDescontoServlet">
		<button type="submit">Gerar Desconto</button>
	</form>

	<%-- Mensagem de sucesso ou erro --%>
	<div class="message">
		<%
		String message = request.getParameter("message");
		String error = request.getParameter("error");
		if (message != null) {
		%>
		<p style="color: green;"><%=message%></p>
		<%
		} else if (error != null) {
		%>
		<p style="color: red;"><%=error%></p>
		<%
		}
		%>
	</div>


	<h1>Identificar Condutor de um Veículo</h1>
	<form method="post" action="perfil3.jsp">
		<label for="matricula">Matrícula do Veículo:</label> <input
			type="text" id="matricula" name="matricula" required maxlength="6"
			pattern="[A-Za-z0-9]{1,6}" /> <label for="date">Data e Hora:</label>
		<input type="datetime-local" id="date" name="date" required />

		<button type="submit">Identificar Condutor</button>
	</form>

	<%-- Processamento da Lógica no JSP --%>
	<%
	String matricula = request.getParameter("matricula");
	String dateStr = request.getParameter("date");
	if (matricula != null && dateStr != null) {
		try {
			LocalDateTime date = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			AluguerDao aluguerDao = new AluguerDao();
			Integer condutorNIF = aluguerDao.findConductorByVehicleAndDate(matricula, date);

			if (condutorNIF != null) {
	%>
	<div class="message success">
		Condutor identificado:
		<%=condutorNIF%>
	</div>
	<%
	} else {
	%>
	<div class="message error">O veículo não esteve alugado na
		data/hora especificada.</div>
	<%
	}
	} catch (Exception e) {
	%>
	<div class="message error">Erro ao processar a solicitação.
		Verifique os dados e tente novamente.</div>
	<%
	}
	}
	%>

	<h1>Introduzir Veículo Novo da Empresa</h1>
	<%
	try {
		db.VeiculoDao veiculoDao = new db.VeiculoDao();
		List<pojo.Veiculo> veiculos = veiculoDao.getAll();

		List<pojo.Veiculo> veiculosNovos = new ArrayList<>();
		for (pojo.Veiculo veiculo : veiculos) {
			if (veiculoDao.veiculoNovo(veiculo.getMatricula()) != null) {
		veiculosNovos.add(veiculo);
			}
		}

		if (!veiculosNovos.isEmpty()) {
	%>
	<table class="table">
		<thead>
			<tr>
				<th>Matrícula</th>
				<th>Modelo</th>
				<th>Marca</th>
				<th>Cor</th>
				<th>Localidade</th>
				<th>Piso</th>
				<th>Fila</th>
				<th>Posição</th>
				<th>Ação</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (pojo.Veiculo veiculo : veiculosNovos) {
			%>
			<tr>
				<td><%=veiculo.getMatricula()%></td>
				<td><%=veiculo.getNomeMod()%></td>
				<td><%=veiculo.getNomeMarca()%></td>
				<td><%=veiculo.getCor()%></td>
				<form method="post" action="IntroduzirVeiculoServlet">
					<td><input type="text" name="localidade" required
						placeholder="Localidade" /></td>
					<td><input type="number" name="piso" required
						placeholder="Piso" /></td>
					<td><input type="text" name="fila" required maxlength="2"
						pattern="[a-zA-Z-]+" placeholder="Fila" /></td>
					<td><input type="number" name="posFila" required min="1"
						placeholder="Posição na Fila" /></td>
					<td><input type="hidden" name="matricula"
						value="<%=veiculo.getMatricula()%>" />
						<button type="submit">Introduzir</button></td>
				</form>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<%
	} else {
	%>
	<p>Não há veículos novos disponíveis para introdução.</p>
	<%
	}
	} catch (Exception e) {
	%>
	<p style="color: red;">Erro ao carregar veículos novos. Tente
		novamente mais tarde.</p>
	<%
	e.printStackTrace();
	}
	%>



	<br />
	<input title="Go back" type="button" value="Back"
		onClick="javascript:window.history.back()" />
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