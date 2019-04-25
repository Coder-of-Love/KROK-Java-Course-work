package classes;

import java.io.File;
import java.util.Scanner;

public class UserInterface {

    public void init(){
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
                    userInsert();
                    break;
                case "2":
                case "specify":
                    userSpecify();
                    break;
                case "3":
                case "delete":
                    userDelete();
                    break;
                case "4":
                case "phone":
                    userSearchByPhone();
                    break;
                case "5":
                case "stats":
                    userStats();
                    break;
                case "6":
                case "all":
                    userViewAll();
                    break;
                case "7":
                case "xml":
                    userExportToXML();
                    break;

            }
        }

    }

    private void showMainMenu(){
        System.out.println("Введите команду..");
        System.out.println("0 или exit для выхода");
        System.out.println("1 или insert для добавления сотрудника");
        System.out.println("2 или specify для добавления дополнительной информации о сотруднике");
        System.out.println("3 или delete для удаления сотрудника по id");
        System.out.println("4 или phone для поиска сотрудника по номеру телефона");
        System.out.println("5 или stats для вывода статистике по зарплатам");
        System.out.println("6 или all для просмотра всех сотрудников");
        System.out.println("7 или xml для экспорта данных бд в файл \"employees.xml\"");
    }

    private void userInsert() {
        System.out.println("Через пробел введите id, имя, должность, возраст, зарплату и afID");
        int resIns = BusinessLogic.insert(new Employee(new Scanner(System.in).nextLine()));
        if (resIns == 0) {
            System.out.println("Сотрудник успешно добавлен!");
        } else {
            System.out.println("Сотрудник не был добавлен! Смотрите подробности в логе..");
        }
    }

    private void userSpecify() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id сотрудника для которого хотите добавить дополнительную информацию..");
        int id = Integer.parseInt(scanner.nextLine());
        Employee employee = BusinessLogic.getEmployeeById(id);
        int afID = employee.getAfID();
        if (afID == 0) {
            afID = id;
            System.out.println("Введите телефон сотрудника..");
            String phone = scanner.nextLine();
            System.out.println("Введите адрес сотрудника..");
            String adress = scanner.nextLine();
            int resSpec = BusinessLogic.insert(new AdditionalInfo(afID, phone, adress));
            if (resSpec == 0) {
                BusinessLogic.bindEmployeeToAdditionalInfo(id, afID);
                System.out.println("Информация о сотруднике успешно дополнена!");
            }
            else {
                System.out.println("Информация не была добавлена! Смотрите подробности в логе..");
            }
        } else {
            System.out.println("О сотруднике уже есть доп. информация!");
        }
    }

    private void userDelete() {
        System.out.println("Введите id сотрудника которого хотите удалить..");
        int resDel = BusinessLogic.deleteEmployee(new Scanner(System.in).nextInt());
        if (resDel == 0) {
            System.out.println("Сотрудник успешно удален!");
        } else {
            System.out.println("При удалении произошла ошибка! Смотрите подробности в логе..");
        }
    }

    private void userSearchByPhone() {
        System.out.println("Введите номер телефона сотрудника которого хотите найти..");
        System.out.println(BusinessLogic.searchEmployeeByPhone(new Scanner(System.in).nextLine()));
    }

    private void userViewAll() {
        System.out.println(BusinessLogic.viewAll());
    }

    private void userExportToXML(){
        int resExp = BusinessLogic.exportToXML(new File("employees.xml"));
        if (resExp == 0) {
            System.out.println("Данные успешно экспортированы!");
        } else {
            System.out.println("При экспорте произошла ошибка! Смотрите подробности в логе..");
        }
    }

    private void userStats(){
        System.out.println("Введите должность для которой хотите узнать статистику..");
        String job = new Scanner(System.in).nextLine();
        System.out.println("Средняя зарплата по должности " + job +" равна " + BusinessLogic.getAverageSalary(job));
        System.out.println("Средняя зарплата в общем равна " + BusinessLogic.getAverageSalary());
    }

}
