package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    private User setRoleIDs(User user) {
        Set<Role> roleSet = new HashSet<>();
        user.getRoles()
                .forEach(role -> {
                    Role newRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Unable to find role"));
                    roleSet.add(newRole);
                });
        user.setRoles(roleSet);
        return user;
    }

    public UserDTO create(UserDTO user, String role){

        User newUser = User.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        if (user.getRoles() == null) {

                Role defaultRole = roleRepository.findByName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Cannot find " + role + " role"));
                roles.add(defaultRole);

        }
        else {
            user.getRoles().forEach(r -> {
                Role ro = roleRepository.findByName(ERole.valueOf(r))
                        .orElseThrow(() -> new RuntimeException("Cannot find role: " + r));
                roles.add(ro);
            });
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);

        return user;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserDTO update(UserDTO user) {

        User actUser = findById(user.getId());
        actUser.setId(user.getId());
        actUser.setUsername(user.getUsername());
        actUser.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(actUser);
        //System.out.println("User update done!");
        return user;
    }

    public void delete(Long id) {
        User actUser = findById(id);
        userRepository.delete(actUser);
        //System.out.println("User deletion successful!");
    }

}
