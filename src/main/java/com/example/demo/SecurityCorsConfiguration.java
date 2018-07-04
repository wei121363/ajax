package com.example.demo;

 import org.springframework.boot.web.servlet.FilterRegistrationBean;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.core.Ordered;
 import org.springframework.web.cors.CorsConfiguration;
 import org.springframework.web.cors.CorsConfigurationSource;
 import org.springframework.web.filter.CorsFilter;

 import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecurityCorsConfiguration {


    @Bean
    public FilterRegistrationBean corsFilter() {
        CorsConfigurationSource source = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {

                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin(httpServletRequest.getHeader("Origin"));
                config.addAllowedHeader(CorsConfiguration.ALL);
                config.addAllowedMethod(CorsConfiguration.ALL);
                return config;
            }
        };

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //补充一下
        bean.addUrlPatterns("/*");
         bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
