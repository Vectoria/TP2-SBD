package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Db {
	//	for how to set up data source see below.
	public static final String username = "root";
	public static final String password = "fixe";
	private static final String database = "tp1";					
	private static final String localhost = "localhost";  			// máquina onde está o MySQL
	
	static final String drv = "com.mysql.cj.jdbc.Driver";  	// indica o driver que vai carregar
	private static final String MySQLServer = "jdbc:mysql://" + localhost + ":3306";
	private static final String MySQLParameters = "?useTimezone=true&serverTimezone=UTC&allowMultiQueries=true";
	static String url = MySQLServer + "/" + database + MySQLParameters;
	private static boolean loadDrv=false;
	
	static Connection getConn(final String userName, final String password, final String database) {
		if (!loadDrv) {
			try {
				Class.forName(drv);
				loadDrv=true;
				System.out.println("Carregou o Driver JDBC: " + drv );
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Não é possível carregar o Driver JDBC: " + drv );
				System.out.println("Verifique a propriedade classpath");
				return null;
			}
			if(!existsDb(database)) {
				System.out.println("Vai criar a base de dados: " + database);
				createDb(database);
				System.out.println("Vai carregar os dados e apresentar na consola! ");
				//Init.main(null);
			}
		}
		String URL = url;
		if (database == null || database.isBlank())
			URL =  MySQLServer + MySQLParameters;
		try {
			return DriverManager.getConnection(URL, userName, password);
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("A ligação falhou: " + e.getMessage());
		}
		return null;
	}

	static Connection getConn(final String bd) {
		return getConn(username, password, bd);
	}

	static Connection getConn() {
		return getConn(username, password, database);
	}

	static boolean existsDb(String dbName) {
		ResultSet resultSet = null;
		boolean existe = false;
		try (Connection conn = getConn(null); Statement stmt = conn.createStatement();) {
			resultSet = conn.getMetaData().getCatalogs();
			while (resultSet.next()) {
				String databaseName = resultSet.getString(1);
				if (databaseName.equals(dbName)) {
					existe = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}

	static boolean createDb(String database) {
		if(!existsDb(database))
			try (Connection conn = getConn(null); Statement stmt = conn.createStatement();) {
				String sql = "CREATE DATABASE " + database;
				stmt.executeUpdate(sql);
				System.out.println("Database '" + database + "' created successfully...");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		else
			System.out.println("Database '" + database + "' already existed and will be used...");
		return false;
	}
}
