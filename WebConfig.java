
package com.vehicle.vehicle_service;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HandlerInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Object handler) throws Exception {

                HttpSession session = request.getSession(false);

                if (session == null ||
                    session.getAttribute("loggedInUser") == null) {

                    response.sendRedirect("/login");
                    return false;
                }

                String role = (String) session.getAttribute("userRole");
                String path = request.getRequestURI();

                if ("Admin".equals(role)) {
                    return true;
                }

                if ("Service Staff".equals(role)) {

                    if (path.startsWith("/reports")) {
                        response.sendError(403, "Access Denied");
                        return false;
                    }

                    return true;
                }

                if ("Customer".equals(role)) {

                    if (path.startsWith("/bookings") ||
                        path.startsWith("/service-history")) {
                        return true;
                    }

                    response.sendError(403, "Access Denied");
                    return false;
                }

                response.sendError(403, "Access Denied");
                return false;
            }

        }).addPathPatterns(
                "/customers/**",
                "/vehicles/**",
                "/bookings/**",
                "/spare-parts/**",
                "/billing/**",
                "/service-history/**",
                "/reports/**"
        );
    }
}
