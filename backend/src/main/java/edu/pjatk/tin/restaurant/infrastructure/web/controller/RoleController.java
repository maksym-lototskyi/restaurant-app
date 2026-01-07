package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.domain.restaurant_user.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @GetMapping
    public List<String> getAllRoles(){
        return Arrays.stream(Role.values()).map(Role::name).toList();
    }
}
