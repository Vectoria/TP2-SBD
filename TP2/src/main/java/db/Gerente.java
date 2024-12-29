package db;

import java.sql.*;
import java.util.*;

public class Gerente {

	private Connection conn;

	public Gerente() throws SQLException {
		this.conn = Db.getConn();
	}

	public List<Map<String, Object>> getMenosLucrativas() {
		String query = "SELECT v.nomeMarca, SUM(a.custoFinal) AS lucro_total " + "FROM Veiculo v "
				+ "JOIN Aluguer a ON v.matricula = a.matricula " + "GROUP BY v.nomeMarca " + "ORDER BY lucro_total ASC "
				+ "LIMIT 3;";
		return executeQuery(query);
	}

	public void atualizarAvaliacoesModelos() {
		String query = "SET SQL_SAFE_UPDATES = 0; " + "UPDATE Modelo m " + "SET m.avaliacaoModelo = ("
				+ "    SELECT AVG(" + "        CASE " + "            WHEN a.qualidadeServicoAluguer = 'adorei' THEN 10 "
				+ "            WHEN a.qualidadeServicoAluguer = 'gostei' THEN 5 "
				+ "            WHEN a.qualidadeServicoAluguer = 'não vou voltar' THEN 0 " + "            ELSE NULL "
				+ "        END" + "    ) " + "    FROM Aluguer a "
				+ "    WHERE a.matricula IN (SELECT v.matricula FROM Veiculo v WHERE v.nomeMod = m.nomeMod)" + ") "
				+ "WHERE m.nomeMod IS NOT NULL; " + "SET SQL_SAFE_UPDATES = 1;";
		executeUpdate(query);
	}

	public List<Map<String, Object>> getModelosMaisBemAvaliadosSemanaPassada() {
		String query = "SELECT " + "    m.nomeMod, " + "    m.nomeMarca, " + "    AVG( " + "        CASE "
				+ "            WHEN a.qualidadeServicoAluguer = 'adorei' THEN 10 "
				+ "            WHEN a.qualidadeServicoAluguer = 'gostei' THEN 5 "
				+ "            WHEN a.qualidadeServicoAluguer = 'não vou voltar' THEN 0 " + "            ELSE NULL "
				+ "        END " + "    ) AS avaliacaoSemanaPassada " + "FROM " + "    Aluguer a " + "JOIN "
				+ "    Veiculo v ON a.matricula = v.matricula " + "JOIN " + "    Modelo m ON v.nomeMod = m.nomeMod "
				+ "WHERE " + "    (a.dhInicio >= inicioSemanaPassada() AND a.dhInicio < fimSemanaPassada()) "
				+ "    OR (a.dhFim >= inicioSemanaPassada() AND a.dhFim < fimSemanaPassada()) "
				+ "    OR (a.dhInicio < inicioSemanaPassada() AND a.dhFim >= fimSemanaPassada()) " + "GROUP BY "
				+ "    m.nomeMod, m.nomeMarca " + "ORDER BY " + "    avaliacaoSemanaPassada DESC " + "LIMIT 5;";
		return executeQuery(query);
	}

	public List<Map<String, Object>> getVeiculosMenorQuilometragemUltimoTrimestre() {
		String query = "SELECT i.matricula, SUM(i.numKM) AS total_km " + "FROM Intervencao i "
				+ "WHERE ultimoTrimestre(i.dhRegisto) " + "GROUP BY i.matricula " + "ORDER BY total_km ASC "
				+ "LIMIT 10;";
		return executeQuery(query);
	}

	public List<Map<String, Object>> getClientesPorFreguesia(String freguesia) {
		String query = "SELECT clienteNIF AS id_cliente, nome, avaliacaoCliente, nomeFreguesia " + "FROM Cliente "
				+ "WHERE nomeFreguesia = ? " + "ORDER BY avaliacaoCliente DESC " + "LIMIT 100;";
		List<Map<String, Object>> result = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, freguesia);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Map<String, Object> row = new HashMap<>();
					row.put("id_cliente", rs.getInt("id_cliente"));
					row.put("nome", rs.getString("nome"));
					row.put("avaliacaoCliente", rs.getDouble("avaliacaoCliente"));
					row.put("nomeFreguesia", rs.getString("nomeFreguesia"));
					result.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Map<String, Object>> executeQuery(String query) {
		List<Map<String, Object>> result = new ArrayList<>();
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				result.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void executeUpdate(String query) {
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
