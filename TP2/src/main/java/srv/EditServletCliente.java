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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ClienteDao clienteDao = new ClienteDao();
			MoradaDao moradaDao = new MoradaDao();

			// Atualizar a morada
			Morada morada = moradaDao.getById(request.getParameter("rua"),
					Integer.parseInt(request.getParameter("codigoPostalP1")),
					Integer.parseInt(request.getParameter("codigoPostalP2")),
					Integer.parseInt(request.getParameter("numeroPorta")));
			if (morada != null) {
				morada.setNomeFreguesia(request.getParameter("nomeFreguesia"));
				morada.setNomeConcelho(request.getParameter("nomeConcelho"));
				morada.setNomeDistrito(request.getParameter("nomeDistrito"));
				moradaDao.update(morada);
			}

			// Atualizar o cliente
			Cliente cliente = clienteDao.getById(Integer.parseInt(request.getParameter("clienteNIF")));
			if (cliente != null) {
				cliente.setContactoTel(Integer.parseInt(request.getParameter("contactoTel")));
				cliente.setEmail(request.getParameter("email"));
				cliente.setNome(request.getParameter("nome"));
				cliente.setCondutorNIF(Integer.parseInt(request.getParameter("condutorNIF")));
				clienteDao.update(cliente);
			}

			response.sendRedirect("index.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Cliente_form.jsp");
		}
	}
}
