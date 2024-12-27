<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp"%>
<%-- <%@page import="usr.*"%> --%>
<%@ page import="db.Gerente"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%-- <%User x=Check.login(request, response, 4);%> --%>
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
<title>Gerente - Relatórios</title>
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
	margin-top: 20px;
}

.table th, .table td {
	border: 1px solid #ccc;
	padding: 10px;
	text-align: left;
}

.table th {
	background-color: #f2f2f2;
}

input[type="text"], button {
	padding: 8px;
	margin: 10px 0;
}

button {
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
</head>
<body>
	<%-- <h2>(<%=x.getProfile()%>) <%=x.welcome()%></h2> --%>
	<br />
	<p>Congratulations on taking the next big step in your career. Best
		wishes for your time at [new company] — they're lucky to have you.</p>
	<p style="font-family: verdana">
		Gerente<br /> 1 – Apresentar histórico de um determinado veículo,
		avaliações e intervenções existentes no registo cronológico.<br /> 2
		– Exibir o ranking das 3 marcas de veículos que geraram menor lucro.<br />
		3 – Exibir o ranking dos 5 modelos dos veículos com melhor avaliação
		na semana passada.<br /> 4 – Exibir o ranking dos 10 veículos que
		percorreram menos quilómetros no último trimestre.<br /> 5 – Exibir o
		ranking dos 100 clientes ordenados por reputação e filtrados por
		freguesia de morada.<br />
	</p>
	<br />

	<%
	Gerente gerente = new Gerente();
	%>

	<!-- Ranking das 3 marcas de veículos menos lucrativas -->
	<h2>2 - Marcas com Menor Lucro</h2>
	<table class="table">
		<thead>
			<tr>
				<th>Marca</th>
				<th>Lucro Total (€)</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Map<String, Object>> menosLucrativas = gerente.getMenosLucrativas();
			for (Map<String, Object> linha : menosLucrativas) {
			%>
			<tr>
				<td><%=linha.get("nomeMarca")%></td>
				<td><%=linha.get("lucro_total")%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Ranking dos 5 modelos mais bem avaliados na semana passada -->
	<h2>3 - Modelos Mais Bem Avaliados na Semana Passada</h2>
	<table class="table">
		<thead>
			<tr>
				<th>Modelo</th>
				<th>Marca</th>
				<th>Avaliação Média</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Map<String, Object>> maisBemAvaliados = gerente.getModelosMaisBemAvaliadosSemanaPassada();
			for (Map<String, Object> linha : maisBemAvaliados) {
			%>
			<tr>
				<td><%=linha.get("nomeMod")%></td>
				<td><%=linha.get("nomeMarca")%></td>
				<td><%=linha.get("avaliacaoModelo")%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Ranking dos 10 veículos com menor quilometragem no último trimestre -->
	<h2>4 - Veículos com Menor Quilometragem no Último Trimestre</h2>
	<table class="table">
		<thead>
			<tr>
				<th>Matrícula</th>
				<th>Total Quilómetros</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Map<String, Object>> menorQuilometragem = gerente.getVeiculosMenorQuilometragemUltimoTrimestre();
			for (Map<String, Object> linha : menorQuilometragem) {
			%>
			<tr>
				<td><%=linha.get("matricula")%></td>
				<td><%=linha.get("total_km")%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<!-- Filtro por Freguesia -->
	<h2>5 - Filtro por Freguesia</h2>
	<form method="post">
		<label for="freguesia">Digite o nome da freguesia:</label> <input
			type="text" id="freguesia" name="freguesia" required>
		<button type="submit">Pesquisar</button>
	</form>

	<%
	// Captura o nome da freguesia inserido pelo usuário
	String freguesia = request.getParameter("freguesia");
	if (freguesia != null && !freguesia.isEmpty()) {
		try {
			// Obtém os clientes filtrados pela freguesia
			List<Map<String, Object>> clientes = gerente.getClientesPorFreguesia(freguesia);

			if (!clientes.isEmpty()) {
	%>
	<table class="table">
		<thead>
			<tr>
				<th>NIF</th>
				<th>Nome</th>
				<th>Avaliação</th>
				<th>Freguesia</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Map<String, Object> cliente : clientes) {
			%>
			<tr>
				<td><%=cliente.get("id_cliente")%></td>
				<td><%=cliente.get("nome")%></td>
				<td><%=cliente.get("avaliacaoCliente")%></td>
				<td><%=cliente.get("nomeFreguesia")%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<%
	} else {
	%>
	<p>
		Nenhum cliente encontrado para a freguesia "<%=freguesia%>".
	</p>
	<%
	}
	} catch (Exception e) {
	e.printStackTrace();
	%>
	<p>Erro ao buscar dados. Por favor, tente novamente mais tarde.</p>
	<%
	}
	}
	%>

	<br />
	<input title="Go back" type="button" value="Back"
		onClick="javascript:window.history.back()" />
</body>
</html>
