package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.BinnenLocatieOnderdeel;
import nl.kampmeijer.brp2.models.Locatie;
import nl.kampmeijer.brp2.models.Onderdeel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.*;

public class BinnenLocatieOnderdeelService {

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

    public static boolean update(int lo_id, String newOnderdeelnaam, String newRuimteNaam, String newVerdiepingNummer) {
        try {
            int result = updateData(
                    "UPDATE locatiesonderdelen SET onderdeelnaam = '" + newOnderdeelnaam + ", ruimtenaam = '" + newRuimteNaam + ", verdiepingnummer = '" + newVerdiepingNummer +
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
