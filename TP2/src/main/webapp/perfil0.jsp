<!DOCTYPE html>
<html>
<%@page errorPage="error.jsp"%>
 <%@page import="usr.*"%> 
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%User x=Check.login(request, response,0);%> 
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

table {
	width: 100%;
	border-collapse: collapse;
	margin: 20px 0;
	font-size: 18px;
	text-align: left;
}

table, th, td {
	border: 1px solid #ddd;
}

th, td {
	padding: 12px;
	text-align: center;
}

th {
	background-color: #f2f2f2;
}

.but {
	background-color: Khaki;
	padding: 8px 20px;
	text-decoration: none;
	font-weight: bold;
	border-radius: 5px;
	cursor: pointer;
}
</style>
</head>
<body>
	<%-- <h2>(<%=x.getProfile()%>) <%=x.welcome()%></h2> --%>
	<br />
	<p>Best wishes for this exciting new job! We'll always remember you
		as a great boss.</p>
	<br>
	<p style="font-family: verdana">
		Administrador<br /> 1 – Criar/Atualizar dados/fichas dos clientes e
		dos respetivos condutores.<br /> 2 – Criar/Atualizar dados dos
		veículos incluído conteúdos multimédia.<br /> 3 – Exportar para um
		documento XML/JSON dados de um veículo incluindo o registo
		cronológico.<br /> 4 – Importar de um documento XML/JSON dados de um
		veículo incluindo o registo cronológico.<br /> <a href="view.jsp">Gestão
			de Perfis dos Utilizadores</a><br />
	</p>
	<br />
	<input title="Go back" type="button" value="Back"
		onClick="javascript:window.history.back()" />

	<%-- Adição das tabelas de Clientes e Condutores abaixo do parágrafo 1.1 --%>
	<hr>

	<%-- Código para carregar listas de Clientes e Condutores --%>
	<%@ page
		import="java.util.List, pojo.Cliente, pojo.Condutor, db.ClienteDao, db.CondutorDao"%>
	<%
	ClienteDao clienteDao = new ClienteDao();
	CondutorDao condutorDao = new CondutorDao();

	List<Cliente> clientes = clienteDao.getAll();
	List<Condutor> condutores = condutorDao.getAll();
	%>

	<!-- Tabela de Clientes -->
	<h2>Lista de Clientes</h2>
	<table>
		<tr>
			<th>NIF</th>
			<th>Moeda Preferida</th>
			<th>Língua e Cultura</th>
			<th>Telefone</th>
			<th>E-mail</th>
			<th>Nome</th>
			<th>Rua</th>
			<th>Código Postal</th>
			<th>Distrito</th>
			<th>Ação</th>
		</tr>
		<%
		for (Cliente cliente : clientes) {
		%>
		<tr>
			<td><%=cliente.getClienteNIF()%></td>
			<td><%=cliente.getMoedaPref()%></td>
			<td><%=cliente.getPrefLingCult()%></td>
			<td><%=cliente.getContactoTel()%></td>
			<td><%=cliente.getEmail()%></td>
			<td><%=cliente.getNome()%></td>
			<td><%=cliente.getRua()%></td>
			<td><%=cliente.getCodigoPostalP1()%>-<%=cliente.getCodigoPostalP2()%></td>
			<td><%=cliente.getNomeDistrito()%></td>
			<td>
				<form method="post" action="Cliente_form.jsp">
					<input type="hidden" name="clienteNIF"
						value="<%=cliente.getClienteNIF()%>" /> <a class="but"
						onclick="this.parentNode.submit();">Editar</a>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<!-- Tabela de Condutores -->
	<h2>Lista de Condutores</h2>
	<table>
		<tr>
			<th>NIF</th>
			<th>Data de Nascimento</th>
			<th>Data de Emissão</th>
			<th>Data de Validade</th>
			<th>Tipo de Habilitação</th>
			<th>Ação</th>
		</tr>
		<%
		for (Condutor condutor : condutores) {
		%>
		<tr>
			<td><%=condutor.getCondutorNIF()%></td>
			<td><%=condutor.getDataNascimento()%></td>
			<td><%=condutor.getDataEmissao() != null ? condutor.getDataEmissao() : "N/A"%></td>
			<td><%=condutor.getDataValidade() != null ? condutor.getDataValidade() : "N/A"%></td>
			<td><%=condutor.getTipoHab() != null ? condutor.getTipoHab() : "N/A"%></td>
			<td>
				<form method="post" action="Condutor_form.jsp">
					<input type="hidden" name="condutorNIF"
						value="<%=condutor.getCondutorNIF()%>" /> <a class="but"
						onclick="this.parentNode.submit();">Editar</a>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	
	<!-- Tabela de Veículos -->
<h2>Lista de Veículos</h2>
<table>
	<tr>
		<th>Matricula</th>
		<th>Marca</th>
		<th>Modelo</th>
		<th>Cor</th>
		<th>Potência</th>
		<th>Capacidade de Carga</th>
		<th>Num de Lugares</th>
		<th>Num de Portas</th>
		<th>Num de Eixos</th>
		<th>Combustível</th>
		<th>Valor por Dia (Útil)</th>
		<th>Valor por Dia (Não Útil)</th>
		<th>Data de Tarifa</th>
		<th>Ação</th>
	</tr>
	<%@ page
		import="java.util.List, pojo.Veiculo,db.VeiculoDao"%>
	<%
	// Código para obter os veículos do banco de dados
	VeiculoDao veiculoDao = new VeiculoDao();
	List<Veiculo> veiculos = veiculoDao.getAll();  // Obtém todos os veículos da base de dados
	
	// Loop para exibir cada veículo na tabela
	for (Veiculo veiculo : veiculos) {
	%>
	<tr>
		<td><%= veiculo.getMatricula() %></td>
		<td><%= veiculo.getNomeMarca() %></td>
		<td><%= veiculo.getNomeMod() %></td>
		<td><%= veiculo.getCor() %></td>
		<td><%= veiculo.getPotencia() %> CV</td>
		<td><%= veiculo.getCapacidadeCarga() %> kg</td>
		<td><%= veiculo.getNumLugares() %></td>
		<td><%= veiculo.getNumPortas() %></td>
		<td><%= veiculo.getNumEixos() %></td>
		<td><%= veiculo.getCombustivel() %></td>
		<td><%= veiculo.getValorDiaUtil() %> €</td>
		<td><%= veiculo.getValorDiaNaoUtil() %> €</td>
		<td><%= veiculo.getDataTarifa() != null ? veiculo.getDataTarifa() : "N/A" %></td>
		<td>
			<form method="post" action="Veiculo_form.jsp">
				<input type="hidden" name="matricula" value="<%= veiculo.getMatricula() %>" />
				<a class="but" onclick="this.parentNode.submit();">Editar</a>
			</form>
		</td>
	</tr>
	<%
	}
	%>
</table>
	

</body>
</html>
