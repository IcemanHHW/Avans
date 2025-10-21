package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Fysiotherapeut;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermFysio {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private TextField textField = new TextField();
    private ListView<Fysiotherapeut> listview = new ListView();
    private ArrayList<Fysiotherapeut> allFysios = new ArrayList();

    public SchermFysio(Pane root) {
        addButton.relocate(290, 90);
        updateButton.relocate(390, 90);
        deleteButton.relocate(300, 150);
        textField.relocate(300, 50);

        ResultSet r = null;
        try {
            r = getData("select * from fysiotherapeuten");
            while (r.next()) {
                Fysiotherapeut f = new Fysiotherapeut();
                f.setId(r.getInt("id"));
                f.setFysnm(r.getString("fysnm"));
                allFysios.add(f);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van fysiotherapeuten");
            se.printStackTrace();
        }

        for (Fysiotherapeut f : allFysios) { // for - each
            listview.getItems().add(f);
        }

        addButton.setOnAction(event -> {
            String addFieldText = textField.getText();

            if (!addFieldText.isEmpty()) {
                int iResult = insertData("insert into fysiotherapeuten(fysnm) values ('" + addFieldText + "')");
                System.out.println(iResult + " rij toegevoegd");
                listview.refresh();
            }
        });

        updateButton.setOnAction(event -> {
            String updateFieldText = textField.getText();
            Fysiotherapeut selectedItem = listview.getSelectionModel().getSelectedItem();

            if (!updateFieldText.isEmpty()) {
                int iResult = updateData("UPDATE fysiotherapeuten SET fysnm = '" + updateFieldText + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                selectedItem.setFysnm(updateFieldText);
                listview.refresh();
            }
        });

        deleteButton.setOnAction(event -> {
            Fysiotherapeut selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete from fysiotherapeuten WHERE id = " + selectedItem.getId());
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
