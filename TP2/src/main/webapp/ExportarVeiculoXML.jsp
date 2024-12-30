<%@ page
	import="java.util.List, db.VeiculoDao, db.IntervencaoDao, pojo.Veiculo, pojo.Intervencao"%>
<%
String matricula = request.getParameter("matricula");

if (matricula != null && !matricula.isEmpty()) {
	VeiculoDao veiculoDao = new VeiculoDao();
	IntervencaoDao intervencaoDao = new IntervencaoDao();

	Veiculo veiculo = veiculoDao.getById(matricula);
	List<Intervencao> intervencoes = intervencaoDao.getByMatricula(matricula);

	if (veiculo != null) {
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename=veiculo_" + matricula + ".xml");

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<veiculo>");
		out.println("<matricula>" + veiculo.getMatricula() + "</matricula>");
		out.println("<marca>" + veiculo.getNomeMarca() + "</marca>");
		out.println("<modelo>" + veiculo.getNomeMod() + "</modelo>");
		out.println("<cor>" + veiculo.getCor() + "</cor>");
		out.println("<potencia>" + veiculo.getPotencia() + "</potencia>");
		out.println("<capacidadeCarga>" + veiculo.getCapacidadeCarga() + "</capacidadeCarga>");
		out.println("<numLugares>" + veiculo.getNumLugares() + "</numLugares>");
		out.println("<numPortas>" + veiculo.getNumPortas() + "</numPortas>");
		out.println("<numEixos>" + veiculo.getNumEixos() + "</numEixos>");
		out.println("<combustivel>" + veiculo.getCombustivel() + "</combustivel>");
		out.println("<valorDiaUtil>" + veiculo.getValorDiaUtil() + "</valorDiaUtil>");
		out.println("<valorDiaNaoUtil>" + veiculo.getValorDiaNaoUtil() + "</valorDiaNaoUtil>");
		out.println(
		"<dataTarifa>" + (veiculo.getDataTarifa() != null ? veiculo.getDataTarifa() : "N/A") + "</dataTarifa>");

		out.println("<intervencoes>");
		for (Intervencao intervencao : intervencoes) {
	out.println("<intervencao>");
	out.println("<numKm>" + intervencao.getNumKM() + "</numKm>");
	out.println("<dhRegisto>" + intervencao.getDhRegisto() + "</dhRegisto>");
	out.println("<tipoInt>" + intervencao.getTipoInt() + "</tipoInt>");
	out.println("<custoInt>" + intervencao.getCustoInt() + "</custoInt>");
	out.println("</intervencao>");
		}
		out.println("</intervencoes>");

		out.println("</veiculo>");
	} else {
		out.println("<error>Veículo não encontrado</error>");
	}
} else {
	out.println("<error>Matrícula não fornecida</error>");
}
%>
