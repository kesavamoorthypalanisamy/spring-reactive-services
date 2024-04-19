package com.hippo.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.userservice.models.UserDto;
import com.hippo.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("all")
    public Flux<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable("id") Integer userId) {
        return userService.getUserById(userId).map(u -> ResponseEntity.ok().body(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Mono<UserDto> createUser(@RequestBody Mono<UserDto> user) {
        return userService.createUser(user);
    }
    
    @PutMapping
    public Mono<ResponseEntity<UserDto>> updateUser(@RequestBody Mono<UserDto> user) {
        return userService.updateUser(user).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable("id") Integer userId,
            @RequestBody Mono<UserDto> user) {
        return userService.updateUser(userId, user).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> removeUser(@PathVariable("id") Integer userId) {
        return userService.removeuser(userId);
    }
    
}
