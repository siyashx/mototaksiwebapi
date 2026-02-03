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
        // yalnız admin endpoint-ləri qoruyuruq
        return uri == null || !uri.startsWith("/api/menupro/admin/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String u = req.getHeader("X-ADMIN-USER");
        String p = req.getHeader("X-ADMIN-PASS");

        if (u == null || p == null || !u.equals(adminUser) || !p.equals(adminPass)) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"message\":\"ADMIN_UNAUTHORIZED\"}");
            return;
        }

        chain.doFilter(req, res);
    }
}
