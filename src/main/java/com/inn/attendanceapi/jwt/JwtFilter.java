package com.inn.attendanceapi.jwt;

import com.inn.attendanceapi.FactoryPattern.UserFactory;
import com.inn.attendanceapi.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsersDetailsService service;

    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if(httpServletRequest.getServletPath().matches("/user/login|/user/forgotPassword")){//routes not nessecite auth
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String token = null;

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
                claims = jwtUtil.extractAllClaims(token);
            }

            if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = service.loadUserByUsername(userName);
                if(jwtUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

    }

    public boolean isAdmin() {
        String roleClaim = (String) claims.get("role");
        return roleClaim != null && UserFactory.UserRole.ADMIN.name().equalsIgnoreCase(roleClaim);
    }

    public boolean isProfessor() {
        String roleClaim = (String) claims.get("role");
        return roleClaim != null && UserFactory.UserRole.PROFESSOR.name().equalsIgnoreCase(roleClaim);
    }

    public boolean isStudent() {
        String roleClaim = (String) claims.get("role");
        return roleClaim != null && UserFactory.UserRole.STUDENT.name().equalsIgnoreCase(roleClaim);
    }

    public String getCurrentUser(){
        return userName;
    }

}