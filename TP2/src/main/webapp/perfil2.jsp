<!DOCTYPE html>
<%@page import="pojo.LugarVeiculo"%>
<html>
<%@page errorPage="error.jsp"%>
<%@page import="usr.*,java.util.List"%>
<%@page
	import="db.CondutorDao, db.AluguerDao, db.DescontoDao, db.QualidadeServicoDao, db.LugarVeiculoDao, db.VeiculoDao"%>
<%@page
	import="pojo.Condutor, pojo.Aluguer, pojo.Desconto, pojo.QualidadeServico, pojo.Veiculo"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%
User x = Check.login(request, response, 2);
%>
<%
try {
	if (x != null && x.getNif() != 0) {
		CondutorDao condutorDao = new CondutorDao();
		Condutor condutor = condutorDao.getById(x.getNif());
		AluguerDao aluguerDao = new AluguerDao();
		Veiculo veiculopendente = aluguerDao.procurarVeiculoCondutorPendente(condutor.getCondutorNIF());
		LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();
		LugarVeiculo lugarVeiculoPendente = veiculopendente != null
		? lugarVeiculoDao.getByMatricula(veiculopendente.getMatricula())
		: null;
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
<title>Condutor</title>
<link rel="stylesheet" type="text/css" href="css/perfis.css">
</head>
<body>
	<h2>
		(<%=x.getProfile()%>)
		<%=x.welcome()%>
	</h2>
	<br />
	<p>If you want your colleague to leave in stitches, we have a few
		funny congratulations messages you can use. (We don't recommend these
		messages for your boss, though — keep it 100% professional there.)</p>
	<p style="font-family: verdana">
		Condutor<br /> 1 - Levantar veículo, visualiza dados do veículo
		atribuído à reserva e respetivo lugar de estacionamento.<br /> 2 -
		Entregar veículo, procura lugares de estacionamento vagos no parque
		mais perto do local onde se encontra e indica qual foi o lugar onde
		estacionou. Opcionalmente, realiza a avaliação do aluguer/serviço e
		faz comentário.<br />
	</p>
	<%
	if (veiculopendente != null) {
	%>
	<h1>Levantar veículo</h1>
	<%
	if (lugarVeiculoPendente == null) {
	%>
	<h3>Veiculo já levantado</h3>
	<%
	} else {
	%>
	<h3>
		<%=veiculopendente.toString()%>, localizado em
		<%=lugarVeiculoPendente.getLocalidade()%>, no piso
		<%=lugarVeiculoPendente.getPiso()%>, fila
		<%=lugarVeiculoPendente.getFila()%>, posição
		<%=lugarVeiculoPendente.getPosFila()%>
		<form method="post" action="LevantarVeiculoServlet"
			style="display: inline;">
			<input type="hidden" name="matricula"
				value="<%=veiculopendente.getMatricula()%>">
			<button type="submit" class="small-button">Levantar Veículo</button>
		</form>
	</h3>
	<br />
	<%
	}
	%>
	<h1>Entregar veículo</h1>
	<%
	if (lugarVeiculoPendente == null) {
		List<LugarVeiculo> lugaresVazios = lugarVeiculoDao.getLugaresVazios();
	%>
	<h2>Selecione um lugar para estacionar o veículo</h2>
	<ul>
		<%
		for (LugarVeiculo lugar : lugaresVazios) {
		%>
		<li><strong>Localidade:</strong> <%=lugar.getLocalidade()%>, <strong>Piso:</strong>
			<%=lugar.getPiso()%>, <strong>Fila:</strong> <%=lugar.getFila()%>, <strong>Posição:</strong>
			<%=lugar.getPosFila()%>
			<form method="post" action="EntregarVeiculoServlet"
				style="display: inline;">
				<input type="hidden" name="matricula"
					value="<%=veiculopendente.getMatricula()%>"> <input
					type="hidden" name="localidade" value="<%=lugar.getLocalidade()%>">
				<input type="hidden" name="piso" value="<%=lugar.getPiso()%>">
				<input type="hidden" name="fila" value="<%=lugar.getFila()%>">
				<input type="hidden" name="posFila" value="<%=lugar.getPosFila()%>">
				<input type="hidden" name="condutorNIF"
					value="<%=condutor.getCondutorNIF()%>">
				<button type="submit" class="small-button">Finalizar Aluguer</button>
			</form></li>
		<%
		}
		%>
	</ul>
	<%
	} else {
	%>
	<h3>Precisa levantar o veículo</h3>
	<%
	}
	%>
	<%
	} else {
	%>
	<h1>Precisa de estar num aluguer pendente</h1>
	<%
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
