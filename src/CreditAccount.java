public class CreditAccount extends Account{
    private final double creditLimit;

    CreditAccount(String accountNumber, double balance, Customer owner, double creditLimit){
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

        System.out.println("Владелец: " + owner.getFullName() + "\tСчет: " + accountNumber +
                "\tОперация: СНЯТИЕ" + "\tСумма: " + amount);

        return true;
    }

}
