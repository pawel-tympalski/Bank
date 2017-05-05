package command.bank;

import java.io.IOException;

import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public interface Command {
	
	void execute() throws DAOException,IOException,NumberFormatException, BankNotFoundException, BankException;
    void printCommandInfo();

}