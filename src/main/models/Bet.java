package main.models;

import javax.persistence.*;

@Entity
@Table(name = "bets")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 2, nullable = false)
    private String result;

    // Check if the match is over (has result value) or not
    // If match is over User can not delete bet

    @Column(name = "is_completed")
    private Boolean completeness = false;

    // Profit from a bet (can be have negative value)

    @Column(insertable = false)
    private Integer profit;

    @ManyToOne
    @JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "fk_match"))
    private Match match;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user"))
    private User client;
}
