package services;

import classes.CurrencyConverter;
import interfaces.BankService;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;

public class BankServiceImpl extends UnicastRemoteObject implements BankService {
    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:C:/Users/Yasmine/IdeaProjects/TutoringApp/Bank.sqlite";
    CurrencyConverter converter = new CurrencyConverter();

    public BankServiceImpl() throws RemoteException {
        super();
    }


    @Override
    public boolean hasSufficientFunds(long accountnumber, float amount) throws RemoteException {
        String sql = " select balance from account where AccountNumber = ? ";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, accountnumber);
            ResultSet rs = pstmt.executeQuery();
            double balance = rs.getDouble("balance");
            if (balance < amount) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean verifyAccount(long account_number,String card_holder,Integer cvv,String account_type) throws RemoteException {
        String sql = " select * from account where AccountNumber = ? and Card_Holder = ? and cvv = ? and AccountType = ? ";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, account_number);
            pstmt.setString(2, card_holder);
            pstmt.setInt(3, cvv);
            pstmt.setString(4, account_type);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean processPayment(long cardNumber, String cardHolder, Integer cvv, String cardType,float rate) throws RemoteException {

        try {
            if (!verifyAccount(cardNumber, cardHolder, cvv, cardType)) {
                return false;
            }

            String sql = " select Currency from account where AccountNumber = ? ";
            String fromCurrency;
            try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, cardNumber);
                ResultSet rs = pstmt.executeQuery();
                fromCurrency = rs.getString("Currency");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            //float exchangeRate = converter.getExchangeRate(fromCurrency, "EUR");
            //float convertedAmount = exchangeRate * rate;
            // Check if 'fromAccount' has sufficient funds
            if (!hasSufficientFunds(cardNumber, rate)) {
                //classes.EmailUtil.sendEmail("rabeb.sdiri23@gmail.com", "Test Subject", "Hello, World!");

                return false; // Insufficient funds
            }
            // Deduct amount from 'fromAccount'
            deductAmount(cardNumber, rate);

            // Record the transaction
            //recordTransaction(fromAccount, toAccount, amount, fromCurrency, toCurrency);

            return true; // Transaction successful
        } catch (Exception e) {
            // Handle any exceptions, such as database errors
            e.printStackTrace();
            return false; // Transaction failed
        }
    }
    @Override
    public void deductAmount(long cardNumber, float amount) throws RemoteException {
        String sql = "UPDATE Account SET balance = balance - ? WHERE AccountNumber = ?";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, amount);
            pstmt.setLong(2, cardNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void recordTransaction(Integer fromAccount, Integer toAccount, float amount, String fromCurrency, String toCurrency) throws RemoteException {
        String sql = "INSERT INTO Transaction (FromAccountId,ToAccountId,Amount,TransactionDate,Currency) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(SQLITE_CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, fromAccount);
            pstmt.setInt(2, toAccount);
            pstmt.setDouble(3, amount);
            pstmt.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            pstmt.setString(5, fromCurrency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
