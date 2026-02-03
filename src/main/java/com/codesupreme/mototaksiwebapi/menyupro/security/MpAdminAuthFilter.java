package com.codesupreme.mototaksiwebapi.menyupro.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MpAdminAuthFilter extends OncePerRequestFilter {

    @Value("${menupro.admin.username}")
    private String adminUser;

    @Value("${menupro.admin.password}")
    private String adminPass;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // ✅ PRE-FLIGHT (OPTIONS) heç vaxt auth tələb etməsin
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;

        // yalnız admin endpoint-ləri qoruyuruq
        return uri == null || !uri.startsWith("/api/menupro/admin/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // ✅ CORS header-larını həmişə yaz (401 olsa belə)
        String origin = req.getHeader("Origin");
        if (origin != null && (
                origin.equals("https://mototaksi.az") ||
                        origin.equals("http://localhost:5173") ||
                        origin.equals("http://localhost:3000")
        )) {
            res.setHeader("Access-Control-Allow-Origin", origin);
            res.setHeader("Vary", "Origin");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-ADMIN-USER, X-ADMIN-PASS");
        }

        // (əgər hər ehtimala qarşı burada OPTIONS düşsə)
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(200);
            return;
        }

        String u = req.getHeader("X-ADMIN-USER");
        String p = req.getHeader("X-ADMIN-PASS");

        if (u == null || p == null || !u.equals(adminUser) || !p.equals(adminPass)) {
            res.setStatus(401);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.getWriter().write("{\"message\":\"ADMIN_UNAUTHORIZED\"}");
            return;
        }

        chain.doFilter(req, res);
    }
}
