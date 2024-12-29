package db;

import pojo.Desconto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DescontoDao {

	private static final String INSERT_SQL = "INSERT INTO Desconto (codigo, valor, nVezesUsadas) VALUES (?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Desconto SET valor = ?, nVezesUsadas = ? WHERE codigo = ?";
	private static final String DELETE_SQL = "DELETE FROM Desconto WHERE codigo = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Desconto";
	private static final String SELECT_BY_PK_SQL = "SELECT * FROM Desconto WHERE codigo = ?";

	public int save(Desconto desconto) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setInt(1, desconto.getCodigo());
			ps.setDouble(2, desconto.getValor());
			ps.setInt(3, desconto.getNVezesUsadas());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Desconto desconto) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setDouble(1, desconto.getValor());
			ps.setInt(2, desconto.getNVezesUsadas());
			ps.setInt(3, desconto.getCodigo());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(int codigo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setInt(1, codigo);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Desconto> getAll() {
		List<Desconto> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Desconto desconto = new Desconto();
				desconto.setCodigo(rs.getInt("codigo"));
				desconto.setValor(rs.getDouble("valor"));
				desconto.setNVezesUsadas(rs.getInt("nVezesUsadas"));
				list.add(desconto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Desconto getByCodigo(int codigo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_PK_SQL)) {
			ps.setInt(1, codigo);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Desconto desconto = new Desconto();
					desconto.setCodigo(rs.getInt("codigo"));
					desconto.setValor(rs.getDouble("valor"));
					desconto.setNVezesUsadas(rs.getInt("nVezesUsadas"));
					return desconto;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
