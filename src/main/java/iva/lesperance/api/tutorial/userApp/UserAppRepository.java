package iva.lesperance.api.tutorial.userApp;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAppRepository {
    Optional<UserApp> findUserByEmail(String email);
}
