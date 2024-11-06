package com.demo.springboot.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErpCorsFilter extends CorsFilter {
    private static final Logger logger = LogManager.getLogger(ErpCorsFilter.class);

    public ErpCorsFilter() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        logger.info("============ Start CorsFilter.doFilterInternal() ============");
        CorsConfiguration corsConfig = new CorsConfiguration();
        List<String> allowedHeaders = Arrays.asList("*");
        List<String> exposedHeaders = Arrays.asList("*");
        List<String> allowedMethods = Arrays.asList("POST", "GET", "DELETE", "PUT", "OPTIONS");
        List<String> allowedOrigins = Arrays.asList("*");
        corsConfig.setAllowedHeaders(allowedHeaders);
        corsConfig.setAllowedMethods(allowedMethods);
        // corsConfig.setAllowedOrigins(allowedOrigins);
        corsConfig.setAllowedOriginPatterns(allowedOrigins);
        corsConfig.setExposedHeaders(exposedHeaders);
        corsConfig.setMaxAge(36000L);
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        logger.info("============ End CorsFilter.doFilterInternal() ============");
        return source;
    }
}
