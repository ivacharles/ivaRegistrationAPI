package iva.lesperance.api.tutorial.registration;

import iva.lesperance.api.tutorial.email.EmailSenderService;
import iva.lesperance.api.tutorial.registration.token.ConfirmationToken;
import iva.lesperance.api.tutorial.registration.token.ConfirmationTokenService;
import iva.lesperance.api.tutorial.security.config.RegexValidator;
import iva.lesperance.api.tutorial.userApp.UserApp;
import iva.lesperance.api.tutorial.userApp.UserAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RegistrationService {

    private final RegexValidator validator;
    private final UserAppService userAppService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    public String register(RegistrationForm request){

        boolean isFirstNameValid = validator.isNameValid(request.getFName());
        boolean isLastNameValid = validator.isNameValid(request.getLName());
        boolean isValidEmail = validator.isEmailValid(request.getUsername());
        boolean isPwdValid = validator.isPwdValid(request.getPassword());
        boolean isPhoneValid = validator.isPhoneValid(request.getPhoneNumber());

        String token2activateAcc;

        if (!isValidEmail || !isFirstNameValid || !isLastNameValid || !isPwdValid || !isPhoneValid){
            throw new IllegalStateException(String.format("something is wrong in validating... %s", request.getFName()));
        }else {
             token2activateAcc = userAppService.signUpUser(new UserApp(
                    request.getFName(),
                    request.getLName(),
                    request.getUsername(),
                    request.getPhoneNumber(),
                    request.getPassword()
            ));
             if (token2activateAcc.isEmpty()){
                 throw new IllegalStateException(String.format("token: %s was not created", token2activateAcc));
             }else {
                 String link2ActivateAcc = "http://localhost:8080/api/one/register/confirm/" + token2activateAcc;
                 emailSenderService.send(
                         request.getUsername(),
                         buildEmail(request.getFName(), link2ActivateAcc));
                 return "Check your email";
             }
        }
    }

    public RegistrationForm findUserByUsername(String username) {
        UserApp userApp = userAppService.findUserByUsername(username);
        return new RegistrationForm(userApp.getFName(),userApp.getLName(),userApp.getUsername(),userApp.getPhoneNumber(),null);
    }

    public String confirmToken(String token) {
        //get the token from the DB
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        //check for the confirmAt date prop
        if (confirmationToken.getConfirmAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        //check for the createdAt date prop and the now time
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        //set the confirmedAt time when user click on this url, (when this method is called)
        confirmationTokenService.setConfirmedAt(token);
        //enable the user then
        userAppService.enableUserApp(confirmationToken.getUserUsername());
        //send confirmation
        return "confirmed";
    }

    //html rep of the email
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    //TODO:
    // 1- create a user
    // 2- get requests to CRUD the user
    //      a- create a registration form
    //      b- create endpoints for CRUD in  RestController
    // 3- create a repository class for CRUD
    //      a- access the database using queries
    // 2- Enable the user (activate user account)
    //      a- create a token
    //      b- send it via email to the user
    //      c- once the user click on the link it activates the account automatically

}
