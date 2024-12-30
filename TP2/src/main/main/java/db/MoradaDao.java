package db;

import pojo.Morada;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoradaDao {
	private static final String INSERT_SQL = "INSERT INTO Morada (rua, codigoPostalP1, codigoPostalP2, numeroPorta, nomeFreguesia, nomeConcelho, nomeDistrito) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Morada SET nomeFreguesia = ?, nomeConcelho = ?, nomeDistrito = ? WHERE rua = ? AND codigoPostalP1 = ? AND codigoPostalP2 = ? AND numeroPorta = ?";
	private static final String DELETE_SQL = "DELETE FROM Morada WHERE rua = ? AND codigoPostalP1 = ? AND codigoPostalP2 = ? AND numeroPorta = ?";
	private static final String SELECT_ALL_SQL = "SELECT rua, codigoPostalP1, codigoPostalP2, numeroPorta, nomeFreguesia, nomeConcelho, nomeDistrito, codigoPostalCompleto FROM Morada";
	private static final String SELECT_BY_PK_SQL = "SELECT rua, codigoPostalP1, codigoPostalP2, numeroPorta, nomeFreguesia, nomeConcelho, nomeDistrito, codigoPostalCompleto FROM Morada WHERE rua = ? AND codigoPostalP1 = ? AND codigoPostalP2 = ? AND numeroPorta = ?";

	public int save(Morada morada) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setString(1, morada.getRua());
			ps.setInt(2, morada.getCodigoPostalP1());
			ps.setInt(3, morada.getCodigoPostalP2());
			ps.setInt(4, morada.getNumeroPorta());
			ps.setString(5, morada.getNomeFreguesia());
			ps.setString(6, morada.getNomeConcelho());
			ps.setString(7, morada.getNomeDistrito());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Morada morada) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, morada.getNomeFreguesia());
			ps.setString(2, morada.getNomeConcelho());
			ps.setString(3, morada.getNomeDistrito());
			ps.setString(4, morada.getRua());
			ps.setInt(5, morada.getCodigoPostalP1());
			ps.setInt(6, morada.getCodigoPostalP2());
			ps.setInt(7, morada.getNumeroPorta());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(String rua, int codigoPostalP1, int codigoPostalP2, int numeroPorta) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setString(1, rua);
			ps.setInt(2, codigoPostalP1);
			ps.setInt(3, codigoPostalP2);
			ps.setInt(4, numeroPorta);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Morada> getAll() {
		List<Morada> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Morada morada = new Morada();
				morada.setRua(rs.getString("rua"));
				morada.setCodigoPostalP1(rs.getInt("codigoPostalP1"));
				morada.setCodigoPostalP2(rs.getInt("codigoPostalP2"));
				morada.setNumeroPorta(rs.getInt("numeroPorta"));
				morada.setNomeFreguesia(rs.getString("nomeFreguesia"));
				morada.setNomeConcelho(rs.getString("nomeConcelho"));
				morada.setNomeDistrito(rs.getString("nomeDistrito"));
				morada.setCodigoPostalCompleto(rs.getString("codigoPostalCompleto"));
				list.add(morada);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Morada getById(String rua, int codigoPostalP1, int codigoPostalP2, int numeroPorta) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_PK_SQL)) {
			ps.setString(1, rua);
			ps.setInt(2, codigoPostalP1);
			ps.setInt(3, codigoPostalP2);
			ps.setInt(4, numeroPorta);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Morada morada = new Morada();
					morada.setRua(rs.getString("rua"));
					morada.setCodigoPostalP1(rs.getInt("codigoPostalP1"));
					morada.setCodigoPostalP2(rs.getInt("codigoPostalP2"));
					morada.setNumeroPorta(rs.getInt("numeroPorta"));
					morada.setNomeFreguesia(rs.getString("nomeFreguesia"));
					morada.setNomeConcelho(rs.getString("nomeConcelho"));
					morada.setNomeDistrito(rs.getString("nomeDistrito"));
					morada.setCodigoPostalCompleto(rs.getString("codigoPostalCompleto"));
					return morada;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
