package usr;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Verificação de utilizadores
 */

public class Check {
	// Exemplos de SQL Injection, risco agravado devido a allowMultiQueries=true

	// password: ' or 1=1; update user set password='a' where 'a'='a  -- todas ficam igual 'a'
	// password: ' or 1=1; delete from user where 'a'='a			  -- apaga users
	
	// SELECT userid FROM user WHERE NOT blocked AND binary username = 'k' AND
	// password = 'x' or 1=1; UPDATE user SET password='a' where 'a'='a';  

	// elimina com comentários o texto a mais (pode funcionar em alguns SGBDs)!
	// SELECT * FROM user WHERE NOT blocked AND username = '' OR 1=1 -- ' AND password = 'foo';
	
	public static BigDecimal fnCheck(String MyUser, String MyPassword) {
		BigDecimal userID = null;
		ResultSet rs = null;
		try (Connection conn = Db.getConn();
				PreparedStatement stmt = conn.prepareStatement("SELECT fnCheck(?,?) as userid");) {
			stmt.setString(1, MyUser);
			stmt.setString(2, MyPassword);
			rs = stmt.executeQuery();
			if (rs.next())
				userID = rs.getBigDecimal("userid");
			System.out.println(userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		System.out.println(userID);
		return userID;
	}
	
	public static boolean isLogin(String MyUser, String MyPassword) {
		return fnCheck(MyUser,MyPassword)!=null;
	}
	
	public static BigDecimal getUser() {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Enter userName: ");  
			String userName = scan.nextLine();  
			System.out.println("Enter password: ");  
			String password= scan.nextLine();
			BigDecimal userID=usr.Check.fnCheck(userName,password);
			if(userID!=null)
				System.out.println("You are successfully logged in!");
			else
				System.out.println("Login failed for user: "+userName);
			return userID;
		}
	}
	
	public static User login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		return login(request, response, 0);
	}
	
	public static User login(HttpServletRequest request, HttpServletResponse response, String profile) 
			throws ServletException, IOException {
		if(profile==null || profile.isBlank())
			return null;
		if(profile.equalsIgnoreCase("Administrador"))
			return login(request, response, 0);
		if(profile.equalsIgnoreCase("Cliente"))
			return login(request, response, 1);
		if(profile.equalsIgnoreCase("Condutor"))
			return login(request, response, 2);
		if(profile.equalsIgnoreCase("Funcionário"))
			return login(request, response, 3);
		if(profile.equalsIgnoreCase("Gerente"))
			return login(request, response, 4);
		return null;
	}
	
	public static User login(HttpServletRequest request, HttpServletResponse response, int profile) 
			throws ServletException, IOException {
		HttpSession session=request.getSession(false);   
		if(session!=null) {
			User u=(User)session.getAttribute("user");
			if(u!=null && u.getProfile()==profile)
			  return u;
			else
			  session.invalidate();
		}
		response.sendRedirect("login.html");
		//request.getRequestDispatcher("/login.html").forward(request, response);
		return null;  // nunca executa return
	}
}
