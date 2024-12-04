package htvbank;

import java.sql.*;

public class Connect {
    
    Connection c;
    Statement s;
    public Connect () {
        try {
            c = DriverManager.getConnection("jdbc:mysql:///HTVBank","root","wbCDkwT8");
            s = c.createStatement();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}