package srv;

import java.io.IOException;
import java.io.PrintWriter;
import db.VeiculoDao;
import pojo.Veiculo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SaveServletVeiculo")
public class SaveServletVeiculo extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			VeiculoDao veiculoDao = new VeiculoDao();
			Veiculo veiculo = new Veiculo();

			veiculo.setMatricula(request.getParameter("matricula"));
			veiculo.setCor(request.getParameter("cor"));
			veiculo.setNomeMod(request.getParameter("nomeMod"));

			int status = veiculoDao.save(veiculo);
			if (status > 0) {
				response.sendRedirect("perfil0.jsp");
			} else {
				out.println("Erro ao salvar veículo.");
				//response.sendRedirect("Veiculo_form.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//response.sendRedirect("Veiculo_form.jsp");
			out.println("<h1>Erro: " + e.getMessage() + "</h1>");
		}
	}
}
