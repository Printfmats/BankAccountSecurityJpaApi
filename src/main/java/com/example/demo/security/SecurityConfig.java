package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }
    //    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

        @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .authorizeHttpRequests( args ->{
                    args.requestMatchers("/css/**").permitAll();
                    args.requestMatchers("/lockout").permitAll();
                    args.requestMatchers("/api/**").hasAnyRole("ADMIN","USER","GUEST");
                    args.anyRequest().authenticated();

                })
//                .oauth2Login()
//                .and()
                .formLogin()
                .failureHandler(customAuthenticationFailureHandler)
                .loginPage("/login").permitAll()
                .successForwardUrl("/api")
                .and()
                .logout()
                .and()
                .csrf().disable()
                .userDetailsService(jpaUserDetailsService)
                .build();
    }

}
