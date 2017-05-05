package dao;

import java.sql.Connection;

import exception.BankNotFoundException;

public interface BaseDAO {

	Connection openConnection() throws DAOException, BankNotFoundException;
	
	void closeConnection() throws DAOException;

	Connection getConnection();
}
