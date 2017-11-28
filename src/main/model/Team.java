package main.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Calendar;

@Entity
@Table(name = "teamy")
public class Team {

    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String fullName;
    @Basic
    @Column(name = "bday")
    private Date dateOfBirth;

    public Team(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = new Date(Calendar.getInstance().getTimeInMillis());
    }

    public Team() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}