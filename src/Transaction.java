import java.time.LocalDateTime;

public class Transaction {
    public TransactionType type;
    public double amount;
    public int fromAccountNumber;
    public int toAccountNumber;
    public LocalDateTime timestamp;
    public boolean success;
    public String message;

    @Override
    public String toString(){
        return "transactionType: " + type.getType() +
                "amount: " + amount +
                "fromAccountNumber: " + fromAccountNumber +
                "toAccountNumber: " + toAccountNumber +
                "timestamp: " + timestamp.toString() +
                "message: " + message;
    }
}

