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
            System.out.println("1. Вход в аккаунт");
            System.out.println("2. Регистрация пользователя");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> registerUser();
                case 3 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Некорректный выбор, попробуйте снова.");
            }
        }
    }

    private void login() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        currentUser = userService.GetUser(login);

        if (currentUser == null) {
            System.out.println("Пользователь не найден! Сначала зарегистрируйтесь.");
            return;
        }

        System.out.println("Пользователь найден. Вход выполнен успешно.");

        System.out.print("Введите ID аккаунта: ");
        String accountId = scanner.nextLine();
        currentAccount = accountService.GetAccount(accountId);

        if (currentAccount == null) {
            System.out.println("Аккаунт не найден. Создать новый аккаунт? (да/нет)");
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("да")) {
                registerAccount();
            } else {
                System.out.println("Вход отменен.");
            }
            return;
        }

        accountMenu();
    }

    private void registerAccount() {
        if (currentUser == null) {
            System.out.println("Сначала зарегистрируйте пользователя!");
            return;
        }

        System.out.print("Введите ID аккаунта: ");
        String id = scanner.nextLine();
        currentAccount = new Account(currentUser.login);
        accountService.AddAccount(currentAccount);
        System.out.println("Аккаунт успешно зарегистрирован!");
        accountMenu();
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
        while (true) {
            System.out.println("1. Просмотр баланса");
            System.out.println("2. Просмотр операций");
            System.out.println("3. Пополнение счета");
            System.out.println("4. Снятие средств");
            System.out.println("5. Выйти из аккаунта");
            System.out.println("6. Удалить аккаунт");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showBalance();
                case 2 -> showOperations();
                case 3 -> depositFunds();
                case 4 -> withdrawFunds();
                case 5 -> {
                    System.out.println("Выход из аккаунта.");
                    currentAccount = null;
                    return;
                }
                case 6 -> deleteAccount();
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }

    private void deleteAccount() {
        if (currentAccount == null) {
            System.out.println("Ошибка: Вы не вошли в аккаунт.");
            return;
        }

        System.out.print("Вы уверены, что хотите удалить аккаунт? (да/нет): ");
        String response = scanner.nextLine().toLowerCase();

        if (response.equals("да")) {
            accountService.DeleteAccount(currentAccount);
            System.out.println("Аккаунт успешно удален.");
            currentAccount = null;
        } else {
            System.out.println("Удаление отменено.");
        }
    }

    private void showOperations() {
        List<Operation> operations = operationService.GetOperations(currentAccount.id);
        for (Operation op : operations) {
            System.out.println(op.accountId + ": " + op.operationType + ": " + op.amount);
        }
    }

    private void showBalance() {
        Double balance = accountService.GetBalance(currentAccount.id);
        if (balance == null) {
            System.out.println("Ошибка получения баланса!");
        } else {
            System.out.println("Ваш баланс: " + balance);
        }
    }

    private void depositFunds() {
        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();
        accountService.Deposit(currentAccount.id, amount);
        operationService.AddOperation(currentAccount.id, OperationType.Deposit, amount);
        System.out.println("Счет пополнен на " + amount);
    }

    private void withdrawFunds() {
        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();
        accountService.Withdraw(currentAccount.id, amount);
        operationService.AddOperation(currentAccount.id, OperationType.Withdrawal, amount);
        System.out.println("Операция завершена.");
    }
}
