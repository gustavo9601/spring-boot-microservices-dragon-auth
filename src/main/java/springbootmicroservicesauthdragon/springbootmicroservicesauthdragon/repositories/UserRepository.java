package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.models.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);
}
