package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.data.CSVReader;
import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.util.*;
import java.util.stream.Collectors;

public class GemeenteMonumentService {

    private final Map<String, GemeenteMonument> monumentenByUniekNummer = new HashMap<>();

    public GemeenteMonumentService(String csvPath) {
        CSVReader csvReader = new CSVReader();
        for (GemeenteMonument monument : csvReader.importCsv(csvPath)) {
            if (monument == null || monument.getUniekNummer() == null) {
                continue;
            }
            String key = monument.getUniekNummer().trim().toUpperCase();
            if (key.isEmpty()) {
                continue;
            }
            monumentenByUniekNummer.put(key, monument);
        }
    }

    /**
     * Find monument by unique number (case-insensitive)
     */
    public GemeenteMonument getByUniekNummer(String uniekNummer) {
        if (uniekNummer == null) {
            return null;
        }
        return monumentenByUniekNummer.get(
                uniekNummer.trim().toUpperCase()
        );
    }

    /**
     * Search monuments by address fields (straat  or postcode)
     */
    public Collection<GemeenteMonument> searchByAdres(String query, int limit) {
        if (query == null || query.trim().length() < 2) {
            return Collections.emptyList();
        }
        String q = query.trim().toLowerCase();
        return monumentenByUniekNummer.values().stream()
                .filter(m -> containsIgnoreCase(m.getStraat(), q) || containsIgnoreCase(m.getPostcode(), q))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Collection<GemeenteMonument> getAll() {
        return Collections.unmodifiableCollection(
                monumentenByUniekNummer.values()
        );
    }

    private boolean containsIgnoreCase(String value, String query) {
        return value != null && value.toLowerCase().contains(query);
    }
}
