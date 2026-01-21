package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.getData;
import static nl.kampmeijer.brp2.database.DatabaseHelper.insertData;

public class AanvraagGemeenteMonumentService {
    public List<String> getAll() {
        List<String> aanvragenGemeenteMonumenten = new ArrayList<>();

        try {
            ResultSet r = getData("SELECT " +
                    "gma.aanvraagnummer, " +
                    "gma.unieknummer, " +
                    "gma.categorienaam, " +
                    "gma.lo_id, " +
                    "c.categorienaam AS categorie_naam " +
                    "lo.locatienaam AS locatie_naam " +
                    "lo.onderdeelnaam AS onderdeel_naam " +
                    "lo.ruimtenaam AS ruimte_naam " +
                    "lo.verdiepingnummer AS verdieping_nummer " +
                    "lo.onderdeelnaam AS onderdeel_naam " +
                    "lo.gevelnaam AS gevel_naam " +
                    "lo.blootstellingnaam AS blootstelling_naam " +
                    "FROM gemeentemonumentenaanvragen gma" +
                    "JOIN locatiesonderdelen lo ON (lo.lo_id = gma.lo_id)'");
            while (r.next()) {
                Categorie categorie = new Categorie(r.getString("categorienaam"));
                Onderdeel onderdeel = new Onderdeel(r.getString("onderdeel_naam"));
                Locatie locatienaam = new Locatie(r.getString("locatie_naam"));
                LocatieOnderdeel locatieOnderdeel = null;

                if (locatienaam.getLocatieNaam().equals("Binnenshuis")) {
                   locatieOnderdeel = new BinnenLocatieOnderdeel(
                           r.getInt("lo_id"),
                           locatienaam,
                           onderdeel,
                           r.getString("ruimte_naam"),
                           r.getString("verdieping_nummer")
                    );
                }

                if (locatienaam.getLocatieNaam().equals("Buitenshuis")) {
                    locatieOnderdeel = new BuitenLocatieOnderdeel(
                            r.getInt("lo_id"),
                            locatienaam,
                            onderdeel,
                            r.getString("gevel_naam"),
                            r.getString("blootstelling_naam")
                    );
                }

                AanVraagGemeenteMonument aanvraagGemeenteMonument = new AanVraagGemeenteMonument(
                        r.getInt("aanvraagnummer"),
                        categorie,
                        locatieOnderdeel,
                        null
                );
                aanvragenGemeenteMonumenten.add(aanvraagGemeenteMonument.toString());
            }
        } catch (SQLException e) {
            System.err.println("SQL-fout bij ophalen AanvragenGemeenteMonumenten: " + e.getMessage());
            throw new RuntimeException("DATABASE_LOAD_ERROR");
        } catch (NullPointerException e) {
            System.err.println("Null-waarde bij ophalen AanvragenGemeenteMonumenten");
            throw new RuntimeException("DATABASE_NULL_ERROR");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
        return aanvragenGemeenteMonumenten;
    }

    public boolean add(Categorie categorie, LocatieOnderdeel locatieOnderdeel, GemeenteMonument gemeenteMonument) {
        try {
            int result = insertData(
                    "INSERT INTO gemeentemonumentenaanvragen (unieknummer, categorienaam, lo_id) " +
                            "VALUES ('" + gemeenteMonument.getUniekNummer() + "', '" + categorie.getCategorieNaam() + "', '" + locatieOnderdeel.getLo_id() + "'");
            return result > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Ongeldige invoer: " + e.getMessage());
            throw new RuntimeException("INVALID_INPUT");
        } catch (RuntimeException e) {
            System.err.println("Onverwachte fout: " + e.getMessage());
            throw e;
        }
    }
}
