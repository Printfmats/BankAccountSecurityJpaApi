package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    String myKey = "mySecretKey";
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
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( args ->{
                    args.requestMatchers("/css/**").permitAll();
                    args.requestMatchers("/lockout").permitAll();
                    args.requestMatchers("/api/**").hasAnyRole("ADMIN","USER","GUEST");
                    args.anyRequest().authenticated();

                })
                .sessionManagement(session -> {
                    session.maximumSessions(1)
                            .maxSessionsPreventsLogin(true); //prevent a user from logging in multiple times
                })
                .logout( logout ->{
                    logout.logoutUrl("/logout");
                    logout.addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.COOKIES)));//COOKIES
                    logout.deleteCookies("JSESSIONID");
                })
                .formLogin(login -> {
                    login.loginPage("/login").permitAll();
                    login.successForwardUrl("/api");
                    login.failureHandler(customAuthenticationFailureHandler);
                })
                .rememberMe(remember -> {
                    remember
                            .rememberMeServices(rememberMeServices(jpaUserDetailsService))
                            .key(myKey)
                            .tokenValiditySeconds(86400) //remember me is working 24H
                            .userDetailsService(jpaUserDetailsService);
                })
                .sessionManagement(session -> {
                      session.sessionFixation().newSession();//lesser posibility to steal user session
                    }
                )
                .csrf().disable()
                .userDetailsService(jpaUserDetailsService)
                .build();
    }

}
