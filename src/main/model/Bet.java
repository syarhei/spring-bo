package main.model;

import javax.persistence.*;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    private Integer id;

    private Integer price;

    @Column(length = 2)
    private String result;

    // Check whether thr bet is completed or not

    @Column(name = "is_completed")
    private Boolean completeness;

    // Profit from a bet (can be have negative value)

    private Integer profit;

    @ManyToOne
    @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "fk_match"))
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private User client;
}
