package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Categorie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class CategorieService {

    public List<Categorie> getAll() {
        List<Categorie> categorieen = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT * FROM categorieen");

            while (r.next()) {
                categorieen.add(new Categorie(r.getString("categorieNaam")));
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen categorieën: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen categorieën");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return categorieen;
    }

    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO categorieen (categorieNaam) VALUES ('" + name + "')");
            return result > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    public boolean update(String oldName, String newName) {
        try {
            int result = updateData("UPDATE categorieen SET categorieNaam = '" + newName + "' WHERE categorieNaam = '" + oldName + "'");
            return result > 0;

        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen categorie");
            throw new RuntimeException("DATABASE_NULL_ERROR");

        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM categorieen WHERE categorieNaam = '" + name + "'");
            return result > 0;

        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
