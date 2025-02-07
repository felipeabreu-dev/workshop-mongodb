package io.github.abreufelipedev.resources;

import io.github.abreufelipedev.domain.User;
import io.github.abreufelipedev.dto.UserDTO;
import io.github.abreufelipedev.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();

        List<UserDTO> users = list.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        User user = userService.findById(id);

        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO){
        User user = userService.fromDTO(userDTO);
        user = userService.insert(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
