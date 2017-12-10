package main.models;

import javax.persistence.*;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private Integer price;

    @Column(length = 2, nullable = false, updatable = false)
    private String result;

    // Check if the match is over (has result value) or not
    // If match is over User can not delete bet

    @Column(name = "is_completed", insertable = false, columnDefinition = "bit(1) default 0")
    private Boolean completeness;

    // Profit from a bet (can be have negative value)

    @Column(insertable = false)
    private Integer profit;

    @ManyToOne
    @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "fk_match"), nullable = false, updatable = false)
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"), nullable = false, updatable = false)
    private User user;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Boolean completeness) {
        this.completeness = completeness;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
