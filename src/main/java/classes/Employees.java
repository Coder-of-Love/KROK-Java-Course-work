package classes;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class Employees implements Serializable {

    private List<Employee> employees;

    public Employees(){
        this.employees = new ArrayList<>();
    }

    public Employees(List<Employee> list) {
        this.employees = list;
    }

    @JacksonXmlProperty(localName = "Employee")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Employee> getEmployees() {
        return employees;
    }

    public Employees (ResultSet rs) throws SQLException {
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
        this.employees = tmp;
    }

    public Employees(File file) {
        List<Employee> res = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                Employee tmp = new Employee(line);
                if (tmp.getId() != -1) {
                    res.add(tmp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.employees = res;
    }

    @Override
    public String toString() {
        return this.getEmployees().toString();
    }
}
