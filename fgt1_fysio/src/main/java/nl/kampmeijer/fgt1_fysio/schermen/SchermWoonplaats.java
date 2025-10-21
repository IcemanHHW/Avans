package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView<Woonplaats> listview = new ListView();
    private ArrayList<Woonplaats> allWoonplaats = new ArrayList();

    public SchermWoonplaats(Pane root) {
        addButton.relocate(400, 400);
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
            se.printStackTrace();
        }

        for (Woonplaats w : allWoonplaats) { // for - each
            listview.getItems().add(w);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into woonplaatsen(wpnm) values ('Hallo')");
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
