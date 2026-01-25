package nl.kampmeijer.brp2.data;

import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    /**
     * Imports {@link GemeenteMonument} records from a CSV file located on the classpath.
     *
     * <p>The first line of the CSV file is treated as a header and ignored.
     * Each subsequent line is parsed into a {@link GemeenteMonument} object
     * if it contains valid data.</p>
     *
     * <p>Rows are skipped when:
     * <ul>
     *     <li>The row has fewer than the required number of columns</li>
     *     <li>Required fields are empty</li>
     *     <li>The uniekNummer does not start with {@code "GM-"}</li>
     * </ul>
     * </p>
     *
     * @param resourcePath the classpath-relative path to the CSV file
     * @return a list of successfully imported {@link GemeenteMonument} objects
     * @throws RuntimeException if the CSV resource cannot be found or read
     */
    public List<GemeenteMonument> importCsv(String resourcePath) {
        List<GemeenteMonument> monumenten = new ArrayList<>();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new RuntimeException("CSV resource not found: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] c = line.split(",");
                if (c.length < 8) {
                    continue;
                }

                String uniekNummer = c[7].trim();
                String straat = c[0].trim();
                String huisNr = c[1].trim();
                String huisLetter = c[2].trim();
                String postcode = c[3].trim();
                String plaats = c[4].trim();

                if (uniekNummer.isEmpty() || straat.isEmpty() || huisNr.isEmpty() || postcode.isEmpty() || plaats.isEmpty()) {
                    continue;
                }

                if (!uniekNummer.startsWith("GM-")) {
                    continue;
                }

                GemeenteMonument monument = new GemeenteMonument(uniekNummer, straat, huisNr, huisLetter, postcode, plaats);
                monumenten.add(monument);
            }
        } catch (Exception e) {
            System.err.println("Fout bij importeren CSV: " + e.getMessage());
        }

        return monumenten;
    }
}
