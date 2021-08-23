package iva.lesperance.api.tutorial;


import com.fasterxml.jackson.databind.ObjectMapper;
import iva.lesperance.api.tutorial.registration.RegistrationController;
import iva.lesperance.api.tutorial.registration.RegistrationForm;
import iva.lesperance.api.tutorial.userApp.UserApp;
import iva.lesperance.api.tutorial.userApp.UserAppRepository;
import iva.lesperance.api.tutorial.userApp.UserAppService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({RegistrationController.class, UserApp.class, UserAppService.class} )

public class RegistrationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserAppService userAppRepository;

    RegistrationForm request = RegistrationForm.builder()
                                 .fName("iva")
                                 .lName("charles")
                                 .username("ilesperance24@gmail.com")
                                 .phoneNumber("7186139273")
                                 .password("98S3236qnw#")
                                 .build();
    UserApp user = new UserApp(request.getFName(),
            request.getLName(), request.getUsername(), request.getPhoneNumber()
            ,request.getPassword());

    @Test
    public void getUserByIdSuccess() throws Exception{
        Mockito.when(userAppRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
        .get("/app/one/user/ilesperance24@gmail.com")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }
}
