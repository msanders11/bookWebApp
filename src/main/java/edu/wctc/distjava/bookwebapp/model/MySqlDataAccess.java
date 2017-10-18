package edu.wctc.distjava.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.Vector;

/**
 *
 * @author Mike
 */
public class MySqlDataAccess implements DataAccess {

    private final int ALL_RECORDS = 0;
    private final boolean DEBUG = true;

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    @Override
    public void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public final int updateRecordById(String tableName, List<String> colNames, List<Object> colValues, Object pkValue, String pkColName) throws SQLException {
        //Validation - not null, min size of 1 for each list, see if each list size match

        String sql = "UPDATE " + tableName + " SET ";

        for (int i = 0; i < colNames.size(); i++) {
            if (i < colNames.size() - 1) {
                sql += colNames.get(i) + " = " + "?, ";
            } else {
                sql += colNames.get(i) + " = " + "?";
            }
        }

        sql += " WHERE " + pkColName + " = ?";

        pstmt = conn.prepareStatement(sql);

        for (int i = 1; i <= colValues.size(); i++) {
            pstmt.setObject(i, colValues.get(i - 1));
        }
        pstmt.setObject(colValues.size() + 1, pkValue);
        return pstmt.executeUpdate();
    }

    @Override
    public int createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(", ", "(", ")");

        for (String col : colNames) {
            sj.add(col);
        }

        //adding the for loop results to the sql String
        sql += sj.toString();
        sql += " VALUES ";

        //reinstantiate the StringJoing to remove data already stored in
        sj = new StringJoiner(", ", "(", ")");

        for (Object value : colValues) {
            sj.add("?");
        }
        sql += sj.toString();
        if (DEBUG) {
            System.out.println(sql);
        }

        pstmt = conn.prepareStatement(sql);

        for (int i = 1; i <= colValues.size(); i++) {
            pstmt.setObject(i, colValues.get(i - 1));
        }

        return pstmt.executeUpdate();
    }

    public final List<Map<String, Object>> getRecordById(String tableName, String pkColName, Object pkValue) throws SQLException {
        List<Map<String, Object>> rawData = new Vector<>();
//        String sql = "SELECT " + colName + " FROM " + tableName + " WHERE "
        String sql = "SELECT * FROM " + tableName + " WHERE "
                + pkColName + " = " + pkValue;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = null;

        while (rs.next()) {
            record = new LinkedHashMap();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }

        return rawData;
    }

    /**
     * Returns records from table, requires Open Connection
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    public final List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        if (maxRecords > ALL_RECORDS) {
            sql = "select * from " + tableName + " limit " + maxRecords;
        } else {
            sql = "select * from " + tableName;
        }

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = null;

        while (rs.next()) {
            record = new LinkedHashMap();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }

        return rawData;
    }

    @Override
    public final int deleteRecordById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM " + tableName + " WHERE " + pkColName + " = ?";

        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);

        return pstmt.executeUpdate();
    }

    //Testing the getAll method against a DBs
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataAccess db = new MySqlDataAccess();

        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root",
                "admin");

//        int recsUpdated = db.updateRecordById("author",
//                Arrays.asList("author_name", "date_added"),
//                Arrays.asList("James Patterson", "10-10-17"),
//                6,
//                "author_id");
        List<Map<String, Object>> list = db.getRecordById("author", "author_id", 29);

        for (Map<String, Object> rec : list) {
            System.out.println(rec);
        }

        db.closeConnection();

        //Test adding a record to the DB
//        int recsAdded = db.createRecord("author",
//                Arrays.asList("author_name", "date_added"),
//                Arrays.asList("Mark Twain", "12-12-26")
//        );
//
//        db.closeConnection();
//        
//        System.out.println("Recs created: " + recsAdded);
//        
//        db.openConnection(
//                "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root",
//                "admin"
//        );
//        
//        //Test for Deleting records by PK
//        int recsDeleted = db.deleteRecordById("author", "author_id", 3);
//
//        //Test for Retrieving all records
//        List<Map<String, Object>> list = db.getAllRecords("author", 0);
//
//        for (Map<String, Object> rec : list) {
//            System.out.println(rec);
//        }
//        
//        db.closeConnection();
//
//    }
    }
}
