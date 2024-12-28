package srv;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import usr.*;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public String jumpTo(User x) {
		switch(x.getProfile()) {
		case 0: return "/perfil0.jsp";
		case 1: return "/perfil1.jsp";
		case 2: return "/perfil2.jsp";
		case 3: return "/perfil3.jsp";
		case 4: return "/perfil4.jsp";
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10: return "/example.jsp";
		default: return "/";
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("j_username");
		String password=request.getParameter("j_password");
		Db.init(false);  // se a base de dados existir não é alterada
		if(name!=null && password!=null && Check.isLogin(name, password)){
			HttpSession session=request.getSession(true);
			User x=UserDao.getByName(name);
			session.setAttribute("user", x);
			// salta para a página ligada ao respetivo perfil
			request.getRequestDispatcher(jumpTo(x)).forward(request, response);
		}
		else 
			request.getRequestDispatcher("/login.html").forward(request, response);
	}

}
