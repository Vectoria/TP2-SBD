<%@page errorPage="error.jsp"%>
<%@page import="usr.*"%>
<%@page import="java.util.List"%>
<%@page
	import="java.util.List, pojo.Cliente, pojo.Condutor, db.ClienteDao, db.CondutorDao, pojo.Veiculo, db.VeiculoDao"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
User x = Check.login(request, response, 0);
ClienteDao clienteDao = new ClienteDao();
CondutorDao condutorDao = new CondutorDao();
VeiculoDao veiculoDao = new VeiculoDao();

List<Cliente> clientes = clienteDao.getAll();
List<Condutor> condutores = condutorDao.getAll();
List<Veiculo> veiculos = veiculoDao.getAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Perfil Administrativo</title>
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
	text-align: center;
}

.table th {
	background-color: #f2f2f2;
}

input[type="button"], button, a.button {
	padding: 8px 12px;
	background-color: Khaki;
	color: black;
	text-decoration: none;
	border-radius: 5px;
	cursor: pointer;
	border: none;
	font-weight: bold;
}

input[type="button"]:hover, button:hover, a.button:hover {
	background-color: #FFD700;
}
</style>
</head>
<body>
	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%></h2>
	<p>
		Administrador<br> 1 – Criar/Atualizar dados/fichas dos clientes e
		dos respetivos condutores.<br> 2 – Criar/Atualizar dados dos
		veículos incluído conteúdos multimédia.<br> 3 – Exportar para um
		documento XML/JSON dados de um veículo incluindo o registo
		cronológico.<br> 4 – Importar de um documento XML/JSON dados de
		um veículo incluindo o registo cronológico.<br>
	</p>

	<!-- Tabela de Clientes -->
	<h2>Lista de Clientes</h2>
	<table class="table">
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
						value="<%=cliente.getClienteNIF()%>" />
					<button type="submit">Editar</button>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<a href="Cliente_form.jsp" class="button">Criar Cliente</a>

	<!-- Tabela de Condutores -->
	<h2>Lista de Condutores</h2>
	<table class="table">
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
						value="<%=condutor.getCondutorNIF()%>" />
					<button type="submit">Editar</button>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<a href="Condutor_form.jsp" class="button">Criar Condutor</a>

	<!-- Tabela de Veículos -->
	<h2>Veículos</h2>
	<table class="table">
		<tr>
			<th>Matrícula</th>
			<th>Marca</th>
			<th>Modelo</th>
			<th>Cor</th>
			<th>Editar</th>
			<th>Exportar</th>
		</tr>
		<%
		for (Veiculo veiculo : veiculos) {
		%>
		<tr>
			<td><%=veiculo.getMatricula()%></td>
			<td><%=veiculo.getNomeMarca()%></td>
			<td><%=veiculo.getNomeMod()%></td>
			<td><%=veiculo.getCor()%></td>
			<td>
				<form method="post" action="Veiculo_form.jsp">
					<input type="hidden" name="matricula"
						value="<%=veiculo.getMatricula()%>" />
					<button type="submit">Editar</button>
				</form>
			</td>
			<td>
				<form method="get" action="ExportarVeiculoXML.jsp">
					<input type="hidden" name="matricula"
						value="<%=veiculo.getMatricula()%>">
					<button type="submit">Exportar XML</button>
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<div style="margin: 20px 0;">
		<a href="ImportarVeiculoXML.jsp" class="button">Importar Veículo
			XML</a> <a href="Veiculo_form.jsp" class="button">Criar Veículo</a>
	</div>

</body>
</html>
