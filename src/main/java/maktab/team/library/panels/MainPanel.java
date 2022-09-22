package maktab.team.library.panels;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import maktab.team.library.model.Person;
import maktab.team.library.service.PersonService;
import maktab.team.library.utils.Utils;

import java.util.Scanner;

public class MainPanel {

    @Getter
    @Setter
    private static Person currentPerson;

    @SneakyThrows
    public static void setupMainPanel() {
        printMainMenu();

        final Scanner scanner = Utils.getScanner();

        while (!scanner.hasNextInt()) {
            System.out.print("\033[31;1mERROR: Invalid Input!\033[0m \n\033[1mNumber: \033[0m");
            scanner.next();
        }
        final int selectNumber = scanner.nextInt();

        switch (selectNumber) {
            case 1:
                // login into the account
                // User(username, password)

                System.out.print("\033[1mUsername: \033[0m");
                while (!scanner.hasNext()) {
                    System.out.print("\033[31;1mERROR: Invalid Input!\033[0m \n\033[1mUsername: \033[0m");
                    scanner.next();
                }
                final String username = scanner.next();
                if (!PersonService.validatePersonData("username", username)) {
                    Utils.clearConsole();
                    System.out.println("\033[93;1mWARN: Lotfan Dobare Talash Konid!\nIn Esm Dar Database Vojod Darad!\033[0m");
                    MainPanel.setupMainPanel();
                    break;
                }

                int i = 1;
                while (i <= 3) {
                    System.out.print("\033[1mPassword: \033[0m");
                    while (!scanner.hasNext()) {
                        System.out.print("\033[31;1mERROR: Invalid Input!\033[0m \n\033[1mPassword: \033[0m");
                        scanner.next();
                    }
                    final String password = scanner.next();
                    final Person loginUser = new Person(username, password);

                    if (PersonService.checkCredentials(loginUser)) {
                        Utils.clearConsole();

                        MainPanel.setCurrentPerson(PersonService.getUser(loginUser));
                        System.out.println("\033[92;1mShoma Ba Movafaghiat Login Shodid!\033[0m");
                        LoginPanel.setupMainPanel();
                        break;
                    }
                    System.out.println("\033[31;1mERROR: Password Shoma Eshtebah Ast!\033[0m");
                    if (i >= 3) {
                        for (int time = 10; time >= 0; time--) {
                            Thread.sleep(1000);
                            System.out.printf("\033[93;1mWARN: Lotfan %d Sanie Digar Dobare Talash Konid!\033[0m\r", time);
                        }
                        i = 0;
                    }
                    i++;
                }
                break;

            case 2:
                // User(username, password)
                // register an account

                System.out.print("\033[1mUsername: \033[0m");
                while (!scanner.hasNext()) {
                    System.out.print("\033[31;1mERROR: Invalid Input!\033[0m \n\033[1mUsername: \033[0m");
                    scanner.next();
                }
                final String registerUsername = scanner.next();

                if (PersonService.validatePersonData("username", registerUsername)) {
                    Utils.clearConsole();
                    System.out.println("\033[93;1mWARN: Lotfan Dobare Talash Konid!\nIn Esm Dar Database Vojod Darad!\033[0m");
                    MainPanel.setupMainPanel();
                    break;
                }

                System.out.print("\033[1mPassword: \033[0m");
                while (!scanner.hasNext()) {
                    System.out.print("\033[31;1mERROR: Invalid Input!\033[0m \n\033[1mPassword: \033[0m");
                    scanner.next();
                }
                final String registerPassword = scanner.next();

                if (PersonService.validatePersonData("password", registerPassword)) {
                    Utils.clearConsole();
                    System.out.println("\033[93;1mWARN: Lotfan Dobare Talash Konid!\nIn Password Dar Database Vojod Darad!\033[0m");
                    MainPanel.setupMainPanel();
                    break;
                }

                final Person registerUser = new Person(registerUsername, registerPassword);

                if (!PersonService.addUser(registerUser)) {
                    Utils.clearConsole();
                    System.out.println("\033[31;1mERROR: Shoma Sabte Nam Nashodid. Dobare Talash Konid!\033[0m");
                    MainPanel.setupMainPanel();
                    break;
                }

                Utils.clearConsole();
                System.out.println("\033[92;1mShoma Ba Movafaghiat Register Shodid!\033[0m");

                MainPanel.setCurrentPerson(PersonService.getUser(registerUser));
                LoginPanel.setupMainPanel();
                break;

            case 3:
                // exit the program and remove the current user
                MainPanel.setCurrentPerson(null);
                scanner.close();
                break;

            default:
                Utils.clearConsole();
                MainPanel.setupMainPanel();
        }
    }

    public static void printMainMenu() {
        System.out.println(
                "――――― Welcome To Library ――――――\n " +
                        "✎ 1. Login\n " +
                        "✎ 2. Register\n " +
                        "✎ 3. Exit Menu");
    }
}
