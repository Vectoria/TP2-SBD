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

			// Parse the numID first
			String numIDStr = request.getParameter("numID");
			int numID = Integer.parseInt(numIDStr);

			// Check if carta already exists
			CartaConducao existingCarta = cartaDao.getById(numID);
			CartaConducao carta = existingCarta != null ? existingCarta : new CartaConducao();

			// Set or update carta values
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

			// Update or save carta based on whether it exists
			int cartaResult;
			if (existingCarta != null) {
				cartaResult = cartaDao.update(carta);
			} else {
				cartaResult = cartaDao.save(carta);
			}

			// Process condutor if carta was saved/updated successfully
			if (cartaResult > 0) {
				String nifStr = request.getParameter("condutorNIF");
				int nif = Integer.parseInt(nifStr);

				// Check if condutor exists
				Condutor existingCondutor = condutorDao.getById(nif);
				Condutor condutor = existingCondutor != null ? existingCondutor : new Condutor();

				// Set or update condutor values
				condutor.setCondutorNIF(nif);
				condutor.setNumID(numID);

				String dataNascimento = request.getParameter("dataNascimento");
				if (dataNascimento != null && !dataNascimento.isEmpty()) {
					condutor.setDataNascimento(LocalDate.parse(dataNascimento));
				}

				// Additional carta-related fields
				condutor.setTipoHab(carta.getTipoHab());
				condutor.setDataEmissao(carta.getDataEmissao());
				condutor.setDataValidade(carta.getDataValidade());

				// Update or save condutor based on whether it exists
				int condutorResult;
				if (existingCondutor != null) {
					condutorResult = condutorDao.update(condutor);
				} else {
					condutorResult = condutorDao.save(condutor);
				}

				if (condutorResult > 0) {
					response.sendRedirect("index.jsp");
					return;
				}
			}

			response.sendRedirect("Condutor_form.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Condutor_form.jsp");
		}
	}
}