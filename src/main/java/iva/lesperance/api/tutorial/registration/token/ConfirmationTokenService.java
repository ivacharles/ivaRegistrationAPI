package iva.lesperance.api.tutorial.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepo confirmationTokenRepo;

    public boolean saveConfirmationToken(ConfirmationToken confirmationToken) {
        return confirmationTokenRepo.saveConfirmationToken(confirmationToken);
    }
    

    public Optional<ConfirmationToken> getConfirmationToken(String token) {
        return confirmationTokenRepo.getConfirmationToken(token);
    }

    //this update the time and date the token sent via email was clicked on
    public boolean setConfirmedAt(String token) {
        return confirmationTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }
}
