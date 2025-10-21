package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Datum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermDatum {
    private Button addButton = new Button("Toevoegen");
    private ListView<Datum> listview = new ListView();
    private ArrayList<Datum> allDatums = new ArrayList();

    public SchermDatum(Pane root) {
        addButton.relocate(400, 400);
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
            se.printStackTrace();
        }

        for (Datum d : allDatums) { // for - each
            listview.getItems().add(d);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into datums(dtm) values ('2025-10-21')");
            System.out.println(iResult);
        });

        root.getChildren().add(listview);
        root.getChildren().add(addButton);
    }

    public ResultSet getData(String sql) {
        ResultSet result = null;
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            result = stat.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Niet mijn fout !!!");
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
            System.out.println("Niet mijn fout !!!");
        }
        return result;
    }
}
