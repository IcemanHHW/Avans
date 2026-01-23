package nl.kampmeijer.brp2.data;

import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    /**
     * Imports GemeenteMonument records from a CSV file.
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
     * @param filePath the path to the CSV file
     * @return a list of successfully imported {@link GemeenteMonument} objects
     */
    public List<GemeenteMonument> importCsv(String filePath) {
        List<GemeenteMonument> monumenten = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] c = line.split(",");
                if (c.length < 6) {
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
