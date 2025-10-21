package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermPatient {
    private Button addButton = new Button("Toevoegen");
    private ListView<Patient> listview = new ListView();
    private ArrayList<Patient> allPatients = new ArrayList();

    public SchermPatient(Pane root) {
        addButton.relocate(400, 400);
        ResultSet r = null;
        try {
            r = getData("select * from patienten");
            while (r.next()) {
                Patient p = new Patient();
                p.setId(r.getInt("id"));
                p.setPnm(r.getString("pnm"));
                allPatients.add(p);
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

        for (Patient p : allPatients) { // for - each
            listview.getItems().add(p);
        }

        addButton.setOnAction(event -> {
            int iResult = insertData("insert into patienten(pnm) values ('Hallo')");
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
