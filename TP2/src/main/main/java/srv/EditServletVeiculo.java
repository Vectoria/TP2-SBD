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

@WebServlet("/EditServletVeiculo")
public class EditServletVeiculo extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			VeiculoDao veiculoDao = new VeiculoDao();
			Veiculo veiculo = veiculoDao.getById(request.getParameter("matricula"));

			if (veiculo != null) {
				veiculo.setCor(request.getParameter("cor"));
				veiculo.setNomeMod(request.getParameter("nomeMod"));

				int status = veiculoDao.update(veiculo);
				if (status > 0) {
					response.sendRedirect("Veiculo_list.jsp");
				} else {
					out.println("Erro ao atualizar veículo.");
					response.sendRedirect("Veiculo_form.jsp?matricula=" + veiculo.getMatricula());
				}
			} else {
				out.println("Veículo não encontrado.");
				response.sendRedirect("Veiculo_list.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("Veiculo_form.jsp");
		}
	}
}
