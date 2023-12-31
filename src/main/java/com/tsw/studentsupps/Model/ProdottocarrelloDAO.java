package com.tsw.studentsupps.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottocarrelloDAO {
    public static int doRetrieveQuantita(String carrelloId, String prodottoId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT quantita FROM prodottocarrello " +
                            "WHERE id_carrello= UUID_TO_BIN(?, 1) AND id_prodotto= UUID_TO_BIN(?, 1)");
            ps.setString(1, carrelloId);
            ps.setString(2, prodottoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> doRetrieveProdotti(String carrelloId) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id_prodotto, 1) FROM prodottocarrello " +
                            "WHERE id_carrello= UUID_TO_BIN(?, 1)");
            ps.setString(1, carrelloId);
            ResultSet rs = ps.executeQuery();

            List<String> prodList= new ArrayList<>();
            while(rs.next()) {
                prodList.add(rs.getString(1));
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(String carrelloId, String prodottoId, int quantita) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement(
                    "INSERT INTO prodottocarrello (id_carrello, id_prodotto, quantita)" +
                            "VALUES(UUID_TO_BIN(?, 1), UUID_TO_BIN(?, 1), ?)");

            ps.setString(1, carrelloId);
            ps.setString(2, prodottoId);
            ps.setInt(3, quantita);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdateQuantita(String carrelloId, String prodottoId, int quantita) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodottocarrello SET quantita= ? " +
                            "WHERE id_carrello= UUID_TO_BIN(?, 1) AND id_prodotto= UUID_TO_BIN(?, 1)");
            ps.setString(2, carrelloId);
            ps.setString(3, prodottoId);
            ps.setInt(1, quantita);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doDelete(String carrelloId, String prodottoId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM prodottocarrello " +
                            "WHERE id_carrello= UUID_TO_BIN(?, 1) AND id_prodotto= UUID_TO_BIN(?, 1)");

            ps.setString(1, carrelloId);
            ps.setString(2, prodottoId);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
