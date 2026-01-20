package nl.kampmeijer.brp2.services;

import nl.kampmeijer.brp2.data.CSVReader;
import nl.kampmeijer.brp2.models.GemeenteMonument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GemeenteMonumentService {

    private final Map<String, GemeenteMonument> monumentenByUniekNummer;

    public GemeenteMonumentService(String csvPath) {
        monumentenByUniekNummer = new HashMap<>();

        CSVReader CSVRReader = new CSVReader();

        for (GemeenteMonument m : CSVRReader.importCsv(csvPath)) {
            monumentenByUniekNummer.put(m.getUniekNummer(), m);
        }
    }

    public GemeenteMonument getByUniekNummer(String uniekNummer) {
        return monumentenByUniekNummer.get(uniekNummer);
    }

    public Collection<GemeenteMonument> getAll() {
        return monumentenByUniekNummer.values();
    }
}
