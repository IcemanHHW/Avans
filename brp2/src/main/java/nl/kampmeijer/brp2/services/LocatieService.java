package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Locatie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class LocatieService {

    public List<Locatie> getAll() {
        List<Locatie> locaties = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT * FROM locaties");

            while (r.next()) {
                locaties.add(new Locatie(r.getString("locatienaam")));
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen Locaties: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen Locaties");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return locaties;
    }

    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO locaties (locatieNaam) VALUES ('" + name + "')");
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
            int result = updateData("UPDATE locaties SET locatieNaam = '" + newName + "' WHERE locatieNaam = '" + oldName + "'");
            return result > 0;

        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen Locatie");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM locaties WHERE locatieNaam = '" + name + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
