package com.example.demo.security.config;

import com.example.demo.security.handler.FormAuthenticationFailureHandler;
import com.example.demo.security.handler.FormAuthenticationSuccessHandler;
import com.example.demo.security.metadataSource.UrlResourcesMapFactoryBean;
import com.example.demo.security.metadataSource.UrlSecurityMetadataSource;
import com.example.demo.security.provider.FormAuthenticationProvider;
import com.example.demo.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final SecurityResourceService securityResourceService;

    public SecurityConfig(UserDetailsService userDetailsService, SecurityResourceService securityResourceService) {
        this.userDetailsService = userDetailsService;
        this.securityResourceService = securityResourceService;
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UrlResourcesMapFactoryBean urlResourcesMapFactoryBean(){
        return new UrlResourcesMapFactoryBean(securityResourceService);
    }
    @Bean
    public FilterInvocationSecurityMetadataSource metadataSource(UrlResourcesMapFactoryBean resourcesMapFactoryBean) throws Exception {
        return new UrlSecurityMetadataSource(resourcesMapFactoryBean.getObject(), securityResourceService);
    }

    @Bean
    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<?>> voters = new ArrayList<>();
        voters.add(new RoleVoter());
        return new AffirmativeBased(voters);
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor(
            AuthenticationManager authenticationManager,
            FilterInvocationSecurityMetadataSource metadataSource,
            AccessDecisionManager accessDecisionManager){
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(metadataSource);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        interceptor.setAuthenticationManager(authenticationManager);

        return interceptor;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, FilterSecurityInterceptor filterSecurityInterceptor) throws Exception {
        httpSecurity
                .authenticationProvider(authenticationProvider())
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login_process")
                .successHandler(formSuccessHandler())
                .failureHandler(formFailureHandler())
                .and()
                .addFilterBefore(filterSecurityInterceptor, FilterSecurityInterceptor.class);

        return httpSecurity.build();
    }

}
