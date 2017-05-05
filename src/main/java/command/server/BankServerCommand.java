package command.server;

import java.io.IOException;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public interface BankServerCommand {

	void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, BankException;
}
