package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Datum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class SchermDatum {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private DatePicker datePicker = new DatePicker();
    private ListView<Datum> listview = new ListView();
    private ArrayList<Datum> allDatums = new ArrayList();

    public SchermDatum(Pane root) {
        addButton.relocate(290, 90);
        updateButton.relocate(390, 90);
        deleteButton.relocate(300, 150);
        datePicker.relocate(300, 50);

        ResultSet r = null;
        try {
            r = getData("select * from datums");
            while (r.next()) {
                Datum d = new Datum();
                d.setId(r.getInt("id"));
                d.setDtm(r.getString("dtm"));
                allDatums.add(d);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van adressen");
            se.printStackTrace();
        }

        for (Datum d : allDatums) { // for - each
            listview.getItems().add(d);
        }

        addButton.setOnAction(event -> {
             LocalDate addDate = datePicker.getValue();

            if (addDate != null) {
                int iResult = insertData("insert into datums(dtm) values ('" + addDate + "')");
                System.out.println(iResult + " rij toegevoegd");
                Datum newDatum = new Datum();
                newDatum.setDtm(addDate.toString());
                listview.getItems().add(newDatum);
            }
        });

        updateButton.setOnAction(event -> {
            LocalDate updateDate = datePicker.getValue();
            Datum selectedItem = listview.getSelectionModel().getSelectedItem();

            if (updateDate != null) {
                int iResult = updateData("UPDATE datums SET dtm = '" + updateDate + "' WHERE id = " + selectedItem.getId());
                System.out.println(iResult + " rij aangepast");
                selectedItem.setDtm(String.valueOf(updateDate));
                listview.refresh();
            }
        });

        deleteButton.setOnAction(event -> {
            Datum selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete from datums WHERE id = " + selectedItem.getId());
            System.out.println(iResult + " rij verwijderd");
            listview.getItems().remove(selectedItem);
            listview.refresh();
        });

        root.getChildren().addAll(addButton, datePicker, updateButton, deleteButton, listview);
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
