package command.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class BankStatisticsClientCommand extends ClientCommand {

	public BankStatisticsClientCommand(ClientConnection connection) {
		super(connection);
		
	}

	@Override
	public void execute() throws ClassNotFoundException, UnknownHostException, IOException {
		sendMessageInteger(30);
		String message = getMessageString();
		System.out.println(message);
	}

}
