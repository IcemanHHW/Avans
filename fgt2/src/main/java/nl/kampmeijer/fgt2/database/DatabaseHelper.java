package nl.kampmeijer.fgt2.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    /**
     * Executes the given SQL query and retrieves the resulting data from the database.
     *
     * @param sql the SQL query to be executed
     * @return a ResultSet object containing the data retrieved from the query,
     *         or null if an error occurs or the query returns no results
     */
    public static ResultSet getData(String sql) {
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

    /**
     * Executes the given SQL insert statement to insert data into the database.
     *
     * @param insertStatement the SQL insert statement to be executed
     * @return the number of rows affected by the insert operation
     */
    public static int insertData(String insertStatement) {
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

    /**
     * Executes the given SQL update statement against the database.
     *
     * @param updateStatement the SQL update statement to be executed (e.g., UPDATE or DELETE statements)
     * @return the number of rows affected by the update operation
     */
    public static int updateData(String updateStatement) {
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
