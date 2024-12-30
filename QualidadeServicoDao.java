package db;

import pojo.QualidadeServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QualidadeServicoDao {

	private static final String INSERT_SQL = "INSERT INTO Qualidade_Servico (clienteNIF, avaliacao, comentario) VALUES (?, ?, ?)";
	private static final String SELECT_BY_CLIENTE_SQL = "SELECT * FROM Qualidade_Servico WHERE clienteNIF = ?";
	private static final String DELETE_SQL = "DELETE FROM Qualidade_Servico WHERE idComentario = ?";

	public int save(QualidadeServico qualidadeServico) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			ps.setInt(1, qualidadeServico.getClienteNIF());
			ps.setInt(2, qualidadeServico.getAvaliacao());
			ps.setString(3, qualidadeServico.getComentario());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<QualidadeServico> getByClienteNIF(int clienteNIF) {
		List<QualidadeServico> list = new ArrayList<>();
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_CLIENTE_SQL)) {
			ps.setInt(1, clienteNIF);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					QualidadeServico qualidadeServico = new QualidadeServico();
					qualidadeServico.setIdComentario(rs.getInt("idComentario"));
					qualidadeServico.setClienteNIF(rs.getInt("clienteNIF"));
					qualidadeServico.setAvaliacao(rs.getInt("avaliacao"));
					qualidadeServico.setComentario(rs.getString("comentario"));
					list.add(qualidadeServico);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int delete(int idComentario) {
		try (Connection conn = Db.getConn(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
			ps.setInt(1, idComentario);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
