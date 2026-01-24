package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.BinnenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

/**
 * Service class for managing {@link BinnenLocatieOnderdeel} entities.
 *
 * <p>This service provides CRUD-like functionality for location components
 * that belong to the "Binnenshuis" location type. Data is retrieved and
 * stored using direct SQL queries.</p>
 */
public class BinnenLocatieOnderdeelService {

    /**
     * Retrieves all "Binnenshuis" location components from the database.
     *
     * <p>The result is returned as a list of formatted strings generated
     * by {@link BinnenLocatieOnderdeel#locatieOnderdeelInfo()}.</p>
     *
     * @return a list of formatted BinnenLocatieOnderdeel descriptions
     *
     * @throws RuntimeException if a database error or unexpected error occurs
     */
    public List<String> getAll() {
        List<String> binnenLocatieOnderdelen = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT lo_id, locatienaam, onderdeelnaam, ruimtenaam, verdiepingnummer FROM locatiesonderdelen WHERE locatienaam = 'Binnenshuis'");

            while (r.next()) {
                Onderdeel onderdeel = new Onderdeel(r.getString("onderdeelnaam"));
                Locatie locatienaam = new Locatie(r.getString("locatienaam"));
                BinnenLocatieOnderdeel binnenLocatieOnderdeel = new BinnenLocatieOnderdeel(
                        r.getInt("lo_id"),
                        locatienaam,
                        onderdeel,
                        r.getString("ruimtenaam"),
                        r.getString("verdiepingnummer")
                );
                binnenLocatieOnderdelen.add(binnenLocatieOnderdeel.locatieOnderdeelInfo());
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen BinnenLocatieOnderdelen: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen BinnenLocatieOnderdelen");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return binnenLocatieOnderdelen;
    }

    /**
     * Inserts a new {@link BinnenLocatieOnderdeel} into the database.
     *
     * <p>After insertion, the generated database ID is used to create
     * and return a new {@link BinnenLocatieOnderdeel} instance.</p>
     *
     * @param locatie the location (expected to be "Binnenshuis")
     * @param onderdeel the associated onderdeel
     * @param ruimteNaam the room name
     * @param verdiepingNummer the floor number
     *
     * @return the newly created {@link BinnenLocatieOnderdeel}
     *
     * @throws RuntimeException if insertion fails or input is invalid
     */
    public BinnenLocatieOnderdeel add(Locatie locatie, Onderdeel onderdeel, String ruimteNaam, String verdiepingNummer) {
        try {
            int loId = insertDataAndGetId(
                    "INSERT INTO locatiesonderdelen (locatienaam, onderdeelnaam, ruimtenaam, verdiepingnummer) " +
                            "VALUES ('" + locatie + "', '" + onderdeel.getOnderdeelNaam() + "', '" + ruimteNaam + "', '" + verdiepingNummer + "'");
            if (loId <= 0) {
                throw new RuntimeException("DATABASE_INSERT_ERROR");
            }
            return new BinnenLocatieOnderdeel(loId, locatie, onderdeel, ruimteNaam, verdiepingNummer);
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Updates an existing {@link BinnenLocatieOnderdeel} in the database.
     *
     * @param lo_id the ID of the location component to update
     * @param newOnderdeelNaam the new onderdeel name
     * @param newRuimteNaam the new room name
     * @param newVerdiepingNummer the new floor number
     *
     * @return {@code true} if at least one record was updated, otherwise {@code false}
     *
     * @throws RuntimeException if a database or unexpected error occurs
     */
    public static boolean update(int lo_id, String newOnderdeelNaam, String newRuimteNaam, String newVerdiepingNummer) {
        try {
            int result = updateData(
                    "UPDATE locatiesonderdelen SET " +
                            "onderdeelnaam = '" + newOnderdeelNaam +
                            "', ruimtenaam = '" + newRuimteNaam +
                            "', verdiepingnummer = '" + newVerdiepingNummer +
                            "' WHERE lo_id = '" + lo_id + "'"
            );
            return result > 0;
        } catch (NullPointerException e) {
            System.err.println("Null bij aanpassen BinnenLocatieOnderdeel");
            throw new RuntimeException("DATABASE_NULL_ERROR");

        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Deletes a {@link BinnenLocatieOnderdeel} from the database.
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
