package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.BuitenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

/**
 * Service class for managing {@link BuitenLocatieOnderdeel} entities.
 *
 * <p>This service provides CRUD-like functionality for location components
 * that belong to the "Buitenshuis" location type. Data is retrieved and
 * stored using direct SQL queries.</p>
 */
public class BuitenLocatieOnderdeelService {

    /**
     * Retrieves all "Buitenshuis" location components from the database.
     *
     * <p>The result is returned as a list of formatted strings generated
     * by {@link BuitenLocatieOnderdeel#locatieOnderdeelInfo()}.</p>
     *
     * @return a list of formatted BuitenLocatieOnderdeel descriptions
     *
     * @throws RuntimeException if a database error or unexpected error occurs
     */
    public List<String> getAll() {
        List<String> buitenLocatiesOnderdelen = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT lo_id, locatienaam, onderdeelnaam, gevelnaam, blootstellingnaam FROM locatiesonderdelen WHERE locatienaam = 'Buitenshuis'");

            while (r.next()) {
                Onderdeel onderdeel = new Onderdeel(r.getString("onderdeelnaam"));
                Locatie locatienaam = new Locatie(r.getString("locatienaam"));
                BuitenLocatieOnderdeel buitenLocatieOnderdeel = new BuitenLocatieOnderdeel(
                        r.getInt("lo_id"),
                        locatienaam,
                        onderdeel,
                        r.getString("gevelnaam"),
                        r.getString("blootstellingnaam")
                );
                buitenLocatiesOnderdelen.add(buitenLocatieOnderdeel.locatieOnderdeelInfo());
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen BuitenLocatiesOnderdelen: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen BuitenLocatiesOnderdelen");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return buitenLocatiesOnderdelen;
    }

    /**
     * Inserts a new {@link BuitenLocatieOnderdeel} into the database.
     *
     * <p>After insertion, the generated database ID is used to create
     * and return a new {@link BuitenLocatieOnderdeel} instance.</p>
     *
     * @param locatie the location (expected to be "Binnenshuis")
     * @param onderdeel the associated onderdeel
     * @param gevelNaam the gevel name
     * @param blootstellingNaam the blootstelling name
     *
     * @return the newly created {@link BuitenLocatieOnderdeel}
     *
     * @throws RuntimeException if insertion fails or input is invalid
     */
    public BuitenLocatieOnderdeel add(Locatie locatie, Onderdeel onderdeel, String gevelNaam, String blootstellingNaam) {
        try {
            int loId = insertDataAndGetId(
                    "INSERT INTO locatiesonderdelen (locatienaam, onderdeelnaam, gevelnaam, blootstellingnaam) " +
                            "VALUES ('Buitenshuis', '" + onderdeel.getOnderdeelNaam() + "', '" + gevelNaam + "', '" + blootstellingNaam + "'");
            if (loId <= 0) {
                throw new RuntimeException("DATABASE_INSERT_ERROR");
            }
            return new BuitenLocatieOnderdeel(loId, locatie, onderdeel, gevelNaam, blootstellingNaam);
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an existing {@link BuitenLocatieOnderdeel} in the database.
     *
     * @param lo_id the ID of the location component to update
     * @param newOnderdeelNaam the new onderdeel name
     * @param newGevelNaam the new gevel name
     * @param newBlootstellingNaam the new blootstelling name
     *
     * @return {@code true} if at least one record was updated, otherwise {@code false}
     *
     * @throws RuntimeException if a database or unexpected error occurs
     */
    public static boolean update(int lo_id, String newOnderdeelNaam, String newGevelNaam, String newBlootstellingNaam) {
        try {
            int result = updateData(
                    "UPDATE locatiesonderdelen SET onderdeelnaam = '" + newOnderdeelNaam + ", ruimtenaam = '" + newGevelNaam + ", verdiepingnummer = '" + newBlootstellingNaam +
                            "' WHERE lo_id = '" + lo_id + "'"
            );
            return result > 0;
        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen BuitenLocatieOnderdeel");
            throw new RuntimeException("DATABASE_NULL_ERROR");

        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes a {@link BuitenLocatieOnderdeel} from the database.
     *
     * @param lo_id the ID of the location component to delete
     *
     * @return {@code true} if the record was deleted, otherwise {@code false}
     *
     * @throws RuntimeException if a database or unexpected error occurs
     */
    public static boolean delete(int lo_id) {
        try {
            int result = updateData("DELETE FROM locatiesonderdelen WHERE lo_id = '" + lo_id + "'");
            return result > 0;
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
