package com.synth.hotelhousekeepingservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security baseline emitted whenever Authorization Policies are
 * declared in the spec.
 *
 * Policy decisions are made per-method via @PreAuthorize on the generated
 * controllers (see Controller.java); this configuration class supplies the
 * surrounding scaffolding that makes those annotations effective:
 *
 *   - @EnableMethodSecurity turns on @PreAuthorize evaluation.
 *   - csrf().disable() because the generated REST endpoints are stateless.
 *   - sessionCreationPolicy(STATELESS) — no HTTP session is created.
 *   - HTTP Basic auth as the default authentication mechanism. Replace
 *     with your own (JWT, OAuth2 Resource Server, etc.) at integration
 *     time; the @PreAuthorize annotations will keep working unchanged.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(httpBasic -> {})
                .build();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService users() {
        var encoder = org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
        var admin = org.springframework.security.core.userdetails.User.builder()
                .username("admin").password(encoder.encode("admin")).roles("ADMIN", "SUPERVISOR", "STAFF").build();
        var user = org.springframework.security.core.userdetails.User.builder()
                .username("user").password(encoder.encode("user")).roles("USER").build();
        return new org.springframework.security.provisioning.InMemoryUserDetailsManager(admin, user);
    }
}
