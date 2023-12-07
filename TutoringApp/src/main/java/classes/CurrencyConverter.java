package classes;

import java.io.Serializable;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class CurrencyConverter implements Serializable {
  /*  public String convertLetter(String curr1, String curr2, double amount) throws ServiceException, RemoteException {
        FxtopServicesPortType service = new FxtopServicesLocator().getFxtopServicesPort();
        double newAmount = Double.parseDouble(service.convert(new Double(amount).toString(), curr1, curr2, "", "",
                "").getExchangeRate());
        NumberConversionSoapType NumberConversionSoapType numberToLetter numberToLetter = new NumberConversionLocator
        NumberConversionLocator().getNumberConversionSoap getNumberConversionSoap();
        String amountString = numberToLetter.numberToWords(new UnsignedLong(Math.round(amount)));
        String newAmountString = numberToLetter.numberToWords(new UnsignedLong(Math.round(newAmount)));
        String phrase = amountString + " " + curr1 + " = " + newAmountString + " " + curr2;
        return phrase;
    }*/
}