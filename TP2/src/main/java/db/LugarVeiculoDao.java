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

	public List<String> getAllLocalidades() {
		List<String> localidades = new ArrayList<>();
		String sql = "SELECT DISTINCT localidade FROM Lugar_Veiculo";

		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				localidades.add(rs.getString("localidade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return localidades;
	}

	public List<String> getAllMarcas() {
		List<String> marcas = new ArrayList<>();
		String sql = "SELECT DISTINCT marca FROM Veiculo WHERE matricula IN (SELECT matricula FROM Lugar_Veiculo)";

		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				marcas.add(rs.getString("marca"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return marcas;
	}

	public List<String> getModelosByMarca(String marca) {
		List<String> modelos = new ArrayList<>();
		String sql = "SELECT DISTINCT modelo FROM Veiculo WHERE marca = ? AND matricula IN (SELECT matricula FROM Lugar_Veiculo)";

		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, marca);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					modelos.add(rs.getString("modelo"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return modelos;
	}

	public List<LugarVeiculo> getVeiculosDisponiveis(String localidade, String modelo) {
		List<LugarVeiculo> veiculos = new ArrayList<>();
		String sql = "SELECT lv.localidade, lv.piso, lv.fila, lv.posFila, v.matricula, v.modelo, v.cor "
				+ "FROM Lugar_Veiculo lv " + "JOIN Veiculo v ON lv.matricula = v.matricula "
				+ "WHERE lv.localidade = ? AND v.modelo = ?";

		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, localidade);
			ps.setString(2, modelo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					LugarVeiculo lugar = new LugarVeiculo();
					lugar.setLocalidade(rs.getString("localidade"));
					lugar.setPiso(rs.getInt("piso"));
					lugar.setFila(rs.getString("fila"));
					lugar.setPosFila(rs.getInt("posFila"));
					lugar.setMatricula(rs.getString("matricula"));
					veiculos.add(lugar);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return veiculos;
	}
}
