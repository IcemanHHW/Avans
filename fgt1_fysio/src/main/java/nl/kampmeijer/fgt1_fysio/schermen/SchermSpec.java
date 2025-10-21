package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView<Specialisatie> listview = new ListView();
    private ArrayList<Specialisatie> allSpecs = new ArrayList();

    public SchermSpec(Pane root) {
        addButton.relocate(400, 400);
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
            se.printStackTrace();
        }

        for (Specialisatie s : allSpecs) { // for - each
            listview.getItems().add(s);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into specialisaties(spec) values ('Hallo')");
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
