package db;

import pojo.Veiculo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDao {

	private static final String INSERT_SQL = "INSERT INTO Veiculo (matricula, midia, cor, numLugares, capacidadeCarga, numPortas, numEixos, potencia, combustivel, nomeMod, nomeMarca, dataTarifa, valorDiaUtil, valorDiaNaoUtil, tipoHab) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Veiculo SET midia = ?, cor = ?, numLugares = ?, capacidadeCarga = ?, numPortas = ?, numEixos = ?, potencia = ?, combustivel = ?, nomeMod = ?, nomeMarca = ?, dataTarifa = ?, valorDiaUtil = ?, valorDiaNaoUtil = ?, tipoHab = ? WHERE matricula = ?";
	private static final String DELETE_SQL = "DELETE FROM Veiculo WHERE matricula = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Veiculo";
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM Veiculo WHERE matricula = ?";

	public int save(Veiculo veiculo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setString(1, veiculo.getMatricula());
			ps.setString(2, veiculo.getMidia());
			ps.setString(3, veiculo.getCor());
			ps.setInt(4, veiculo.getNumLugares());
			ps.setDouble(5, veiculo.getCapacidadeCarga());
			ps.setInt(6, veiculo.getNumPortas());
			ps.setInt(7, veiculo.getNumEixos());
			ps.setInt(8, veiculo.getPotencia());
			ps.setString(9, veiculo.getCombustivel());
			ps.setString(10, veiculo.getNomeMod());
			ps.setString(11, veiculo.getNomeMarca());
			ps.setObject(12, veiculo.getDataTarifa() != null ? Date.valueOf(veiculo.getDataTarifa()) : null);
			ps.setDouble(13, veiculo.getValorDiaUtil());
			ps.setDouble(14, veiculo.getValorDiaNaoUtil());
			ps.setString(15, veiculo.getTipoHab());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Veiculo veiculo) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, veiculo.getMidia());
			ps.setString(2, veiculo.getCor());
			ps.setInt(3, veiculo.getNumLugares());
			ps.setDouble(4, veiculo.getCapacidadeCarga());
			ps.setInt(5, veiculo.getNumPortas());
			ps.setInt(6, veiculo.getNumEixos());
			ps.setInt(7, veiculo.getPotencia());
			ps.setString(8, veiculo.getCombustivel());
			ps.setString(9, veiculo.getNomeMod());
			ps.setString(10, veiculo.getNomeMarca());
			ps.setObject(11, veiculo.getDataTarifa() != null ? Date.valueOf(veiculo.getDataTarifa()) : null);
			ps.setDouble(12, veiculo.getValorDiaUtil());
			ps.setDouble(13, veiculo.getValorDiaNaoUtil());
			ps.setString(14, veiculo.getTipoHab());
			ps.setString(15, veiculo.getMatricula());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Veiculo getById(String matricula) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
			ps.setString(1, matricula);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Veiculo veiculo = new Veiculo();
					veiculo.setMatricula(rs.getString("matricula"));
					veiculo.setMidia(rs.getString("midia"));
					veiculo.setCor(rs.getString("cor"));
					veiculo.setNumLugares(rs.getInt("numLugares"));
					veiculo.setCapacidadeCarga(rs.getDouble("capacidadeCarga"));
					veiculo.setNumPortas(rs.getInt("numPortas"));
					veiculo.setNumEixos(rs.getInt("numEixos"));
					veiculo.setPotencia(rs.getInt("potencia"));
					veiculo.setCombustivel(rs.getString("combustivel"));
					veiculo.setNomeMod(rs.getString("nomeMod"));
					veiculo.setNomeMarca(rs.getString("nomeMarca"));
					veiculo.setDataTarifa(
							rs.getDate("dataTarifa") != null ? rs.getDate("dataTarifa").toLocalDate() : null);
					veiculo.setValorDiaUtil(rs.getDouble("valorDiaUtil"));
					veiculo.setValorDiaNaoUtil(rs.getDouble("valorDiaNaoUtil"));
					veiculo.setTipoHab(rs.getString("tipoHab"));
					return veiculo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Veiculo> getAll() {
		List<Veiculo> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setMatricula(rs.getString("matricula"));
				veiculo.setMidia(rs.getString("midia"));
				veiculo.setCor(rs.getString("cor"));
				veiculo.setNumLugares(rs.getInt("numLugares"));
				veiculo.setCapacidadeCarga(rs.getDouble("capacidadeCarga"));
				veiculo.setNumPortas(rs.getInt("numPortas"));
				veiculo.setNumEixos(rs.getInt("numEixos"));
				veiculo.setPotencia(rs.getInt("potencia"));
				veiculo.setCombustivel(rs.getString("combustivel"));
				veiculo.setNomeMod(rs.getString("nomeMod"));
				veiculo.setNomeMarca(rs.getString("nomeMarca"));
				veiculo.setDataTarifa(rs.getDate("dataTarifa") != null ? rs.getDate("dataTarifa").toLocalDate() : null);
				veiculo.setValorDiaUtil(rs.getDouble("valorDiaUtil"));
				veiculo.setValorDiaNaoUtil(rs.getDouble("valorDiaNaoUtil"));
				veiculo.setTipoHab(rs.getString("tipoHab"));
				list.add(veiculo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
