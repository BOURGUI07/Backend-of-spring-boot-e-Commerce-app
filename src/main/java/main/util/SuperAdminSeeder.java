/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import lombok.RequiredArgsConstructor;
import main.models.User;
import main.repo.RoleRepo;
import main.repo.UserRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Component
@RequiredArgsConstructor
public class SuperAdminSeeder implements ApplicationListener<ContextRefreshedEvent>{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder encoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createSuperAdmin();
    }
    
    private void createSuperAdmin(){
        var role = roleRepo.findByName(RoleEnum.SUPERADMIN);
        if(!userRepo.existsByUsername("admin")){
            var user = new User();
            user.setUsername("admin");
            user.setEmail("admin@gmail.com");
            user.setFirstname("firstname");
            user.setLastname("lastname");
            user.setRole(role.get());
            user.setPhone("0123456789");
            user.setPassword(encoder.encode("MARADONA86regate@"));
            userRepo.save(user);
        }
    }
    
}
