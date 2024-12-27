package usr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Acesso á base de dados
 */

public class Db {
	public static final String username = "root";
	public static final String password = "fixe";
	static final String database = "tp1";	// cria a base de dados se não existir
	public static final String localhost = "localhost";  // máquina onde está o MySQL
	static private String drv = "com.mysql.cj.jdbc.Driver";  // indica o driver que vai carregar
	
	static boolean encript = true;   // cifra a password na base de dados 

	//Class.forName("com.mysql.cj.jdbc.Driver");
	//dbURL = "jdbc:mysql://localhost:port/db_name?useTimezone=true&serverTimezone=UTC";
	public static Connection getConn(final String userName, final String password, final String database) {
		final String MySQLServer = "jdbc:mysql://"+localhost+":3306";
		final String MySQLParameters = "?useTimezone=true&serverTimezone=UTC&allowMultiQueries=true";
		String URL = MySQLServer + MySQLParameters;
		if (drv!=null)
			try {
				Class.forName(drv);
				drv = null;
			} catch (ClassNotFoundException cnfe) {
				System.out.println("Não é possível carregar o Driver JDBC '"
						+ drv + ",");
				System.out.println("Verifique a propriedade classpath");
			} 
		if(database!=null && !database.equals(""))
			URL = MySQLServer + "/" + database + MySQLParameters;
		try {
			return DriverManager.getConnection(URL, userName, password);
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("A ligação falhou: "+e.getMessage());
		}
		return null;
	}
	
	public static Connection getConn(final String bd) {
		return getConn(username, password, bd);
	}
	
	public static Connection getConn() {
		return getConn(username, password, database);
	}
	
