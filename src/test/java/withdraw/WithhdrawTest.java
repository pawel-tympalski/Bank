package withdraw;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import command.bank.BankCommander;
import command.bank.DBSelectBankCommander;
import command.bank.DBSelectClientCommander;
import command.bank.SetActiveAccount;
import dao.BaseDaoImpl;
import dao.DAOException;
import exception.BankException;
import exception.BankNotFoundException;
import service.Database;

public class WithhdrawTest {

	//Current client is Anna Smith
	//Balance of the active account - 5000
	
	@Test
	public void checkBalanceAfter100Withdraws() throws InterruptedException {
		
		BankServerThreadedMock  bankserver = new BankServerThreadedMock();
		try {
			Database database = new Database(new BaseDaoImpl());
			database.insertSampleDataToDatabase();
			((DBSelectBankCommander)BankCommander.getMap().get("SetActiveBank")).setBankName("ACL").execute();
			((DBSelectClientCommander)BankCommander.getMap().get("SetActiveClient")).setClientName("Anna Smith").execute();
			((SetActiveAccount)BankCommander.getMap().get("SetActiveAccount")).setNumber(66).execute();
			float amountBefore = BankCommander.getCurrentClient().getActiveAccount().getBalance();
			System.out.println(BankCommander.getCurrentBank().getName());
		
			bankserver.start();
		
			Thread mockClient = new Thread(new Runnable(){

				@Override
				public void run() {
					List<Thread> list = new ArrayList<Thread>();
					for(int i = 0; i<=99;i++){
						list.add(new Thread(new BankClientMock()));
					}
					for(int i = 0; i <=99;i++ ){
						list.get(i).start();
					
						try {
							list.get(i).join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				
				}
			
			});
			mockClient.start();
			mockClient.join();
		
			
			float amountAfter= BankCommander.getCurrentClient().getActiveAccount().getBalance();
		
		
			assertEquals(amountBefore - 100, amountAfter, 0.1);
		
		} catch (IOException | ClassNotFoundException | SQLException | DAOException | BankNotFoundException | NumberFormatException | BankException e) {
			e.printStackTrace();
		}
				

	}
}
