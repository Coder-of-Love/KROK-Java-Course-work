package classes;

import java.util.Scanner;

public class UserInterface {

    public static void init(){
        BusinessLogic.connect();
        Scanner scanner = new Scanner(System.in);
        String cmd;
        boolean running = true;

        while (running){
            showMainMenu();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "0":
                case "exit":
                    BusinessLogic.disconnect();
                    running = false;
                    break;
                case "1":
                case "insert":
            }
        }

    }

    private static void showMainMenu(){
        System.out.println("Введите команду..");
        System.out.println("0 или exit для выхода");
        System.out.println("1 или insert для добавления сотрудника");
        System.out.println("2 или delete для удаления сотрудника");
        System.out.println("3 или search для поиска сотрудника по номеру телефона");
        System.out.println("4 или stats для вывода статистике по зарплатам");
    }

}
