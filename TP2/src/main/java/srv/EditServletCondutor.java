package srv;

import java.io.IOException;
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

@WebServlet("/EditServletCondutor")
public class EditServletCondutor extends HttpServlet {
	private static final long serialVersionUID = -4617797030215810878L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			CartaConducaoDao cartaDao = new CartaConducaoDao();
			CondutorDao condutorDao = new CondutorDao();

			String numIDStr = request.getParameter("numID");
			String nifStr = request.getParameter("condutorNIF");
			int numID = Integer.parseInt(numIDStr);
			int nif = Integer.parseInt(nifStr);

			// Update CartaConducao
			CartaConducao carta = cartaDao.getById(numID);
			if (carta != null) {
				carta.setTipoHab(request.getParameter("tipoHab"));

				String dataEmissao = request.getParameter("dataEmissao");
				if (dataEmissao != null && !dataEmissao.isEmpty()) {
					carta.setDataEmissao(LocalDate.parse(dataEmissao));
				}

				String dataValidade = request.getParameter("dataValidade");
				if (dataValidade != null && !dataValidade.isEmpty()) {
					carta.setDataValidade(LocalDate.parse(dataValidade));
				}

				if (cartaDao.update(carta) <= 0) {
					throw new Exception("Failed to update carta");
				}
			}

			// Get the birth date from the form
			String dataNascimento = request.getParameter("dataNascimento");
			LocalDate birthDate = null;
			if (dataNascimento != null && !dataNascimento.isEmpty()) {
				birthDate = LocalDate.parse(dataNascimento);
			}

			// Update Condutor
			Condutor condutor = condutorDao.getById(nif);
			if (condutor != null) {
				condutor.setNumID(numID);
				condutor.setDataNascimento(birthDate); // Set the birth date

				// Keep existing values for other fields
				condutor.setTipoHab(carta.getTipoHab());
				condutor.setDataEmissao(carta.getDataEmissao());
				condutor.setDataValidade(carta.getDataValidade());

				if (condutorDao.update(condutor) <= 0) {
					throw new Exception("Failed to update condutor");
				}
			}

			response.sendRedirect("perfil0.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Condutor_form.jsp");
		}
	}
}