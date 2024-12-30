package db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojo.CartaConducao;

public class CartaConducaoDao {
	private static final String INSERT_SQL = "INSERT INTO Carta_Conducao (numID, tipoHab, dataValidade, dataEmissao) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE Carta_Conducao SET tipoHab = ?, dataValidade = ?, dataEmissao = ? WHERE numID = ?";
    private static final String DELETE_SQL = "DELETE FROM Carta_Conducao WHERE numID = ?";
    private static final String SELECT_ALL_SQL = "SELECT numID, tipoHab, dataValidade, dataEmissao FROM Carta_Conducao";
    private static final String SELECT_BY_ID_SQL = "SELECT numID, tipoHab, dataValidade, dataEmissao FROM Carta_Conducao WHERE numID = ?";

    public int save(CartaConducao cartaConducao) {
        try (Connection conn = Db.getConn();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, cartaConducao.getNumID());
            ps.setString(2, cartaConducao.getTipoHab());
            ps.setDate(3, Date.valueOf(cartaConducao.getDataValidade()));
            ps.setDate(4, Date.valueOf(cartaConducao.getDataEmissao()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(CartaConducao cartaConducao) {
        try (Connection conn = Db.getConn();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, cartaConducao.getTipoHab());
            ps.setDate(2, Date.valueOf(cartaConducao.getDataValidade()));
            ps.setDate(3, Date.valueOf(cartaConducao.getDataEmissao()));
            ps.setInt(4, cartaConducao.getNumID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int numID) {
        try (Connection conn = Db.getConn();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, numID);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<CartaConducao> getAll() {
        List<CartaConducao> list = new ArrayList<>();
        try (Connection conn = Db.getConn();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CartaConducao cartaConducao = new CartaConducao();
                cartaConducao.setNumID(rs.getInt("numID"));
                cartaConducao.setTipoHab(rs.getString("tipoHab"));
                cartaConducao.setDataValidade(rs.getDate("dataValidade").toLocalDate());
                cartaConducao.setDataEmissao(rs.getDate("dataEmissao").toLocalDate());
                list.add(cartaConducao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public CartaConducao getById(int numID) {
        try (Connection conn = Db.getConn();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, numID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartaConducao cartaConducao = new CartaConducao();
                    cartaConducao.setNumID(rs.getInt("numID"));
                    cartaConducao.setTipoHab(rs.getString("tipoHab"));
                    cartaConducao.setDataValidade(rs.getDate("dataValidade").toLocalDate());
                    cartaConducao.setDataEmissao(rs.getDate("dataEmissao").toLocalDate());
                    return cartaConducao;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
