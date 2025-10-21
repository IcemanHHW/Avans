package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView<Fysiotherapeut> listview = new ListView();
    private ArrayList<Fysiotherapeut> allFysios = new ArrayList();

    public SchermFysio(Pane root) {
        addButton.relocate(400, 400);
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
            se.printStackTrace();
        }

        for (Fysiotherapeut f : allFysios) { // for - each
            listview.getItems().add(f);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into fysiotherapeuten(fysnm) values ('Hallo')");
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
