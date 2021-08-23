package iva.lesperance.api.tutorial.registration;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class RegistrationForm {
    private final String fName;
    private final String lName;
    private final String username;
    private final String phoneNumber;
    private final String password;
}
