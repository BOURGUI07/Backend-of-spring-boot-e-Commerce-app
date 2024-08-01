/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import main.models.Role;
import main.repo.RoleRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepo repo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }
    
    private void loadRoles(){
        var roleNames = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN};
        Map<RoleEnum,String> map = Map.of(
                RoleEnum.USER, "User Role",
                RoleEnum.ADMIN, "Admin Role"
                );
        Arrays.stream(roleNames).forEach((RoleEnum roleName) -> {
            var role = repo.findByName(roleName);
            role.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                    roleToCreate.setName(roleName);
                    roleToCreate.setDesc(map.get(roleName));
                    repo.save(roleToCreate);
            }
                    
            );
        }
                
                
        );
        
    }
    
}
