package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Woonplaats;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermWoonplaats {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private TextField textField = new TextField();
    private ListView<Woonplaats> listview = new ListView();
    private ArrayList<Woonplaats> allWoonplaats = new ArrayList();

    public SchermWoonplaats(Pane root) {
        addButton.relocate(290, 90);
        updateButton.relocate(390, 90);
        deleteButton.relocate(300, 150);
        textField.relocate(300, 50);

        ResultSet r = null;
        try {
            r = getData("select * from woonplaatsen");
            while (r.next()) {
                Woonplaats w = new Woonplaats();
                w.setId(r.getInt("id"));
                w.setWpnm(r.getString("wpnm"));
                allWoonplaats.add(w);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van woonplaatsen");
            se.printStackTrace();
        }

        for (Woonplaats w : allWoonplaats) { // for - each
            listview.getItems().add(w);
        }

        addButton.setOnAction(event -> {
            String addFieldText = textField.getText();

            if (!addFieldText.isEmpty()) {
                int iResult = insertData("insert into woonplaatsen(wpnm) values ('" + addFieldText + "')");
                System.out.println(iResult + " rij toegevoegd");
                Woonplaats newWoonplaats = new Woonplaats();
                newWoonplaats.setWpnm(addFieldText);
                listview.getItems().add(newWoonplaats);
                textField.clear();
            }
        });

        updateButton.setOnAction(event -> {
            String updateFieldText = textField.getText();
            Woonplaats selectedItem = listview.getSelectionModel().getSelectedItem();

            if (!updateFieldText.isEmpty()) {
                int iResult = updateData("UPDATE adressen SET wpnm = '" + updateFieldText + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                selectedItem.setWpnm(updateFieldText);
                listview.refresh();
                textField.clear();
            }
        });

        deleteButton.setOnAction(event -> {
            Woonplaats selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete from woonplaatsen WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            listview.getItems().remove(selectedItem);
            listview.refresh();
        });

        root.getChildren().addAll(addButton, textField, updateButton, deleteButton, listview);
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
