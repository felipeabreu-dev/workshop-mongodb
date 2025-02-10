package io.github.abreufelipedev.resources;

import io.github.abreufelipedev.domain.Post;
import io.github.abreufelipedev.domain.User;
import io.github.abreufelipedev.dto.UserDTO;
import io.github.abreufelipedev.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Users-API")
public class UserResource {

    @Autowired
    private UserService userService;

    @Operation(summary = "Find all users", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();

        List<UserDTO> users = list.stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Find user by ID", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        User user = userService.findById(id);

        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @Operation(summary = "Insert new user", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created"),
    })
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

    @Operation(summary = "Delete user by ID", method = "DELETE")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update user by ID", method = "PUT")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody UserDTO userDTO){
        User user = userService.fromDTO(userDTO);
        user.setId(id);
        user = userService.update(user);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find posts by user ID", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts found")
    })
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> findUserPosts(@PathVariable String id){
        User user = userService.findById(id);

        List<Post> posts = user.getPosts();

        return ResponseEntity.ok().body(posts);
    }
}
