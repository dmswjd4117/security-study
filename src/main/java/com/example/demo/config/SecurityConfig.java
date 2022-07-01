package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                            .antMatchers("/", "/member").permitAll()
                            .antMatchers("/me").hasAnyRole("USER", "ADMIN")
                            .antMatchers("/manage") .hasAnyRole("MANAGER")
                            .antMatchers("/config").hasAnyRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .userDetailsService(userDetailsService)
                .formLogin()
                .successHandler((req, res, auth)->{
                    System.out.println(auth.getPrincipal());
                    System.out.println(auth.getCredentials());
                    System.out.println(auth.getAuthorities());
                })
                .failureHandler((req, res, exc)->{
                    exc.printStackTrace();
                })
                .permitAll();

        return httpSecurity.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailService(){
//        UserDetails user = Member.builder()
//                .username("u")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//
//        UserDetails manager = Member.builder()
//                .username("m")
//                .password(passwordEncoder().encode("1111"))
//                .roles("MANAGER", "USER")
//                .build();
//
//        UserDetails admin = Member.builder()
//                .username("a")
//                .password(passwordEncoder().encode("1111"))
//                .roles("ADMIN", "MANAGER", "USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, manager);
//    }

}
