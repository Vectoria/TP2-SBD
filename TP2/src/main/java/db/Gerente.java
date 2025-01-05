package db;

import java.sql.*;
import java.util.*;

public class Gerente {

	private Connection conn;

	public Gerente() throws SQLException {
		this.conn = Db.getConn();
	}

	// devolve o nome da coluna e o seu valor associado na linha, do historico do veiculo
	public List<Map<String, Object>> getHistoricoVeiculo(String matricula) {
		String query = "SELECT " + "    v.matricula, " + "    i.numKm AS numKm, " + "    i.dhRegisto AS dhRegisto, "
				+ "    i.tipoInt AS tipoInt, " + "    i.custoInt AS custoInt, " + "    NULL AS dhInicio, "
				+ "    NULL AS dhFim, " + "    NULL AS qualidadeServicoAluguer " + "FROM " + "    Veiculo v "
				+ "LEFT JOIN " + "    Intervencao i ON v.matricula = i.matricula " + "WHERE " + "    v.matricula = ? "
				+ "UNION ALL " + "SELECT " + "    v.matricula, " + "    NULL AS numKm, " + "    NULL AS dhRegisto, "
				+ "    NULL AS tipoInt, " + "    NULL AS custoInt, " + "    a.dhInicio AS dhInicio, "
				+ "    a.dhFim AS dhFim, " + "    a.qualidadeServicoAluguer AS qualidadeServicoAluguer " + "FROM "
				+ "    Veiculo v " + "LEFT JOIN " + "    Aluguer a ON v.matricula = a.matricula " + "WHERE "
				+ "    v.matricula = ? " + "ORDER BY " + "    COALESCE(dhRegisto, dhInicio)";

		List<Map<String, Object>> result = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, matricula);
			ps.setString(2, matricula);
			try (ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData metaData = rs.getMetaData();
				while (rs.next()) {
					Map<String, Object> row = new HashMap<>();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						row.put(metaData.getColumnLabel(i), rs.getObject(i));
					}
					result.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Map<String, Object>> getMenosLucrativas() {
		String query = "SELECT v.nomeMarca, SUM(a.custoFinal) AS lucro_total " + "FROM Veiculo v "
				+ "JOIN Aluguer a ON v.matricula = a.matricula " + "GROUP BY v.nomeMarca " + "ORDER BY lucro_total ASC "
				+ "LIMIT 3;";
		return executeQuery(query);
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

	// devolve a coluna e o valor associado dos melhores clientes da freguesia
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

	//generalização onde captura o nome da coluna, e associa o valor por linha
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

}
