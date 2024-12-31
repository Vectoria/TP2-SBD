package db;

import pojo.LugarVeiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LugarVeiculoDao {

	private static final String INSERT_SQL = "INSERT INTO Lugar_Veiculo (localidade, piso, fila, posFila, matricula) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Lugar_Veiculo SET matricula = ? WHERE localidade = ? AND piso = ? AND fila = ? AND posFila = ?";
	private static final String DELETE_SQL = "DELETE FROM Lugar_Veiculo WHERE localidade = ? AND piso = ? AND fila = ? AND posFila = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Lugar_Veiculo";
	private static final String SELECT_BY_PK_SQL = "SELECT * FROM Lugar_Veiculo WHERE localidade = ? AND piso = ? AND fila = ? AND posFila = ?";

	public int save(LugarVeiculo lugarVeiculo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setString(1, lugarVeiculo.getLocalidade());
			ps.setInt(2, lugarVeiculo.getPiso());
			ps.setString(3, lugarVeiculo.getFila());
			ps.setInt(4, lugarVeiculo.getPosFila());
			ps.setString(5, lugarVeiculo.getMatricula());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(LugarVeiculo lugarVeiculo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, lugarVeiculo.getMatricula());
			ps.setString(2, lugarVeiculo.getLocalidade());
			ps.setInt(3, lugarVeiculo.getPiso());
			ps.setString(4, lugarVeiculo.getFila());
			ps.setInt(5, lugarVeiculo.getPosFila());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(String localidade, int piso, String fila, int posFila) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setString(1, localidade);
			ps.setInt(2, piso);
			ps.setString(3, fila);
			ps.setInt(4, posFila);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<LugarVeiculo> getAll() {
		List<LugarVeiculo> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				LugarVeiculo lugarVeiculo = new LugarVeiculo();
				lugarVeiculo.setLocalidade(rs.getString("localidade"));
				lugarVeiculo.setPiso(rs.getInt("piso"));
				lugarVeiculo.setFila(rs.getString("fila"));
				lugarVeiculo.setPosFila(rs.getInt("posFila"));
				lugarVeiculo.setMatricula(rs.getString("matricula"));
				list.add(lugarVeiculo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public LugarVeiculo getById(String localidade, int piso, String fila, int posFila) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_PK_SQL)) {
			ps.setString(1, localidade);
			ps.setInt(2, piso);
			ps.setString(3, fila);
			ps.setInt(4, posFila);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					LugarVeiculo lugarVeiculo = new LugarVeiculo();
					lugarVeiculo.setLocalidade(rs.getString("localidade"));
					lugarVeiculo.setPiso(rs.getInt("piso"));
					lugarVeiculo.setFila(rs.getString("fila"));
					lugarVeiculo.setPosFila(rs.getInt("posFila"));
					lugarVeiculo.setMatricula(rs.getString("matricula"));
					return lugarVeiculo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LugarVeiculo getByMatricula(String matricula) {
		String sql = "SELECT * FROM Lugar_Veiculo WHERE matricula = ?";
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, matricula);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					LugarVeiculo lugarVeiculo = new LugarVeiculo();
					lugarVeiculo.setLocalidade(rs.getString("localidade"));
					lugarVeiculo.setPiso(rs.getInt("piso"));
					lugarVeiculo.setFila(rs.getString("fila"));
					lugarVeiculo.setPosFila(rs.getInt("posFila"));
					lugarVeiculo.setMatricula(rs.getString("matricula"));
					return lugarVeiculo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
