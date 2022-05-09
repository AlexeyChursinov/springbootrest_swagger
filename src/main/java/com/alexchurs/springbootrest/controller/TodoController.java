package com.alexchurs.springbootrest.controller;

import com.alexchurs.springbootrest.entity.TodoEntity;
import com.alexchurs.springbootrest.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Этот Controller отвечает за запросы связанные с задачами
 */

@RestController
@RequestMapping("/todos")
@Tag(name = "Задачи", description = "Контроллер задач для пользователей. Один пользователь может содержить несколько задач")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * Метод для создания задачи
     *
     * @param todo   передаем TodoEntity в теле запроса
     * @param userId id пользователя будем передавать query параметром
     */
    @PostMapping
    @Operation(summary = "Метод для создания новой задачи. Задача привязывается к пользователю по id")
    public ResponseEntity createTodo(@RequestBody TodoEntity todo,
                                     @Parameter(description = "id пользователя", required = true)
                                     @RequestParam Long userId) {

        try {
            return ResponseEntity.ok(todoService.createTodo(todo, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }

    }

    /**
     * Метод для обновления. put запрос
     *
     * @param id передаем параметром id задачи, чтобы её обновить
     *           Пользователь будет отправлять запрос, в задаче будет менятся поле completed
     */
    @PutMapping
    @Operation(summary = "Метод для обновления задачи. В задаче обновляется признак того, что задача выполнена(complete)")
    public ResponseEntity completeTodo(@Parameter(description = "id задачи", required = true)
                                       @RequestParam Long id) {

        try {
            return ResponseEntity.ok(todoService.complete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }

    }

    /**
     * Метод для удаления задачи
     * @param id - id задачи
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Метод для удаления задачи.")
    public ResponseEntity deleteTodo(@Parameter(description = "id задачи", required = true)
                                     @PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(todoService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

}
