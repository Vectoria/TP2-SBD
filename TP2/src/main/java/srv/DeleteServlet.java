package srv;

import java.io.IOException;

import db.Dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 2173812416937754960L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Dao.delete(request.getParameter("old_cof_name"));
		Dao.doSQL2(null,Dao.getOld(request));
		String to = request.getParameter("to");
		if(to==null || to.isBlank())
			to="index.jsp";	
		response.sendRedirect(to);
	}
}
