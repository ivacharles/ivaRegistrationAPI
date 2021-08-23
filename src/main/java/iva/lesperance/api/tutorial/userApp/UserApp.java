package iva.lesperance.api.tutorial.userApp;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserApp implements UserDetails {

    private Long id;
    private String fName;
    private String lName;
    private String username;
    private String phoneNumber;
    private String password;
    private String oldPassword;
    private Collection<UserAppRole> userAppRoles;
    private Boolean locked;
    private Boolean enabled;
    private LocalDateTime userCreated;
    private LocalDateTime userUpdated;

    public UserApp(String fName, String lName, String username, String phoneNumber, String password) {
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.oldPassword = password;
        this.locked = false;
        this.enabled = false;
        this.userCreated = LocalDateTime.now();;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAppRoles.stream()
                .map(a -> new SimpleGrantedAuthority(a.getUserRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
