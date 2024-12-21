package Database;

import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name, country, club, position;
    private int jersey, salary, age;
    private float height;
    private long price = 0;

    public void setClub(String club) {
        this.club = club;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPosition() {
        return position;
    }

    public float getHeight() {
        return height;
    }

    public int getJersey() {
        return jersey;
    }

    public int getSalary() {
        return salary;
    }

    public String getClub() {
        return club;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getPrice() {
        return price;
    }
}
