package dao;

import exception.BankNotFoundException;
import model.Bank;

interface BankDAO {
    
    int getBankIdByName (String name) throws DAOException, BankNotFoundException;

    void save(Bank bank) throws DAOException, BankNotFoundException;

    void remove(Bank bank) throws DAOException, BankNotFoundException;
}