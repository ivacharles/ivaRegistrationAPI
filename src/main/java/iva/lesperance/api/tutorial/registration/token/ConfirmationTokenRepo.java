package iva.lesperance.api.tutorial.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepo {
    boolean saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getConfirmationToken(String username);
    boolean updateConfirmedAt(String token, LocalDateTime time);

}
