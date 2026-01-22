package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.kampmeijer.brp2.database.DatabaseHelper.getData;
import static nl.kampmeijer.brp2.database.DatabaseHelper.insertData;

public class AanvraagGemeenteMonumentService {
    private final GemeenteMonumentService gemeenteMonumentService = new GemeenteMonumentService("");

    public List<AanVraagGemeenteMonument> getAll() {
        List<AanVraagGemeenteMonument> aanvragenGemeenteMonumenten = new ArrayList<>();
        String sql =
                "SELECT " +
                        "gma.aanvraagnummer AS aanvraagnummer, " +
                        "gma.unieknummer AS unieknummer, " +
                        "c.categorienaam AS categorie_naam, " +

                        "lo.lo_id AS lo_id, " +
                        "lo.locatienaam AS locatie_naam, " +
                        "lo.onderdeelnaam as onderdeel_naam, " +
                        "lo.ruimtenaam AS ruimte_naam, " +
                        "lo.verdiepingnummer AS verdieping_nummer, " +
                        "lo.gevelnaam AS gevel_naam, " +
                        "lo.blootstellingnaam AS blootstelling_naam " +

                        "FROM gemeentemonumentenaanvragen gma " +
                        "JOIN categorieen c ON c.categorie_id = gma.categorie_id " +
                        "JOIN locatiesonderdelen lo ON lo.lo_id = gma.lo_id";
        try {
            ResultSet r = getData(sql);

            while (r.next()) {
                String uniekNummer = r.getString("unieknummer");
                GemeenteMonument gemeenteMonument = gemeenteMonumentService.getByUniekNummer(uniekNummer);
                Categorie categorie = new Categorie(r.getString("categorie_naam"));
                Onderdeel onderdeel = new Onderdeel(r.getString("onderdeel_naam"));
                Locatie locatie = new Locatie(r.getString("locatie_naam"));
                int loId = r.getInt("lo_id");
                LocatieOnderdeel locatieOnderdeel;
                if ("Binnenshuis".equalsIgnoreCase(locatie.getLocatieNaam())) {
                    locatieOnderdeel = new BinnenLocatieOnderdeel(
                            loId,
                            locatie,
                            onderdeel,
                            r.getString("ruimte_naam"),
                            r.getString("verdieping_nummer")
                    );
                } else if ("Buitenshuis".equalsIgnoreCase(locatie.getLocatieNaam())) {
                    locatieOnderdeel = new BuitenLocatieOnderdeel(
                            loId,
                            locatie,
                            onderdeel,
                            r.getString("gevel_naam"),
                            r.getString("blootstelling_naam")
                    );
                } else {
                    throw new RuntimeException("ONBEKENDE_LOCATIE");
                }
                AanVraagGemeenteMonument aanvraagGemeenteMonument = new AanVraagGemeenteMonument(
                        r.getInt("aanvraagnummer"),
                        categorie,
                        locatieOnderdeel,
                        gemeenteMonument
                        );
                aanvragenGemeenteMonumenten.add(aanvraagGemeenteMonument);
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
