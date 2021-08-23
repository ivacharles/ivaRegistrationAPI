package iva.lesperance.api.tutorial.registration.token;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConfirmationTokenMapper implements RowMapper<ConfirmationToken> {
    @Override
    public ConfirmationToken mapRow(ResultSet resultSet, int i) throws SQLException {
        long tokeID = resultSet.getLong("tokenID");
        String userUsername = resultSet.getString("userUsername");
        String token = resultSet.getString("token");
        LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
        LocalDateTime expiresAt = resultSet.getTimestamp("expiresAt").toLocalDateTime();

        return new ConfirmationToken(tokeID, userUsername,token, createdAt,expiresAt);
    }
}
