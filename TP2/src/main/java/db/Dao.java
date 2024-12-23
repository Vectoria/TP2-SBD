package db;

import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import pojo.Pojo;

import java.sql.*;
import java.time.LocalDate;

public class Dao extends Db {

	public static int save(Pojo c) {
		if(c==null)
			return 0;
		return doSQL1(c, null);
	}
	
	public static int update(Pojo c, String key) {
		if(c==null)
			return 0;
		if(key==null || key.isBlank())
			return 0;
		return doSQL1(c,key);
	}
	
	public static int delete(String key) {
		if(key==null || key.isBlank())
			return 0;
		return doSQL1(null,key);
	}
	
	private static int doSQL1(Pojo c, String key) {
		int ui=0;
		if(c!=null) {
			if (key == null || key.isBlank())
				ui=1;
		}
		else
			ui=2;
		String[] cmd = 
		{"UPDATE COFFEES SET COF_NAME=?, SUP_DATE=?, PRICE=?, SALES=?, TOTAL=? WHERE COF_NAME=?",
		 "INSERT INTO COFFEES (COF_NAME, SUP_DATE, PRICE, SALES, TOTAL) " + "VALUES (?, ?, ?, ?, ?)",
		 "DELETE FROM COFFEES WHERE COF_NAME=?"};
		int status = 0;
		Connection con = getConn();
		PreparedStatement ps = null;
		do {
			try {
				// System.out.println("Executa "+cmd[ui]);
				ps = con.prepareStatement(cmd[ui]);
				if(ui!=2) { // ! delete
					ps.setString(1, c.getName());
					ps.setObject(2, c.getSup_date());
					ps.setFloat(3, c.getPrice());
					ps.setInt(4, c.getSales());
					ps.setInt(5, c.getTotal());
				}
				if(ui==0)  		// ! update
					ps.setString(6, key);
				if(ui==2)  		// ! delete
					ps.setString(1, key);
				status = ps.executeUpdate();
				if(status==0)  	// não havia registo antigo, vai inserir
					ui++;
				else
					break;
			} catch (SQLException e) {
				System.err.println("Erro na instrução SQL: (" + e.getErrorCode() + ") "+ e.getMessage());
			}
		} while (ui < 2);
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Erro no close: (" + e.getErrorCode() + ") " + e.getMessage());
		}
		return status;
	}

	public static int doSQL2(Pojo c, Pojo o) {
		int ui=0;   	// update
		if(c!=null) {
			if (o == null)
				ui=1; 	// insert
		}
		else
			ui=2; 		// delete
		String[] cmd = 
		{"UPDATE COFFEES SET COF_NAME=?, SUP_DATE=?, PRICE=?, SALES=?, TOTAL=? WHERE COF_NAME=? AND SUP_DATE=? AND ROUND(PRICE,2)=ROUND(?,2) AND SALES=? AND TOTAL=?",
		 "INSERT INTO COFFEES (COF_NAME, SUP_DATE, PRICE, SALES, TOTAL) " + "VALUES (?, ?, ?, ?, ?)",
		 "DELETE FROM COFFEES WHERE COF_NAME=? AND SUP_DATE=? AND ROUND(PRICE,2)=ROUND(?,2) AND SALES=? AND TOTAL=?"};
		int status = 0;
		Connection con = getConn();
		PreparedStatement ps = null;
		do {
			try {
				// System.out.println("Executa "+cmd[ui]);
				ps = con.prepareStatement(cmd[ui]);
				if(ui!=2) { // ! delete
					ps.setString(1, c.getName());
					ps.setObject(2, c.getSup_date());
					ps.setFloat(3, c.getPrice());
					ps.setInt(4, c.getSales());
					ps.setInt(5, c.getTotal());
				}
				int mais=0;		// ! delete
				if(ui==0)  		
					mais=5; 	// ! update
				if(ui!=1) {
					ps.setString(1+mais, o.getName());
					ps.setObject(2+mais, o.getSup_date());
					ps.setFloat(3+mais, o.getPrice());
					ps.setInt(4+mais, o.getSales());
					ps.setInt(5+mais, o.getTotal());
				}
				status = ps.executeUpdate();
				if(status==0)  	// não havia registo antigo, vai inserir
					ui++;
				else
					break;
			} catch (SQLException e) {
				System.err.println("Erro na instrução SQL: (" + e.getErrorCode() + ") "+ e.getMessage());
			}
		} while (ui < 2);
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Erro no close: (" + e.getErrorCode() + ") " + e.getMessage());
		}
		return status;
	}

	public static Pojo getNew(HttpServletRequest request) {
		Pojo p=new Pojo();
		p.setCof_name(request.getParameter("cof_name"));
		p.setSup_date(LocalDate.parse(request.getParameter("sup_date"))); //default, ISO_LOCAL_DATE
		p.setPrice(Float.parseFloat(request.getParameter("price")));
		p.setSales(Integer.parseInt(request.getParameter("sales")));
		p.setTotal(Integer.parseInt(request.getParameter("total")));
		return p;
	}

	public static Pojo getOld(HttpServletRequest request) {
		Pojo p=new Pojo();
		p.setCof_name(request.getParameter("old_cof_name"));
		p.setSup_date(LocalDate.parse(request.getParameter("old_sup_date"))); //default, ISO_LOCAL_DATE
		p.setPrice(Float.parseFloat(request.getParameter("old_price")));
		p.setSales(Integer.parseInt(request.getParameter("old_sales")));
		p.setTotal(Integer.parseInt(request.getParameter("old_total")));
		return p;
	}
	
	public static Pojo getByName(String cof_name) {
		List<Pojo> list = getAll("COF_NAME = TRIM('"+cof_name+"')",null);
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}

	public static List<Pojo> getAll() {
		return getAll(null, null);
	}
	
	public static List<Pojo> getAll(String where, String orderBy) {
		if(where!=null && !where.isBlank())
			where = "WHERE "+where;
		else
			where = "";
		if(orderBy!=null && !orderBy.isBlank())
			orderBy = "ORDER BY "+orderBy;
		else
			orderBy = "ORDER BY 1";
		List<Pojo> list = new ArrayList<Pojo>();
		// não precisa de close
		try (Connection con = getConn();
			 PreparedStatement ps = con.prepareStatement(
		"SELECT COF_NAME, SUP_DATE, PRICE, SALES, TOTAL FROM COFFEES "+where+" "+orderBy);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Pojo c = new Pojo();
				c.setCof_name(rs.getString("COF_NAME"));
				c.setSup_date(rs.getObject("SUP_DATE", LocalDate.class));
				c.setPrice(rs.getFloat("PRICE"));
				c.setSales(rs.getInt("SALES"));
				c.setTotal(rs.getInt("TOTAL"));
				list.add(c);
			}
		} catch (SQLException e) {
			System.err.println("Erro no Select: (" + e.getErrorCode() + ") " + e.getMessage());
			System.err.println();
		}
		return list;
	}
}
