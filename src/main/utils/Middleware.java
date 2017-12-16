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

        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies == null ? null : Token.getCookie(request.getCookies(), "token");

        if (cookie == null) {
            return checkGuestPermissions(request);
        }

        // Get token value: nickname and role
        Jws<Claims> token = Token.parse(cookie.getValue());
        String nickname = Token.getNickname(token);
        String role = Token.getRole(token).toUpperCase();

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
        return checkStaticResource(path, method);
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
        return checkStaticResource(path, method);
    }

    // Check is this path a html/css/js?
    private boolean checkStaticResource(String url, String method) {
        return !url.startsWith("/api"); //  && (url.endsWith(".html") || url.endsWith(".css") || url.endsWith(".js")) && method.equals("GET");
    }
}
