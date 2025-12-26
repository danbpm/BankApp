import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank {
    private final ArrayList<Customer> customers = new ArrayList<>();

    private final  ArrayList<Account> accounts = new ArrayList<>();

    private final ArrayList<Transaction> transactions = new ArrayList<>();

    // Количество клиентов для выдачи ID новым клиентом
    private int customersNumber = 0;

    // Количество счетов любого типа для выдачи номеров
    private int accountsNumber = 0;


    public ArrayList<Customer> getAllCustomers(){
        return new ArrayList<>(customers);
    }

    public ArrayList<Account> getAccountsByCustomerId(int customerId){
        Customer customer = null;
        for (Customer c : customers){
            if (c.getId() == customerId){
                customer = c;
            }
        }

        if (customer == null) {
            System.out.println("Клиент с таким идентификационным номером не существует.");
            return null;
        }

        ArrayList<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getOwner().getId() == customerId) {
                customerAccounts.add(account);
            }
        }

        return customerAccounts;
    }

    public Account findAccount(String accountNumber){
        for (Account account : accounts) {
            if (account.getAccountNumber()
                    .equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public Account openCreditAccount(Customer owner){
        String accountNumber = String.format("%010d", ++accountsNumber);
        Account account = new CreditAccount(accountNumber,
                BankConstants.startBalance,
                owner,
                BankConstants.creditLimit);

        accounts.add(account);

        System.out.println("Владелец: " + owner.getFullName() + "\tКредитный счет: " +
                accountNumber + "\tБаланс: " + account.getBalance());

        return account;
    }

    public Account openDebitAccount(Customer owner){
        String accountNumber = String.format("%010d", ++accountsNumber);
        Account account = new DebitAccount(accountNumber, BankConstants.startBalance, owner);

        accounts.add(account);

        System.out.println("Владелец: " + owner.getFullName() + "\tДебетовый счет: " +
                accountNumber + "\tБаланс: " + account.getBalance());

        return account;
    }

    public Customer createCustomer(String fullName){
        Customer customer = new Customer(++customersNumber, fullName);
        customers.add(customer);

        System.out.println("Создан новый клиент. Имя: " + customer.getFullName());

        return customer;
    }

    public Customer findCustomerById(int id){
        for (Customer customer : customers){
            if (customer.getId() == id){
                return customer;
            }
        }
        System.out.println("Клиент с таким номером не найден");
        return null;
    }

    public boolean deposit(String accountNumber, double amount){
        Account account = findAccount(accountNumber);

        if (account == null) {
            transactions.add(new Transaction(
                    TransactionType.DEPOSIT,
                    amount,
                    null,
                    accountNumber,
                    LocalDateTime.now(),
                    false,
                    "Счет с таким номером не существует"
            ));
            return false;
        }

        boolean success = account.deposit(amount);
        String message = success ? "OK" : "Неверная сумма";

        transactions.add(new Transaction(
                TransactionType.DEPOSIT,
                amount,
                null,
                accountNumber,
                LocalDateTime.now(),
                success,
                message
        ));

        return success;

    }

    public boolean withdraw(String accountNumber, double amount){
        Account account = findAccount(accountNumber);

        if (account == null){
            transactions.add(new Transaction(
                    TransactionType.WITHDRAW,
                    amount,
                    null,
                    accountNumber,
                    LocalDateTime.now(),
                    false,
                    "Счет с таким номером не существует"
            ));

            return false;
        }

        boolean success = account.withdraw(amount);
        String message = success ? "OK" : "Неверная сумма";

        transactions.add(new Transaction(
                TransactionType.WITHDRAW,
                amount,
                null,
                accountNumber,
                LocalDateTime.now(),
                success,
                message
        ));

        return success;
    }

    public boolean transfer(String from, String to, double amount) {
        Account fromAccount = findAccount(from);

        if (fromAccount == null) {
            transactions.add(new Transaction(
                    TransactionType.TRANSFER,
                    amount,
                    from,
                    to,
                    LocalDateTime.now(),
                    false,
                    "Счет с номером отправителя не существует"
            ));

            return false;
        }

        Account toAccount = findAccount(to);

        if (toAccount == null) {
            transactions.add(new Transaction(
                    TransactionType.TRANSFER,
                    amount,
                    from,
                    to,
                    LocalDateTime.now(),
                    false,
                    "Счет с номером назначения не существует"
            ));

            return false;
        }

        if (from.equals(to)){
            transactions.add(new Transaction(
                    TransactionType.TRANSFER,
                    amount,
                    from,
                    to,
                    LocalDateTime.now(),
                    false,
                    "Перевод на тот же счет"
            ));

            return false;
        }

        boolean success = fromAccount.transfer(toAccount, amount);
        String message = success ? "ОК" : "Неверная сумма";

        transactions.add(new Transaction(
                TransactionType.TRANSFER,
                amount,
                to,
                from,
                LocalDateTime.now(),
                success,
                message
        ));

        return success;
    }

    public void printCustomerAccounts(int customerId){
        ArrayList<Account> accounts = getAccountsByCustomerId(customerId);

        if (accounts == null || accounts.isEmpty()) {
            System.out.println("Нет открытых счетов.");
            return;
        }

        for (Account account : accounts){
            System.out.println("Владелец: " + account.getOwner().getFullName() +
                    " Счет: " + account.getAccountNumber() +
                    " Баланс: " + account.getBalance());
        }

    }

    public void printTransactions(){
        System.out.println("=".repeat(20) + "ИСТОРИЯ ТРАЗАКЦИЙ" + "=".repeat(20));
        for (Transaction transaction : transactions){
            System.out.println(transaction.toString());
        }
    }

    public void printReport(){
        int creditCount = 0;
        int debitCount = 0;
        double creditTotalBalance = 0;
        double debitTotalBalance = 0;

        for (Account account : accounts){
            if (account instanceof CreditAccount){
                creditCount++;
                creditTotalBalance += account.getBalance();
            } else if (account instanceof DebitAccount){
                debitCount++;
                debitTotalBalance += account.getBalance();
            }
        }

        int successTransactionCount = 0;
        for (Transaction transaction : transactions){
            if (transaction.isSuccess()){
                successTransactionCount++;
            }
        }
        int failedTransactionCount = transactions.size() - successTransactionCount;

        System.out.println("=".repeat(20) + "ОТЧЕТ БАНКА" + "=".repeat(20));
        System.out.println("Кредитных счетов: " + creditCount);
        System.out.println("Дебетовых счетов: " + debitCount);
        System.out.println("Суммарный баланс кредитных счетов: " + creditTotalBalance);
        System.out.println("Суммарный баланс дебетовых счетов: " + debitTotalBalance);
        System.out.println("Успешных операций: " + successTransactionCount);
        System.out.println("Неуспешных операций: " + failedTransactionCount);
    }


}
