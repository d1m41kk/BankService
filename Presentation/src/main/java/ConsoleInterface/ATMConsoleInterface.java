package ConsoleInterface;

import Entities.Models.Account;
import Entities.Models.Operation;
import Entities.Models.User;
import Entities.Services.AccountService;
import Entities.Services.OperationService;
import Entities.Services.UserService;
import Enums.HairColor;
import Enums.OperationType;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для консольного интерфейса
 */

public class ATMConsoleInterface {
    private final AccountService accountService;
    private final OperationService operationService;
    private final UserService userService;
    private final Scanner scanner;
    private Account currentAccount;
    private User currentUser;

    public ATMConsoleInterface(AccountService accountService, OperationService operationService, UserService userService) {
        this.accountService = accountService;
        this.operationService = operationService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void Run() {
        while (true) {
            System.out.println("1. Получить информацию пользователя");
            System.out.println("2. Регистрация счета");
            System.out.println("3. Регистрация пользователя");
            System.out.println("4. Посмотреть информацию по счету");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showUsersInfo();
                case 2 -> registerAccount();
                case 3 -> registerUser();
                case 4 -> accountMenu();
                case 5 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Некорректный выбор, попробуйте снова.");
            }
        }
    }

    private void registerAccount() {
        if (currentUser == null) {
            System.out.println("Сначала зарегистрируйте пользователя!");
            return;
        }

        System.out.print("Введите ID аккаунта: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        currentAccount = new Account(id, currentUser.Login);
        accountService.AddAccount(currentAccount);
        System.out.println("Аккаунт успешно зарегистрирован!");
    }

    private void showUsersInfo() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        currentUser = userService.GetUser(login);

        if (currentUser == null) {
            System.out.println("Пользователь не найден!");
            return;
        }

        System.out.println("Имя: " + currentUser.Name);
        System.out.println("Возраст: " + currentUser.Age);
        System.out.println("Цвет волос: " + currentUser.HairColor);
    }

    private void registerUser() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();

        System.out.print("Введите ваше имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите ваш пол (М/Ж): ");
        String choice = scanner.nextLine();

        Boolean sex = switch (choice) {
            case "М" -> Boolean.TRUE;
            case "Ж" -> Boolean.FALSE;
            default -> null;
        };

        if (sex == null) {
            System.out.println("Некорректный ввод пола.");
            return;
        }

        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();

        System.out.println("Выберите цвет волос:");
        System.out.println("1. Брюнет");
        System.out.println("2. Блонд");
        System.out.println("3. Белый");
        int choice2 = scanner.nextInt();
        scanner.nextLine();

        HairColor hairColor = switch (choice2) {
            case 1 -> HairColor.Brown;
            case 2 -> HairColor.Blond;
            case 3 -> HairColor.White;
            default -> null;
        };

        if (hairColor == null) {
            System.out.println("Некорректный выбор цвета волос.");
            return;
        }

        User user = new User(login, name, sex, age, hairColor);
        userService.AddUser(user);
        currentUser = user;

        System.out.println("Пользователь успешно создан!");
    }

    private void accountMenu() {
        if (currentAccount == null) {
            System.out.println("Сначала зарегистрируйте счет!");
            return;
        }

        while (true) {
            System.out.println("1. Просмотр операций");
            System.out.println("2. Просмотр баланса");
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
        if (balance == null) {
            System.out.println("Ошибка получения баланса!");
        } else {
            System.out.println("Ваш баланс: " + balance);
        }
    }

    private void depositFunds() {
        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();
        accountService.Deposit(currentAccount.Id, amount);
        operationService.AddOperation(currentAccount.Id, OperationType.Deposit, amount);
        System.out.println("Счет пополнен на " + amount);
    }

    private void withdrawFunds() {
        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();
        accountService.Withdraw(currentAccount.Id, amount);
        operationService.AddOperation(currentAccount.Id, OperationType.Withdrawal, amount);
        System.out.println("Операция завершена.");
    }
}