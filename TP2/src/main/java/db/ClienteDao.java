package db;

import pojo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

	private static final String INSERT_SQL = "INSERT INTO Cliente (clienteNIF, moedaPref, prefLingCult, contactoTel, email, nome, condutorNIF, codigo, rua, codigoPostalP1, codigoPostalP2, numeroPorta, nomeFreguesia, nomeConcelho, nomeDistrito, avaliacaoCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE Cliente SET moedaPref = ?, prefLingCult = ?, contactoTel = ?, email = ?, nome = ?, condutorNIF = ?, codigo = ?, rua = ?, codigoPostalP1 = ?, codigoPostalP2 = ?, numeroPorta = ?, nomeFreguesia = ?, nomeConcelho = ?, nomeDistrito = ?, avaliacaoCliente = ? WHERE clienteNIF = ?";
	private static final String DELETE_SQL = "DELETE FROM Cliente WHERE clienteNIF = ?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM Cliente";
	private static final String SELECT_BY_ID_SQL = "SELECT * FROM Cliente WHERE clienteNIF = ?";

	public int save(Cliente cliente) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setInt(1, cliente.getClienteNIF());
			ps.setString(2, cliente.getMoedaPref());
			ps.setString(3, cliente.getPrefLingCult());
			ps.setInt(4, cliente.getContactoTel());
			ps.setString(5, cliente.getEmail());
			ps.setString(6, cliente.getNome());
			ps.setInt(7, cliente.getCondutorNIF());
			ps.setObject(8, cliente.getCodigo(), Types.INTEGER);
			ps.setString(9, cliente.getRua());
			ps.setInt(10, cliente.getCodigoPostalP1());
			ps.setInt(11, cliente.getCodigoPostalP2());
			ps.setInt(12, cliente.getNumeroPorta());
			ps.setString(13, cliente.getNomeFreguesia());
			ps.setString(14, cliente.getNomeConcelho());
			ps.setString(15, cliente.getNomeDistrito());
			ps.setDouble(16, cliente.getAvaliacaoCliente());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Cliente cliente) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
			ps.setString(1, cliente.getMoedaPref());
			ps.setString(2, cliente.getPrefLingCult());
			ps.setInt(3, cliente.getContactoTel());
			ps.setString(4, cliente.getEmail());
			ps.setString(5, cliente.getNome());
			ps.setInt(6, cliente.getCondutorNIF());
			ps.setObject(7, cliente.getCodigo(), Types.INTEGER);
			ps.setString(8, cliente.getRua());
			ps.setInt(9, cliente.getCodigoPostalP1());
			ps.setInt(10, cliente.getCodigoPostalP2());
			ps.setInt(11, cliente.getNumeroPorta());
			ps.setString(12, cliente.getNomeFreguesia());
			ps.setString(13, cliente.getNomeConcelho());
			ps.setString(14, cliente.getNomeDistrito());
			ps.setDouble(15, cliente.getAvaliacaoCliente());
			ps.setInt(16, cliente.getClienteNIF());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(int clienteNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setInt(1, clienteNIF);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Cliente> getAll() {
		List<Cliente> list = new ArrayList<>();
		try (Connection conn = Db.getConn();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setClienteNIF(rs.getInt("clienteNIF"));
				cliente.setMoedaPref(rs.getString("moedaPref"));
				cliente.setPrefLingCult(rs.getString("prefLingCult"));
				cliente.setContactoTel(rs.getInt("contactoTel"));
				cliente.setEmail(rs.getString("email"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCondutorNIF(rs.getInt("condutorNIF"));
				cliente.setCodigo((Integer) rs.getObject("codigo"));
				cliente.setRua(rs.getString("rua"));
				cliente.setCodigoPostalP1(rs.getInt("codigoPostalP1"));
				cliente.setCodigoPostalP2(rs.getInt("codigoPostalP2"));
				cliente.setNumeroPorta(rs.getInt("numeroPorta"));
				cliente.setNomeFreguesia(rs.getString("nomeFreguesia"));
				cliente.setNomeConcelho(rs.getString("nomeConcelho"));
				cliente.setNomeDistrito(rs.getString("nomeDistrito"));
				cliente.setAvaliacaoCliente(rs.getDouble("avaliacaoCliente"));
				list.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Cliente getById(int clienteNIF) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
			ps.setInt(1, clienteNIF);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Cliente cliente = new Cliente();
					cliente.setClienteNIF(rs.getInt("clienteNIF"));
					cliente.setMoedaPref(rs.getString("moedaPref"));
					cliente.setPrefLingCult(rs.getString("prefLingCult"));
					cliente.setContactoTel(rs.getInt("contactoTel"));
					cliente.setEmail(rs.getString("email"));
					cliente.setNome(rs.getString("nome"));
					cliente.setCondutorNIF(rs.getInt("condutorNIF"));
					cliente.setCodigo((Integer) rs.getObject("codigo"));
					cliente.setRua(rs.getString("rua"));
					cliente.setCodigoPostalP1(rs.getInt("codigoPostalP1"));
					cliente.setCodigoPostalP2(rs.getInt("codigoPostalP2"));
					cliente.setNumeroPorta(rs.getInt("numeroPorta"));
					cliente.setNomeFreguesia(rs.getString("nomeFreguesia"));
					cliente.setNomeConcelho(rs.getString("nomeConcelho"));
					cliente.setNomeDistrito(rs.getString("nomeDistrito"));
					cliente.setAvaliacaoCliente(rs.getDouble("avaliacaoCliente"));
					return cliente;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
