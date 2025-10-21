package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermFysioAfspraak {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private ComboBox<Fysiotherapeut> fysioBox = new ComboBox<>();
    private ComboBox<Patient> patientBox = new ComboBox<>();
    private ComboBox<Datum> datumBox = new ComboBox<>();

    private ListView<FysioAfspraak> listview = new ListView();
    private ArrayList<FysioAfspraak> allFysioAfspraken = new ArrayList();

    public SchermFysioAfspraak(Pane root) {
        addButton.relocate(300, 180);
        updateButton.relocate(380, 180);
        deleteButton.relocate(300, 280);
        fysioBox.relocate(300, 50);
        patientBox.relocate(300, 90);
        datumBox.relocate(300, 130);

        ResultSet r = null;
        try {
            r = getData("""
        SELECT fa.fysio_id,
               fa.patient_id,
               fa.datum_id
               f.fysnm AS fysio_name,
               p.pnm AS patient_name,
               d.dtm AS datum_date,
        FROM fysioafspraken fa
        JOIN fysiotherapeuten f ON (f.id = fa.fysio_id)
        JOIN patienten p ON (p.id = fa.patient_id)
        JOIN datums d ON (d.id = fa.datum_id)
    """);
            while (r.next()) {
                FysioAfspraak fa = new FysioAfspraak();
                fa.setFysio_id(r.getInt("fysio_id"));
                fa.setPatient_id(r.getInt("patient_id"));
                fa.setDatum_id(r.getInt("datum_id"));
                fa.setFysio_name(r.getString("fysio_name"));
                fa.setPatient_name(r.getString("patient_name"));
                fa.setDatum_date(r.getString("datum_date"));
                allFysioAfspraken.add(fa);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van fysioafspraken");
            se.printStackTrace();
        }

        for (FysioAfspraak fa : allFysioAfspraken) { // for - each
            listview.getItems().add(fa);
        }

        loadFysios();
        loadPatients();
        loadDatums();

        addButton.setOnAction(event -> {
            Fysiotherapeut addFysio = fysioBox.getSelectionModel().getSelectedItem();
            Patient addPatient = patientBox.getSelectionModel().getSelectedItem();
            Datum addDatum = datumBox.getSelectionModel().getSelectedItem();

            if (addFysio!= null && addPatient != null &&  addDatum != null) {
                int iResult = insertData("insert into fysioafspraken(fysio_id, patient_id, datum_id) " +
                        "values ('" + addFysio.getId() + ", " + addPatient.getId() +", " + addDatum.getId() + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    FysioAfspraak newFysioAfspraak = new FysioAfspraak();
                    newFysioAfspraak.setFysio_name(addFysio.getFysnm());
                    newFysioAfspraak.setPatient_name(addPatient.getPnm());
                    newFysioAfspraak.setDatum_date(addDatum.getDtm());
                    listview.getItems().add(newFysioAfspraak);
                }
            }
        });

        updateButton.setOnAction(event -> {
            Fysiotherapeut updateFysio = fysioBox.getSelectionModel().getSelectedItem();
            Patient updatePatient = patientBox.getSelectionModel().getSelectedItem();
            Datum updateDatum = datumBox.getSelectionModel().getSelectedItem();
            FysioAfspraak selectedItem = listview.getSelectionModel().getSelectedItem();

            if (updateFysio!= null && updatePatient != null &&  updateDatum != null) {
                int iResult = updateData("UPDATE fysioafspraken " +
                        "SET fysio_id = " + updateFysio.getId() + ", patient_id = " + updatePatient.getId() +  ", datum_id = " + updateDatum.getId() +
                        " WHERE fysio_id = " + selectedItem.getFysio_id() + " AND patient_id = " + selectedItem.getPatient_id() + " AND datum_id = " + selectedItem.getDatum_id());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setFysio_name(updateFysio.getFysnm());
                    selectedItem.setPatient_name(updatePatient.getPnm());
                    selectedItem.setDatum_date(updateDatum.getDtm());
                    listview.refresh();
                }
            }
        });

        deleteButton.setOnAction(event -> {
            FysioAfspraak selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete FROM fysioafspraken " +
                    "WHERE patient_id = " + selectedItem.getPatient_id() + " AND patient_id = " + selectedItem.getPatient_id() + " AND datum_id = " + selectedItem.getDatum_id());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });

        root.getChildren().addAll(addButton, fysioBox, patientBox, datumBox, updateButton, deleteButton, listview);
    }
    /**
     * Loads the list of fysiotherapeuten from the database and populates the fysioBox dropdown
     * with the retrieved data. This method retrieves all the records from the <strong>fysiotherapeuten</strong> table,
     * maps each record to a Fysiotherapeut object, and adds them to the fysioBox.
     * <p>
     * If an error occurs during the database operation, an error message is logged to the
     * standard output and the stack trace of the exception is printed.
     */
    private void loadFysios() {
        ResultSet r = null;
        try {
            r = getData("select * from fysiotherapeuten");
            while (r.next()) {
                Fysiotherapeut f = new Fysiotherapeut();
                f.setId(r.getInt("id"));
                f.setFysnm(r.getString("fysnm"));
                fysioBox.getItems().add(f);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van fysiotherapeuten");
            se.printStackTrace();
        }
    }
    /**
     * Loads the list of patienten from the database and populates the patientenBox dropdown
     * with the retrieved data. This method retrieves all the records from the <strong>patienten</strong> table,
     * maps each record to a Patient object, and adds them to the patientBox.
     * <p>
     * If an error occurs during the database operation, an error message is logged to the
     * standard output and the stack trace of the exception is printed.
     */
    private void loadPatients() {
        ResultSet r = null;
        try {
            r = getData("select * from patienten");
            while (r.next()) {
                Patient p = new Patient();
                p.setId(r.getInt("id"));
                p.setPnm(r.getString("pnm"));
                patientBox.getItems().add(p);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van patienten");
            se.printStackTrace();
        }
    }
    /**
     * Loads the list of datums from the database and populates the datumBox dropdown
     * with the retrieved data. This method retrieves all the records from the <strong>datums</strong> table,
     * maps each record to a Datum object, and adds them to the datumBox.
     * <p>
     * If an error occurs during the database operation, an error message is logged to the
     * standard output and the stack trace of the exception is printed.
     */
    private void loadDatums() {
        ResultSet r = null;
        try {
            r = getData("select * from datums");
            while (r.next()) {
                Datum d = new Datum();
                d.setId(r.getInt("id"));
                d.setDtm(r.getString("dtm"));
                datumBox.getItems().add(d);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van datums");
            se.printStackTrace();
        }
    }

    /**
     * Executes the given SQL query and retrieves the resulting data from the database.
     *
     * @param sql the SQL query to be executed
     * @return a ResultSet object containing the data retrieved from the query,
     *         or null if an error occurs or the query returns no results
     */
    public ResultSet getData(String sql) {
        ResultSet result = null;
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            result = stat.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Fout bij getData()");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Executes the given SQL insert statement to insert data into the database.
     *
     * @param insertStatement the SQL insert statement to be executed
     * @return the number of rows affected by the insert operation
     */
    public int insertData(String insertStatement) {
        int result = 0;
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            result = stat.executeUpdate(insertStatement);
        } catch (SQLException e) {
            System.out.println("Fout bij insertData()");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Executes the given SQL update statement against the database.
     *
     * @param updateStatement the SQL update statement to be executed (e.g., UPDATE or DELETE statements)
     * @return the number of rows affected by the update operation
     */
    public int updateData(String updateStatement) {
        int result = 0;
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            result = stat.executeUpdate(updateStatement);
        } catch (SQLException e) {
            System.out.println("Fout bij updateData()");
            e.printStackTrace();
        }
        return result;
    }
}
