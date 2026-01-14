package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.BuitenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class BuitenLocatieOnderdeelService {

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

    public boolean add(String onderdeelNaam, String gevelNaam, String blootstellingNaam) {
        try {
            int result = insertData("INSERT INTO locatiesonderdelen (locatienaam, onderdeelnaam, gevelnaam, blootstellingnaam) " +
                    "VALUES ('Buitenshuis', '" + onderdeelNaam + "', '" + gevelNaam + "', '" + blootstellingNaam + "'");
            return result > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }

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
