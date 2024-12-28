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
                     "INSERT INTO user (userid, updated, profile, username, password, firstname, lastname, email, nif) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setBigDecimal(1, u.getUserid());
            ps.setObject(2, u.getUpdated());
            ps.setByte(3, u.getProfile());
            ps.setString(4, u.getUsername());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getFirstname());
            ps.setString(7, u.getLastname());
            ps.setString(8, u.getEmail());
            ps.setInt(9, u.getNif());
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
                     "UPDATE user SET updated = ?, profile = ?, username = ?, password = ?, firstname = ?, lastname = ?, email = ?, nif = ? " +
                             "WHERE userid = ?")) {
            ps.setObject(1, u.getUpdated());
            ps.setByte(2, u.getProfile());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getFirstname());
            ps.setString(6, u.getLastname());
            ps.setString(7, u.getEmail());
            ps.setInt(8, u.getNif());
            ps.setBigDecimal(9, u.getUserid());
            status = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in Update: (" + e.getErrorCode() + ") " + e.getMessage());
        }
        return status;
    }

    public static int delUser(User u) {
        if (u == null || u.getUserid() == null)
            return 0;
        return del("userid", u.getUserid().toString());
    }

    private static int del(String atributo, String valor) {
        if (valor == null || valor.isBlank())
            return 0;
        int status = 0;
        try (Connection con = getConn();
             PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE " + atributo + " = ?")) {
            ps.setString(1, valor);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in Delete: (" + e.getErrorCode() + ") " + e.getMessage());
        }
        return status;
    }

    public static User getById(BigDecimal userid) {
        if (userid == null)
            return null;
        return get("userid = " + userid.toString());
    }

    public static User getByName(String username) {
        if (username == null || username.isBlank())
            return null;
        return get("username = '" + username + "'");
    }

    public static User getByEmail(String email) {
        if (email == null || email.isBlank())
            return null;
        return get("email = '" + email + "'");
    }

    private static User get(String where) {
        List<User> users = getAll(where, null);
        if (users.size() == 1)
            return users.get(0);
        return null;
    }

    public static List<User> getAll() {
        return getAll("", "");
    }

    public static List<User> getAll(String where, String orderBy) {
        if (where != null && !where.isBlank())
            where = "WHERE " + where;
        else
            where = "";
        if (orderBy != null && !orderBy.isBlank())
            orderBy = "ORDER BY " + orderBy;
        else
            orderBy = "ORDER BY 1";

        List<User> list = new ArrayList<>();
        try (Connection con = getConn();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT userid, updated, profile, username, password, firstname, lastname, email, nif " +
                             "FROM user " + where + " " + orderBy)) {
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
                u.setNif(rs.getInt("nif"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error in Select: (" + e.getErrorCode() + ") " + e.getMessage());
        }
        return list;
    }
}
