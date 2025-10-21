package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Specialisatie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermSpec {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private TextField textField = new TextField();
    private ListView<Specialisatie> listview = new ListView();
    private ArrayList<Specialisatie> allSpecs = new ArrayList();

    public SchermSpec(Pane root) {
        addButton.relocate(290, 90);
        updateButton.relocate(390, 90);
        deleteButton.relocate(300, 150);
        textField.relocate(300, 50);

        ResultSet r = null;
        try {
            r = getData("select * from specialisaties");
            while (r.next()) {
                Specialisatie s = new Specialisatie();
                s.setId(r.getInt("id"));
                s.setSpec(r.getString("spec"));
                allSpecs.add(s);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van specialisaties");
            se.printStackTrace();
        }

        for (Specialisatie s : allSpecs) { // for - each
            listview.getItems().add(s);
        }

        addButton.setOnAction(event -> {
            String addFieldText = textField.getText();

            if (!addFieldText.isEmpty()) {
                int iResult = insertData("insert into specialisaties(spec) values ('" + addFieldText + "')");
                System.out.println(iResult + " rij toegevoegd");
                Specialisatie newSpec = new Specialisatie();
                newSpec.setSpec(addFieldText);
                listview.getItems().add(newSpec);
                textField.clear();

            }
        });

        updateButton.setOnAction(event -> {
            String updateFieldText = textField.getText();
            Specialisatie selectedItem = listview.getSelectionModel().getSelectedItem();

            if (!updateFieldText.isEmpty()) {
                int iResult = updateData("UPDATE specialisaties SET spec = '" + updateFieldText + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                selectedItem.setSpec(updateFieldText);
                listview.refresh();
                textField.clear();
            }
        });

        deleteButton.setOnAction(event -> {
            Specialisatie selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete from specialisaties WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            listview.getItems().remove(selectedItem);
            listview.refresh();
        });

        root.getChildren().addAll(addButton, textField, updateButton, deleteButton, listview);
    }

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
