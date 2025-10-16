package com.mycompany.movieapp;

import java.sql.SQLException;
import java.sql.Connection;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/blok1?useSSL=false"); //Vul hier de naam van de database in op de plek van 'blok1'
        ds.setUsername("avans"); // vul hier de gebruikersnaam in
        ds.setPassword("avans"); // vul hier het wachtwoord in
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() {

    }

}
