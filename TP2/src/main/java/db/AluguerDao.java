package db;

import pojo.Aluguer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AluguerDao {
	private static final String INSERT_SQL = "INSERT INTO Aluguer (dhInicio, dhFim, clienteNIF, condutorNIF, matricula, localidade, dhEntrega, custoFinal, moedaPref, codigo, dataTarifa, valorDiaUtil, valorDiaNaoUtil, qualidadeServicoAluguer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Aluguer SET dhEntrega = ?, custoFinal = ?, moedaPref = ?, codigo = ?, dataTarifa = ?, valorDiaUtil = ?, valorDiaNaoUtil = ?, qualidadeServicoAluguer = ? WHERE dhInicio = ? AND dhFim = ? AND clienteNIF = ?";
	private static final String DELETE_SQL = "DELETE FROM Aluguer WHERE dhInicio = ? AND dhFim = ? AND clienteNIF = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Aluguer";
	private static final String SELECT_BY_PK_SQL = "SELECT * FROM Aluguer WHERE dhInicio = ? AND dhFim = ? AND clienteNIF = ?";

	public int save(Aluguer aluguer) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setTimestamp(1, Timestamp.valueOf(aluguer.getDhInicio()));
			ps.setTimestamp(2, Timestamp.valueOf(aluguer.getDhFim()));
			ps.setInt(3, aluguer.getClienteNIF());
			ps.setObject(4, aluguer.getCondutorNIF(), Types.INTEGER);
			ps.setString(5, aluguer.getMatricula());
			ps.setString(6, aluguer.getLocalidade());
			ps.setTimestamp(7, aluguer.getDhEntrega() != null ? Timestamp.valueOf(aluguer.getDhEntrega()) : null);
			ps.setBigDecimal(8, aluguer.getCustoFinal());
			ps.setString(9, aluguer.getMoedaPref());
			ps.setObject(10, aluguer.getCodigo(), Types.INTEGER);
			ps.setDate(11, aluguer.getDataTarifa() != null ? Date.valueOf(aluguer.getDataTarifa()) : null);
			ps.setBigDecimal(12, aluguer.getValorDiaUtil());
			ps.setBigDecimal(13, aluguer.getValorDiaNaoUtil());
			ps.setString(14, aluguer.getQualidadeServicoAluguer());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Aluguer aluguer) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setTimestamp(1, aluguer.getDhEntrega() != null ? Timestamp.valueOf(aluguer.getDhEntrega()) : null);
			ps.setBigDecimal(2, aluguer.getCustoFinal());
			ps.setString(3, aluguer.getMoedaPref());
			ps.setObject(4, aluguer.getCodigo(), Types.INTEGER);
			ps.setDate(5, aluguer.getDataTarifa() != null ? Date.valueOf(aluguer.getDataTarifa()) : null);
			ps.setBigDecimal(6, aluguer.getValorDiaUtil());
			ps.setBigDecimal(7, aluguer.getValorDiaNaoUtil());
			ps.setString(8, aluguer.getQualidadeServicoAluguer());
			ps.setTimestamp(9, Timestamp.valueOf(aluguer.getDhInicio()));
			ps.setTimestamp(10, Timestamp.valueOf(aluguer.getDhFim()));
			ps.setInt(11, aluguer.getClienteNIF());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Timestamp dhInicio, Timestamp dhFim, int clienteNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setTimestamp(1, dhInicio);
			ps.setTimestamp(2, dhFim);
			ps.setInt(3, clienteNIF);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Aluguer> getAll() {
		List<Aluguer> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(mapResultSetToAluguer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Aluguer getById(Timestamp dhInicio, Timestamp dhFim, int clienteNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_PK_SQL)) {
			ps.setTimestamp(1, dhInicio);
			ps.setTimestamp(2, dhFim);
			ps.setInt(3, clienteNIF);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToAluguer(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Aluguer mapResultSetToAluguer(ResultSet rs) throws SQLException {
		Aluguer aluguer = new Aluguer();
		aluguer.setDhInicio(rs.getTimestamp("dhInicio").toLocalDateTime());
		aluguer.setDhFim(rs.getTimestamp("dhFim").toLocalDateTime());
		aluguer.setClienteNIF(rs.getInt("clienteNIF"));
		aluguer.setCondutorNIF(rs.getObject("condutorNIF") != null ? rs.getInt("condutorNIF") : null);
		aluguer.setMatricula(rs.getString("matricula"));
		aluguer.setLocalidade(rs.getString("localidade"));
		aluguer.setDhEntrega(
				rs.getTimestamp("dhEntrega") != null ? rs.getTimestamp("dhEntrega").toLocalDateTime() : null);
		aluguer.setCustoFinal(rs.getBigDecimal("custoFinal"));
		aluguer.setMoedaPref(rs.getString("moedaPref"));
		aluguer.setCodigo(rs.getObject("codigo") != null ? rs.getInt("codigo") : null);
		aluguer.setDataTarifa(rs.getDate("dataTarifa") != null ? rs.getDate("dataTarifa").toLocalDate() : null);
		aluguer.setValorDiaUtil(rs.getBigDecimal("valorDiaUtil"));
		aluguer.setValorDiaNaoUtil(rs.getBigDecimal("valorDiaNaoUtil"));
		aluguer.setQualidadeServicoAluguer(rs.getString("qualidadeServicoAluguer"));
		return aluguer;
	}

	private static final String FIND_CONDUCTOR_SQL = "SELECT condutorNIF FROM Aluguer "
			+ "WHERE matricula = ? AND ? BETWEEN dhInicio AND dhFim";

	public Integer findConductorByVehicleAndDate(String matricula, LocalDateTime date) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(FIND_CONDUCTOR_SQL)) {
			ps.setString(1, matricula);
			ps.setTimestamp(2, Timestamp.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getObject("condutorNIF", Integer.class); // Nullable
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // No conductor found
	}
}
