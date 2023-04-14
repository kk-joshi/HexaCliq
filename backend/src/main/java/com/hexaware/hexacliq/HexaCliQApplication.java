package com.hexaware.hexacliq;

import com.hexaware.hexacliq.dao.IRoleRepository;
import com.hexaware.hexacliq.dto.User;
import com.hexaware.hexacliq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class HexaCliQApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HexaCliQApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

    private List<User> getUsers() {
        User user1 = new User();
        user1.setUserName("PuneetJ");
        user1.setFirstName("Puneet");
        user1.setLastName("Joshi");
        user1.setPassword(bCryptPasswordEncoder.encode("Punnet@123"));
        user1.setEmail("PunnetJoshi@hexaware.com");
        user1.setEnabled(true);
        user1.setProfile("CONSULTANT");
        user1.setPhone("9890123123");
        user1.setEmpId(78100);

        User user2 = new User();
        user2.setUserName("NiyatiS");
        user2.setFirstName("Niyati");
        user2.setLastName("Shaha");
        user2.setPassword(bCryptPasswordEncoder.encode("Niyati@123"));
        user2.setEmail("NiyatiShaha@hexaware.com");
        user2.setEnabled(true);
        user2.setProfile("CONSULTANT");
        user2.setPhone("9890123124");
        user2.setEmpId(78101);

        User user3 = new User();
        user3.setUserName("NiyatiS");
        user3.setFirstName("Niyati");
        user3.setLastName("Shaha");
        user3.setPassword(bCryptPasswordEncoder.encode("Niyati@123"));
        user3.setEmail("NiyatiShaha@hexaware.com");
        user3.setEnabled(true);
        user3.setProfile("CONSULTANT");
        user3.setPhone("9890123124");
        user3.setEmpId(78102);

        User user4 = new User();
        user4.setUserName("ChinmayG");
        user4.setFirstName("Chinmay");
        user4.setLastName("Ghadhade");
        user4.setPassword(bCryptPasswordEncoder.encode("Chinmay@123"));
        user4.setEmail("NiyatiShaha@hexaware.com");
        user4.setEnabled(true);
        user4.setProfile("CONSULTANT");
        user4.setPhone("9890123124");
        user4.setEmpId(79101);

        User user5 = new User();
        user5.setUserName("PratikT");
        user5.setFirstName("Pratik");
        user5.setLastName("Thakur");
        user5.setPassword(bCryptPasswordEncoder.encode("Pratik@123"));
        user5.setEmail("NiyatiShaha@hexaware.com");
        user5.setEnabled(true);
        user5.setProfile("CONSULTANT");
        user5.setPhone("9890123124");
        user5.setEmpId(78101);

        return List.of(user1, user2, user3, user4, user5);


    }
}
