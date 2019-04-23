/*
toDo:
    -Консольное приложение
    -Обеспечить обработку исключений, логирование, тестирование
    -Доп. задание:
        -Импорт/Экспорт таблиц с помощью аннотаций
        -Валидация таблиц
        -Чтение/Запись объектов по одному или списком
        -Очиста таблицы
        -...
);
*/
package apps;

import classes.BusinessLogic;
import classes.UserInterface;

import java.io.File;

public class MainApp {
    public static void main(String[] args) {
        //UserInterface.init();
        BusinessLogic.connect();
        //BusinessLogic.insert(new Employee(56, "testEmp2", "qs", 111, 0.55, 0));
        //BusinessLogic.insert(new AdditionalInfo(55, "111", "testIns"));
        BusinessLogic.importFromTXT(new File("employees.txt"));
        BusinessLogic.exportFromDB();
        System.out.println(BusinessLogic.employees);
        try {
            BusinessLogic.exportToXML(new File("employeesExp.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(BusinessLogic.additionalInfoList);
        //BusinessLogic.insert("testIns", "tj", 11, 123.45, 0);
        System.out.println(BusinessLogic.getAverageSalary("tj"));
        System.out.println(BusinessLogic.getAverageSalary());
        System.out.println(BusinessLogic.searchByPhone("555"));
        System.out.println();
        BusinessLogic.disconnect();
    }
}
