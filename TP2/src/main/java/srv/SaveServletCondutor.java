package srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import db.CartaConducaoDao;
import db.CondutorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.CartaConducao;
import pojo.Condutor;

@WebServlet("/SaveServletCondutor")
public class SaveServletCondutor extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			CartaConducaoDao cartaDao = new CartaConducaoDao();
			CondutorDao condutorDao = new CondutorDao();
			String numIDStr = request.getParameter("numID");
			int numID = Integer.parseInt(numIDStr);
			CartaConducao existingCarta = cartaDao.getById(numID);
			CartaConducao carta = existingCarta != null ? existingCarta : new CartaConducao();
			carta.setNumID(numID);
			carta.setTipoHab(request.getParameter("tipoHab"));

			String dataEmissao = request.getParameter("dataEmissao");
			if (dataEmissao != null && !dataEmissao.isEmpty()) {
				carta.setDataEmissao(LocalDate.parse(dataEmissao));
			}

			String dataValidade = request.getParameter("dataValidade");
			if (dataValidade != null && !dataValidade.isEmpty()) {
				carta.setDataValidade(LocalDate.parse(dataValidade));
			}

			int cartaResult;
			if (existingCarta != null) { //da update da carta ou carrega-se na base de dados
				cartaResult = cartaDao.update(carta); 
			} else {
				cartaResult = cartaDao.save(carta);
			}

			// com carta criada/atualizada
			if (cartaResult > 0) {
				String nifStr = request.getParameter("condutorNIF");
				int nif = Integer.parseInt(nifStr);

				Condutor existingCondutor = condutorDao.getById(nif);
				Condutor condutor = existingCondutor != null ? existingCondutor : new Condutor();

				condutor.setCondutorNIF(nif);
				condutor.setNumID(numID);

				String dataNascimento = request.getParameter("dataNascimento");
				if (dataNascimento != null && !dataNascimento.isEmpty()) {
					condutor.setDataNascimento(LocalDate.parse(dataNascimento));
				}

				condutor.setTipoHab(carta.getTipoHab());
				condutor.setDataEmissao(carta.getDataEmissao());
				condutor.setDataValidade(carta.getDataValidade());

				int condutorResult;
				if (existingCondutor != null) {
					condutorResult = condutorDao.update(condutor);
				} else {
					condutorResult = condutorDao.save(condutor);
				}

				if (condutorResult > 0) { //se atualizou ou salvou condutor, redireciona
					response.sendRedirect("perfil0.jsp");
					return;
				}
			}

			//response.sendRedirect("Condutor_form.jsp");
			response.getWriter().println("<h1>Erro</h1>");

		} catch (Exception e) {
			e.printStackTrace();
			//response.sendRedirect("Condutor_form.jsp");
			response.getWriter().println("<h1>Erro: " + e.getMessage() + "</h1>");
		}
	}
}