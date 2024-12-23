package db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojo.Condutor;

public class CondutorDao {
	private static final String INSERT_SQL = "INSERT INTO Condutor (condutorNIF, numID, dataNascimento, reputacao, dataEmissao, dataValidade, tipoHab) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Condutor SET numID = ?, dataNascimento = ?, reputacao = ?, dataEmissao = ?, dataValidade = ?, tipoHab = ? WHERE condutorNIF = ?";
	private static final String DELETE_SQL = "DELETE FROM Condutor WHERE condutorNIF = ?";
	private static final String SELECT_ALL_SQL = "SELECT condutorNIF, numID, dataNascimento, reputacao, dataEmissao, dataValidade, tipoHab FROM Condutor";
	private static final String SELECT_BY_ID_SQL = "SELECT condutorNIF, numID, dataNascimento, reputacao, dataEmissao, dataValidade, tipoHab FROM Condutor WHERE condutorNIF = ?";

	public int save(Condutor condutor) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setInt(1, condutor.getCondutorNIF());
			ps.setInt(2, condutor.getNumID());
			ps.setDate(3, Date.valueOf(condutor.getDataNascimento()));
			ps.setObject(4, condutor.getReputacao(), Types.INTEGER);
			ps.setObject(5, condutor.getDataEmissao() != null ? Date.valueOf(condutor.getDataEmissao()) : null,
					Types.DATE);
			ps.setObject(6, condutor.getDataValidade() != null ? Date.valueOf(condutor.getDataValidade()) : null,
					Types.DATE);
			ps.setString(7, condutor.getTipoHab());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Condutor condutor) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setInt(1, condutor.getNumID());
			ps.setDate(2, Date.valueOf(condutor.getDataNascimento()));
			ps.setObject(3, condutor.getReputacao(), Types.INTEGER);
			ps.setObject(4, condutor.getDataEmissao() != null ? Date.valueOf(condutor.getDataEmissao()) : null,
					Types.DATE);
			ps.setObject(5, condutor.getDataValidade() != null ? Date.valueOf(condutor.getDataValidade()) : null,
					Types.DATE);
			ps.setString(6, condutor.getTipoHab());
			ps.setInt(7, condutor.getCondutorNIF());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(int condutorNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setInt(1, condutorNIF);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Condutor> getAll() {
		List<Condutor> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Condutor condutor = new Condutor();
				condutor.setCondutorNIF(rs.getInt("condutorNIF"));
				condutor.setNumID(rs.getInt("numID"));
				condutor.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
				condutor.setReputacao(rs.getObject("reputacao", Integer.class));
				condutor.setDataEmissao(
						rs.getObject("dataEmissao", Date.class) != null ? rs.getDate("dataEmissao").toLocalDate()
								: null);
				condutor.setDataValidade(
						rs.getObject("dataValidade", Date.class) != null ? rs.getDate("dataValidade").toLocalDate()
								: null);
				condutor.setTipoHab(rs.getString("tipoHab"));
				list.add(condutor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Condutor getById(int condutorNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
			ps.setInt(1, condutorNIF);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Condutor condutor = new Condutor();
					condutor.setCondutorNIF(rs.getInt("condutorNIF"));
					condutor.setNumID(rs.getInt("numID"));
					condutor.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
					condutor.setReputacao(rs.getObject("reputacao", Integer.class));
					condutor.setDataEmissao(
							rs.getObject("dataEmissao", Date.class) != null ? rs.getDate("dataEmissao").toLocalDate()
									: null);
					condutor.setDataValidade(
							rs.getObject("dataValidade", Date.class) != null ? rs.getDate("dataValidade").toLocalDate()
									: null);
					condutor.setTipoHab(rs.getString("tipoHab"));
					return condutor;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
