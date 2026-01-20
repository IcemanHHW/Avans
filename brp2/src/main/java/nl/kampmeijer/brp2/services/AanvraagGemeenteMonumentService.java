package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.models.AanVraagGemeenteMonument;
import nl.kampmeijer.brp2.models.Categorie;
import nl.kampmeijer.brp2.models.LocatieOnderdeel;
import nl.kampmeijer.brp2.models.GemeenteMonument;
import static nl.kampmeijer.brp2.database.DatabaseHelper.insertData;

public class AanvraagGemeenteMonumentService {
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
