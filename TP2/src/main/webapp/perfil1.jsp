<%@page import="usr.*"%>
<%@page
	import="db.ClienteDao, db.AluguerDao, db.DescontoDao, db.QualidadeServicoDao"%>
<%@page
	import="pojo.Cliente, pojo.Aluguer, pojo.Desconto, pojo.QualidadeServico"%>
<%@page import="java.util.List, java.math.BigDecimal"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page errorPage="error.jsp"%>
<%
User x = Check.login(request, response, 1);
%>

<%
try {
	if (x != null && x.getNif() != 0) {
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = clienteDao.getById(x.getNif());
		AluguerDao aluguerDao = new AluguerDao();
		List<Aluguer> alugueres = aluguerDao.getByClienteNIF(x.getNif());
		DescontoDao descontoDao = new DescontoDao();
		List<Desconto> descontos = descontoDao.getAll();
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="pt-PT, en-US">
<title>Perfil</title>
<style>
p {
	font-size: 1.2em;
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

input[type="button"], button {
	padding: 8px 12px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 5px;
}

input[type="button"]:hover, button:hover {
	background-color: #45a049;
}

select {
	padding: 5px;
}
</style>
</head>
<body>
	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%></h2>

	<p style="font-family: verdana">
		Cliente<br /> 1 - Reservar veículo, seleciona tipo ou modelo, o
		parque de levantamento e o período do aluguer.<br /> 2 - Consultar
		estado das reservas, avaliações, custo final previsto e efetivo.<br />
		3 - Consultar reputação e descontos.<br />
	</p>

	<%
	if (cliente != null) {
	%>
	<h2>Reputação</h2>
	<div>
		<%=cliente.getAvaliacaoCliente()%>/10,0
	</div>

	<!-- Tabela de Comentários e Avaliações -->
	<h3>Comentários e Avaliações</h3>
	<table class="table">
		<thead>
			<tr>
				<th>Avaliação</th>
				<th>Comentário</th>
			</tr>
		</thead>
		<tbody>
			<%
			QualidadeServicoDao qualidadeServicoDao = new QualidadeServicoDao();
			List<QualidadeServico> comentarios = qualidadeServicoDao.getByClienteNIF(cliente.getClienteNIF());

			if (comentarios != null && !comentarios.isEmpty()) {
				for (QualidadeServico comentario : comentarios) {
			%>
			<tr>
				<td><%=comentario.getAvaliacao()%>/10</td>
				<td><%=comentario.getComentario()%></td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="2">Nenhum comentário ou avaliação disponível.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<%
	} else {
	%>
	<div style="color: red;"># Cliente não encontrado *Não foi
		possível encontrar suas informações de cliente.*</div>
	<%
	}
	%>

	<table class="table">
		<thead>
			<tr>
				<th>NIF do Condutor</th>
				<th>Matrícula</th>
				<th>Data e Hora de Início</th>
				<th>Data e Hora de Fim</th>
				<th>Data e Hora de Entrega</th>
				<th>Custo Previsto (€)</th>
				<th>Custo Final (€)</th>
				<th>Qualidade do Serviço</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Aluguer aluguer : alugueres) {
				BigDecimal custoPrevisto = aluguerDao.calcularCustoPrevisto(aluguer.getDhInicio(), aluguer.getDhFim(),
				aluguer.getValorDiaUtil(), aluguer.getValorDiaNaoUtil());
			%>
			<tr>
				<td><%=aluguer.getCondutorNIF() != 0 ? aluguer.getCondutorNIF() : "N/A"%></td>
				<td><%=aluguer.getMatricula()%></td>
				<td><%=aluguer.getDhInicio()%></td>
				<td><%=aluguer.getDhFim()%></td>
				<td><%=aluguer.getDhEntrega() != null ? aluguer.getDhEntrega() : "N/A"%></td>
				<td><%=custoPrevisto%></td>
				<td><%=aluguer.getCustoFinal() != null ? aluguer.getCustoFinal() : "N/A"%></td>
				<td>
					<form method="post">
						<input type="hidden" name="dhInicio"
							value="<%=aluguer.getDhInicio()%>"> <input
							type="hidden" name="dhFim" value="<%=aluguer.getDhFim()%>">
						<input type="hidden" name="clienteNIF"
							value="<%=aluguer.getClienteNIF()%>"> <select
							name="qualidadeServicoAluguer" onchange="this.form.submit()">
							<option value="não vou voltar"
								<%="não vou voltar".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>não
								vou voltar</option>
							<option value="gostei"
								<%="gostei".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>gostei</option>
							<option value="adorei"
								<%="adorei".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>adorei</option>
						</select>
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>






	<h2>Descontos Disponíveis</h2>
	<table class="table">
		<thead>
			<tr>
				<th>Código</th>
				<th>Valor (%)</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Desconto desconto : descontos) {
			%>
			<tr>
				<td><%=desconto.getCodigo()%></td>
				<td><%=desconto.getValor() * 100%>%</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<br />
	<input type="button" value="Voltar"
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
