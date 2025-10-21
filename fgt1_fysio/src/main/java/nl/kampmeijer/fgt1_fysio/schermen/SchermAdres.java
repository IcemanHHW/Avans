package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Adres;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermAdres {
    private Button addButton = new Button("Toevoegen");
    private ListView<Adres> listview = new ListView();
    private ArrayList<Adres> allAddresses = new ArrayList();

    public SchermAdres(Pane root) {
        addButton.relocate(400, 400);
        ResultSet r = null;
        try {
            r = getData("select * from adressen");
            while (r.next()) {
                Adres a = new Adres();
                a.setId(r.getInt("id"));
                a.setAnm(r.getString("anm"));
                allAddresses.add(a);
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        for (Adres a : allAddresses) { // for - each
            listview.getItems().add(a);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into adressen(anm) values ('Hallo')");
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
