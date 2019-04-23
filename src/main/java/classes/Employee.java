package classes;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="employee")
public class Employee implements Serializable{
    private int id;
    private String name;
    private String job;
    private int age;
    private double salary;
    private int afID;

    public Employee(int id, String name, String job, int age, double salary, int afID) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.age = age;
        this.salary = salary;
        this.afID = afID;
    }

    public Employee(String rawString) {
        String[] values = rawString.split(" ");
        try {
            this.id = Integer.parseInt(values[0]);
            this.name = values[1];
            this.job = values[2];
            this.age = Integer.parseInt(values[3]);
            this.salary = Double.parseDouble(values[4]);
            this.afID = Integer.parseInt(values[5]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<Employee> listFromResultSet(ResultSet rs) throws SQLException {
        List<Employee> tmp = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String job = rs.getString("job");
            int age = rs.getInt("age");
            double salary = rs.getDouble("salary");
            int afID = rs.getInt("afID");
            tmp.add(new Employee(id, name, job, age, salary, afID));
        }
        return tmp;
    }

    public static List<Employee> listFromTXT(File file) {
        List<Employee> res = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                Employee tmp = new Employee(line);
                if (tmp.id != -1) {
                    res.add(tmp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setJob(String job) {
        this.job = job;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @XmlElement
    public void setAfID(int afID) {
        this.afID = afID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public int getAfID() {
        return afID;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " job: " + job + " age: " + age + " salary: " + salary + " afID: " + afID;
    }
}
