package com.tsw.studentsupps.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelloDAO {
    public static Carrello doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, updated_at FROM carrello WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carrello cr= new Carrello();
                cr.setId(rs.getString(1));
                cr.setTotale(rs.getDouble(2));
                cr.setUpdated_at(rs.getTimestamp(3));
                return cr;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Carrello> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), totale, updated_at FROM carrello");
            ResultSet rs= ps.executeQuery();

            List<Carrello> carList= new ArrayList<>();
            while(rs.next()) {
                Carrello cr= new Carrello();
                cr.setId(rs.getString(1));
                cr.setTotale(rs.getDouble(2));
                cr.setUpdated_at(rs.getTimestamp(3));

                carList.add(cr);
            }
            return carList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Carrello car) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO carrello (totale, updated_at) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, car.getTotale());
            ps.setTimestamp(2, car.getUpdated_at());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String id= rs.getString(1);
            car.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void doUpdate(Carrello car) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE carrello SET totale= ?, updated_at= ? " +
                            "WHERE id= ?");
            ps.setString(3, car.getId());

            ps.setDouble(1, car.getTotale());
            ps.setTimestamp(2, car.getUpdated_at());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
