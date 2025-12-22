public class DebitAccount extends Account{
    DebitAccount(int accountNumber, double balance, int owner){
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
        System.out.println("Счет id = " + accountNumber + ". Снятие средств. " +
                "Количество: " + amount + " у.е.");


        return true;
    }

}
