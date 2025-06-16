package org.example.attendanceai.config.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Integrate CORS here
                .csrf(AbstractHttpConfigurer::disable)  // More explicit disable
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/v3/api-docs/**",        // OpenAPI JSON
                                "/swagger-ui/**",         // Swagger static resources
                                "/swagger-ui.html"       // Swagger UI HTML
                        ).permitAll()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/authenticate", "/api/v1/auth/validate-token")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/facedetection/start", "/api/v1/facedetection/save").permitAll()
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Ensure JWT filter is added here
        return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins (NOT RECOMMENDED FOR PRODUCTION)
        configuration.setAllowedOriginPatterns(Collections.singletonList("*")); // Use setAllowedOriginPatterns for '*' or patterns
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Auth-Token", "Access-Control-Allow-Origin")); // Common headers
        configuration.setExposedHeaders(Arrays.asList("Authorization", "X-Total-Count", "Access-Control-Allow-Origin")); // Headers exposed to the client
        configuration.setAllowCredentials(true); // Must be true for '*', so use setAllowedOriginPatterns instead of setAllowedOrigins
        configuration.setMaxAge(3600L); // How long the pre-flight response can be cached (in seconds)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this CORS configuration to all paths
        return source;
    }

}