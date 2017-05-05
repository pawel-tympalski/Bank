package command.server;

import java.io.IOException;
import command.bank.BankCommander;
import command.bank.BankStatisticsCommand;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;

public class BankStatisticsServerCommand extends ServerCommand {

	public BankStatisticsServerCommand(ServerConnection serverConnection) {
		super(serverConnection);
		
	}
	@Override
	public void execute() throws IOException, ClassNotFoundException, DAOException, BankNotFoundException,
		NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, BankException {
		BankStatisticsCommand statisticCommand = (BankStatisticsCommand)BankCommander.getMap().get("BankStatistics");
		statisticCommand.execute();
		sendMessageString(statisticCommand.getInfo());
	}
}
