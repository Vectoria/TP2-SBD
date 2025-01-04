package srv;

import db.DescontoDao;
import pojo.Desconto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;

@WebServlet("/GerarDescontoServlet")
public class GerarDescontoServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Gerar código aleatorio e valor
			Random random = new Random();
			int codigo = 100000 + random.nextInt(900000); 
			double valor = 0.05 + (0.30 - 0.05) * random.nextDouble(); // Valor entre 0.05 e 0.30

			Desconto desconto = new Desconto();
			desconto.setCodigo(codigo);
			desconto.setValor(Math.round(valor * 100.0) / 100.0); 

			DescontoDao descontoDao = new DescontoDao();
			int result = descontoDao.save(desconto);

			if (result > 0) {
				response.sendRedirect("perfil3.jsp");
			} else {
				//response.sendRedirect("Descontos.jsp?error=Erro ao criar desconto.");
				response.getWriter().println("<h1>Erro</h1>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//response.sendRedirect("Descontos.jsp?error=Exceção ao criar desconto.");
			response.getWriter().println("<h1>Erro: " + e.getMessage() + "</h1>");
		}
	}
}
