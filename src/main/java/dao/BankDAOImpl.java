package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.BankNotFoundException;
import model.Bank;

public class BankDAOImpl extends BaseDaoImpl implements BankDAO {
	
	 
	    
	public int getBankIdByName(String name) throws DAOException, BankNotFoundException {
		
		String sql = "SELECT ID FROM BANK WHERE NAME= ?";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("ID");
				return id;
			} else {
				throw new BankNotFoundException(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}
		
	}

	@Override
	public void save(Bank bank) throws DAOException, BankNotFoundException {
		String name = bank.getName();

		String sql = "INSERT INTO BANK(NAME) VALUES(?)";
		PreparedStatement stmt;

		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

	@Override
	public void remove(Bank bank) throws DAOException, BankNotFoundException {
		String name = bank.getName();

		String sql = "DELETE FROM BANK WHERE NAME = ? ";
		PreparedStatement stmt;
		try {
			openConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("");
		} finally {
			closeConnection();
		}

	}

}
