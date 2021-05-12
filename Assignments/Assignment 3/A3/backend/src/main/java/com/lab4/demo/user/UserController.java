package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.USERS;
import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.CREATE_USER;


@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping(CREATE_USER)
    public UserDTO create(@RequestBody UserDTO user, @PathVariable String role) {
        return userService.create(user, role);
    }

    @PatchMapping
    public UserDTO update(@RequestBody UserDTO user) {
        return userService.update(user);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { userService.delete(id); }

}
