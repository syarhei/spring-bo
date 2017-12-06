package main.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Middleware", urlPatterns = "/*")
public class Middleware implements Filter {

    private enum ROLES {
        ADMIN, USER, GUEST, BOT
    }

    public void destroy() { }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (checkGuestPermissions((HttpServletRequest)req)) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).setStatus(403);
        }
    }

    public void init(FilterConfig config) throws ServletException { }

    private boolean checkPermissions(HttpServletRequest req) {

        // TODO: Get token with user role
        String role = "ADMIN";

        switch (ROLES.valueOf(role)) {
            case ADMIN:
                return true;
            case GUEST:
                return checkGuestPermissions(req);
            default:
                return false;
        }
    }

    private boolean checkGuestPermissions(HttpServletRequest req) {
        return (req.getMethod().equals("GET")) || (req.getServletPath().contains("/api/session"));
    }

    private boolean checkUserPermissions(HttpServletRequest req) {
        // TODO: Create user permission's paths
        return true;
    }
}
