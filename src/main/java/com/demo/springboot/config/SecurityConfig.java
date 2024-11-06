package com.demo.springboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.demo.springboot.filter.ErpAuthenticationFilter;
import com.demo.springboot.service.security.ErpUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    ErpAuthenticationFilter authenticationFilter;

    @Autowired
    private ErpUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 不加密
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        SecurityFilterChain chain = httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/api/auth/**", HttpMethod.POST.name())).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(header -> {
                    header.frameOptions(FrameOptionsConfig::disable);
                })
                .build();

        return chain;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
