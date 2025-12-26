import java.time.LocalDateTime;

public class Transaction {
    private final TransactionType type;
    private final double amount;
    private final String fromAccountNumber;
    private final String toAccountNumber;
    private final LocalDateTime timestamp;
    private final boolean success;
    private final String message;

    public Transaction(TransactionType type,
                       double amount,
                       String fromAccountNumber,
                       String toAccountNumber,
                       LocalDateTime timestamp,
                       boolean success,
                       String message){
        this.type = type;
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.timestamp = timestamp;
        this.success = success;
        this.message = message;
    }


    public boolean isSuccess(){
        return success;
    }

    @Override
    public String toString(){
        return "transactionType: " + type.toString() +
                "\tamount: " + amount +
                "\tfromAccountNumber: " + fromAccountNumber +
                "\ttoAccountNumber: " + toAccountNumber +
                "\ttimestamp: " + timestamp.toString() +
                "\tmessage: " + message;
    }
}

