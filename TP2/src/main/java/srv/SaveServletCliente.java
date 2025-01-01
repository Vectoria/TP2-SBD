package srv;

import java.io.IOException;
import java.io.PrintWriter;

import db.ClienteDao;
import db.MoradaDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Cliente;
import pojo.Morada;

@WebServlet("/SaveServletCliente")
public class SaveServletCliente extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			// Criar DAO
			ClienteDao clienteDao = new ClienteDao();
			MoradaDao moradaDao = new MoradaDao();

			// Preencher a morada
			Morada morada = new Morada();
			morada.setRua(request.getParameter("rua"));
			morada.setCodigoPostalP1(Integer.parseInt(request.getParameter("codigoPostalP1")));
			morada.setCodigoPostalP2(Integer.parseInt(request.getParameter("codigoPostalP2")));
			morada.setNumeroPorta(Integer.parseInt(request.getParameter("numeroPorta")));
			morada.setNomeFreguesia(request.getParameter("nomeFreguesia"));
			morada.setNomeConcelho(request.getParameter("nomeConcelho"));
			morada.setNomeDistrito(request.getParameter("nomeDistrito"));

			// Salvar a morada
			moradaDao.save(morada);

			// Preencher o cliente
			Cliente cliente = new Cliente();
			cliente.setClienteNIF(Integer.parseInt(request.getParameter("clienteNIF")));
			cliente.setMoedaPref(request.getParameter("moedaPref")); // Captura moeda preferida
			cliente.setPrefLingCult(request.getParameter("prefLingCult")); // Captura língua preferida
			cliente.setContactoTel(Integer.parseInt(request.getParameter("contactoTel")));
			cliente.setEmail(request.getParameter("email"));
			cliente.setNome(request.getParameter("nome"));
			cliente.setCondutorNIF(Integer.parseInt(request.getParameter("condutorNIF")));
			cliente.setRua(morada.getRua());
			cliente.setCodigoPostalP1(morada.getCodigoPostalP1());
			cliente.setCodigoPostalP2(morada.getCodigoPostalP2());
			cliente.setNumeroPorta(morada.getNumeroPorta());
			cliente.setNomeFreguesia(morada.getNomeFreguesia());
			cliente.setNomeConcelho(morada.getNomeConcelho());
			cliente.setNomeDistrito(morada.getNomeDistrito());

			// Salvar o cliente
			clienteDao.save(cliente);

			response.sendRedirect("perfil0.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			//response.sendRedirect("Cliente_form.jsp");
			response.getWriter().println("<h1>Erro: " + e.getMessage() + "</h1>");
		}
	}
}
