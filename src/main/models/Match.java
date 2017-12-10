package main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Two teams that take part in this match

    @ManyToOne
    @JoinColumn(name = "team_id_1", nullable = false, foreignKey = @ForeignKey(name = "fk_team_1"), updatable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id_2", nullable = false, foreignKey = @ForeignKey(name = "fk_team_2"), updatable = false)
    private Team team2;

    // Coefficient values on [First Team Win (team_1), Draw, Second Team Win (team_2)] respectively
    // Value >= 1

    @Column(name = "coefficient_win_1", scale = 2, precision = 4, nullable = false, updatable = false)
    private BigDecimal coefficientWin1;

    @Column(name = "coefficient_draw", scale = 2, precision = 4, nullable = false, updatable = false)
    private BigDecimal coefficientDraw;

    @Column(name = "coefficient_win_2", scale = 2, precision = 4, nullable = false, updatable = false)
    private BigDecimal coefficientWin2;

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

    public void setCoefficientWin2(BigDecimal coefficientWin2) {
        this.coefficientWin2 = coefficientWin2;
    }

    public BigDecimal getCoefficientWin2() {
        return coefficientWin2;
    }

    public void setCoefficientDraw(BigDecimal coefficientDraw) {
        this.coefficientDraw = coefficientDraw;
    }

    public BigDecimal getCoefficientDraw() {
        return coefficientDraw;
    }

    public void setCoefficientWin1(BigDecimal coefficientWin1) {
        this.coefficientWin1 = coefficientWin1;
    }

    public BigDecimal getCoefficientWin1() {
        return coefficientWin1;
    }

    public Match() {}
}
