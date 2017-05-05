package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.BankNotFoundException;

public class BaseDaoImpl implements BaseDAO {
    Connection conn;
    public Connection openConnection() throws DAOException, BankNotFoundException {
        try {
            Class.forName("org.h2.Driver"); 
            conn = DriverManager.getConnection("jdbc:h2:~/bank;mv_store=false",
                "sa", 
                "" 
                );
            return conn;
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
                             throw new DAOException("");
        }
       
    }
     public Connection getConnection(){
    	 return conn;
     }
 
    public void closeConnection() throws DAOException {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DAOException("");
            
        }
    }
}
