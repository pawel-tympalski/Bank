package service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.BaseDAO;
import dao.DAOException;
import exception.BankNotFoundException;

public class Database {

	BaseDAO databaseConnection;
	
	public  Database(BaseDAO c){
		databaseConnection = c;
	}
	
	public void openConnection() throws ClassNotFoundException, SQLException, DAOException, BankNotFoundException {
		databaseConnection.openConnection();
			
	}
	
	public void closeConnection() throws SQLException, DAOException {

		databaseConnection.closeConnection();
	}
	
	public void insertSampleDataToDatabase() throws ClassNotFoundException, SQLException, DAOException, BankNotFoundException{
		openConnection();
		dropTable();
		createBankTable();
		createClientsTable();
		createAccountsTable();
		
		String bankName = "ACL";
		insertBank(bankName);
		int id1 = IDGeneratorClients.generateID(bankName);
		int id2 = IDGeneratorClients.generateID(bankName);	
		int id3 = IDGeneratorClients.generateID(bankName);	
		
		insertClients(id1,"Anna Smith", "f", 1000, "12 567", "sds@de.pl", "Boston", "ACL");
		insertClients(id2,"Richard Jones", "m", 3000, "222 22", "dfd@re.pl", "New York", "ACL");
		insertClients(id3,"Robert Harris", "m", 2000, "333 333", "rot@et.pl", "Los Angeles", "ACL");

		IDGeneratorAccounts.generateID(bankName);
		
		insertAccounts(66,5000, "c", id1);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),3000, "s", id1);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),4000, "s", id1);

		insertAccounts(IDGeneratorAccounts.generateID(bankName),6000, "c", id2);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),2000, "s", id2);

		insertAccounts(IDGeneratorAccounts.generateID(bankName),5000, "c", id3);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),3000, "s", id3);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),2000, "s", id3);
		insertAccounts(IDGeneratorAccounts.generateID(bankName),1000, "s", id3);
		
		closeConnection();
		
	}
	
	public void dropTable() throws SQLException {

		String sql = "DROP TABLE IF EXISTS BANK,CLIENTS,ACCOUNTS";
		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);
	}
	
	
	
	public void createBankTable() throws SQLException {
		String sql = "CREATE TABLE BANK (ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL , NAME VARCHAR(100))";

		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);

	}
	
	public void createClientsTable() throws SQLException {

		String sql = "CREATE TABLE CLIENTS (ID INT PRIMARY KEY  NOT NULL, NAME VARCHAR(100), GENDER VARCHAR(10), OVERDRAFT FLOAT, PHONE VARCHAR(40),EMAIL VARCHAR(50), CITY VARCHAR(50), BANKID INT,FOREIGN KEY(BANKID) REFERENCES BANK(ID)  ) ";
		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);

	}
	
	
	public void createAccountsTable() throws SQLException {

		String sql = "CREATE TABLE ACCOUNTS (ID INT PRIMARY KEY NOT NULL, BALANCE FLOAT, TYPE VARCHAR(10),CLIENTID INT ,FOREIGN KEY(CLIENTID) REFERENCES CLIENTS(ID))";
		
		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);
	}
	
	
	
	public void insertBank(String name) throws SQLException {

		String sql = "INSERT INTO BANK(NAME) VALUES(" + "'" + name + "')";

		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);

	}
	
	public void insertClients(int id,String name, String gender, float overdraft, String phone, String email, String city,
			String bankName) throws SQLException {
		int bankId = 0;
		String sql2 = "SELECT ID FROM BANK WHERE NAME = ?";
		PreparedStatement stmt2 = databaseConnection.getConnection().prepareStatement(sql2);
		stmt2.setString(1, bankName);
		ResultSet rs = stmt2.executeQuery();
		while(rs.next()){
			bankId = rs.getInt(1);
		}
		
		String sql = "INSERT INTO CLIENTS(ID, NAME, GENDER,OVERDRAFT,PHONE,EMAIL,CITY,BANKID) VALUES(" + id + ","+ "'" + name + "','"
				+ gender + "'," + overdraft + ",'" + phone + "','" + email + "','" + city + "'," + bankId + ")";

		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);

	}
	
	public void insertAccounts(int id,float balance, String type, int clientId) throws SQLException {
		String sql = "INSERT INTO ACCOUNTS(ID, BALANCE, TYPE, CLIENTID) VALUES(" + id + ","  + balance + ",'" + type + "'," + clientId
				+ ")";

		Statement stmt = databaseConnection.getConnection().createStatement();
		stmt.executeUpdate(sql);

	}
}
