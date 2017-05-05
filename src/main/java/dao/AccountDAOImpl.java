package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import command.bank.BankCommander;
import exception.BankNotFoundException;
import model.AbstractAccount;
import model.CheckingAccount;
import model.Client;
import model.SavingAccount;

public class AccountDAOImpl extends BaseDaoImpl implements AccountDAO{

	@Override
	public void save(AbstractAccount account) throws DAOException, BankNotFoundException {

		Float balance = account.getBalance();
		System.out.println(balance);
		String typeAccount = account.getClass().getSimpleName();
		System.out.println(typeAccount);
		if (typeAccount.equalsIgnoreCase("SavingAccount")) {
			typeAccount = "s";
		} else if(typeAccount.equalsIgnoreCase("CheckingAccount")) {
			typeAccount = "c";
		}
		

		Client client = BankCommander.getCurrentClient();
		int clientID = client.getId();
		System.out.println(clientID);
		int accountId = account.getId();
		System.out.println(accountId);
		String sql = "UPDATE ACCOUNTS SET BALANCE = ? , TYPE = ? , CLIENTID = ? WHERE ID = ? ";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, balance);
			stmt.setString(2, typeAccount);
			stmt.setInt(3, clientID);
			stmt.setInt(4, accountId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public void add(AbstractAccount account) throws DAOException, BankNotFoundException {

		Float balance = account.getBalance();
		String typeAccount = account.getClass().getSimpleName();
		if (typeAccount.equalsIgnoreCase("SavingAccount")) {

			typeAccount = "s";
		} else if (typeAccount.equalsIgnoreCase("CheckingAccount")) {
				typeAccount = "c";
		}
		

		String sql = "INSERT INTO ACCOUNTS(BALANCE,TYPE,CLIENTID) VALUES(?,?,?)";

		PreparedStatement stmt;
		try {
			openConnection();

			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, balance);
			stmt.setString(2, typeAccount);
			stmt.setInt(3, BankCommander.getCurrentClient().getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public void removeByClientId(int idClient) throws DAOException, BankNotFoundException {

		String sql = "DELETE FROM ACCOUNTS WHERE CLIENTID =?";

		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idClient);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public Set<AbstractAccount> getClientAccounts(int idClient) throws DAOException, BankNotFoundException {
		
		Set<AbstractAccount> set = new HashSet<AbstractAccount>();
		String sql = "SELECT A.ID, A.BALANCE,A.TYPE,A.CLIENTID, C.OVERDRAFT FROM ACCOUNTS AS A JOIN CLIENTS AS C ON A.CLIENTID = C.ID WHERE A.CLIENTID =?";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, idClient);
			stmt.executeQuery();

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int accountId = rs.getInt("ID");
				float balance = rs.getFloat("BALANCE");
				String type = rs.getString("TYPE");
				Float overdraft = rs.getFloat("OVERDRAFT");
				AbstractAccount account = null;
				if (type.equalsIgnoreCase("c")) {
					account = new CheckingAccount(balance, accountId, overdraft);
				} else {
					if (type.equalsIgnoreCase("s")) {

						account = new SavingAccount(balance, accountId);
					}

				}

				set.add(account);
			}

			return set;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}
}
