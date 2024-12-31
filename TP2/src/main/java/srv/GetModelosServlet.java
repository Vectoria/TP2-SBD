package srv;

import db.VeiculoDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/GetModelosServlet")
public class GetModelosServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String marca = request.getParameter("marca");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter out = response.getWriter()) {
			if (marca != null && !marca.trim().isEmpty()) {
				VeiculoDao veiculoDao = new VeiculoDao();
				List<String> modelos = veiculoDao.getModelosByMarca(marca);

				// Converter a lista de modelos para JSON
				out.print("[");
				for (int i = 0; i < modelos.size(); i++) {
					out.print("\"" + modelos.get(i) + "\"");
					if (i < modelos.size() - 1) {
						out.print(",");
					}
				}
				out.print("]");
			} else {
				out.print("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
