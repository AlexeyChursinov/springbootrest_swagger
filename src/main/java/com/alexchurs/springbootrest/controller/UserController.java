package com.alexchurs.springbootrest.controller;

import com.alexchurs.springbootrest.entity.UserEntity;
import com.alexchurs.springbootrest.exception.UserAlreadyExistException;
import com.alexchurs.springbootrest.exception.UserNotFoundException;
import com.alexchurs.springbootrest.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Этот Controller отвечает за запросы связанные с пользователем
 */

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "Контроллер пользователей")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Метод для регистрации пользователей
     * Post запрос. Данные будут передаваться в теле запроса.
     *
     * @param user - объект пользователя
     * @RequestBody - тело запроса
     */
    @PostMapping(produces = "text/plain;charset=UTF-8")
    @Operation(summary = "Метод для создания нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно сохранен")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity registration(@RequestBody UserEntity user) {
        try {
            userService.registration(user);
            return ResponseEntity.ok().body("Пользователь успешно сохранен");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //ошибка, если пользователь уже создан (пользователь должен быть уникальным)
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    /**
     * Метод для получения пользователя по id
     * Будем принимать query параметр в get запросе
     *
     * @param id - id пользователя
     */
    @GetMapping
    @Operation(summary = "Метод для получения пользователя")
    public ResponseEntity getOneUser(@Parameter(description = "id пользователя", required = true)
                                     @RequestParam Long id) {
        try {
            return ResponseEntity.ok().body(userService.getOne(id)); //возвращаем в ответ id пользователя
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //ошибка, если пользователь не был найден
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    /**
     * Метод для получнеия всех пользователей
     */
    @GetMapping("/all")
    @Operation(summary = "Метод для получения всех пользователей")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    /**
     * Метод для удаления пользователя
     * id передаем, как часть пути запроса, для этого используем @PathVariable
     *
     * @param id - id пользователя
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Метод для удаления пользователя")
    public ResponseEntity deleteUser(@Parameter(description = "id пользователя", required = true)
                                     @PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

}
