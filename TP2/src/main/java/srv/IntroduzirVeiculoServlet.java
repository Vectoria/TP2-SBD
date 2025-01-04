package srv;

import db.LugarVeiculoDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/IntroduzirVeiculoServlet")
public class IntroduzirVeiculoServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String matricula = request.getParameter("matricula");
		String localidade = request.getParameter("localidade");
		int piso = Integer.parseInt(request.getParameter("piso"));
		String fila = request.getParameter("fila");
		int posFila = Integer.parseInt(request.getParameter("posFila"));

		LugarVeiculoDao lugarVeiculoDao = new LugarVeiculoDao();

		try {
			boolean sucesso = lugarVeiculoDao.inserirVeiculoNoLugar(matricula, localidade, piso, fila, posFila);
			if (sucesso) {
				response.sendRedirect("perfil3.jsp");
			} else {
				response.getWriter().println(
						"<h1>Erro: condutor não autorizado</h1><input title=\"Go back\" type=\"button\" value=\"Back\"\r\n"
								+ "		onClick=\"javascript:window.history.back()\" />");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("perfil3.jsp?error=Erro interno ao processar a solicitação.");
		}
	}
}
