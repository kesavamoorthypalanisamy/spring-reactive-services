package com.hippo.userservice.service;

import org.springframework.stereotype.Service;
import com.hippo.userservice.models.UserDto;
import com.hippo.userservice.repository.UserRepository;
import com.hippo.userservice.util.EntityMapperUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll().map(EntityMapperUtil::toUserDto);
    }

    public Mono<UserDto> getUserById(Integer userId) {
        return userRepository.findById(userId).map(EntityMapperUtil::toUserDto);
    }

    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono.map(EntityMapperUtil::toUser).flatMap(userRepository::save)
                .map(EntityMapperUtil::toUserDto);
    }

    public Mono<UserDto> updateUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .flatMap(u -> userRepository.findById(u.getId())
                        .map(user -> EntityMapperUtil.toUser(u)).flatMap(userRepository::save))
                .map(EntityMapperUtil::toUserDto);
    }

    //If FE passes uuserId in path variable then this service can be utilized
    public Mono<UserDto> updateUser(Integer userId, Mono<UserDto> userDtoMono) {
        return userRepository.findById(userId).flatMap(
                user -> userDtoMono.map(EntityMapperUtil::toUser).flatMap(userRepository::save))
                .map(EntityMapperUtil::toUserDto);

    }
    
    public Mono<Void> removeuser(Integer userId) {
        // return userRepository.findById(userId).flatMap(u -> userRepository.deleteById(u.getId()));
        return userRepository.deleteById(userId);
    }
    
}
