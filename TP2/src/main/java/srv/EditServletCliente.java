package srv;

import java.io.IOException;

import db.ClienteDao;
import db.MoradaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Cliente;
import pojo.Morada;

@WebServlet("/EditServletCliente")
public class EditServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Criar DAOs
			ClienteDao clienteDao = new ClienteDao();
			MoradaDao moradaDao = new MoradaDao();

			// Obter dados da morada do request
			String rua = request.getParameter("rua");
			int codigoPostalP1 = Integer.parseInt(request.getParameter("codigoPostalP1"));
			int codigoPostalP2 = Integer.parseInt(request.getParameter("codigoPostalP2"));
			int numeroPorta = Integer.parseInt(request.getParameter("numeroPorta"));

			// Obter a morada existente
			Morada morada = moradaDao.getById(rua, codigoPostalP1, codigoPostalP2, numeroPorta);

			// Se a morada existir, atualizar seus dados
			if (morada != null) {
				morada.setNomeFreguesia(request.getParameter("nomeFreguesia"));
				morada.setNomeConcelho(request.getParameter("nomeConcelho"));
				morada.setNomeDistrito(request.getParameter("nomeDistrito"));

				// Atualizar a morada na base de dados
				int moradaResult = moradaDao.update(morada);
				if (moradaResult == 0) {
					throw new Exception("Falha ao atualizar a morada");
				}
			} else {
				throw new Exception("Morada não encontrada");
			}

			// Obter o cliente existente
			int clienteNIF = Integer.parseInt(request.getParameter("clienteNIF"));
			Cliente cliente = clienteDao.getById(clienteNIF);

			// Se o cliente existir, atualizar seus dados
			if (cliente != null) {
				// Atualizar dados básicos do cliente
				cliente.setMoedaPref(request.getParameter("moedaPref"));
				cliente.setPrefLingCult(request.getParameter("prefLingCult"));
				cliente.setContactoTel(Integer.parseInt(request.getParameter("contactoTel")));
				cliente.setEmail(request.getParameter("email"));
				cliente.setNome(request.getParameter("nome"));
				cliente.setCondutorNIF(Integer.parseInt(request.getParameter("condutorNIF")));

				// Atualizar dados da morada no cliente
				cliente.setRua(rua);
				cliente.setCodigoPostalP1(codigoPostalP1);
				cliente.setCodigoPostalP2(codigoPostalP2);
				cliente.setNumeroPorta(numeroPorta);
				cliente.setNomeFreguesia(request.getParameter("nomeFreguesia"));
				cliente.setNomeConcelho(request.getParameter("nomeConcelho"));
				cliente.setNomeDistrito(request.getParameter("nomeDistrito"));
				// Manter valores existentes para campos não presentes no form
				cliente.setCodigo(cliente.getCodigo());
				cliente.setAvaliacaoCliente(cliente.getAvaliacaoCliente());

				// Atualizar o cliente na base de dados
				int clienteResult = clienteDao.update(cliente);
				if (clienteResult == 0) {
					throw new Exception("Falha ao atualizar o cliente");
				}

				// Se tudo correu bem, redirecionar para a página principal
				response.sendRedirect("index.jsp");
			} else {
				throw new Exception("Cliente não encontrado");
			}

		} catch (NumberFormatException e) {
			// Erro específico para problemas de conversão de números
			request.setAttribute("error", "Erro de formato nos dados numéricos: " + e.getMessage());
			request.getRequestDispatcher("Cliente_form.jsp").forward(request, response);
		} catch (Exception e) {
			// Log do erro para debugging
			e.printStackTrace();

			// Enviar mensagem de erro para a página
			request.setAttribute("error", "Erro ao atualizar dados: " + e.getMessage());
			request.getRequestDispatcher("Cliente_form.jsp").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Redirecionar GET requests para o formulário
		response.sendRedirect("Cliente_form.jsp");
	}
}