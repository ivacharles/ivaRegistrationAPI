package iva.lesperance.api.tutorial.databaseT;

import iva.lesperance.api.tutorial.userApp.UserApp;
import iva.lesperance.api.tutorial.userApp.UserAppRepository;
import iva.lesperance.api.tutorial.userApp.UserAppService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
public class dbTest {

    private final UserAppRepository userAppRepository;

//    @DisplayName("(1) test connection")
//    @Test
//    void T1() throws Exception {
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userApp","root","#Ivaoy");
//        System.out.println(conn);
//        conn.close();
//    }

//    @DisplayName("check is username exist in the database")
//    @ParameterizedTest()
//    void T2() throws Exception {
//        boolean expected = true;
//        boolean actual =false;
//        if(userAppRepository.findUserByUsername("ilesperance24@gmail.com").isPresent()){
//            UserApp userApp = userAppRepository.findUserByUsername("ilesperance24@gmail.com").get();
//            System.out.println(userApp.getFName());
//            actual = true;
//        }
//        assertEquals(expected,actual);
//    }


}
