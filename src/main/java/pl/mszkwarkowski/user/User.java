package pl.mszkwarkowski.user;

import java.math.BigDecimal;

/**
 * The "User" class represents an user object. It has various attributes of users.
 */
public class User {
    private int id;
    private String name;
    private BigDecimal debt;

    public User(){};

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getDebt() { return debt; }

    public void setDebt(BigDecimal debt) { this.debt = debt; }
}
