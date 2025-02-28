package ConsoleInterface;

import Entities.Models.Account;
import Entities.Models.Operation;
import Entities.Services.AccountService;
import Entities.Services.AdminService;
import Entities.Services.OperationService;
import Enums.HairColor;
import Enums.OperationType;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для консольного интерфейса
 */

public class ATMConsoleInterface {
    private final AccountService accountService;
    private final AdminService adminService;
    private final OperationService operationService;
    private final Scanner scanner;
    private Account currentAccount;

    public ATMConsoleInterface(AccountService accountService, AdminService adminService, OperationService operationService) {
        this.accountService = accountService;
        this.adminService = adminService;
        this.operationService = operationService;
        this.scanner = new Scanner(System.in);
    }

    public void Run() {
        while (true) {
            System.out.println("1. Вход в аккаунт");
            System.out.println("2. Регистрация аккаунта");
            System.out.println("3. Вход администратора");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> adminLogin();
                case 4 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Некорректный выбор, попробуйте снова.");
            }
        }
    }

    private void login() {
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();
        System.out.print("Введите PIN: ");
        int pin = scanner.nextInt();

        currentAccount = accountService.Login(id, pin);
        if (currentAccount != null) {
            System.out.println("Успешный вход!");
            accountMenu();
        } else {
            System.out.println("Ошибка входа. Проверьте ID и PIN.");
        }
    }

    private void register() {
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();
        System.out.print("Введите PIN: ");
        int pin = scanner.nextInt();
        scanner.nextLine();  // очистка буфера перед строковым вводом

        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();

        System.out.println("Выберите цвет волос:");
        System.out.println("1. Брюнет");
        System.out.println("2. Блонд");
        System.out.println("3. Белый");
        int choice = scanner.nextInt();
        scanner.nextLine();

        HairColor hairColor = switch (choice) {
            case 1 -> HairColor.Brown;
            case 2 -> HairColor.Blond;
            case 3 -> HairColor.White;
            default -> null;
        };

        Account account = new Account(id, pin, name, age, hairColor);
        accountService.AddAccount(account);
        System.out.println("Аккаунт успешно зарегистрирован!");
    }

    private void adminLogin() {
        System.out.print("Введите Admin ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (adminService.Login(id, password)) {
            System.out.println("Администратор вошёл в систему.");
            adminMenu();
        } else {
            System.out.println("Ошибка входа.");
        }
    }

    private void accountMenu() {
        while (true) {
            System.out.println("1. Просмотр операций");
            System.out.println("2 Просмотр баланса");
            System.out.println("3. Пополнение счета");
            System.out.println("4. Снятие средств");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showOperations();
                case 2 -> showBalance();
                case 3 -> depositFunds();
                case 4 -> withdrawFunds();
                case 5 -> {
                    System.out.println("Выход из аккаунта.");
                    currentAccount = null;
                    return;
                }
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }

    private void showOperations() {
        List<Operation> operations = operationService.GetOperations(currentAccount.Id);
        for (Operation op : operations) {
            System.out.println(op.AccountId + ": " + op.OperationType + ": " + op.Amount);
        }
    }

    private void showBalance() {
        Double balance = accountService.GetBalance(currentAccount.Id);
        System.out.println("Ваш баланс: " + balance);
    }

    private void depositFunds() {
        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();
        accountService.Deposit(currentAccount.Id, currentAccount.Pin, amount);
        operationService.AddOperation(currentAccount.Id, OperationType.Deposit, amount);
        System.out.println("Счет пополнен на " + amount);
    }

    private void withdrawFunds() {
        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();
        accountService.Withdraw(currentAccount.Id, currentAccount.Pin, amount);
        operationService.AddOperation(currentAccount.Id, OperationType.Withdrawal, amount);
        System.out.println("Операция завершена.");
    }

    private void adminMenu() {
        while (true) {
            System.out.println("1. Удалить аккаунт");
            System.out.println("2. Выйти");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> deleteAccount();
                case 2 -> {
                    System.out.println("Выход из админ-панели.");
                    return;
                }
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }

    private void deleteAccount() {
        System.out.print("Введите ID аккаунта для удаления: ");
        int id = scanner.nextInt();
        Account account = new Account(id, 0, "", 0, null);
        accountService.DeleteAccount(account);
        System.out.println("Аккаунт удалён.");
    }
}
