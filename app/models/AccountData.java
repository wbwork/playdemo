package models;

import java.math.BigInteger;

import controllers.Application.BankForm;

public class AccountData {
  public Long accountno;
  public Long blz;

  public AccountData(BankForm bf) {

    accountno = new Long(bf.accountno);
    blz = new Long(bf.blz);
  }

  public String getIban() {
    // Assume a German Bank Account
    String praefix = "DE";
    Long bban = blz * 10000000000L + accountno;
    BigInteger bban2 = new BigInteger(bban.toString());

    BigInteger bbanWithCountryCode =
        bban2.multiply(new BigInteger("1000000")).add(new BigInteger("131400"));

    BigInteger checksum =
        new BigInteger("98").subtract(bbanWithCountryCode.mod(new BigInteger("97")));
    String checksumString =
        (checksum.compareTo(new BigInteger("10")) == -1 ? "0" + checksum.toString() : checksum
            .toString());
    String iban = praefix + checksumString + bban.toString();
    return iban;
  }

}
