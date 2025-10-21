package nl.kampmeijer.fgt1_fysio.schermen;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import nl.kampmeijer.fgt1_fysio.DBCPDataSource;
import nl.kampmeijer.fgt1_fysio.classes.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SchermPatientAdresGegevens {
    private Button addButton = new Button("Toevoegen");
    private Button updateButton = new Button("Aanpassen");
    private Button deleteButton = new Button("Verwijderen");
    private ComboBox<Patient> patientBox = new ComboBox<>();
    private ComboBox<Adres> adresBox = new ComboBox<>();
    private ComboBox<Woonplaats> woonplaatsBox = new ComboBox<>();

    private ListView<PatientAdresGegevens> listview = new ListView();
    private ArrayList<PatientAdresGegevens> allPatientAdresGegevens = new ArrayList();

    public SchermPatientAdresGegevens(Pane root) {
        addButton.relocate(300, 180);
        updateButton.relocate(380, 180);
        deleteButton.relocate(300, 280);
        patientBox.relocate(300, 50);
        adresBox.relocate(300, 90);
        woonplaatsBox.relocate(300, 130);

        ResultSet r = null;
        try {
            r = getData("""
        SELECT pag.patient_id,
               pag.adres_id,
               pag.woonplaats_id
               p.pnm AS patient_name,
               a.anm AS adres_name,
               w.wpnm AS woonplaats_name,
        FROM patientenadresgegevens pag
        JOIN patienten p ON (p.id = pag.patient_id)
        JOIN adressen a ON (a.id = pag.adres_id)
        JOIN woonplaatsen w ON (w.id = pag.woonplaats_id)
    """);
            while (r.next()) {
                PatientAdresGegevens pag = new PatientAdresGegevens();
                pag.setPatient_id(r.getInt("patient_id"));
                pag.setAdres_id(r.getInt("adres_id"));
                pag.setWoonplaats_id(r.getInt("woonplaats_id"));
                pag.setPatient_name(r.getString("patient_name"));
                pag.setAdres_name(r.getString("adres_name"));
                pag.setWoonplaats_name(r.getString("woonplaats_name"));
                allPatientAdresGegevens.add(pag);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van patientenadresgegevens");
            se.printStackTrace();
        }

        for (PatientAdresGegevens pag : allPatientAdresGegevens) { // for - each
            listview.getItems().add(pag);
        }

        loadPatients();
        loadAdresses();
        loadWoonplaatsen();

        addButton.setOnAction(event -> {
            Patient addPatient = patientBox.getSelectionModel().getSelectedItem();
            Adres addAdres = adresBox.getSelectionModel().getSelectedItem();
            Woonplaats addWoonplaats = woonplaatsBox.getSelectionModel().getSelectedItem();

            if (addPatient!= null && addAdres != null &&  addWoonplaats != null) {
                int iResult = insertData("insert into patientenadresgegevens(patient_id, adres_id, woonplaats_id) " +
                        "values ('" + addPatient.getId() + ", " + addAdres.getId() +", " + addWoonplaats.getId() + "')");
                System.out.println(iResult + " rij toegevoegd");
                if (iResult > 0) {
                    PatientAdresGegevens newPatientAdresGegevens = new PatientAdresGegevens();
                    newPatientAdresGegevens.setPatient_name(addPatient.getPnm());
                    newPatientAdresGegevens.setAdres_name(addAdres.getAnm());
                    newPatientAdresGegevens.setWoonplaats_id(addWoonplaats.getId());
                    listview.getItems().add(newPatientAdresGegevens);
                }
            }
        });

        updateButton.setOnAction(event -> {
            Patient updatePatient = patientBox.getSelectionModel().getSelectedItem();
            Adres updateAdres = adresBox.getSelectionModel().getSelectedItem();
            Woonplaats updateWoonplaats = woonplaatsBox.getSelectionModel().getSelectedItem();
            PatientAdresGegevens selectedItem = listview.getSelectionModel().getSelectedItem();

            if (updatePatient!= null && updateAdres != null &&  updateWoonplaats != null) {
                int iResult = updateData("UPDATE patientenadresgegevens " +
                        "SET patient_id = " + updatePatient.getId() + ", adres_id = " + updateAdres.getId() +  ", woonplaats_id = " + updateWoonplaats.getId() +
                        " WHERE patient_id = " + selectedItem.getPatient_id() + " AND adres_id = " + selectedItem.getAdres_id() + " AND woonplaats_id = " + selectedItem.getWoonplaats_id());
                System.out.println(iResult + " rij aangepast");
                if (iResult > 0) {
                    selectedItem.setPatient_name(updatePatient.getPnm());
                    selectedItem.setAdres_name(updateAdres.getAnm());
                    selectedItem.setWoonplaats_name(updateWoonplaats.getWpnm());
                    listview.refresh();
                }
            }
        });

        deleteButton.setOnAction(event -> {
            PatientAdresGegevens selectedItem = listview.getSelectionModel().getSelectedItem();
            int iResult = updateData("delete FROM patientenadresgegevens " +
                    "WHERE patient_id = " + selectedItem.getPatient_id() + " AND adres_id = " + selectedItem.getAdres_id() + " AND woonplaats_id = " + selectedItem.getWoonplaats_id());
            System.out.println(iResult + " rij verwijderd");
            if (iResult > 0) {
                listview.getItems().remove(selectedItem);
                listview.refresh();
            }
        });

        root.getChildren().addAll(addButton, patientBox, adresBox, woonplaatsBox, updateButton, deleteButton, listview);
    }

    private void loadPatients() {
        ResultSet r = null;
        try {
            r = getData("select * from patienten");
            while (r.next()) {
                Patient p = new Patient();
                p.setId(r.getInt("id"));
                p.setPnm(r.getString("pnm"));
                patientBox.getItems().add(p);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van patienten");
            se.printStackTrace();
        }
    }

    private void loadAdresses() {
        ResultSet r = null;
        try {
            r = getData("select * from adressen");
            while (r.next()) {
                Adres a = new Adres();
                a.setId(r.getInt("id"));
                a.setAnm(r.getString("anm"));
                adresBox.getItems().add(a);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van adressen");
            se.printStackTrace();
        }
    }

    private void loadWoonplaatsen() {
        ResultSet r = null;
        try {
            r = getData("select * from woonplaatsen");
            while (r.next()) {
                Woonplaats w = new Woonplaats();
                w.setId(r.getInt("id"));
                w.setWpnm(r.getString("wpnm"));
                woonplaatsBox.getItems().add(w);
            }
        } catch (Exception se) {
            System.out.println("Fout bij ophalen van woonplaatsen");
            se.printStackTrace();
        }
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
