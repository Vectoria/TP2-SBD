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
			// Recuperar os parâmetros do formulário
			int clienteNIF = Integer.parseInt(request.getParameter("clienteNIF"));
			int avaliacao = Integer.parseInt(request.getParameter("avaliacao"));
			String comentario = request.getParameter("comentario");

			// Criar uma nova instância de QualidadeServico
			QualidadeServico qualidadeServico = new QualidadeServico();
			qualidadeServico.setClienteNIF(clienteNIF);
			qualidadeServico.setAvaliacao(avaliacao);
			qualidadeServico.setComentario(comentario);

			// Salvar no banco de dados usando QualidadeServicoDao
			QualidadeServicoDao dao = new QualidadeServicoDao();
			int result = dao.save(qualidadeServico);

			// Redirecionar ou exibir mensagem de sucesso/erro
			if (result > 0) {
				response.sendRedirect("perfil2.jsp"); // Página de sucesso
			} else {
				request.setAttribute("error", "Erro ao salvar a avaliação.");
				request.getRequestDispatcher("QualidadeServico_form.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ocorreu um erro: " + e.getMessage());
			request.getRequestDispatcher("QualidadeServico_form.jsp").forward(request, response);
		}
	}
}
