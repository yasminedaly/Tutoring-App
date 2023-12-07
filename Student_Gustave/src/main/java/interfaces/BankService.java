package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankService extends Remote {
    //void createAccount( Integer userid, double balance,Integer accountnumber,String currency) throws RemoteException, ClassNotFoundException;
    boolean hasSufficientFunds(long accountnumber, float amount)throws RemoteException;

    boolean verifyAccount(long cardNumber, String cardHolder, Integer cvv, String cardType) throws RemoteException;

    boolean processPayment(long cardNumber, String cardHolder, Integer cvv, String cardType,float rate) throws RemoteException;
    void deductAmount(long cardNumber, float amount) throws RemoteException;

    void recordTransaction(Integer fromAccount, Integer toAccount, float amount, String fromCurrency, String toCurrency) throws RemoteException;
}