package classes;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
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

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getJob() {
        return job;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    @XmlElement
    public double getSalary() {
        return salary;
    }

    @XmlElement
    public int getAfID() {
        return afID;
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " job: " + job + " age: " + age + " salary: " + salary + " afID: " + afID;
    }
}
