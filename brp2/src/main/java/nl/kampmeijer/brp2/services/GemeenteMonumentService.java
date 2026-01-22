package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.data.CSVReader;
import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GemeenteMonumentService {

    private final Map<String, GemeenteMonument> monumentenByUniekNummer =
            new HashMap<>();

    public GemeenteMonumentService(String csvPath) {

        CSVReader csvReader = new CSVReader();

        for (GemeenteMonument monument : csvReader.importCsv(csvPath)) {

            if (monument == null || monument.getUniekNummer() == null) {
                continue;
            }

            String key = monument.getUniekNummer()
                    .trim()
                    .toUpperCase();

            if (key.isEmpty()) {
                continue;
            }

            monumentenByUniekNummer.put(key, monument);
        }
    }

    public GemeenteMonument getByUniekNummer(String uniekNummer) {
        if (uniekNummer == null) {
            return null;
        }

        return monumentenByUniekNummer.get(
                uniekNummer.trim().toUpperCase()
        );
    }

    public Collection<GemeenteMonument> getAll() {
        return Collections.unmodifiableCollection(
                monumentenByUniekNummer.values()
        );
    }
}
