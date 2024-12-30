//package db;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import pojo.TipoHabilitacao;
//
//public class TipoHabilitacaoDao {
//	  private static final String INSERT_SQL = "INSERT INTO Tipo_Habilitacao (tipoHab) VALUES (?)";
//	    private static final String UPDATE_SQL = "UPDATE Tipo_Habilitacao SET tipoHab = ? WHERE tipoHab = ?";
//	    private static final String DELETE_SQL = "DELETE FROM Tipo_Habilitacao WHERE tipoHab = ?";
//	    private static final String SELECT_ALL_SQL = "SELECT tipoHab FROM Tipo_Habilitacao";
//	    private static final String SELECT_BY_ID_SQL = "SELECT tipoHab FROM Tipo_Habilitacao WHERE tipoHab = ?";
//
//	    public int save(TipoHabilitacao tipoHabilitacao) {
//	        try (Connection conn = Db.getConn();
//	             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
//	            ps.setString(1, tipoHabilitacao.getTipoHab());
//	            return ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        return 0;
//	    }
//
//	    public int update(TipoHabilitacao tipoHabilitacao, String oldTipoHab) {
//	        try (Connection conn = Db.getConn();
//	             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
//	            ps.setString(1, tipoHabilitacao.getTipoHab());
//	            ps.setString(2, oldTipoHab);
//	            return ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        return 0;
//	    }
//
//	    public int delete(String tipoHab) {
//	        try (Connection conn = Db.getConn();
//	             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
//	            ps.setString(1, tipoHab);
//	            return ps.executeUpdate();
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        return 0;
//	    }
//
//	    public List<TipoHabilitacao> getAll() {
//	        List<TipoHabilitacao> list = new ArrayList<>();
//	        try (Connection conn = Db.getConn();
//	             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
//	             ResultSet rs = ps.executeQuery()) {
//	            while (rs.next()) {
//	                TipoHabilitacao tipoHabilitacao = new TipoHabilitacao();
//	                tipoHabilitacao.setTipoHab(rs.getString("tipoHab"));
//	                list.add(tipoHabilitacao);
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        return list;
//	    }
//
//	    public TipoHabilitacao getById(String tipoHab) {
//	        try (Connection conn = Db.getConn();
//	             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
//	            ps.setString(1, tipoHab);
//	            try (ResultSet rs = ps.executeQuery()) {
//	                if (rs.next()) {
//	                    TipoHabilitacao tipoHabilitacao = new TipoHabilitacao();
//	                    tipoHabilitacao.setTipoHab(rs.getString("tipoHab"));
//	                    return tipoHabilitacao;
//	                }
//	            }
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
//	        return null;
//	    }
//}
