/*
toDo:
   - Консольное приложение
   -(Готово) Импорт из БД в память (в List объектов соответствующего типа)
   -(Готово) Импорт из txt файла
   -(Готово) Добавление сотрудника в БД
   --(Готово) Добавление доп.инфо о сотруднике
   --(Готово) Добавление связи
   -(Готово) Получить среднюю зарплату по всем работникам
   -(Готово) Получить среднюю зарплату по сотрудникам, определенной должности
   -(Готово) Поиск сотрудника по номеру телефона
   -(Готово) Экспорт всех объектов из БД в XML файл
 */

package classes;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BusinessLogic {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static Employees employees;
    public static List<AdditionalInfo> additionalInfoList = new ArrayList<>();

    //___While connected___
    public static void exportFromDB(){
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Employees");
            employees = new Employees(rs);

            rs = statement.executeQuery("SELECT * FROM AdditionalInfo");
            additionalInfoList = AdditionalInfo.listFromResultSet(rs);
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAll(Employees employees) {
        List<Employee> tmp = employees.getEmployees();
        for (Employee e : tmp) {
            BusinessLogic.insert(e);
        }
    }

    public static void importFromTXT(File file) {
        Employees tmp = new  Employees(file);
        BusinessLogic.insertAll(tmp);
    }

    public static void exportToXML(File file) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        BusinessLogic.exportFromDB();
        xmlMapper.writeValue(file, employees);
    }

    public static void insert(String name, String job, int age, double salary, int afID){
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Employees (name, job, age, salary, afID)" +
                    "VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, job);
            preparedStatement.setInt(3, age);
            preparedStatement.setDouble(4, salary);
            preparedStatement.setInt(5, afID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    public static void insert(Employee employee){
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Employees WHERE id = ?");
            preparedStatement.setInt(1, employee.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()){
                preparedStatement = connection.prepareStatement("INSERT INTO Employees (id, name, job, age, salary, afID)" +
                        "VALUES (?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(2, employee.getName());
                preparedStatement.setString(3, employee.getJob());
                preparedStatement.setInt(4, employee.getAge());
                preparedStatement.setDouble(5, employee.getSalary());
                preparedStatement.setInt(6, employee.getAfID());
                preparedStatement.setInt(1, employee.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String phone, String adress) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO AdditionalInfo (phone, adress) " +
                                                                                            "VALUES (?, ?)");
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, adress);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insert(AdditionalInfo additionalInfo) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM AdditionalInfo WHERE id = ?");
            preparedStatement.setInt(1, additionalInfo.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                preparedStatement = connection.prepareStatement("INSERT INTO AdditionalInfo (id, phone, adress)" +
                                                                                                "VALUES (?, ?, ?)");
                preparedStatement.setInt(1, additionalInfo.getId());
                preparedStatement.setString(2, additionalInfo.getPhone());
                preparedStatement.setString(3, additionalInfo.getAdress());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void oneToMany(int id, int afID) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM AdditionalInfo WHERE employeeID = ?");
            preparedStatement.setInt(1, afID);
            ResultSet rsAI = preparedStatement.executeQuery();
            if (rsAI.next()) {
                rsAI.beforeFirst();
                preparedStatement = connection.prepareStatement("UPDATE Employees SET afID = ? WHERE id = ?");
                preparedStatement.setInt(1, afID);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getAverageSalary(String job) {
        try {
            preparedStatement = connection.prepareStatement("SELECT AVG(salary) FROM Employees WHERE job = ?");
            preparedStatement.setString(1, job);
            double result = preparedStatement.executeQuery().getDouble(1);
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            return 0d;
        }
    }

    public static double getAverageSalary() {
        try {
            statement = connection.createStatement();
            double result = statement.executeQuery("SELECT AVG(salary) FROM Employees").getDouble(1);
            statement.close();
            return result;
        } catch (SQLException e) {
            return 0d;
        }
    }

    public static Employees searchByPhone(String phone) {
        Employees tmp;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT *\n" +
                            "FROM Employees e\n" +
                            "WHERE e.id IN (SELECT e.id\n" +
                            "               FROM Employees e, AdditionalInfo a\n" +
                            "               WHERE e.afID = a.id\n" +
                            "                    AND\n" +
                            "                    a.phone = ?)");
            preparedStatement.setString(1, phone);
            ResultSet rs = preparedStatement.executeQuery();
            tmp = new Employees(rs);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("searchByPhone");
        }
        return tmp;
    }
    ///////////////////////

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:employees.db");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Невозможно подключиться к базе данных!");
        }
    }

    public static void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("connection");
        }
    }

}
