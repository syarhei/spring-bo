package main.model;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    private Integer id;

    // Two teams that take part in this match

    @ManyToOne
    @JoinColumn(name = "team_1", foreignKey = @ForeignKey(name = "fk_team_1"))
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_2", foreignKey = @ForeignKey(name = "fk_team_2"))
    private Team team2;

    // Coefficient values on [Win First Team (team_1), Draw, Win Second Team(team_2)] respectively
    // Value >= 1

    @Column(name = "win_1")
    private Double win1;

    private Double draw;

    @Column(name = "win_2")
    private Double win2;

    // Name of place where game will be played

    @Column(length = 30)
    private String place;

    // Result of the match: 'w1'|'d'|'w2'
    // Default: null

    @Column(length = 2, insertable = false)
    private String result;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Double getWin1() {
        return win1;
    }

    public Double getWin2() {
        return win2;
    }

    public void setWin1(Double win1) {
        this.win1 = win1;
    }

    public void setWin2(Double win2) {
        this.win2 = win2;
    }

    public Double getDraw() {
        return draw;
    }

    public void setDraw(Double draw) {
        this.draw = draw;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Match() {}
}
