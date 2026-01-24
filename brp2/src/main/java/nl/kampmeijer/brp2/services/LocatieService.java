package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Locatie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

/**
 * Service class responsible for managing {@link Locatie} entities.
 * <p>
 * This service provides CRUD operations for locations stored in the database,
 * including retrieving all locations, adding new ones, updating existing ones,
 * and deleting locations.
 */
public class LocatieService {

    /**
     * Retrieves all locations from the database.
     *
     * @return a list of {@link Locatie} objects
     * @throws RuntimeException if a database error or unexpected error occurs
     */
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

    /**
     * Adds a new location to the database.
     *
     * @param name the name of the location to add
     * @return {@code true} if the location was successfully added
     * @throws RuntimeException if the input is invalid or the insert fails
     */
    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO locaties (locatienaam) VALUES ('" + name + "')");
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
     * Updates the name of an existing location.
     *
     * @param oldName the current location name
     * @param newName the new location name
     * @return {@code true} if the update was successful
     * @throws RuntimeException if a database error occurs
     */
    public boolean update(String oldName, String newName) {
        try {
            int result = updateData("UPDATE locaties SET locatienaam = '" + newName + "' WHERE locatienaam = '" + oldName + "'");
            return result > 0;

        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen Locatie");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes a location from the database.
     *
     * @param name the name of the location to delete
     * @return {@code true} if the location was successfully deleted
     * @throws RuntimeException if a database error occurs
     */
    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM locaties WHERE locatienaam = '" + name + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
