package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecensioneDAO {
    public static Recensione doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), descrizione, voto, autore FROM recensione WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Recensione r= new Recensione();
                r.setId(rs.getString(1));
                r.setDescrizione(rs.getString(2));
                r.setVoto(rs.getShort(3));
                r.setAutore(rs.getString(4));

                return r;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Recensione> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), descrizione, voto, autore FROM recensione");
            ResultSet rs= ps.executeQuery();

            List<Recensione> recList= new ArrayList<>();
            while(rs.next()) {
                Recensione r= new Recensione();
                r.setId(rs.getString(1));
                r.setDescrizione(rs.getString(2));
                r.setVoto(rs.getShort(3));
                r.setAutore(rs.getString(4));

                recList.add(r);
            }
            return recList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void doSave(Recensione rec) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO recensione (id, descrizione, voto, autore)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, rec.getDescrizione());
            ps.setShort(3, rec.getVoto());
            ps.setString(4, rec.getAutore());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            rec.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Recensione rec) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE recensione SET descrizione= ?, voto= ?, autore= ?" +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(4, rec.getId());

            ps.setString(1, rec.getDescrizione());
            ps.setShort(2, rec.getVoto());
            ps.setString(3, rec.getAutore());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
