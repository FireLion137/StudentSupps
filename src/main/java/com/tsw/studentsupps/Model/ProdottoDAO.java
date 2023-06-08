package com.tsw.studentsupps.Model;

import com.fasterxml.uuid.Generators;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProdottoDAO {
    public static Prodotto doRetrieveById(String id) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita FROM Prodotto WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveAll() {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita FROM prodotto");
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));

                prodList.add(p);
            }
            return prodList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Prodotto> doRetrieveByCategoria(String idCat) {
        try (Connection con= ConPool.getConnection()) {
            PreparedStatement ps= con.prepareStatement("SELECT BIN_TO_UUID(id, 1), nome, descrizione,prezzo,IVA,quantita " +
                    "FROM Prodotto, ProdottoCategoria " +
                    "WHERE Prodotto.id = prodottocategoria.id_prodotto AND " +
                    "prodottocategoria.id_categoria = ?");
            ps.setString(1, idCat);
            ResultSet rs= ps.executeQuery();

            List<Prodotto> prodCatList= new ArrayList<>();
            while(rs.next()) {
                Prodotto p= new Prodotto();
                p.setId(rs.getString(1));
                p.setNome(rs.getString(2));
                p.setDescrizione(rs.getString(3));
                p.setPrezzo(rs.getDouble(4));
                p.setIVA(rs.getShort(5));
                p.setQuantita(rs.getInt(6));
                prodCatList.add(p);
            }
            return prodCatList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doSave(Prodotto prod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (id, nome, descrizione,prezzo,IVA,quantita)" +
                            "VALUES(UUID_TO_BIN(?, 1),?,?,?,?,?)");

            UUID randUUID= Generators.defaultTimeBasedGenerator().generate();
            ps.setString(1, randUUID.toString());
            ps.setString(2, prod.getNome());
            ps.setString(3, prod.getDescrizione());
            ps.setDouble(4, prod.getPrezzo());
            ps.setShort(5, prod.getIVA());
            ps.setInt(6, prod.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            prod.setId(randUUID.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doUpdate(Prodotto prod) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Prodotto SET nome= ?, descrizione= ?, prezzo= ?, IVA= ?, quantita= ? " +
                            "WHERE id= UUID_TO_BIN(?, 1)");
            ps.setString(6, prod.getId());

            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescrizione());
            ps.setDouble(3, prod.getPrezzo());
            ps.setShort(4, prod.getIVA());
            ps.setInt(5, prod.getQuantita());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


