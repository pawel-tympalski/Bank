package dao;

import java.util.Set;
import exception.BankNotFoundException;
import model.AbstractAccount;

interface AccountDAO {
    public void save(AbstractAccount account) throws DAOException, BankNotFoundException;
    public void add(AbstractAccount account) throws DAOException, BankNotFoundException;
    public void removeByClientId(int idClient) throws DAOException, BankNotFoundException;
    public Set<AbstractAccount> getClientAccounts(int idClient) throws DAOException, BankNotFoundException;
}
