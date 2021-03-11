package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public Connection createCon(){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/receiptDb", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
