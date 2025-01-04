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

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String marca = request.getParameter("marca");
		//System.out.println("Marca recebida: " + marca); 

		PrintWriter out = response.getWriter();
		try {
			if (marca != null && !marca.trim().isEmpty()) {
				VeiculoDao veiculoDao = new VeiculoDao();
				List<String> modelos = veiculoDao.getModelosByMarca(marca);

				//System.out.println("Modelos encontrados: " + modelos); 

				// Usar StringBuilder para construir o JSON
				StringBuilder json = new StringBuilder("[");
				for (int i = 0; i < modelos.size(); i++) {
					if (i > 0) {
						json.append(",");
					}
					json.append("\"").append(modelos.get(i).replace("\"", "\\\"")).append("\"");
				}
				json.append("]");

				out.print(json.toString()); //construiu o json para o dropbox
			} else {
				out.print("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("[]");
		}
	}
}
