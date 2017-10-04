package edu.wctc.distjava.bookwebapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class Ms_SqlServerDataAccess implements DataAccess {

    private final int ALL_RECORDS = 0;

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public Ms_SqlServerDataAccess(String driverClass, String url, String userName, String password) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }

    @Override
    public void openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
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
    @Override
    public List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        if (maxRecords > ALL_RECORDS) {
            sql = "select TOP " + maxRecords + " * from " + tableName;
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
    public int deleteRecordById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + pkColName + " = ";

        if (pkValue instanceof String) {
            sql += "'" + pkValue.toString() + "'";
        } else {
            sql += Long.parseLong(pkValue.toString());
        }

        openConnection();
        stmt = conn.createStatement();
        int recsDeleted = stmt.executeUpdate(sql);

        closeConnection();

        return recsDeleted;
    }

    @Override
    public final String getDriverClass() {
        return driverClass;
    }

    @Override
    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public final String getUrl() {
        return url;
    }

    @Override
    public final void setUrl(String url) {
        this.url = url;
    }

    @Override
    public final String getUserName() {
        return userName;
    }

    @Override
    public final void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public final String getPassword() {
        return password;
    }

    @Override
    public final void setPassword(String password) {
        this.password = password;
    }

    //Testing the getAll method against a DBs
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataAccess db = new Ms_SqlServerDataAccess(
                "org.apache.derby.jdbc.ClientDriver",
                "jdbc:derby://localhost:1527/sample",
                "app",
                "app"
        );

        List<Map<String, Object>> list = db.getAllRecords("CUSTOMER", 0);

        for (Map<String, Object> rec : list) {
            System.out.println(rec);
        }

    }

}
