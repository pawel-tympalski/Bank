package command.client;

import java.io.IOException;
import java.net.UnknownHostException;

public interface BankClientCommand {

	void execute() throws ClassNotFoundException,UnknownHostException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
}
