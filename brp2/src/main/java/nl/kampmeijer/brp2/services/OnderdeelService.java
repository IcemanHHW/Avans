package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class OnderdeelService {

    public List<Onderdeel> getAll() {
        List<Onderdeel> onderdelen = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT * FROM onderdelen");

            while (r.next()) {
                onderdelen.add(new Onderdeel(r.getString("onderdeelnaam")));
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen Onderdelen: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen Onderdelen");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return onderdelen;
    }

    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO onderdelen (onderdeelNaam) VALUES ('" + name + "')");
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
            int result = updateData("UPDATE onderdelen SET onderdeelNaam = '" + newName + "' WHERE onderdeelNaam = '" + oldName + "'");
            return result > 0;

        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen Onderdeel");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM onderdelen WHERE onderdeelNaam = '" + name + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
