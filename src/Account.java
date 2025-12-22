public abstract class Account {
    protected int accountNumber;
    protected double balance;
    protected int owner;

    public Account(int accountNumber, double balance, int owner){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public boolean deposit(double amount){
        if (amount < 0) {
            System.out.println("Ошибка внесения средств. Сумма вносимых средств должна быть положительной.");
            return false;
        }
        balance += amount;

        return true;
    }

    public boolean transfer(Account to, double amount){
        if (amount <= 0) {
            System.out.println("Ошибка перевода. Сумма перевода должна быть положительной.");
            return false;
        }
        balance -= amount;
        to.balance += amount;
        System.out.println("Счет id = " + accountNumber + "Перевод средств на счет " + to.accountNumber +
                ". Количество: " + amount + " у.е.");

        return true;
    }

    public abstract boolean withdraw(double amount);
}
