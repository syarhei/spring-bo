package main.models;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Two teams that take part in this match

    @ManyToOne
    @JoinColumn(name = "team_id_1", foreignKey = @ForeignKey(name = "fk_team_1"))
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id_2", foreignKey = @ForeignKey(name = "fk_team_2"))
    private Team team2;

    // Coefficient values on [First Team Win (team_1), Draw, Second Team Win (team_2)] respectively
    // Value >= 1

    @Column(name = "coefficient_win_1", columnDefinition = "decimal(2,2)")
    private Double coefficientWin1;

    @Column(name = "coefficient_draw", columnDefinition = "decimal(2,2)")
    private Double coefficientDraw;

    @Column(name = "coefficient_win_2", columnDefinition = "decimal(2,2)")
    private Double coefficientWin2;

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

    public Double getCoefficientDraw() {
        return coefficientDraw;
    }

    public void setCoefficientDraw(Double coefficientDraw) {
        this.coefficientDraw = coefficientDraw;
    }

    public Double getCoefficientWin1() {
        return coefficientWin1;
    }

    public void setCoefficientWin1(Double coefficientWin1) {
        this.coefficientWin1 = coefficientWin1;
    }

    public Double getCoefficientWin2() {
        return coefficientWin2;
    }

    public void setCoefficientWin2(Double coefficientWin2) {
        this.coefficientWin2 = coefficientWin2;
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
