package srv;

import db.AluguerDao;
import pojo.Aluguer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/FazerAluguerServlet")
public class FazerAluguerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			// Recuperar os parâmetros do formulário
			String matricula = request.getParameter("matricula");
			String localidade = request.getParameter("localidade");
			String dhInicioStr = request.getParameter("dhInicio");
			String dhFimStr = request.getParameter("dhFim");
			String clienteNIFStr = request.getParameter("clienteNIF");
			String condutorNIFStr = request.getParameter("condutorNIF");
			String moedaPref = request.getParameter("moedaPref");
			String descontoStr = request.getParameter("desconto_" + matricula);

			// Converter os dados recebidos
			int clienteNIF = Integer.parseInt(clienteNIFStr);
			int condutorNIF = Integer.parseInt(condutorNIFStr);
			LocalDateTime dhInicio = LocalDateTime.parse(dhInicioStr,
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			LocalDateTime dhFim = LocalDateTime.parse(dhFimStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			Integer descontoCodigo = (descontoStr != null && !descontoStr.isEmpty()) ? Integer.parseInt(descontoStr)
					: null;

			// Criar objeto Aluguer
			Aluguer novoAluguer = new Aluguer();
			novoAluguer.setMatricula(matricula);
			novoAluguer.setLocalidade(localidade);
			novoAluguer.setDhInicio(dhInicio);
			novoAluguer.setDhFim(dhFim);
			novoAluguer.setDhEntrega(null); // Aluguel ainda não finalizado
			novoAluguer.setClienteNIF(clienteNIF);
			novoAluguer.setCondutorNIF(condutorNIF);
			novoAluguer.setMoedaPref(moedaPref);
			novoAluguer.setCodigo(descontoCodigo); // Ajuste para setar o desconto

			// Inserir no banco de dados usando o método save
			AluguerDao aluguerDao = new AluguerDao();
			int result = aluguerDao.save(novoAluguer);

			// Redirecionar com mensagem de sucesso
			if (result > 0) {
				request.setAttribute("mensagemSucesso", "Aluguer realizado com sucesso!");
			} else {
				request.setAttribute("mensagemErro", "Erro ao realizar o aluguel. Tente novamente.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mensagemErro", "Erro ao processar o aluguel: " + e.getMessage());
		}

		// Redirecionar de volta para o JSP de perfil
		request.getRequestDispatcher("perfil1.jsp").forward(request, response);
	}
}
