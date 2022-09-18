package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.configs.JwtsProvider;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.dto.TokenDto;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.dto.UserDto;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.entities.UserEntity;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {


    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtsProvider jwtsProvider;


    // Metodo para crear un usuario
    public UserDto save(UserDto userDto) {

        Optional<UserEntity> userOptional = this.userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("El usuario ya existe [{}]", userDto.getUsername()));
        }

        logger.info("Usuario antes del password decoder, {}", userDto);

        // Encodeando el password
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        logger.info("Usuario despues del password decoder, {}", userDto);

        UserEntity userEntity = this.userRepository.save(new UserEntity(userDto.getUsername(), userDto.getPassword()));

        // Mapeando el userEntity a userDto
        return this.modelMapper.map(userEntity, UserDto.class);
    }


    public TokenDto login(UserDto userDto) {
        UserEntity userEntity = this.userRepository.findByUsername(userDto.getUsername())
                .filter(user -> this.passwordEncoder.matches(userDto.getPassword(), user.getPassword())) // Comparacion de password
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o password incorrecto"));

        String tokenGenerated = this.jwtsProvider.createToken(userEntity);
        logger.info("Token generado: {}", tokenGenerated);

        return new TokenDto(tokenGenerated);

    }

    public TokenDto validateToken(String token) {
        if (this.jwtsProvider.validate(token)) {

            String username = this.jwtsProvider.getUsernameFromToken(token);
            Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);

            if (userEntityOptional.isPresent()) {
                return new TokenDto(token);
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado");

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
    }
}
