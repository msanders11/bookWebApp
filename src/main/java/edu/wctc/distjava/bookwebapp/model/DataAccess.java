package edu.wctc.distjava.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Mike
 */
public interface DataAccess {

    void closeConnection() throws SQLException;

    /**
     * Returns records from table, requires Open Connection
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) 
            throws SQLException, ClassNotFoundException;
    
    int deleteRecordById(String tableName, String pkColName, Object pkValue) 
            throws ClassNotFoundException, SQLException;

    public abstract void openConnection(String driverClass, String url, 
            String userName, String password) throws ClassNotFoundException, SQLException;

    public abstract int createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;
    
    public int updateRecordById(String tableName, List<String> colNames, List<Objects> colValues, Object pkValue, String pkColName) throws SQLException;
    
}
