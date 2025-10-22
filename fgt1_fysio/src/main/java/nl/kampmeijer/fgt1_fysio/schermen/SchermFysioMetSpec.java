package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.FysioMetSpec;
import nl.kampmeijer.fgt1_fysio.classes.Fysiotherapeut;
import nl.kampmeijer.fgt1_fysio.classes.Specialisatie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermFysioMetSpec {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private ComboBox<Fysiotherapeut> fysioBox = new ComboBox<>();
    private ComboBox<Specialisatie> specBox = new ComboBox<>();

    private ListView<FysioMetSpec> listview = new ListView();
    private ArrayList<FysioMetSpec> allFysiosMetSpecs = new ArrayList();

    public SchermFysioMetSpec(Pane root) {
        addButton.relocate(300, 130);
        updateButton.relocate(380, 130);
        deleteButton.relocate(300, 180);
        fysioBox.relocate(300, 50);
        specBox.relocate(300, 90);

        ResultSet r = null;
        try {
            r = getData("""
        SELECT fms.fysio_id,
               fms.spec_id,
               f.fysnm AS fysio_name,
               s.spec AS spec_type
        FROM fysiosmetspecs fms
        JOIN fysiotherapeuten f ON (f.id = fms.fysio_id)
        JOIN specialisaties s ON (s.id = fms.spec_id)
    """);
            while (r.next()) {
                FysioMetSpec fms = new FysioMetSpec();
                fms.setFysio_id(r.getInt("fysio_id"));
                fms.setSpec_id(r.getInt("spec_id"));
                fms.setFysio_name(r.getString("fysio_name"));
                fms.setSpec_type(r.getString("spec_type"));
                allFysiosMetSpecs.add(fms);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van fysiosmetspecs");
            se.printStackTrace();
        }

        for (FysioMetSpec fms : allFysiosMetSpecs) { // for - each
            listview.getItems().add(fms);
        }

        loadFysios();
        loadSpecialisaties();

        addButton.setOnAction(event -> {
            Fysiotherapeut addFysio = fysioBox.getSelectionModel().getSelectedItem();
            Specialisatie addSpec = specBox.getSelectionModel().getSelectedItem();

            if (addFysio != null && addSpec != null) {
                int iResult = insertData("INSERT INTO fysiosmetspecs (fysio_id, spec_id) VALUES (" + addFysio.getId() + ", " + addSpec.getId() + ")");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    FysioMetSpec newFysioMetSpec = new FysioMetSpec();
                    newFysioMetSpec.setFysio_name(addFysio.getFysnm());
                    newFysioMetSpec.setSpec_type(addSpec.getSpec());
                    listview.getItems().add(newFysioMetSpec);
                }
            }
        });

        updateButton.setOnAction(event -> {
            Fysiotherapeut updateFysio = fysioBox.getSelectionModel().getSelectedItem();
            Specialisatie updateSpec = specBox.getSelectionModel().getSelectedItem();
            FysioMetSpec selectedItem = listview.getSelectionModel().getSelectedItem();

            if (updateFysio != null && updateSpec != null) {
                int iResult = updateData("UPDATE fysiosmetspecs SET fysio_id = " + updateFysio.getId() + ", spec_id = " + updateSpec.getId() +
                        " WHERE fysio_id = " + selectedItem.getFysio_id() + " AND spec_id = " + selectedItem.getSpec_id());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setFysio_name(updateFysio.getFysnm());
                    selectedItem.setSpec_type(updateSpec.getSpec());
                    listview.refresh();
                }
            }
        });

        deleteButton.setOnAction(event -> {
            FysioMetSpec selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete FROM fysiosmetspecs WHERE fysio_id = " + selectedItem.getFysio_id() + " AND spec_id = " + selectedItem.getSpec_id());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });

        root.getChildren().addAll(addButton, fysioBox, specBox, updateButton, deleteButton, listview);
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
     * Loads the list of specialisaties from the database and populates the specBox dropdown
     * with the retrieved data. This method retrieves all the records from the <strong>specialisaties</strong> table,
     * maps each record to a Specialisatie object, and adds them to the specBox.
     * <p>
     * If an error occurs during the database operation, an error message is logged to the
     * standard output and the stack trace of the exception is printed.
     */
    private void loadSpecialisaties() {
        ResultSet r = null;
        try {
            r = getData("select * from specialisaties");
            while (r.next()) {
                Specialisatie s = new Specialisatie();
                s.setId(r.getInt("id"));
                s.setSpec(r.getString("spec"));
                specBox.getItems().add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van specialisaties");
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
