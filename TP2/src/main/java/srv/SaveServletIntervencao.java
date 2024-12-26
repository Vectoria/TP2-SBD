package srv;

import db.IntervencaoDao;
import pojo.Intervencao;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SaveServletIntervencao")
public class SaveServletIntervencao extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		try {
			// Parse input parameters
			String matricula = request.getParameter("matricula");
			String dhRegistoStr = request.getParameter("dhRegisto");
			String tipoInt = request.getParameter("tipoInt");
			double custoInt = Double.parseDouble(request.getParameter("custoInt"));

			// Convert data and time
			LocalDateTime dhRegisto = LocalDateTime.parse(dhRegistoStr,
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

			// Create and populate Intervencao object
			Intervencao intervencao = new Intervencao();
			intervencao.setMatricula(matricula);
			intervencao.setDhRegisto(dhRegisto);
			intervencao.setTipoInt(tipoInt);
			intervencao.setCustoInt(custoInt);

			// Save to database
			IntervencaoDao intervencaoDao = new IntervencaoDao();
			int result = intervencaoDao.save(intervencao);

			// Redirect based on result
			if (result > 0) {
				response.sendRedirect("perfil3.jsp"); // Replace with the appropriate success page
			} else {
				response.sendRedirect("Intervencao_form.jsp?error=save_failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Intervencao_form.jsp?error=exception");
		}
	}
}
