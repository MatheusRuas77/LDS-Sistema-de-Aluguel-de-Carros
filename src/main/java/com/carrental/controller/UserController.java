package com.carrental.controller;

import com.carrental.model.User;
import com.carrental.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Post
    public HttpResponse<User> create(@Body @Valid User user) {
        User savedUser = repository.save(user);
        return HttpResponse.created(savedUser);
    }

    @Get
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public HttpResponse<User> findById(Long id) {
        Optional<User> user = repository.findById(id);

        return user
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Put("/{id}")
    public HttpResponse<User> update(Long id, @Body @Valid User updatedUser) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setRg(updatedUser.getRg());
                    user.setCpf(updatedUser.getCpf());
                    user.setProfession(updatedUser.getProfession());
                    user.setPassword(updatedUser.getPassword());
                    user.setAccessLevel(updatedUser.getAccessLevel());

                    repository.update(user);
                    return HttpResponse.ok(user);
                })
                .orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        if (!repository.existsById(id)) {
            return HttpResponse.notFound();
        }

        repository.deleteById(id);
        return HttpResponse.noContent();
    }
}
