<%@page import="usr.*"%>
<%@page
	import="db.ClienteDao, db.AluguerDao, db.DescontoDao, db.QualidadeServicoDao, db.LugarVeiculoDao, db.VeiculoDao"%>
<%@page
	import="pojo.Cliente, pojo.Aluguer, pojo.Desconto, pojo.QualidadeServico, pojo.Veiculo"%>
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
		boolean duranteAluguer = aluguerDao.verificarClienteComAluguerPendente(cliente.getClienteNIF());
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
<script>
function updateModelos() {
    const marca = document.getElementById('marca').value;
    const modeloSelect = document.getElementById('modelo');
    
    // Limpa o dropdown de modelos
    modeloSelect.innerHTML = '<option value="">Carregando...</option>';
    
    if (!marca) {
        modeloSelect.innerHTML = '<option value="">Selecione um modelo</option>';
        return;
    }
    
    // Codifica a marca para a URL e faz a chamada ao servlet
    const encodedMarca = marca.replace(/[^a-zA-Z0-9]/g, function(c) {
        return encodeURIComponent(c);
    });
    
    fetch('GetModelosServlet?marca=' + encodedMarca)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na resposta da rede');
            }
            return response.json();
        })
        .then(data => {
            modeloSelect.innerHTML = '<option value="">Selecione o modelo</option>';
            data.forEach(modelo => {
                const option = document.createElement('option');
                option.value = modelo;
                option.textContent = modelo;
                modeloSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar modelos:', error);
            modeloSelect.innerHTML = '<option value="">Erro ao carregar modelos</option>';
        });
}

// Adicione um event listener para quando a página carregar
document.addEventListener('DOMContentLoaded', function() {
    const marca = document.getElementById('marca').value;
    if (marca) {
        updateModelos();
    }
});
</script>
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
		if (duranteAluguer) {
	%>
	<h2>Aluguer Pendente</h2>
	<p>Você possui um aluguel em andamento. É necessário finalizar o
		aluguel atual antes de buscar novos veículos.</p>
	<%
	} else{
	%>

	<h1>Buscar Veículos</h1>

	<%
	LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();
	VeiculoDao veiculoDao = new VeiculoDao();

	List<String> localidades = lugarVeiculoDao.getAllLocalidades();
	List<String> marcas = veiculoDao.getAllMarcas();

	String selectedLocalidade = request.getParameter("localidade");
	String selectedMarca = request.getParameter("marca");
	String selectedModelo = request.getParameter("modelo");
	List<Veiculo> veiculos = null;

	if (selectedLocalidade != null && selectedMarca != null && selectedModelo != null) {
		veiculos = lugarVeiculoDao.getVeiculosPorLocalidadeEModelo(selectedLocalidade, selectedModelo);
	}
	%>

	<form method="post" action="perfil1.jsp">
		<!-- Campo para Localidade -->
		<label for="localidade">Localidade:</label> <select name="localidade"
			id="localidade" required>
			<option value="">Selecione uma localidade</option>
			<%
			for (String localidade : localidades) {
			%>
			<option value="<%=localidade%>"
				<%=localidade.equals(selectedLocalidade) ? "selected" : ""%>>
				<%=localidade%>
			</option>
			<%
			}
			%>
		</select> <br> <br>

		<!-- Campo para Marca -->
		<label for="marca">Marca:</label> <select name="marca" id="marca"
			onchange="updateModelos()" required>
			<option value="">Selecione uma marca</option>
			<%
			for (String marca : marcas) {
			%>
			<option value="<%=marca%>"
				<%=marca.equals(selectedMarca) ? "selected" : ""%>>
				<%=marca%>
			</option>
			<%
			}
			%>
		</select> <br> <br>

		<!-- Campo para Modelo -->
		<label for="modelo">Modelo:</label> <select name="modelo" id="modelo"
			required>
			<option value="">Selecione um modelo</option>
			<%
			if (selectedMarca != null) {
				List<String> modelos = veiculoDao.getModelosByMarca(selectedMarca);
				for (String modelo : modelos) {
			%>
			<option value="<%=modelo%>"
				<%=modelo.equals(selectedModelo) ? "selected" : ""%>>
				<%=modelo%>
			</option>
			<%
			}
			}
			%>
		</select> <br> <br>

		<!-- Campo para Tempo de Aluguer -->
		<label for="tempoAluguer">Tempo de Aluguer:</label> <input
			type="datetime-local" id="tempoAluguer" name="tempoAluguer" required
			min="<%=java.time.LocalDateTime.now().plusHours(1)
		.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>"
			max="<%=java.time.LocalDateTime.now().plusWeeks(2)
		.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>">
		<br> <br>

		<button type="submit">Buscar Veículos</button>
	</form>


	<!-- Seção de resultados -->
	<%
	if (request.getParameter("localidade") != null && request.getParameter("marca") != null
			&& request.getParameter("modelo") != null) {

		String localidadeBusca = request.getParameter("localidade");
		String modeloBusca = request.getParameter("modelo");
		String selectedTempoAluguer = request.getParameter("tempoAluguer");

		if (selectedTempoAluguer == null || selectedTempoAluguer.isEmpty()) {
			selectedTempoAluguer = java.time.LocalDateTime.now().plusHours(1)
			.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
		}

		if (!localidadeBusca.isEmpty() && !modeloBusca.isEmpty()) {
			veiculos = lugarVeiculoDao.getVeiculosPorLocalidadeEModelo(localidadeBusca, modeloBusca);

			if (veiculos != null && !veiculos.isEmpty()) {
	%>
	<h2>Lista de Veículos Disponíveis</h2>
	<ul>
		<%
		for (Veiculo veiculo : veiculos) {
			System.out.println(veiculo);
			java.time.LocalDateTime dhInicio = java.time.LocalDateTime.now();
			java.time.LocalDateTime dhFim = java.time.LocalDateTime.parse(selectedTempoAluguer,
			java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

			BigDecimal valorDiaUtil = BigDecimal.valueOf(veiculo.getValorDiaUtil());
			BigDecimal valorDiaNaoUtil = BigDecimal.valueOf(veiculo.getValorDiaNaoUtil());
			System.out.println(valorDiaUtil);
			System.out.println(veiculo.getValorDiaNaoUtil());

			BigDecimal custoPrevisto = aluguerDao.calcularCustoPrevisto(dhInicio, dhFim, valorDiaUtil, valorDiaNaoUtil);
			System.out.println(custoPrevisto);
		%>
		<li>
			<form method="post" action="FazerAluguerServlet">
				<strong>Matrícula:</strong>
				<%=veiculo.getMatricula()%>, <strong>Modelo:</strong>
				<%=veiculo.getNomeMod()%>, <strong>Cor:</strong>
				<%=veiculo.getCor()%><br> <label
					for="desconto_<%=veiculo.getMatricula()%>">Código de
					Desconto (6 dígitos):</label> <input type="text"
					id="desconto_<%=veiculo.getMatricula()%>" name="desconto"
					pattern="\d{6}" maxlength="6" placeholder="Opcional"><br>

				<strong>Custo Previsto:</strong>
				<%=Cliente.conversao(custoPrevisto, cliente.getMoedaPref()).setScale(2, BigDecimal.ROUND_CEILING)%>€<br>

				<!-- Campos ocultos para enviar os dados necessários -->
				<input type="hidden" name="matricula"
					value="<%=veiculo.getMatricula()%>"> <input type="hidden"
					name="dhInicio"
					value="<%=dhInicio.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>">
				<input type="hidden" name="dhFim"
					value="<%=dhFim.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))%>">
				<input type="hidden" name="localidade"
					value="<%=selectedLocalidade%>"> <input type="hidden"
					name="clienteNIF" value="<%=cliente.getClienteNIF()%>"> <input
					type="hidden" name="condutorNIF"
					value="<%=cliente.getCondutorNIF()%>"> <input type="hidden"
					name="moedaPref" value="<%=cliente.getMoedaPref()%>">

				<!-- Botão para fazer aluguel do veículo -->
				<button type="submit">Fazer Aluguer</button>
			</form>
		</li>
		<%
		}
		%>
	</ul>

	<%
	} else {
	%>
	<p>Nenhum veículo encontrado para os critérios selecionados.</p>
	<%
	}
	}
	}
	}
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
				<td><%=Cliente.conversao(custoPrevisto, aluguer.getMoedaPref()).setScale(2, BigDecimal.ROUND_CEILING)%></td>
				<td><%=aluguer.getCustoFinal() != null ? Cliente.conversao(aluguer.getCustoFinal(), aluguer.getMoedaPref()).setScale(2, BigDecimal.ROUND_CEILING)  : "N/A"%></td>
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






	<h2>Descontos</h2>
	<h4>Aproveita estes descontos, mas alguns já podem estar invalidos</h4>
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