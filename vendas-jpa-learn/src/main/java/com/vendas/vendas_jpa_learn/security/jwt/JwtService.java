package com.vendas.vendas_jpa_learn.security.jwt;

import com.vendas.vendas_jpa_learn.VendasJpaLearnApplication;
import com.vendas.vendas_jpa_learn.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    public String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    public String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expiracaoString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant); // o Jwt retorna o tipo de expiracao em Date por isso temos que transformar.


        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
    }



    public boolean tokenValido(String token){
        try{
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contextoSpr = SpringApplication.run(VendasJpaLearnApplication.class);
        JwtService service = contextoSpr.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("Gomes").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);
    }

}

