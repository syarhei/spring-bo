package main.services;

import main.models.Match;
import main.models.Team;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Repository
@Transactional
public class MatchService extends Service<Match> {
    public MatchService() {
        super(Match.class);
    }

    public void generateCoefficients(Match match, Team team1, Team team2) {
        Integer games1 = team1.getGames();
        Integer games2 = team2.getGames();

        if (games1 == 0 || games2 == 0) {
            setDefaultCoefficients(match);
        }

        else {
            Double rating1 = team1.getPoints().doubleValue() / games1 * 3;
            Double rating2 = team2.getPoints().doubleValue() / games2 * 3;

            if (rating1 > rating2) {
                setCoefficients(match, true);
            } else {
                setCoefficients(match, false);
            }
        }
    }

    private void setDefaultCoefficients(Match match) {
        BigDecimal win1 = new BigDecimal(Math.random()/4 + 1.5);
        win1 = win1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal win2 = new BigDecimal(Math.random()/4 + 1.5);
        win2 = win2.setScale(2, RoundingMode.HALF_UP);

        BigDecimal draw = new BigDecimal(Math.random()/2 + 1.5);
        draw = draw.setScale(2, RoundingMode.HALF_UP);

        match.setCoefficientWin1(win1);
        match.setCoefficientWin2(win2);
        match.setCoefficientDraw(draw);
    }

    private void setCoefficients(Match match, boolean isFirst) {
        BigDecimal win1 = new BigDecimal(Math.random()/4 + 1.5);
        win1 = win1.setScale(2, RoundingMode.HALF_UP);

        BigDecimal win2 = new BigDecimal(Math.random() + 2.5);
        win2 = win2.setScale(2, RoundingMode.HALF_UP);

        BigDecimal draw = new BigDecimal(Math.random()/2 + 1.75);
        draw = draw.setScale(2, RoundingMode.HALF_UP);

        match.setCoefficientWin1(isFirst ? win1 : win2);
        match.setCoefficientWin2(isFirst ? win2: win1);
        match.setCoefficientDraw(draw);
    }

    // Generate result of the match by coefficients (W1,W2,D)
    public String generateResult(Match object) {
        // Get inverse of coefficient value
        Double win_1 = 1 / object.getCoefficientWin1().doubleValue();
        Double win_2 = 1 / object.getCoefficientWin2().doubleValue();
        Double draw = 1 / object.getCoefficientDraw().doubleValue();

        // Get interval (second border's value) of each coefficients (the last border's value of draw coefficient equals 1)
        Double w1 = win_1 / (win_1 + win_2 + draw);
        Double w2 = win_1 + win_2 / (win_1 + win_2 + draw);

        Double randomValue = Math.random();

        return randomValue < w1 ? "W1" : randomValue < w2 ? "W2" :"D";
    }

    public void updateResult(Match match, String result) {
        match.setResult(result);
        super.update(match.getId(), match);
    }
}
