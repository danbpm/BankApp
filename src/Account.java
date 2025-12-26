public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected Customer owner;

    public Account(String accountNumber, double balance, Customer owner){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }


    public String getAccountNumber(){
        return accountNumber;
    }

    public double getBalance(){
        return balance;
    }

    public Customer getOwner(){
        return owner;
    }

    public boolean deposit(double amount){
        if (amount < 0) {
            System.out.println("Ошибка внесения средств. Сумма вносимых средств должна быть положительной.");
            return false;
        }

        balance += amount;

        System.out.println("Владелец: " + owner.getFullName() + "\tСчет: " + accountNumber +
                "\tОперация: ПОПОЛНЕНИЕ" + "\tСумма: " + amount);

        return true;
    }

    public boolean transfer(Account to, double amount){
        if (this == to){
            System.out.println("Перевод на тот же счет недопустим.");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Ошибка перевода. Сумма перевода должна быть положительной.");
            return false;
        }

        if (withdraw(amount)) {
            to.deposit(amount);
            System.out.println("Владелец: " + owner.getFullName() + "\tСчет: " + accountNumber +
                    "\tОперация: ПЕРЕВОД" + "\tCчет получатель: " + to.accountNumber + "\tСумма: " + amount);

            return true;
        }
        else {
            return false;
        }
    }

    public abstract boolean withdraw(double amount);
}
