package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public UserDto getById(@PathVariable String id) {
        return userService.getUserById(Integer.parseInt(id));
    }

    @GetMapping
    public Page<UserDto> getUsersPage(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return userService.getPage(pageNumber, size);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        userService.deleteUser(id);
    }
}