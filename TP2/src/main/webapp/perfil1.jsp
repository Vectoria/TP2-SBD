<%@page import="usr.*"%>
<%@page
	import="db.ClienteDao, db.AluguerDao, db.DescontoDao, db.QualidadeServicoDao, db.LugarVeiculoDao, db.VeiculoDao"%>
<%@page
	import="pojo.Cliente, pojo.Aluguer, pojo.Desconto, pojo.QualidadeServico, pojo.LugarVeiculo, pojo.Veiculo"%>
<%@page import="java.util.List, java.math.BigDecimal"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<script>
function updateModelos() {
    const marca = document.getElementById('marca').value;
    const modeloSelect = document.getElementById('modelo');
    modeloSelect.innerHTML = '<option value="">Carregando...</option>';
    fetch(`GetModelosServlet?marca=${marca}`)
        .then(response => response.json())
        .then(data => {
            modeloSelect.innerHTML = '<option value="">Selecione o modelo</option>';
            data.forEach(modelo => {
                const option = document.createElement('option');
                option.value = modelo;
                option.textContent = modelo;
                modeloSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar modelos:', error));
}
</script>
</head>
<body>
	<%
	try {
		User x = Check.login(request, response, 1);

		if (x != null && x.getNif() != 0) {
			ClienteDao clienteDao = new ClienteDao();
			Cliente cliente = clienteDao.getById(x.getNif());

			if (cliente != null) {
		AluguerDao aluguerDao = new AluguerDao();
		List<Aluguer> alugueres = aluguerDao.getByClienteNIF(x.getNif());
		DescontoDao descontoDao = new DescontoDao();
		List<Desconto> descontos = descontoDao.getAll();

		// DAO para acesso a Veículos e Lugares
		LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();
		VeiculoDao veiculoDao = new VeiculoDao();

		// Localidades do LugarVeiculoDao
		List<String> localidades = lugarVeiculoDao.getAllLocalidades();
		// Marcas e Modelos do VeiculoDao
		List<String> marcas = veiculoDao.getAllMarcas();

		// Parâmetros da requisição
		String selectedLocalidade = request.getParameter("localidade");
		String selectedMarca = request.getParameter("marca");
		String selectedModelo = request.getParameter("modelo");
		String tempoAluguer = request.getParameter("tempoAluguer");

		// Modelos dependem da Marca selecionada
		List<String> modelos = selectedMarca != null ? veiculoDao.getModelosByMarca(selectedMarca) : null;

		// Veículos disponíveis combinando LugarVeiculo e Veiculo
		List<LugarVeiculo> veiculosDisponiveis = null;

		if (selectedLocalidade != null && selectedMarca != null && selectedModelo != null && tempoAluguer != null) {
			// Obter veículos disponíveis pela localidade e modelo
			veiculosDisponiveis = lugarVeiculoDao.getVeiculosDisponiveis(selectedLocalidade, selectedModelo)
					.stream().filter(lv -> {
						Veiculo veiculo = veiculoDao.getByMatricula(lv.getMatricula());
						return veiculo != null && selectedMarca.equals(veiculo.getNomeMarca());
					}).toList();
		}
	%>
	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%></h2>
	<p style="font-family: verdana">
		Cliente<br /> 1 - Reservar veículo, seleciona tipo ou modelo, o
		parque de levantamento e o período do aluguer.<br /> 2 - Consultar
		estado das reservas, avaliações, custo final previsto e efetivo.<br />
		3 - Consultar reputação e descontos.<br />
	</p>

	<!-- Formulário para Alugar Veículo -->
	<h2>Alugar Veículo</h2>
	<form method="get">
		<label for="localidade">Parque de Estacionamento:</label> <select
			name="localidade" id="localidade" required>
			<option value="">Selecione uma localidade</option>
			<%
			for (String localidade : localidades) {
			%>
			<option value="<%=localidade%>"
				<%=selectedLocalidade != null && selectedLocalidade.equals(localidade) ? "selected" : ""%>><%=localidade%></option>
			<%
			}
			%>
		</select><br> <br> <label for="marca">Marca:</label> <select
			name="marca" id="marca" required onchange="updateModelos()">
			<option value="">Selecione uma marca</option>
			<%
			for (String marca : marcas) {
			%>
			<option value="<%=marca%>"
				<%=selectedMarca != null && selectedMarca.equals(marca) ? "selected" : ""%>><%=marca%></option>
			<%
			}
			%>
		</select><br> <br> <label for="modelo">Modelo:</label> <select
			name="modelo" id="modelo" required>
			<option value="">Selecione o modelo</option>
			<%
			if (modelos != null) {
				for (String modelo : modelos) {
			%>
			<option value="<%=modelo%>"
				<%=selectedModelo != null && selectedModelo.equals(modelo) ? "selected" : ""%>><%=modelo%></option>
			<%
			}
			}
			%>
		</select><br> <br> <label for="tempoAluguer">Tempo de
			Aluguer:</label> <input type="text" name="tempoAluguer" id="tempoAluguer"
			placeholder="Ex: 5 horas, 2 semanas" required
			value="<%=tempoAluguer != null ? tempoAluguer : ""%>" /><br> <br>

		<button type="submit">Buscar Veículos</button>
	</form>

	<%
	if (veiculosDisponiveis != null) {
	%>
	<h3>Veículos Disponíveis</h3>
	<table>
		<thead>
			<tr>
				<th>Localidade</th>
				<th>Piso</th>
				<th>Fila</th>
				<th>Posição</th>
				<th>Matrícula</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (LugarVeiculo lugar : veiculosDisponiveis) {
			%>
			<tr>
				<td><%=lugar.getLocalidade()%></td>
				<td><%=lugar.getPiso()%></td>
				<td><%=lugar.getFila()%></td>
				<td><%=lugar.getPosFila()%></td>
				<td><%=lugar.getMatricula()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<%
	}
	%>

	<h2>Reputação</h2>
	<div><%=cliente.getAvaliacaoCliente()%>/10,0
	</div>

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

	<h2>Histórico de Alugueres</h2>
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
					<form method="post" action="AtualizarQualidadeServicoServlet">
						<input type="hidden" name="dhInicio"
							value="<%=aluguer.getDhInicio()%>"> <input type="hidden"
							name="dhFim" value="<%=aluguer.getDhFim()%>"> <input
							type="hidden" name="clienteNIF"
							value="<%=aluguer.getClienteNIF()%>"> <select
							name="qualidadeServicoAluguer">
							<option value="não vou voltar"
								<%="não vou voltar".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>não
								vou voltar</option>
							<option value="gostei"
								<%="gostei".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>gostei</option>
							<option value="adorei"
								<%="adorei".equals(aluguer.getQualidadeServicoAluguer()) ? "selected" : ""%>>adorei</option>
						</select>
						<button type="submit">Atualizar</button>
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
	<%
	} else {
	%>
	<div style="color: red;"># Cliente não encontrado *Não foi
		possível encontrar suas informações de cliente.*</div>
	<%
	}
	} else {
	%>
	<div style="color: red;"># NIF inválido ou não logado.</div>
	<%
	}
	} catch (Exception e) {
	e.printStackTrace();
	%>
	<div style="color: red;">Ocorreu um erro ao processar a
		solicitação.</div>
	<%
	}
	%>
</body>
</html>