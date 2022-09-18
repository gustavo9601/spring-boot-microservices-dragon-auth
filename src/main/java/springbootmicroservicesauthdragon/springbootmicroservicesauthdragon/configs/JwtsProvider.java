package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtsProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(UserEntity userEntity) {


        Map<String, Object> claims = Jwts.claims()
                .setSubject(userEntity.getUsername());// Le setea el usario al tojen que lo creara

        claims.put("id", userEntity.getId());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000);

        return Jwts.builder()
                .setHeaderParam("company_name", "gm_dragon_corporatioon")
                .setClaims(claims) // pushea los claims
                .setIssuedAt(now) // fecha de creacion
                .setExpiration(expiration) // fecha de expiracion
                .signWith(SignatureAlgorithm.HS512, this.secret) // Especifica la firma
                .compact();

    }


    public boolean validate(String token) {
        // Si no genera exepcion el token es valido
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extrae el usuario del token
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
    }
}
