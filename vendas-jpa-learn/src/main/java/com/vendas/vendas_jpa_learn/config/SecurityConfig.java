package com.vendas.vendas_jpa_learn.config;

import com.vendas.vendas_jpa_learn.security.jwt.JwtAuthFilter;
import com.vendas.vendas_jpa_learn.security.jwt.JwtService;
import com.vendas.vendas_jpa_learn.service.impl.UsuarioServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioServiceImpl usuarioService;

    public SecurityConfig(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    private JwtService jwtService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Quando criamos um usuario na memoria
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//
//            UserDetails user = User.builder()
//                    .username("user")
//                    .password(passwordEncoder().encode("123"))
//                    .roles("USER")
//                    .build();
//            return new InMemoryUserDetailsManager(user);
//
//    }

    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/pedidos/**")
                        .hasRole("USER")
                        .requestMatchers("/api/produtos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**")
                        .permitAll()
                        .anyRequest().authenticated()
                ).httpBasic(withDefaults());
        return http.build();
    }

    //Tinha que ter um metodo aqui para permitir a URLS do swagger
}
