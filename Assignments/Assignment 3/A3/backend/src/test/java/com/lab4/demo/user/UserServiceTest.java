package com.lab4.demo.user;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static com.lab4.demo.TestCreationFactory.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;


    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, passwordEncoder, roleRepository);
    }


    @Test
    void create() {

        String role = "DOCTOR";
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .roles(Set.of(role))
                .build();
        User actUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(rol -> Role.builder().name(ERole.valueOf(rol))
                        .build()).collect(Collectors.toSet()))
                .build();
        when(roleRepository.findByName(ERole.DOCTOR)).thenReturn(Optional.of(Role.builder().name(ERole.DOCTOR).build()));
        when(userRepository.save(actUser)).thenReturn(actUser);
        Assertions.assertEquals(userService.create(user,role), user);
    }

    @Test
    void update() {

        String role = "SECRETARY";
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .roles(Set.of(role))
                .build();
        User actUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(rol -> Role.builder().name(ERole.valueOf(rol)).build()).collect(Collectors.toSet()))
                .build();
        when(roleRepository.findByName(ERole.SECRETARY)).thenReturn(Optional.of(Role.builder().name(ERole.SECRETARY).build()));
        when(userRepository.save(actUser)).thenReturn(actUser);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(actUser));
        Assertions.assertEquals(userService.update(user), user);
    }
}