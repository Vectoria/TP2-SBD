package srv;
import java.io.IOException;
import java.io.PrintWriter;

import db.Dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

	private static final long serialVersionUID = -4617797030215810878L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String old_cof_name = request.getParameter("old_cof_name");

		//Dao.update(Dao.getNew(request), old_cof_name)
		if (Dao.doSQL2(Dao.getNew(request), Dao.getOld(request)) > 0) {
			String to = request.getParameter("to");
			if(to==null || to.isBlank())
				to="index.jsp";	
			response.sendRedirect(to);
		}
		else {
			out.println("<h2>Sorry! Unable to update record ("+old_cof_name+")...</h2>");
			String from = request.getParameter("from");
			if(from==null || from.isBlank())
				from="form.jsp";	
			request.getRequestDispatcher(from+"?cof_name="+old_cof_name).include(request, response);
		}
		out.close();
	}

}
