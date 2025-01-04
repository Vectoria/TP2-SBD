package srv;

import db.LugarVeiculoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/LevantarVeiculoServlet")
public class LevantarVeiculoServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String matricula = request.getParameter("matricula");

		if (matricula != null && !matricula.isEmpty()) {
			try {
				LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();
				boolean sucesso = lugarVeiculoDao.removerMatriculaPorLugar(matricula);

				if (sucesso) {
					response.sendRedirect("perfil2.jsp");
				} else {
					response.getWriter().println("<h1>Erro: Não foi possível levantar o veículo.</h1>");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println("<h1>Erro ao processar o levantamento: " + e.getMessage() + "</h1>");
			}
		} else {
			response.getWriter().println("<h1>Erro: Matrícula inválida.</h1>");
		}
	}
}
