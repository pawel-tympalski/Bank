package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import command.bank.BankCommander;
import exception.BankNotFoundException;
import model.AbstractAccount;
import model.Bank;
import model.CheckingAccount;
import model.Client;
import model.Gender;
import model.SavingAccount;

public class ClientDAOImpl extends BaseDaoImpl implements ClientDAO  {

	@Override
	public Client findClientByName(Bank bank, String name) throws DAOException, BankNotFoundException {

		String bankName = bank.getName();

		String sql = "SELECT C.ID, C.NAME, C.GENDER, C.OVERDRAFT, C.PHONE, C.EMAIL, C.CITY FROM CLIENTS AS C JOIN BANK AS B ON C.BANKID=B.ID WHERE (B.NAME=? AND C.NAME=?)";
		String sql2 = "SELECT A.ID, A.BALANCE, A.TYPE FROM ACCOUNTS AS A JOIN CLIENTS AS C ON A.CLIENTID = C.ID WHERE C.NAME =?";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, bankName);
			System.out.println(name);
			stmt.setString(2, name);
			Client client;
			ResultSet rs = stmt.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				int id = rs.getInt("ID");
				String clientName = rs.getString("NAME");
				String gender = rs.getString("GENDER");
				Gender gender2 = null;
				if (gender.equalsIgnoreCase("m")) {
					gender2 = Gender.MALE;
				}
				if (gender.equalsIgnoreCase("f")) {
					gender2 = Gender.FEMALE;
				}

				float overdraft = rs.getFloat("OVERDRAFT");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String city = rs.getString("CITY");

				client = new Client(clientName, gender2, overdraft, phone, email, city,id);
				

			} else {
				throw new DAOException("Client of given name does not exist");
			}
			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, name);
			rs = stmt.executeQuery();

			while (rs.next()) {
				AbstractAccount abstractAccount = null;
				String accountType = rs.getString("TYPE");
				Float balance = rs.getFloat("BALANCE");
				int accountId = rs.getInt("ID");
				
				if (accountType.equalsIgnoreCase("c")) {

					abstractAccount = new CheckingAccount(balance, accountId, client.getOverdraft());
					abstractAccount.setAccountID(accountId);
					
				}
				if (accountType.equalsIgnoreCase("s")) {

					abstractAccount = new SavingAccount(balance, accountId);
					abstractAccount.setAccountID(accountId);
				}

				client.addAccount(abstractAccount);
			}

			return client;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public List<Client> getAllClients(String bankName) throws DAOException, BankNotFoundException {

		
		String sql = "SELECT C.ID, C.NAME, C.GENDER, C.OVERDRAFT, C.PHONE, C.EMAIL, C.CITY FROM CLIENTS AS C JOIN BANK AS B ON C.BANKID = B.ID where B.NAME=?";
		String sql2 = "SELECT A.ID, A.BALANCE, A.TYPE, A.CLIENTID FROM ACCOUNTS AS A JOIN CLIENTS AS C ON A.CLIENTID = C.ID WHERE C.NAME =?";
		PreparedStatement stmt;
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, bankName);

			Client client;
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int clientId = rs.getInt("ID");
				String clientName = rs.getString("NAME");
				String gender = rs.getString("GENDER");
				Gender gender2 = null;
				if (gender.equalsIgnoreCase("m")) {
					gender2 = Gender.MALE;
				}
				if (gender.equalsIgnoreCase("f")) {
					gender2 = Gender.FEMALE;
				}

				float overdraft = rs.getFloat("OVERDRAFT");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				String city = rs.getString("CITY");

				client = new Client(clientName, gender2, overdraft, phone, email, city,clientId);

				clientList.add(client);

			}

			stmt = conn.prepareStatement(sql2);
			
			for (Client client2 : clientList) {

				stmt.setString(1, client2.getName());
				rs = stmt.executeQuery();

				while (rs.next()) {
					
					SavingAccount savingAccount = null;
					CheckingAccount checkingAccount = null;
					int id = rs.getInt("ID");
					String type = rs.getString("TYPE");
					Float balance = rs.getFloat("BALANCE");
					

					if (type.equalsIgnoreCase("c")) {

						checkingAccount = new CheckingAccount(balance, id, client2.getOverdraft());
						checkingAccount.setAccountID(id);
						client2.addAccount(checkingAccount);
					}
					if (type.equalsIgnoreCase("s")) {

						savingAccount = new SavingAccount(balance, id);
						savingAccount.setAccountID(id);
						client2.addAccount(savingAccount);
					}

				}

			}
			return clientList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public void save(Client client) throws DAOException {

		Bank currentBank = BankCommander.getCurrentBank();
		String nameOfBank = currentBank.getName();
		int bankID = 0;

		String city = client.getCity();
		String email = client.getEmail();

		String phone = client.getPhone();
		Float overdraft = client.getOverdraft();
		String salutation = client.getClientSalutation();
		String gender = null;

		if (salutation.equalsIgnoreCase("Mr.")) {
			gender = "m";
		}

		if (salutation.equalsIgnoreCase("Ms")) {
			gender = "f";
		}

		String clientName = client.getName();

		String sq = "SELECT ID FROM CLIENTS WHERE NAME=?";

		String sql = "INSERT INTO CLIENTS(NAME,GENDER,OVERDRAFT,PHONE,EMAIL,CITY,BANKID) VALUES(?,?,?,?,?,?,?) ";

		String sql3 = "UPDATE CLIENTS SET NAME=?,GENDER=?,OVERDRAFT=?,PHONE=?,EMAIL=?,CITY=?,BANKID=? WHERE NAME=?";

		String getBankID = "SELECT ID FROM BANK WHERE NAME=?";
		
		try {
			openConnection();
			
			PreparedStatement stmt = conn.prepareStatement(getBankID);
			stmt.setString(1, nameOfBank);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				bankID = rs.getInt(1);
			}
			

			stmt = conn.prepareStatement(sq);
			stmt.setString(1, clientName);

			 rs = stmt.executeQuery();

			if (!rs.next()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, clientName);
				stmt.setString(2, gender);
				stmt.setFloat(3, overdraft);
				stmt.setString(4, phone);
				stmt.setString(5, email);
				stmt.setString(6, city);
				stmt.setInt(7, bankID);
				stmt.executeUpdate();

				
				String sql5 = "SELECT ID FROM CLIENTS WHERE NAME=?";
				PreparedStatement preparedStatement = conn.prepareStatement(sql5);
				preparedStatement.setString(1, clientName);

				ResultSet rs3 = preparedStatement.executeQuery();
				if (rs3.next()) {
					
					BankCommander.setCurrentClient(client);
				}
				

			} else {
				PreparedStatement stmt2 = conn.prepareStatement(sql3);
				stmt2.setString(1, clientName);
				stmt2.setString(2, gender);
				stmt2.setFloat(3, overdraft);
				stmt2.setString(4, phone);
				stmt2.setString(5, email);
				stmt2.setString(6, city);
				stmt2.setInt(7, bankID);
				stmt2.setString(8, clientName);
				stmt2.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} catch (BankNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public void remove(Client client) throws DAOException, BankNotFoundException {

		String sql = "DELETE  FROM CLIENTS WHERE NAME=?";
		 
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getName());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}
	}

}
