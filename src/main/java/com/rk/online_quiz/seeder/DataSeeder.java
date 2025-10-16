package com.rk.online_quiz.seeder;

import com.rk.online_quiz.entity.LoginUser;
import com.rk.online_quiz.enums.UserRoles;
import com.rk.online_quiz.repository.LoginUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private LoginUserRepository loginUserRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if Admin exists
        if (loginUserRepository.findByUsername("ramkumarmegavarnan@gmail.com").isEmpty()) {
            LoginUser admin = new LoginUser();
            admin.setUsername("ramkumarmegavarnan@gmail.com");
            admin.setPassword("{noop}Ram@1234"); // or use a password encoder
            admin.setName("System Administrator 1");
            admin.setActive(true);
            admin.setRole(UserRoles.ADMIN);

            loginUserRepository.save(admin);
            System.out.println("✅ Default Admin user created: ramkumarmegavarnan@gmail.com / admin123");
        } else {
            System.out.println("ℹ️ Admin user already exists, skipping creation.");
        }

        if (loginUserRepository.findByUsername("ramkumar180499@gmail.com").isEmpty()) {
            LoginUser admin = new LoginUser();
            admin.setUsername("ramkumar180499@gmail.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("Ram@1234")); // or use a password encoder
            admin.setName("Ramkumar M");
            admin.setActive(true);
            admin.setRole(UserRoles.PARTICIPANT);

            loginUserRepository.save(admin);
            System.out.println("✅ Default Admin user created: ramkumar180499@gmail.com / admin123");
        } else {
            System.out.println("ℹ️ Admin user already exists, skipping creation.");
        }
    }
}