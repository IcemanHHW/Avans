package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.Categorie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

/**
 * Service class responsible for managing {@link Categorie} entities.
 * <p>
 * This service provides CRUD operations for categories stored in the database,
 * including retrieving all categories, adding new ones, updating existing ones,
 * and deleting categories.
 */
public class CategorieService {

    /**
     * Retrieves all categories from the database.
     *
     * @return a list of {@link Categorie} objects
     * @throws RuntimeException if a database error or unexpected error occurs
     */
    public List<Categorie> getAll() {
        List<Categorie> categorieen = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT * FROM categorieen");

            while (r.next()) {
                categorieen.add(new Categorie(r.getString("categorienaam")));
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen Categorieën: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen Categorieën");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return categorieen;
    }

    /**
     * Adds a new category to the database.
     *
     * @param name the name of the category to add
     * @return {@code true} if the category was successfully added
     * @throws RuntimeException if the input is invalid or the insert fails
     */
    public boolean add(String name) {
        try {
            int result = insertData("INSERT INTO categorieen (categorienaam) VALUES ('" + name + "')");
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
     * Updates the name of an existing category.
     *
     * @param oldName the current category name
     * @param newName the new category name
     * @return {@code true} if the update was successful
     * @throws RuntimeException if a database error occurs
     */
    public boolean update(String oldName, String newName) {
        try {
            int result = updateData("UPDATE categorieen SET categorienaam = '" + newName + "' WHERE categorienaam = '" + oldName + "'");
            return result > 0;
        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen Categorie");
            throw new RuntimeException("DATABASE_NULL_ERROR");

        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes a category from the database.
     *
     * @param name the name of the category to delete
     * @return {@code true} if the category was successfully deleted
     * @throws RuntimeException if a database error occurs
     */
    public boolean delete(String name) {
        try {
            int result = updateData("DELETE FROM categorieen WHERE categorienaam = '" + name + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
