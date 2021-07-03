package iva.lesperance.api.tutorial.userApp;

import java.util.Optional;

public class UserAppRepositoryImpl implements UserAppRepository{
    @Override
    public Optional<UserApp> findUserByEmail(String email) {
        return Optional.empty();
    }
}
