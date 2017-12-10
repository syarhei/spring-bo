package main.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private String nickname;

    // TODO: BCrypt password (hash length = 60)
    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 5, nullable = false, updatable = false)
    private String role = "user";

    @Column(nullable = false, insertable = false, columnDefinition = "int default 100000")
    private Integer balance;

    @Column(length = 30)
    private String address;

    @Column(length = 1)
    private String sex = "M";

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
