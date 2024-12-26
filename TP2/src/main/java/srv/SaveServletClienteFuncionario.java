package srv;

import db.ClienteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SaveServletClienteFuncionario")
public class SaveServletClienteFuncionario extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		try {
			// Retrieve form parameters
			String nifStr = request.getParameter("clienteNIF");
			String avaliacaoStr = request.getParameter("avaliacaoCliente");

			// Parse and validate inputs
			int clienteNIF = Integer.parseInt(nifStr);
			double avaliacaoCliente = Double.parseDouble(avaliacaoStr);

			if (avaliacaoCliente < 0 || avaliacaoCliente > 10) {
				throw new IllegalArgumentException("Avaliação deve estar entre 0 e 10.");
			}

			// Update the database
			ClienteDao clienteDao = new ClienteDao();
			int result = clienteDao.updateAvaliacao(clienteNIF, avaliacaoCliente);

			if (result > 0) {
				response.getWriter().println("<h1>Avaliação salva com sucesso!</h1>");
			} else {
				response.getWriter().println("<h1>Erro: Cliente não encontrado!</h1>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("<h1>Erro ao salvar avaliação: " + e.getMessage() + "</h1>");
		}
	}
}
