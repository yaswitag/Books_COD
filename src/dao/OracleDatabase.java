
package dao;

import java.sql.Connection;
import oracle.jdbc.pool.OracleDataSource;

public class OracleDatabase {
    
    private static OracleDataSource ods;
    static {
        try {
            ods = new OracleDataSource();
            ods.setDriverType("thin");
            ods.setServerName("localhost");
            ods.setNetworkProtocol("tcp");
            ods.setDatabaseName("xe");
            ods.setPortNumber(1521);
            ods.setUser("books");
            ods.setPassword("books");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }

    public static Connection getConnection() {
        try {
            return ods.getConnection();
        } catch (Exception ex) {
            return null;
        }
    }
}
