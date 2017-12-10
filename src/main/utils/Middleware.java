package main.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Middleware", urlPatterns = "/*")
public class Middleware implements Filter {

    private enum ROLES {
        ADMIN, USER
    }

    public void destroy() { }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (checkPermissions((HttpServletRequest)req)) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).setStatus(403);
        }
    }

    public void init(FilterConfig config) throws ServletException { }

    private boolean checkPermissions(HttpServletRequest request) {

        Cookie cookie = getCookie(request.getCookies(), "token");

        if (cookie == null) {
            return checkGuestPermissions(request);
        }

        // Get token value: nickname and role
        Jws<Claims> token = Jwts.parser()
                .setSigningKey("Fg67g56av9a1")
                .parseClaimsJws(cookie.getValue());
        String nickname = (String)token.getBody()
                .get("nickname");
        String role = (String)token.getBody()
                .get("role");

        role = role.toUpperCase();

        // choice permission scenario for current role
        switch (ROLES.valueOf(role)) {
            case ADMIN:
                return true;
            case USER:
                return checkUserPermissions(request, nickname);
            default:
                return false;
        }
    }

    private boolean checkGuestPermissions(HttpServletRequest req) {

        String method = req.getMethod();
        String path = req.getServletPath();

        if (path.equals("/api/sessions"))
            return true;
        if (method.equals("GET"))
            if (path.equals("/api/matches") || path.equals("/api/teams"))
                return true;
        if (method.equals("POST") && path.equals("/api/users"))
            return true;
        return false;
    }

    private boolean checkUserPermissions(HttpServletRequest req, String nickname) {

        String method = req.getMethod();
        String path = req.getServletPath();

        if (path.equals("/api/sessions"))
            return true;
        if (path.equals("/api/users/".concat(nickname)))
            return true;
        if (path.contains("/api/bets"))
            return true;
        if (method.equals("GET") && !path.contains("/api/users"))
            return true;
        return false;
    }

    private Cookie getCookie(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(name))
                return cookie;
        return null;
    }
}
