package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

/**
 * Service class responsible for managing {@link Onderdeel} entities.
 * <p>
 * This service provides CRUD operations for onderdelen stored in the database,
 * including retrieving all onderdelen, adding new ones, updating existing ones,
 * and deleting onderdelen.
 */
public class OnderdeelService {

    /**
     * Retrieves all onderdelen from the database.
     *
     * @return a list of {@link Onderdeel} objects
     * @throws RuntimeException if a database error or unexpected error occurs
     */
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

    /**
     * Adds a new onderdeel to the database.
     *
     * @param name the name of the onderdeel to add
     * @return {@code true} if the onderdeel was successfully added
     * @throws RuntimeException if the input is invalid or the insert fails
     */
    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO onderdelen (onderdeelnaam) VALUES ('" + name + "')");
            return result > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the name of an existing onderdeel.
     *
     * @param oldName the current onderdeel name
     * @param newName the new onderdeel name
     * @return {@code true} if the update was successful
     * @throws RuntimeException if a database error occurs
     */
    public boolean update(String oldName, String newName) {
        try {
            int result = updateData("UPDATE onderdelen SET onderdeelnaam = '" + newName + "' WHERE onderdeelnaam = '" + oldName + "'");
            return result > 0;

        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen Onderdeel");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes an onderdeel from the database.
     *
     * @param name the name of the onderdeel to delete
     * @return {@code true} if the onderdeel was successfully deleted
     * @throws RuntimeException if a database error occurs
     */
    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM onderdelen WHERE onderdeelnaam = '" + name + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
