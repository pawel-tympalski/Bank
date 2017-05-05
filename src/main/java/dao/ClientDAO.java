package dao;

import java.util.List;
import exception.BankNotFoundException;
import model.Bank;
import model.Client;

interface ClientDAO {
    
    Client findClientByName (Bank bank, String name) throws DAOException, BankNotFoundException;
   
    List <Client> getAllClients (String bankName) throws DAOException, BankNotFoundException;

    void save (Client client) throws DAOException;

    void remove (Client client) throws DAOException, BankNotFoundException;
}