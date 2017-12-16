package main.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import main.models.Bet;
import main.models.Match;
import main.models.User;
import main.services.BetService;
import main.services.MatchService;
import main.services.UserService;
import main.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bets")
public class BetController extends Controller<Bet> {
    @Autowired
    private BetService betService;
    @Autowired
    private UserService userService;
    @Autowired
    private MatchService matchService;

    @Override
    public ResponseEntity createEntity(@RequestBody Bet object) {
        try {

            User user = userService.getById(object.getUser().getNickname());
            Match match = matchService.getById(object.getMatch().getId());

            if (user == null)
                return ResponseEntity.status(501).body("User is not found");
            if (match == null)
                return ResponseEntity.status(501).body("Match is not found");

            Integer balance = user.getBalance();
            String result = match.getResult();

            // Check, than price is not more then balance & check finishing of the match
            if (object.getPrice() > balance)
                return ResponseEntity.status(501).body("Your price is more than you have in balance");
            if (result != null)
                return ResponseEntity.status(501).body("Match is over");

            return super.createEntity(object);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity getEntities(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = Token.getCookie(cookies, "token");
        if (cookie == null) {
            return ResponseEntity.status(403).build();
        }
        Jws<Claims> token = Token.parse(cookie.getValue());
        if (Token.getRole(token).equals("admin"))
            return super.getEntities(request);

        User user = userService.getById(Token.getNickname(token));

        return ResponseEntity.ok(betService.getBets(user));
    }

    @GetMapping("/{primaryKey}")
    public ResponseEntity getEntity(@PathVariable Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @PutMapping("/{primaryKey}")
    public ResponseEntity updateEntity(@PathVariable Integer primaryKey, @RequestBody Bet object) {
        object.setId(primaryKey);
        return super.updateEntity(primaryKey, object);
    }

    @DeleteMapping("/{primaryKey}")
    public ResponseEntity deleteEntity(@PathVariable Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}
