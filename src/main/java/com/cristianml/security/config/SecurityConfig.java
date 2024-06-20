package com.cristianml.security.config;

import com.cristianml.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatcher("/ecommerce/customer/**") // This is for redirect to specific login
                .authorizeHttpRequests( http -> {
                    http.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll();
                    http.requestMatchers("/ecommerce/customer/**").permitAll();
                    http.anyRequest().authenticated();

                })
                .formLogin(form -> form
                        .loginPage("/ecommerce/customer/login")
                        .defaultSuccessUrl("/ecommerce/customer/home", true)
                        .permitAll()
                ).logout(logout -> logout
                                // this path changes according to the method .securityMatcher("/ecommerce/customer/**")
                                .logoutUrl("/ecommerce/customer/logout")
                                .permitAll()
                )
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain2(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.securityMatcher("/ecommerce/admin/**")
                .authorizeHttpRequests( http -> {
                    http.requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll();
                    http.requestMatchers("/ecommerce/admin/**").hasRole("ADMIN");
                    http.anyRequest().authenticated();

                })
                .formLogin(form -> form
                        .loginPage("/ecommerce/admin/login")
                        .loginProcessingUrl("/ecommerce/admin/login") // URL donde se procesa el login
                        .defaultSuccessUrl("/ecommerce/admin/home", true)
                        .permitAll()
                ).logout(logout -> logout
                        // this path changes according to the method .securityMatcher("/ecommerce/admin/**")
                        .logoutUrl("/ecommerce/admin/logout")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Method to can show the statics objects
    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return (web) -> web.ignoring().requestMatchers("/img/**", "/js/**", "/css/**");
    }*/

}
