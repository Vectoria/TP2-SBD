package srv;

import db.AluguerDao;
import pojo.Aluguer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/AtualizarQualidadeServicoServlet")
public class AtualizarQualidadeServicoServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recupera os parâmetros do formulário
		String dhInicioStr = request.getParameter("dhInicio");
		String dhFimStr = request.getParameter("dhFim");
		String clienteNIFStr = request.getParameter("clienteNIF");
		String qualidadeServicoAluguer = request.getParameter("qualidadeServicoAluguer");

		try {
			// Verifica se os parâmetros são válidos
			if (dhInicioStr != null && dhFimStr != null && clienteNIFStr != null && qualidadeServicoAluguer != null) {
				// Converte o valor recebido (datetime-local) para o formato aceito por
				// Timestamp
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				LocalDateTime dhInicioLocal = LocalDateTime.parse(dhInicioStr, formatter);
				LocalDateTime dhFimLocal = LocalDateTime.parse(dhFimStr, formatter);

				// Converte para Timestamp
				Timestamp dhInicio = Timestamp.valueOf(dhInicioLocal);
				Timestamp dhFim = Timestamp.valueOf(dhFimLocal);

				// Converte clienteNIF para inteiro
				int clienteNIF = Integer.parseInt(clienteNIFStr);

				// Atualiza a qualidade do serviço no banco de dados
				AluguerDao aluguerDao = new AluguerDao();
				Aluguer aluguer = aluguerDao.getById(dhInicio, dhFim, clienteNIF);

				if (aluguer != null) {
					aluguer.setQualidadeServicoAluguer(qualidadeServicoAluguer);
					aluguerDao.updateQualidadeServico(aluguer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Redireciona de volta para perfil1.jsp
		response.sendRedirect("perfil1.jsp");
	}
}
