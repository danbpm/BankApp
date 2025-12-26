public class DebitAccount extends Account{
    DebitAccount(String accountNumber, double balance, Customer owner){
        super(accountNumber, balance, owner);
    }

    @Override
    public boolean withdraw(double amount){
        if (amount < 0){
            System.out.println("Ошибка снятия средств. Сумма снятия должна быть положительной");
            return false;
        }
        if (amount > balance){
            System.out.println("Ошибка снятия средств. Недостаточно средств.");
            return false;
        }

        balance -= amount;

        System.out.println("Владелец: " + owner.getFullName() + "\tСчет: " + accountNumber +
                "\tОперация: СНЯТИЕ" + "\tСумма: " + amount);


        return true;
    }

}
