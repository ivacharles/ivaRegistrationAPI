package iva.lesperance.api.tutorial.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "app/one")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationForm request){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/app/one/register").toUriString());
        return registrationService.register(request);
    }
    @GetMapping("/user/{username}")
    public RegistrationForm getUserByUsername(@PathVariable("username") String username){
        log.info("this getUser method is called");
        return registrationService.findUserByUsername(username);
    }

    @GetMapping(path = "/confirm/{token}")
    public String confirm(@PathVariable("token") String token) {
        log.info("this confirm method is called");
        return registrationService.confirmToken(token);
    }


}
