package srv;

import db.QualidadeServicoDao;
import pojo.QualidadeServico;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SaveQualidadeServicoServlet")
public class SaveQualidadeServicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int clienteNIF = Integer.parseInt(request.getParameter("clienteNIF"));
			int avaliacao = Integer.parseInt(request.getParameter("avaliacao"));
			String comentario = request.getParameter("comentario");

			QualidadeServico qualidadeServico = new QualidadeServico();
			qualidadeServico.setClienteNIF(clienteNIF);
			qualidadeServico.setAvaliacao(avaliacao);
			qualidadeServico.setComentario(comentario);

			QualidadeServicoDao dao = new QualidadeServicoDao();
			int result = dao.save(qualidadeServico); //carrega-se a base de dados

			if (result > 0) {
				response.sendRedirect("perfil2.jsp"); 
			} else {
				request.setAttribute("error", "Erro ao salvar a avaliação.");
				request.getRequestDispatcher("QualidadeServico_form.jsp").forward(request, response);
				response.getWriter().println("<h1>Erro de introdução, acima de 10 ou abaixo de 0 não permitido.</h1>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ocorreu um erro: " + e.getMessage());
			request.getRequestDispatcher("QualidadeServico_form.jsp").forward(request, response);
			response.getWriter().println("<h1>Erro: Matrícula inválida.</h1>");
		}
	}
}
