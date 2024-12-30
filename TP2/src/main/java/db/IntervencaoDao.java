package db;

import pojo.Intervencao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntervencaoDao {

	private static final String INSERT_SQL = "INSERT INTO Intervencao (numKM, matricula, dhRegisto, tipoInt, custoInt) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Intervencao SET dhRegisto = ?, tipoInt = ?, custoInt = ? WHERE numKM = ? AND matricula = ?";
	private static final String DELETE_SQL = "DELETE FROM Intervencao WHERE numKM = ? AND matricula = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Intervencao";
	private static final String SELECT_BY_PK_SQL = "SELECT * FROM Intervencao WHERE numKM = ? AND matricula = ?";

	public int save(Intervencao intervencao) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setInt(1, intervencao.getNumKM());
			ps.setString(2, intervencao.getMatricula());
			ps.setTimestamp(3, Timestamp.valueOf(intervencao.getDhRegisto()));
			ps.setString(4, intervencao.getTipoInt());
			ps.setDouble(5, intervencao.getCustoInt());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Intervencao intervencao) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setTimestamp(1, Timestamp.valueOf(intervencao.getDhRegisto()));
			ps.setString(2, intervencao.getTipoInt());
			ps.setDouble(3, intervencao.getCustoInt());
			ps.setInt(4, intervencao.getNumKM());
			ps.setString(5, intervencao.getMatricula());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(int numKM, String matricula) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setInt(1, numKM);
			ps.setString(2, matricula);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Intervencao> getAll() {
		List<Intervencao> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Intervencao intervencao = new Intervencao();
				intervencao.setNumKM(rs.getInt("numKM"));
				intervencao.setMatricula(rs.getString("matricula"));
				intervencao.setDhRegisto(rs.getTimestamp("dhRegisto").toLocalDateTime());
				intervencao.setTipoInt(rs.getString("tipoInt"));
				intervencao.setCustoInt(rs.getDouble("custoInt"));
				list.add(intervencao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Intervencao getById(int numKM, String matricula) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_PK_SQL)) {
			ps.setInt(1, numKM);
			ps.setString(2, matricula);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Intervencao intervencao = new Intervencao();
					intervencao.setNumKM(rs.getInt("numKM"));
					intervencao.setMatricula(rs.getString("matricula"));
					intervencao.setDhRegisto(rs.getTimestamp("dhRegisto").toLocalDateTime());
					intervencao.setTipoInt(rs.getString("tipoInt"));
					intervencao.setCustoInt(rs.getDouble("custoInt"));
					return intervencao;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Intervencao> getByMatricula(String matricula) {
		List<Intervencao> list = new ArrayList<>();
		String query = "SELECT * FROM Intervencao WHERE matricula = ?";
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, matricula);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Intervencao intervencao = new Intervencao();
					intervencao.setNumKM(rs.getInt("numKM"));
					intervencao.setMatricula(rs.getString("matricula"));
					intervencao.setDhRegisto(rs.getTimestamp("dhRegisto").toLocalDateTime());
					intervencao.setTipoInt(rs.getString("tipoInt"));
					intervencao.setCustoInt(rs.getDouble("custoInt"));
					list.add(intervencao);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
