package command.bank;

import java.io.IOException;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import model.BankInfo;

public class BankStatisticsCommand implements Command {
	private String info;
	@Override
	public void execute()
			throws DAOException, IOException, NumberFormatException, BankNotFoundException, BankException {
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBank(BankCommander.getCurrentBank());
		info = bankInfo.toString();
	}

	public String getInfo(){
		return info;
	}
	
	@Override
	public void printCommandInfo() {
	}

}
