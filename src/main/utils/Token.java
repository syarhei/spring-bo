package main.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.models.User;

import javax.servlet.http.Cookie;

public class Token {

    private static final String key = "Fg67g56av9a1";
    private static final String NICKNAME_TITLE = "nickname";
    private static final String ROLE_TITLE = "role";

    public static Cookie getCookie(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(name))
                return cookie;
        return null;
    }

    public static String stringify(User object) {
        return Jwts.builder()
                .claim(NICKNAME_TITLE, object.getNickname())
                .claim(ROLE_TITLE, object.getRole())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Jws<Claims> parse(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
    }

    public static String getNickname(Jws<Claims> token) {
        return (String)token
                .getBody()
                .get(NICKNAME_TITLE);
    }

    public static String getRole(Jws<Claims> token) {
        return (String)token
                .getBody()
                .get(ROLE_TITLE);
    }
}
