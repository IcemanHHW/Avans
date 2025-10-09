package nl.kampmeijer.dbvoorbeeld;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class SchermMoviesRead {

    private Button knop = new Button("Toevoegen");
    private ListView<Movie> listview = new ListView();
    private ArrayList<Movie> allMovies = new ArrayList();

    public SchermMoviesRead(Pane root) {
        knop.relocate(400, 400);
        ResultSet r = null;
        try {
            r = getData("select * from movies");
            while (r.next()) {
                Movie m = new Movie();
                m.setMovie_id(r.getInt("movie_id"));
                m.setTitle(r.getString("title"));
                m.setYear(r.getString("year"));
                m.setDirector_id(r.getInt("director_id"));
                allMovies.add(m);
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        for (Movie m : allMovies) { // for - each
            listview.getItems().add(m);
        }

        knop.setOnAction(event -> {
            int iResult = insertData("insert into movies(movie_id, title, year, director_id) values (99,'Hallo', '2014', 3)");
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