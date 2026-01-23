package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.data.CSVReader;
import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service responsible for managing {@link GemeenteMonument} data.
 *
 * <p>This service loads monument data from a CSV file on construction and
 * keeps it in memory for fast lookup and search operations.</p>
 *
 * <p>Monuments are indexed by their unique number (uniekNummer) in a
 * case-insensitive manner.</p>
 */
public class GemeenteMonumentService {

    private final Map<String, GemeenteMonument> monumentenByUniekNummer = new HashMap<>();

    /**
     * Creates a new {@link GemeenteMonumentService} and loads monuments
     * from the given CSV file.
     *
     * <p>Invalid rows (null monuments, missing or empty unique numbers)
     * are skipped during import.</p>
     *
     * @param csvPath the file path to the CSV file containing monument data
     */
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
     * Finds a {@link GemeenteMonument} by its uniekNummer.
     *
     * <p>The lookup is case-insensitive and ignores leading/trailing whitespace.</p>
     *
     * @param uniekNummer the uniekNummer of the monument (e.g. "GM-12345")
     * @return the matching {@link GemeenteMonument}, or {@code null} if not found
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
     * Searches monuments by address fields.
     *
     * <p>The search is performed on:</p>
     * <ul>
     *   <li>Street name ({@code straat})</li>
     *   <li>Postcode</li>
     * </ul>
     *
     * <p>The search is case-insensitive and requires a minimum query length
     * of 2 characters.</p>
     *
     * @param query the search query (straat or postcode)
     * @param limit the maximum number of results to return
     * @return a collection of matching {@link GemeenteMonument} objects
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

    /**
     * Returns all loaded monuments.
     *
     * <p>The returned collection is unmodifiable to prevent external changes
     * to the internal data structure.</p>
     *
     * @return an unmodifiable collection of all {@link GemeenteMonument} objects
     */
    public Collection<GemeenteMonument> getAll() {
        return Collections.unmodifiableCollection(
                monumentenByUniekNummer.values()
        );
    }

    /**
     * Checks whether a string contains a query string, ignoring case.
     *
     * @param value the value to search in
     * @param query the query string
     * @return {@code true} if value contains query (case-insensitive), otherwise {@code false}
     */
    private boolean containsIgnoreCase(String value, String query) {
        return value != null && value.toLowerCase().contains(query);
    }
}