	public static boolean existsDb(String dbName) {
		ResultSet resultSet=null;
		boolean existe=false;
		try (Connection conn = getConn(null); Statement stmt = conn.createStatement();) {
			resultSet = conn.getMetaData().getCatalogs();
			while (resultSet.next()) {
				String databaseName = resultSet.getString(1);
				if (databaseName.equals(dbName)) {
					existe=true;
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
	
	private static boolean createDB() {
	      try(Connection conn = getConn(null);
	         Statement stmt = conn.createStatement();
	      ) {		      
	         String sql = "CREATE DATABASE IF NOT EXISTS "+database;
	         stmt.executeUpdate(sql);
	         System.out.println("Database '"+database+"' created successfully..."); 
	         return true;
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } 
	      return false;
	}

	private static boolean createTB() {
		try(Connection conn = getConn();
		         Statement stmt = conn.createStatement();
		      ) {// email NOT LIKE '%_@_%._%'
			String sql = "DROP TABLE IF EXISTS user; "+
		        		  	   "CREATE TABLE user ("+
		        		    		"userid BIGINT NOT NULL AUTO_INCREMENT,"+
		        		    		"updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"+
		        		    		"blocked BOOLEAN DEFAULT true,"+							// inicialmente bloqueado
		        		    		"profile SMALLINT DEFAULT 0 CHECK(profile<10),"+	    	// sem perfil atribuido
		        		    		"username CHAR(10) NOT NULL CHECK (username REGEXP '[a-zA-Z1-9]'),"+
		        		    		"password VARCHAR("+(encript?"64":"20")+") NOT NULL,"+      //md5 -> 32; SHA2('password', 256)->64;
		        		    		"firstname VARCHAR(60) NOT NULL CHECK (firstname REGEXP '[a-zA-Z ''-]'),"+
		        		    		"lastname VARCHAR(60) NOT NULL CHECK(lastname REGEXP '[a-zA-Z ''-]'),"+
		        		    		"email VARCHAR(45) NULL CHECK(email REGEXP '^[a-zA-Z0-9][+a-zA-Z0-9._-]*@[a-zA-Z0-9][a-zA-Z0-9._-]*[a-zA-Z0-9]*\\.[a-zA-Z]{2,4}$'),"+
		        		    		"gender CHAR(1) DEFAULT 'X' CHECK(gender in ('X','M','F')),"+
		        		    		"PRIMARY KEY (userid),"+
		        		    		"UNIQUE (username),"+
		        		    		"UNIQUE (email)"+
		        		    		")";

		         stmt.executeUpdate(sql);
		         System.out.println("Table 'user' created in the database...");
		         return true;
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return false;
	}
	
	private static boolean createTG() {
		try (Connection conn = getConn();
			Statement stmt = conn.createStatement();) {
			String sql = "DROP TRIGGER IF EXISTS iEncript;" + 
						 "DROP TRIGGER IF EXISTS uEncript;";
			if (encript) {
				stmt.executeUpdate(sql);
				sql =   "CREATE TRIGGER uEncript " + 
						"BEFORE UPDATE ON user " + 
							"FOR EACH ROW " + 
						"BEGIN " + 
							"IF (SELECT length(OLD.password)<>64) THEN "+
								"SET NEW.password = SHA2(TRIM(NEW.password),256); "+
							"END IF;"+ 
						"END; " + 
						"CREATE TRIGGER iEncript " + 
						"BEFORE INSERT ON user " + 
							"FOR EACH ROW " + 
						"BEGIN " + 
							"SET NEW.password = SHA2(TRIM(NEW.password),256); "+
						"END";
				stmt.executeUpdate(sql);
				System.out.println("Triggers 'iEncript' and 'uEncript' created in the database...");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean createFN() {
		try(Connection conn = getConn();
		         Statement stmt = conn.createStatement();
		      ) {		      
		          String sql = "DROP FUNCTION IF EXISTS fnCheck;"+      		  
		        		  	   "CREATE FUNCTION fnCheck (usern CHAR(10), pass VARCHAR(64))"+
		        		  			"RETURNS BIGINT "+
		        		  			"NO SQL "+
		        		  		"BEGIN "+
		        		  			"RETURN (SELECT userid FROM user "+
		        		  				"WHERE NOT blocked AND BINARY username=usern AND BINARY password="+
		        		  				((encript) ? "SHA2(TRIM(pass), 256)":"TRIM(pass)")+");"+
		        		  		"END";
		         stmt.executeUpdate(sql);
		         System.out.println("Function 'createFN' created in the database...");
		         return true;
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } 
		return false;
	}
	
	private static boolean loadUsers() {
	    try (Connection conn = getConn();
	         Statement stmt = conn.createStatement();
	    ) {
	        String sql = "INSERT INTO user (username, password, firstname, lastname, email, NIF, profile) VALUES " +
	                "('cartwheel', 'p1', 'Santiago', 'Castro Pacheco', 'San.Pacheco@mail.pt', 290123876, 1)," +
	                "('milkshake', 'p2', 'Veríssimo', 'Simões Silvestre', 'Ver.Silvestre@mail.us', 304020031, 1)," +
	                "('cranberry', 'p3', 'Ismael', 'Cravo Abril', 'Ism.Abril@mail.es', 304020032, 1)," +
	                "('gandalf', 'p4', 'Camilo', 'Rosa Braga', 'Cam.Braga@mail.es', 304020033, 1)," +
	                "('opera', 'p5', 'Gustavo', 'Pascoal Pires', 'Gus.Pires@mail.fr', 409376182, 1)," +
	                "('pegasus', 'p6', 'Odilon', 'Nogueira Dantas', 'Od.Dantas@mail.it', 120398654, 2)," +
	                "('deneb', 'p7', 'Olga', 'Faria Luz', 'Ol.Luz@bustayes.com', 120918267, 2)," +
	                "('luzkira', 'p8', 'Luzia', 'Gilda Reis', 'Luz.Reis@mail.pt', 209837123, 2)," +
	                "('smoke', 'p9', 'Felicidade', 'Varejão Amaral', 'Fel.Amaral@mail.es', 304020030, 2)," +
	                "('bagel', 'p10', 'Luísa', 'Carriço d\'Almeida', 'Lu.Almeida@mail.es', 304020032, 2)," +
	                "('rush', 'p11', 'Natacha', 'Caetano Prego', 'Nat.Prego@mail.es', 304020033, 2)," +
	                "('bird', 'p12', 'Isabela', 'Peres da Ponte', 'Isa.Ponte@mail.pt', NULL, 0)";
	        stmt.executeUpdate(sql);

	        System.out.println("Users loaded into database...");
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public static void init(boolean force) {
		init(force, true);
	}
	
	public static void init(boolean force, boolean encript)  {
		if(!existsDb(database) || force) {
			Db.encript=encript;				// password cifra 'true'
			if(createDB() && createTB() && createTG() && loadUsers() && createFN())
				if(encript)
					System.out.println("Database ready with encrypted password!");
				else
					System.out.println("Database ready without encrypted password!");
		}
		else 
			System.out.println("The database was already created...");
	}
	
	public static void main(String[] args) throws SQLException {
		Db.init(true, false);  // força a criação da BD com senha em claro
		// Db.init(true, true);  // força a criação da BD com senha cifrada
	}

}
