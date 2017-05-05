package command.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ExitClientCommand extends ClientCommand {

	public ExitClientCommand(ClientConnection connection) {
		super(connection);
	}

	@Override
	public void execute() throws UnknownHostException, IOException, ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		sendMessageInteger(4);
		
	}
}
