package iva.lesperance.api.tutorial.registration.token;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConfirmationTokenRepoImpl implements ConfirmationTokenRepo {

    private final JdbcTemplate jdbcTemplate;

    private final String saveConfirmationToken4UserSQL =
            "INSERT INTO userApp.token (userUsername, token, createdAt, expiresAt) values (?,?,?,?);";
    private final String getSaveConfirmationToken4UserSQL=
            "SELECT * FROM userApp.token WHERE token = ?;";
    private final String updateConfirmedAtSQL = "UPDATE userApp.token SET confirmAt =? WHERE token = ?";

    @Override
    public boolean saveConfirmationToken(ConfirmationToken confirmationToken) {
        return jdbcTemplate.update(saveConfirmationToken4UserSQL, confirmationToken.getUserUsername(),
                confirmationToken.getToken(), Timestamp.valueOf(confirmationToken.getCreatedAt()), Timestamp.valueOf(confirmationToken.getExpiresAt())) == 1;
    }

    @Override
    public Optional<ConfirmationToken> getConfirmationToken(String token) {
        List<ConfirmationToken> tokens = jdbcTemplate.query(getSaveConfirmationToken4UserSQL,  new ConfirmationTokenMapper(), token);
        if(tokens.size() ==1) {
            return Optional.of(tokens.get(0));
        }
        else
            return Optional.empty();
    }

    @Override
    public boolean updateConfirmedAt(String token, LocalDateTime time) {
        return jdbcTemplate.update(updateConfirmedAtSQL, time, token) == 1;

    }
}
