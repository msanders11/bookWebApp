package edu.wctc.distjava.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Mike
 */
public class MySqlDataAccess implements DataAccess {

    private final int ALL_RECORDS = 0;

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public MySqlDataAccess(String driverClass, String url, String userName, String password) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }

    public void openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
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
    public final List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        if (maxRecords > ALL_RECORDS) {
            sql = "select * from " + tableName + " limit " + maxRecords;
        } else {
            sql = "select * from " + tableName;
        }
        openConnection();
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

        closeConnection();

        return rawData;
    }

    @Override
    public final int deleteRecordById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " + pkColName + " = ?";
        
        openConnection();   
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);
        int recsDeleted = pstmt.executeUpdate();

        closeConnection();
        
        return recsDeleted;        
    }
    
    
    public final String getDriverClass() {
        return driverClass;
    }

    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public final String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    //Testing the getAll method against a DBs
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataAccess db = new MySqlDataAccess(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root",
                "admin"
        );
        
        //Test for Deleting records by PK
        int recsDeleted = db.deleteRecordById("author", "author_id", 3);

        //Test for Retrieving all records
        List<Map<String, Object>> list = db.getAllRecords("author", 0);

        for (Map<String, Object> rec : list) {
            System.out.println(rec);
        }
        
        

    }

}
