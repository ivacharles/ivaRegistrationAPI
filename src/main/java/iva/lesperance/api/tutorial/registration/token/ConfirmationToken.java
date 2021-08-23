package iva.lesperance.api.tutorial.registration.token;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    private long tokenID;
    private String userUsername;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmAt;

    public ConfirmationToken(String userUsername, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.userUsername = userUsername;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public ConfirmationToken(long tokenID, String userUsername, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.tokenID = tokenID;
        this.userUsername = userUsername;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
/*
 LocalDateTime now = LocalDateTime.now();
 Timestamp timestamp = Timestamp.valueOf(now);
*/