package main.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private String nickname;

    // TODO: BCrypt password (hash length = 64)
    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 5, nullable = false)
    private String role = "user";

    @Column(nullable = false)
    private Integer balance = 100000;

    @Column(length = 30)
    private String address;

    @Column(length = 1)
    private String sex;

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public String getAddress() {
        return address;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {}
}
