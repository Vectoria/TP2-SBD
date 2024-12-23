//package one;
//
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import db.Db;
//
///**
// * Aplicaçãoo de demonstração do JDBC. 
// * We will create one table in our example database. 
// * This table, COFFEES , contains the essential information about the coffees sold at The Coffee Break, 
// * including the coffee names, their prices, the number of pounds sold the current week, 
// * and the number of pounds sold to date. The table COFFEES , 
// * which we describe in more detail later, is shown here: 
// * COF_NAME, SUP_DATE, PRICE, SALES, TOTAL
// * Colombian, NULL, 7.99, 0, 0
// * French_Roast, NULL, 8.99, 0, 0
// * Espresso, 150, NULL, 0, 0
// * Colombian_Decaf, NULL, 8.99, 0, 0
// * French_Roast_Decaf, NULL, 9.99, 0, 0
// * 
// * exemplo clássico atualizado em:
// * https://docs.oracle.com/javase/tutorial/jdbc/basics/tables.html
// * 
// * PFilipe, dezembro 2021
// * 
// */
//public class Init extends Db{
//	static Connection con;
//	static Statement stmt;
//	static ResultSet rs;
//
//	public static void main(String[] args) {
//		
//		// step 1: load driver
//		loadDriver();  // opcional
//		
//		// step 3: establish connection
//		makeConnection();
//		
//		// create a table
//		createTable();
//
//		// insert data
//		insertData();
//
//		// use precompiled statement to update data
//		usePreparedStatement();
//
//		// retrieve data
//		retrieveData();
//
//		// close all resources
//		closeAll();
//	}
//
//	// load a driver
//	static void loadDriver() {
//		try {
//			// step 2: Define connection URL
//			// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  // driver ODBC<-Ponte
//			Class.forName(drv);
//			System.out.println("JDBC Driver loaded: "+drv);
//		} catch (java.lang.ClassNotFoundException e) {
//			System.err.print("ClassNotFoundException: ");
//			System.err.println(e.getMessage());
//		} 
//	}
//
//	// make a connection step 3: establish connection
//	static void makeConnection() {
//		
//		/*private static String server="localhost";
//		private static String database="BD";
//		private static String user="root";
//		private static String password="root";
//		
//		public static String DRV = "com.mysql.cj.jdbc.Driver";
//		public static String URL = "jdbc:mysql://"+server+"/"+database+
//								   "?user="+user+"&password="+password+
//								   "&useLegacyDatetimeCode=false&serverTimezone=Europe/Lisbon"; */
//		
//		try {
//			con = DriverManager.getConnection(url,username,password);	
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			System.out.println("Database connection: " + ex.getMessage());
//			System.out.println();
//			
//		}
//	}
//
//	// create a table
//	static void createTable() {
//		
//		String createString = 
//		"create table COFFEES "
//			+ "(COF_NAME VARCHAR(32), " 
//			+ "SUP_DATE DATE, "
//			+ "PRICE FLOAT, " 
//			+ "SALES INTEGER, " 
//			+ "TOTAL INTEGER, "
//			+ "CONSTRAINT pk_coffees PRIMARY KEY (COF_NAME))";
//		try {
//			// step 4: create a statement
//			stmt = con.createStatement();
//			// step 5: execute a query or update.
//			try {
//				stmt.execute("drop table COFFEES");// if exists, drop it, get new one
//				System.out.println("Drop table coffes...");
//			} catch (SQLException ex) {
//				System.out.println("Drop table: " + ex.getMessage());
//			}
//			stmt.executeUpdate(createString);
//
//		} catch (SQLException ex) {
//			System.err.println("Create Table: " + ex.getMessage());
//		}
//	}
//
//	// insert data to table COFFEES
//	static void insertData() {
//		try {
//			stmt.executeUpdate("INSERT INTO COFFEES "
//					+ "VALUES ('Colombian', NULL, 7.99, 0, 50)");
//			stmt.executeUpdate("INSERT INTO COFFEES "
//					+ "VALUES ('French Roast', NULL, 8.99, 0, 1000)");
//			stmt.executeUpdate("INSERT INTO COFFEES "
//					+ "VALUES ('Espresso', NULL, 5.99, 0, 35)");
//			stmt.executeUpdate("INSERT INTO COFFEES "
//					+ "VALUES ('Colombian Decaf', NULL, 4.99, 0, 49)");
//			stmt.executeUpdate("INSERT INTO COFFEES "
//					+ "VALUES ('French Roast Decaf', NULL,6.99, 0, 32)");
//
//		} catch (SQLException ex) {
//			System.err.println("InsertData: " + ex.getMessage());
//		}
//	}
//
//	// use PreparedStatement to precompile sql statement
//	static void usePreparedStatement() {
//		try {
//			PreparedStatement updateSales;
//			String updateString = "update COFFEES "
//				+ "set SALES = ?, SUP_DATE = ? where COF_NAME like ?";
//			updateSales = con.prepareStatement(updateString);
//			int[] salesForWeek = { 175, 150, 60, 155, 90 };
//			//formatting date in Java using SimpleDateFormat
//	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
//			String[] last_dates = {"01/01/2022", "01/02/2021", "01/03/2022", "01/04/2021", "01/05/2022" };
//			String[] coffees = { "Colombian", "French_Roast", "Espresso","Colombian_Decaf", "French_Roast_Decaf" };
//			int len = coffees.length;
//			for (int i = 0; i < len; i++) {
//				updateSales.setInt(1, salesForWeek[i]);
//				
//    			java.util.Date apptDay=DATE_FORMAT.parse(last_dates[i]);
//    			java.sql.Date sqlDate = new java.sql.Date(apptDay.getTime());
//				
//				updateSales.setDate(2,sqlDate);				
//				updateSales.setString(3, coffees[i]);
//				updateSales.executeUpdate();
//			}
//		} 	catch (ParseException ex) {
//			System.err.println("Erro de sintaxe na data: " + ex.getMessage());
//			ex.printStackTrace();
//
//		}
//			catch (SQLException ex) {
//			System.err.println("UsePreparedStatement: " + ex.getMessage());
//			ex.printStackTrace();
//		}
//	}
//
//	// retrieve data from table COFFEES
//	static void retrieveData() {
//		try {
//			String gdta = "SELECT COF_NAME, SUP_DATE, PRICE, SALES, TOTAL FROM COFFEES WHERE PRICE < 9.00";
//			// step 6: process the results.
//			System.out.println("\r\nCoffees List:\r\n");
//			rs = stmt.executeQuery(gdta);
//			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
//			while (rs.next()) {
//				String s = rs.getString("COF_NAME");
//				java.sql.Date sqlDate = rs.getDate("SUP_DATE");
//				float n = rs.getFloat("PRICE");
//				int sales = rs.getInt("SALES");
//				int total = rs.getInt("TOTAL");
//    			
//				System.out.println(s + " (" + n+") since "+DATE_FORMAT.format(sqlDate)+" -> "+sales+" : "+total);
//			}
//			System.out.println("\r\nEnd of List!\r\n");
//		} catch (SQLException ex) {
//			System.err.println("RetrieveData: " + ex.getMessage());
//		}
//	}
//
//	// close statement and connection
//	// step 7: close connection, etc.
//	static void closeAll() {
//		try {
//			stmt.close();
//			con.close();
//		} catch (SQLException ex) {
//			System.err.println("closeAll: " + ex.getMessage());
//		}
//	}
//}
