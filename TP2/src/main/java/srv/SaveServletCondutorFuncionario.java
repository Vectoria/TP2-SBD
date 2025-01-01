package srv;

import db.CondutorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SaveServletCondutorFuncionario")
public class SaveServletCondutorFuncionario extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try {
			// Retrieve form parameters
			String nifStr = request.getParameter("condutorNIF");
			String avaliacaoStr = request.getParameter("avaliacaoCondutor");

			// Parse and validate inputs
			int condutorNIF = Integer.parseInt(nifStr);
			double avaliacaoCondutor = Double.parseDouble(avaliacaoStr);

			if (avaliacaoCondutor < 0 || avaliacaoCondutor > 10) {
				throw new IllegalArgumentException("Avaliação deve estar entre 0 e 10.");
			}

			// Update the database
			CondutorDao condutorDao = new CondutorDao();
			int result = condutorDao.updateAvaliacao(condutorNIF, avaliacaoCondutor);

			if (result > 0) {
				//response.getWriter().println("<h1>Avaliação salva com sucesso!</h1>");
				response.sendRedirect("perfil3.jsp");
			} else {
				response.getWriter().println("<h1>Erro: Condutor não encontrado!</h1>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("<h1>Erro ao salvar avaliação: " + e.getMessage() + "</h1>");
		}
	}
}
