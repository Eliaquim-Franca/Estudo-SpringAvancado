package com.learning.security_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig {


    // o HttpSecurity vem ja de um contexto e por isso não temos que instanciar ele nem colocar a anotação @Autowired
    //Aqui nesse metodo vamos montar uma autenticação basica.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   SenhaMasterAuthenticationProvider senhaMasterAuthenticationProvider,
                                                   CustomAuthenticationProvider customAuthenticationProvider,
    CustomFilter customFilter) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> {
            customizer.requestMatchers("/public").permitAll();
            customizer.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults())
                .authenticationProvider(senhaMasterAuthenticationProvider)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService (){

        UserDetails userCommon = User.builder()
                .username("Silas")
                .password(passwordEncoder().encode("Eliaquim"))
                .roles("USER")
                .build();

        UserDetails adminCommon = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userCommon, adminCommon);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        //por padrão temos que por ROLE_ mas se quisermos alterar para deixar sem nada é só usarmos a config do spring
        return new GrantedAuthorityDefaults("");

    }
}
