import java.util.ArrayList;
import java.util.Scanner;
// Reviewed for Lab 1
public class Main {
    public static void main(){
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("КОНСОЛЬНОЕ ПРИЛОЖЕНИЕ БАНК");

        while (running){
            System.out.println("=" .repeat(20) + "МЕНЮ" + "=".repeat(20));
            System.out.println("1. Cоздать клиента");
            System.out.println("2. Открыть дебетовый счет");
            System.out.println("3. Открыть кредитный счет");
            System.out.println("4. Пополнить");
            System.out.println("5. Снять");
            System.out.println("6. Перевести");
            System.out.println("7. Показать счета клиента");
            System.out.println("8. Показать транзакции");
            System.out.println("9. Отчет банка");
            System.out.println("10. Выход");
            System.out.println("Выберите действие: ");

            String input = scanner.nextLine();
            if (input.isEmpty()){
                continue;
            }
            try {
                int choice = Integer.parseInt(input.trim());
                switch (choice) {
                    case 1 -> createCustomer(bank, scanner);
                    case 2 -> createDebitAccount(bank, scanner);
                    case 3 -> createCreditAccount(bank, scanner);
                    case 4 -> deposit(bank, scanner);
                    case 5 -> withdraw(bank, scanner);
                    case 6 -> transfer(bank, scanner);
                    case 7 -> showCustomerAccounts(bank, scanner);
                    case 8 -> showAllTransactions(bank);
                    case 9 -> showReport(bank);
                    case 10 -> running = false;
                }
            } catch (NumberFormatException exc){
                System.out.println("Некорректный ввод.");
            }
        }

    }
    ///  Меню выбора клиента банка
    private static Customer selectCustomer(Bank bank, Scanner scanner, String operationName){
        while (true) {
            System.out.println("-".repeat(10) + operationName + "-".repeat(10));

            // Получаем копию списка всех клиентов банка
            ArrayList<Customer> customers = bank.getAllCustomers();

            // Выводим их
            for (Customer c : customers) {
                System.out.println(c.getId() + ". " + c.getFullName());
            }
            System.out.println("0. Отмена");
            System.out.println("Выберите клиента: ");

            // Ждем и читаем ввод от пользователя
            String input = scanner.nextLine();

            // Проверка на пустоту ввода
            if (input.isEmpty()){
                continue;
            }

            // Пытаемся распарсить строку в integer
            try {
                int choice = Integer.parseInt(input.trim());

                // Если ввод 0, то была вызвана отмена операции
                if (choice == 0) {
                    return null;
                }
                // Поскольку элементы массива начинаются с нуля, то отнимаем единицу у полученного значения
                int ind = choice - 1;

                // Проверяем, что введенная позиция действительно соответствует индексам элементов списка пользователей
                if (ind < 0 || ind >= customers.size()){
                    System.out.println("Нет такой позиции.");
                    continue;
                }

                // Безопасно возвращаем соответствующего клиента
                return customers.get(ind);

            } catch (NumberFormatException exc){ // обрабатываем исключение при вводе символов, которые не ожидались
                System.out.println("Некорректный ввод.");
            }
        }
    }

    ///  Меню выбора счета клиента
    private static Account selectCustomerAccount(Bank bank, Scanner scanner, int customerId){
        while (true) {
            // Получаем список счетов выбранного пользователя
            ArrayList<Account> accounts = bank.getAccountsByCustomerId(customerId);

            int accountsCount = accounts.size();

            // Если список пустой, то выводим сообщение о том, что счетов у клиента пока нет.
            if (accountsCount == 0) {
                System.out.println("Нет открытых счетов.");
            }
            else { // иначе выводим все счета клиента для интерактивного взаимодействия
                for (int i = 0; i < accountsCount; i++) {
                    System.out.println((i + 1) + ". Счет: " + accounts.get(i).getAccountNumber());
                }
            }
            System.out.println("0. Отмена");
            System.out.println("Выберите счет: ");

            // Читаем строку
            String input = scanner.nextLine();

            // Проверка на пустой ввод
            if (input.isEmpty()){
                continue;
            }

            // Пытаемся распарсить строку в integer
            try {
                int choice = Integer.parseInt(input.trim());
                if (choice == 0) {
                    return null;
                }
                // поскольку элементы массива начинаются с нуля, то отнимаем единицу у полученного значения
                int ind = choice - 1;

                if (ind < 0 || ind >= accounts.size()){
                    System.out.println("Нет такого счета.");
                    continue;
                }

                return accounts.get(ind);

            } catch (NumberFormatException exc){ // обрабатываем исключение при вводе символов, которые не ожидались
                System.out.println("Некорректный ввод.");
            }
        }
    }

