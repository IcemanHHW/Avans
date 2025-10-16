package nl.kampmeijer.dbvoorbeeld;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class Scherm {

    private Button knop = new Button("Toevoegen");
    private ListView<Director> listview = new ListView();
    private ArrayList<Director> alDirectors = new ArrayList();

    public Scherm(Pane root) {
        knop.relocate(400, 400);
        ResultSet r = null;
        try {
            r = getData("select * from directors");
            while (r.next()) {
                Director d = new Director();
                d.setDirector_id(r.getInt("director_id"));
                d.setName(r.getString("name"));
                d.setCountry(r.getString("country"));
                alDirectors.add(d);
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        for (Director d : alDirectors) { // for - each
            listview.getItems().add(d);
        }

        knop.setOnAction(event -> {
            int iResult = insertData("insert into directors(director_id, name, country) values (99,'Martijn', 'Belgie')");
            System.out.println(iResult);
        });

        root.getChildren().add(listview);
        root.getChildren().add(knop);
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