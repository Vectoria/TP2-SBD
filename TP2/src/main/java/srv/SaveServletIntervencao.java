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
			String matricula = request.getParameter("matricula");
			String dhRegistoStr = request.getParameter("dhRegisto");
			String tipoInt = request.getParameter("tipoInt");
			double custoInt = Double.parseDouble(request.getParameter("custoInt"));
			int numKM = Integer.parseInt(request.getParameter("numKM"));
			LocalDateTime dhRegisto = LocalDateTime.parse(dhRegistoStr,
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

			Intervencao intervencao = new Intervencao();
			intervencao.setNumKM(numKM);
			intervencao.setMatricula(matricula);
			intervencao.setDhRegisto(dhRegisto);
			intervencao.setTipoInt(tipoInt);
			intervencao.setCustoInt(custoInt);

			IntervencaoDao intervencaoDao = new IntervencaoDao();
			int result = intervencaoDao.save(intervencao);

			if (result > 0) {
				response.sendRedirect("perfil3.jsp"); 
			} else {
				response.getWriter().println("<h1>Erro</h1>");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Intervencao_form.jsp?error=exception");
		}
	}
}
