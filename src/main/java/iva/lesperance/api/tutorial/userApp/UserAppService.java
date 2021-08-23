package iva.lesperance.api.tutorial.userApp;

import iva.lesperance.api.tutorial.registration.token.ConfirmationToken;
import iva.lesperance.api.tutorial.registration.token.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAppService implements UserDetailsService {

    private final UserAppRepository userAppRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAppRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("user %s not found", username)));
    }

    //this method return link that the user has to confirm so that the new account is enable and active
    public String signUpUser(UserApp userApp){
        boolean isEmailExistInDB = userAppRepo.findUserByUsername(userApp.getUsername()).isPresent(); // this check if email exist in DB
        if(isEmailExistInDB){
            throw new IllegalStateException(String.format("this username: %s is taken",userApp.getUsername()));
        }else {
            String encodedPwd = bCryptPasswordEncoder.encode(userApp.getPassword());
            userApp.setPassword(encodedPwd);
            userApp.setOldPassword(encodedPwd);

            UserAppRole role = findRole("USER_ROLE"); //get the user role from the DB

            boolean isUserSaved = userAppRepo.saveUser(userApp);

            if (!isUserSaved) {
                throw new IllegalStateException(String.format("user: %s was not saved", userApp.getFName()));
            } else {
                addRole2User(userApp.getUsername(), role.getUserRole()); // save  basic role for the user
                log.info(String.format("user: %s was saved",userApp.getFName()));

                String token = UUID.randomUUID().toString(); // get a random token

                ConfirmationToken confirmationToken = new ConfirmationToken( // create an token obj
                        userApp.getUsername(),
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15));
                confirmationTokenService.saveConfirmationToken(confirmationToken);// store it in the DB

                return token;
            }
        }
    }

    public UserAppRole findRole(String userRole) {
        return userAppRepo.findRole(userRole)
                .orElseThrow(() -> new IllegalStateException(String.format("role: %s does not exist.", userRole)));
    }

    public boolean addRole2User(String username, String userRole) {
        return userAppRepo.addRole2User(username, userRole);
    }

    public UserApp findUserByUsername(String username) {
        return userAppRepo.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("user: %s not found ", username)));
    }

    public boolean enableUserApp(String username) {
        return userAppRepo.enableUserApp(username);
    }
}
