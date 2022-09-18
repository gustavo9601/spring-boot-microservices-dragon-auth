package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.dto.TokenDto;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.dto.UserDto;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto user) {
        return ResponseEntity.ok(this.authService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto user) {
        return ResponseEntity.ok(this.authService.login(user));
    }

    @PostMapping("/check-token")
    public ResponseEntity<TokenDto> checkToken(@RequestParam String token) {
        return ResponseEntity.ok(this.authService.validateToken(token));
    }


}
