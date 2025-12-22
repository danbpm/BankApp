public class CreditAccount extends Account{
    private double creditLimit;

    CreditAccount(int accountNumber, double balance, int owner, double creditLimit){
        super(accountNumber, balance, owner);
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean withdraw(double amount){
        if (amount <= 0){
            System.out.println("Ошибка снятия средств. Сумма снятие должна быть неотрицательной");
            return false;
        }
        if (amount > (balance + creditLimit)) {
            System.out.println("Ошибка снятия средств. Недостаточно средств.");
            return false;
        }

        balance -= amount;
        System.out.println("Счет id = " + accountNumber + ". Снятие средств. " +
                "Количество: " + amount + " у.е.");

        return true;
    }
}
