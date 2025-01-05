package srv;

import db.VeiculoDao;
import db.IntervencaoDao;
import pojo.Veiculo;
import pojo.Intervencao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ExportarVeiculoXMLServlet")
public class ExportarVeiculoXMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String matricula = request.getParameter("matricula");

		if (matricula != null && !matricula.isEmpty()) {
			VeiculoDao veiculoDao = new VeiculoDao();
			IntervencaoDao intervencaoDao = new IntervencaoDao();

			Veiculo veiculo = veiculoDao.getById(matricula);
			List<Intervencao> intervencoes = intervencaoDao.getByMatricula(matricula);

			if (veiculo != null) {
				response.setContentType("application/xml");
				response.setHeader("Content-Disposition", "attachment; filename=veiculo_" + matricula + ".xml");

				try {
					response.getWriter().println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					response.getWriter().println("<veiculo>");
					response.getWriter().println("<matricula>" + veiculo.getMatricula() + "</matricula>");
					response.getWriter().println("<marca>" + veiculo.getNomeMarca() + "</marca>");
					response.getWriter().println("<modelo>" + veiculo.getNomeMod() + "</modelo>");
					response.getWriter().println("<cor>" + veiculo.getCor() + "</cor>");
					response.getWriter().println("<potencia>" + veiculo.getPotencia() + "</potencia>");
					response.getWriter()
							.println("<capacidadeCarga>" + veiculo.getCapacidadeCarga() + "</capacidadeCarga>");
					response.getWriter().println("<numLugares>" + veiculo.getNumLugares() + "</numLugares>");
					response.getWriter().println("<numPortas>" + veiculo.getNumPortas() + "</numPortas>");
					response.getWriter().println("<numEixos>" + veiculo.getNumEixos() + "</numEixos>");
					response.getWriter().println("<combustivel>" + veiculo.getCombustivel() + "</combustivel>");
					response.getWriter().println("<valorDiaUtil>" + veiculo.getValorDiaUtil() + "</valorDiaUtil>");
					response.getWriter()
							.println("<valorDiaNaoUtil>" + veiculo.getValorDiaNaoUtil() + "</valorDiaNaoUtil>");
					response.getWriter().println("<dataTarifa>"
							+ (veiculo.getDataTarifa() != null ? veiculo.getDataTarifa() : "N/A") + "</dataTarifa>");

					response.getWriter().println("<intervencoes>");
					for (Intervencao intervencao : intervencoes) {
						response.getWriter().println("<intervencao>");
						response.getWriter().println("<numKm>" + intervencao.getNumKM() + "</numKm>");
						response.getWriter().println("<dhRegisto>" + intervencao.getDhRegisto() + "</dhRegisto>");
						response.getWriter().println("<tipoInt>" + intervencao.getTipoInt() + "</tipoInt>");
						response.getWriter().println("<custoInt>" + intervencao.getCustoInt() + "</custoInt>");
						response.getWriter().println("</intervencao>");
					}
					response.getWriter().println("</intervencoes>");

					response.getWriter().println("</veiculo>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Veículo não encontrado");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Matrícula não fornecida");
		}
	}
}
