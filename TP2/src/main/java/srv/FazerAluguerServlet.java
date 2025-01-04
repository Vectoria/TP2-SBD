package srv;

import db.AluguerDao;
import db.CondutorDao;
import db.VeiculoDao;
import pojo.Aluguer;
import pojo.Condutor;
import pojo.Veiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@WebServlet("/FazerAluguerServlet")
public class FazerAluguerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			// Retira info do forms
			String matricula = request.getParameter("matricula");
			String localidade = request.getParameter("localidade");
			String dhInicioStr = request.getParameter("dhInicio");
			String dhFimStr = request.getParameter("dhFim");
			String clienteNIFStr = request.getParameter("clienteNIF");
			String condutorNIFStr = request.getParameter("condutorNIF");
			String moedaPref = request.getParameter("moedaPref");
			String descontoStr = request.getParameter("desconto");

			// Converter os dados para os do pojo
			int clienteNIF = Integer.parseInt(clienteNIFStr);
			int condutorNIF = Integer.parseInt(condutorNIFStr);
			LocalDateTime dhInicio = LocalDateTime.parse(dhInicioStr,
					DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			LocalDateTime dhFim = LocalDateTime.parse(dhFimStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
			Integer descontoCodigo = (descontoStr != null && !descontoStr.isEmpty()) ? Integer.parseInt(descontoStr)
					: null;

			Aluguer novoAluguer = new Aluguer();
			novoAluguer.setMatricula(matricula);
			novoAluguer.setLocalidade(localidade);
			novoAluguer.setDhInicio(dhInicio);
			novoAluguer.setDhFim(dhFim);
			novoAluguer.setDhEntrega(null); // Aluguel ainda não finalizado
			novoAluguer.setClienteNIF(clienteNIF);
			novoAluguer.setCondutorNIF(condutorNIF);
			novoAluguer.setMoedaPref(moedaPref);
			novoAluguer.setCodigo(descontoCodigo); // ou é null ou é algum codigo

			CondutorDao condutorDao = new CondutorDao();
			Condutor condutor = condutorDao.getById(condutorNIF);
			String tipoHabCondutor = condutor.getTipoHab();
			LocalDate dataNasc = condutor.getDataNascimento();
			LocalDate dataEmi = condutor.getDataEmissao();
			long idade = ChronoUnit.YEARS.between(dataNasc, dhInicio.toLocalDate());
			long anosCarta = ChronoUnit.YEARS.between(dataEmi, dhInicio.toLocalDate());

			VeiculoDao veiculoDao = new VeiculoDao();
			Veiculo veiculo = veiculoDao.getById(matricula);
			String tipoHabVei = veiculo.getTipoHab();

			AluguerDao aluguerDao = new AluguerDao();
			int result = 0;

			// verificação se tem mais de 25 anos ou 2,5 anos de carta, pode conduzir a2 se
			// tiver b1, ao contrario nao acontece
			if (idade >= 25 || anosCarta >= 2.5) {
				switch (tipoHabCondutor.toUpperCase()) {
				case "B1":
					switch (tipoHabVei.toUpperCase()) {
					case "B1":
					case "A1":
					case "A2":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				case "B2":
					switch (tipoHabVei.toUpperCase()) {
					case "B1":
					case "B2":
					case "A1":
					case "A2":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				case "A1":
					if (tipoHabVei.equalsIgnoreCase("A1")) {
						result = aluguerDao.save(novoAluguer);
					}
					break;
				case "A2":
					switch (tipoHabVei.toUpperCase()) {
					case "A1":
					case "A2":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}

			if (idade < 25 && anosCarta < 2.5) {
				switch (tipoHabCondutor.toUpperCase()) {
				case "B1":
					switch (tipoHabVei.toUpperCase()) {
					case "B1":
					case "A1":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				case "B2":
					switch (tipoHabVei.toUpperCase()) {
					case "B1":
					case "B2":
					case "A1":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				case "A1":
					if (tipoHabVei.equalsIgnoreCase("A1")) {
						// Inserir no banco de dados usando o método save
						result = aluguerDao.save(novoAluguer);
					}
					break;
				case "A2":
					switch (tipoHabVei.toUpperCase()) {
					case "A1":
					case "A2":
						result = aluguerDao.save(novoAluguer);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}

			// Redirecionar com mensagem de sucesso
			if (result > 0) {
				request.setAttribute("mensagemSucesso", "Aluguer realizado com sucesso!");

				request.getRequestDispatcher("perfil1.jsp").forward(request, response);
			} else { // não redireciona e fica com erro
				request.setAttribute("mensagemErro", "Erro ao realizar o aluguer. Tente novamente.");
				response.getWriter().println(
						"<h1>Erro: condutor não autorizado</h1><input title=\"Go back\" type=\"button\" value=\"Back\"\r\n"
								+ "		onClick=\"javascript:window.history.back()\" />");

			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mensagemErro", "Erro ao processar o aluguer: " + e.getMessage());
		}

	}
}