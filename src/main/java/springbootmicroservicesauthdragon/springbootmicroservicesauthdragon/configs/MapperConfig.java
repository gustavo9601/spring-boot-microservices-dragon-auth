package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    /*
    * Libreria que se usara para mapear de objetos a otros objetos
    * */

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
