package com.example.demo.config;

import com.example.demo.security.auth.handler.FormAuthenticationFailureHandler;
import com.example.demo.security.auth.handler.FormAuthenticationSuccessHandler;
import com.example.demo.security.auth.provider.FormAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationSuccessHandler formSuccessHandler(){
        return new FormAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler formFailureHandler(){
        return new FormAuthenticationFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new FormAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/test1", "/test2");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests((auth)->{
                    auth
                            .antMatchers("/me").hasAnyRole("USER", "ADMIN")
                            .antMatchers("/manage") .hasAnyRole("MANAGER")
                            .antMatchers("/config").hasAnyRole("ADMIN")
                            .anyRequest().permitAll();
                })
                .authenticationProvider(authenticationProvider())
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login_process")
                .successHandler(formSuccessHandler())
                .failureHandler(formFailureHandler())
                .permitAll();

        return httpSecurity.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailService(){
//        UserDetails user = User.builder()
//                .username("u")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//
//        UserDetails manager = User.builder()
//                .username("m")
//                .password(passwordEncoder().encode("1111"))
//                .roles("MANAGER", "USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("a")
//                .password(passwordEncoder().encode("1111"))
//                .roles("ADMIN", "MANAGER", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, manager);
//    }

}
