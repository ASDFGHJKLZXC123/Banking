package db_objs;

import java.math.BigDecimal;
import java.util.Date;

/*
    Transaction entity used to store transaction data
 */
public class Transaction {
    private final int userID;
    private final String transactionType;
    private final BigDecimal transactionAmount;
    private final Date transactionDate;

    public Transaction(int userID, String transactionType, BigDecimal transactionAmount, Date transactionDate){
        this.userID = userID;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public int getUserID(){
        return userID;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public BigDecimal getTransactionAmount(){
        return transactionAmount;
    }

    public Date getTransactionDate(){
        return transactionDate;
    }


}
