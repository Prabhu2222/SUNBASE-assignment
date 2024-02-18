package com.prabhu.customermanagementportal.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Value("${custom.string.loginId:user}")
    private String userName;
    @Value("${custom.string.password:user@123}")
    private String password;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    //create a bin of user details service to define authentication related stuff@
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails admin= User.withUsername(userName)
                .password(encoder.encode(password))
                .build();
        return new InMemoryUserDetailsManager(admin);
    }



    //for authorization use SecurityFilter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf->csrf.disable());//disable csrf

        //filtering Http requests
        httpSecurity.authorizeHttpRequests(
                requestMatchers->
                        requestMatchers
                                .requestMatchers("/api/authenticate").permitAll()
                                .anyRequest().authenticated()
                );

        //Authentication EntryPoint for handling Exception
        httpSecurity.exceptionHandling(exceptionConfig->exceptionConfig.authenticationEntryPoint(authenticationEntryPoint));


        //set session policy ,use this ony if u want jwt authentication or else form will reset again and again
        httpSecurity.sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //adding one of our own custom JWT Authentication filter
        httpSecurity.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
//        httpSecurity.formLogin();
        return httpSecurity.build();


    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService(passwordEncoder()));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
