package usr;

import java.util.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class UserDao {

	public static Connection getConn() {
		return Db.getConn();
	}

	public static int insUser(User u) {
		if (u == null || u.getUsername() == null)
			return 0;
		int status = 0;
		try (Connection con = getConn();
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO user (userid, updated, profile, username, password, firstname, lastname, email, NIF) "
								+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
			ps.setBigDecimal(1, u.getUserid());
			ps.setObject(2, u.getUpdated());
			ps.setByte(3, u.getProfile());
			ps.setString(4, u.getUsername());
			ps.setString(5, u.getPassword());
			ps.setString(6, u.getFirstname());
			ps.setString(7, u.getLastname());
			ps.setString(8, u.getEmail());
			ps.setInt(9, u.getNIF());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error in Insert: (" + e.getErrorCode() + ") " + e.getMessage());
		}
		return status;
	}

	public static int updUser(User u) {
		if (u == null || u.getUserid() == null)
			return 0;
		int status = 0;
		try (Connection con = getConn();
				PreparedStatement ps = con.prepareStatement(
						"UPDATE user SET updated = ?, profile = ?, username = ?, password = ?, firstname = ?, "
								+ "lastname = ?, email = ?, NIF = ? WHERE userid = ?")) {
			ps.setObject(1, u.getUpdated());
			ps.setByte(2, u.getProfile());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getFirstname());
			ps.setString(6, u.getLastname());
			ps.setString(7, u.getEmail());
			ps.setInt(8, u.getNIF());
			ps.setBigDecimal(9, u.getUserid());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error in Update: (" + e.getErrorCode() + ") " + e.getMessage());
		}
		return status;
	}

	public static List<User> getAll() {
		List<User> list = new ArrayList<>();
		try (Connection con = getConn();
				PreparedStatement ps = con.prepareStatement(
						"SELECT userid, updated, profile, username, password, firstname, lastname, email, NIF "
								+ "FROM user ORDER BY 1")) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setUserid(rs.getBigDecimal("userid"));
				u.setUpdated(rs.getObject("updated", LocalDateTime.class));
				u.setProfile(rs.getByte("profile"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFirstname(rs.getString("firstname"));
				u.setLastname(rs.getString("lastname"));
				u.setEmail(rs.getString("email"));
				u.setNIF(rs.getInt("NIF"));
				list.add(u);
			}
		} catch (SQLException e) {
			System.err.println("Error in Select: (" + e.getErrorCode() + ") " + e.getMessage());
		}
		return list;
	}
}