    ///  Меню ввода суммы
    private static Double readAmount(Scanner scanner){
        while (true){
            System.out.println("Введите сумму(0 для отмены): ");

            String input = scanner.nextLine();

            if (input.isEmpty()){
                continue;
            }

            try {
                double amount = Double.parseDouble(input.trim());
                if (amount == 0) {
                    return null;
                }
                if (amount < 0) {
                    System.out.println("Сумма должна быть положительной.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException exc) {
                System.out.println("Некорректный ввод.");
            }
        }
    }

    ///  Меню ввода имени клиента
    private static void createCustomer(Bank bank, Scanner scanner){
        System.out.println("Введите имя клиента: ");
        String fullName = scanner.nextLine();
        bank.createCustomer(fullName);
    }

    ///  Сценарий создания дебетового аккаунта
    private static void createDebitAccount(Bank bank, Scanner scanner){
        // Выводим меню выбора клиентов и ждем ввода от пользователя
        Customer customer = selectCustomer(bank, scanner, "ОТКРЫТЬ ДЕБЕТОВЫЙ СЧЕТ");

        // Если вернулся null, то пользователь захотел отменить операцию
        if (customer == null){
            return;
        }

        // Открываем кредитный счет
        bank.openDebitAccount(customer);
    }

    ///  Сценарий создания кредитного аккаунта
    private static void createCreditAccount(Bank bank, Scanner scanner){
        Customer customer = selectCustomer(bank, scanner, "ОТКРЫТЬ КРЕДИТНЫЙ СЧЕТ");

        if (customer == null){
            return;
        }

        bank.openCreditAccount(customer);
    }

    ///  Сценарий проведения пополнения счета
    private static void deposit(Bank bank, Scanner scanner){
        // Выводим меню с выбора клиентов и ждем ввода от пользователя
        Customer customer = selectCustomer(bank, scanner, "ВНЕСТИ ДЕНЬГИ НА СЧЕТ");

        // Запрошена отмена операции
        if (customer == null) {
            return;
        }
        // Выводим меню всех счетов клиента и ждем ввода
        Account account = selectCustomerAccount(bank, scanner, customer.getId());

        // Запрошена отмена операции
        if (account == null){
            return;
        }

        // Выводим меню ввода суммы и ждем ввод
        Double amount = readAmount(scanner);

        // Запрошена отмена операции
        if (amount == null) {
            return;
        }

        // Выполняем соответствующую операцию
        boolean success = bank.deposit(account.getAccountNumber(), amount);

        // Все проверки происходят на этапе ввода и это условие никогда не выполняется, но сделаем проверку, что в
        // бизнес-логике точно не произошло ошибок
        if (!success){
            System.out.println("ОШИБКА");
        }


    }

    ///  Сценарий снятия средств со счета
    private static void withdraw(Bank bank, Scanner scanner){
        // Аналогично функции deposit
        Customer customer = selectCustomer(bank, scanner, "СНЯТЬ ДЕНЬГИ СО СЧЕТА");

        if (customer == null) {
            return;
        }

        Account account = selectCustomerAccount(bank, scanner, customer.getId());

        if (account == null){
            return;
        }

        Double amount = readAmount(scanner);

        if (amount == null) {
            return;
        }

        boolean success = bank.withdraw(account.getAccountNumber(), amount);

        if (!success){
            System.out.println("ОШИБКА");
        }

    }

    ///  Сценарий перевода средств
    private static void transfer(Bank bank, Scanner scanner){
        // Предлагаем выбрать отправителя
        Customer fromCustomer = selectCustomer(bank, scanner, "ПЕРЕВОД. КЛИЕНТ ОТПРАВИТЕЛЬ.");
        if (fromCustomer == null){
            return;
        }

        // Предлагаем выбрать счет отправителя
        Account fromAccount = selectCustomerAccount(bank, scanner, fromCustomer.getId());

        if (fromAccount == null){
            return;
        }

        // Предлагаем выбрать получателя
        Customer toCustomer = selectCustomer(bank, scanner, "ПЕРЕВОД. КЛИЕНТ ПОЛУЧАТЕЛЬ.");
        if (toCustomer == null){
            return;
        }

        // Предлагаем выбрать счет получателя
        Account toAccount = selectCustomerAccount(bank, scanner, toCustomer.getId());
        if (toAccount == null){
            return;
        }

        // Предлагаем ввести сумму
        Double amount = readAmount(scanner);
        if (amount == null){
            return;
        }

        // Осуществляем перевод
        boolean success = bank.transfer(fromAccount.getAccountNumber(), toAccount.getAccountNumber(), amount);

        // Выполняем проверку, что в бизнес логике точно не произошло ошибок
        if (!success){
            System.out.println("ОШИБКА");
        }

    }

    ///  Сценарий вывода информации о всех счетах пользователя
    private static void showCustomerAccounts(Bank bank, Scanner scanner){
        // Предлагаем выбрать клиента
        Customer customer = selectCustomer(bank, scanner, "ВЫВЕСТИ ВСЕ СЧЕТА КЛИЕНТА");

        // Если запрошена отмена операции
        if (customer == null){
            return;
        }

        // Выводим все счета выбранного клиента
        bank.printCustomerAccounts(customer.getId());
    }

    ///  Сценарий вывода истории всех транзакций
    private static void showAllTransactions(Bank bank){
        bank.printTransactions();
    }

    ///  Сценарий вывода отчета банка
    private static void showReport(Bank bank){
        bank.printReport();
    }

}
