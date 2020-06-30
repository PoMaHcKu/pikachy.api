package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("{id}")
    public UserDto getById(@PathVariable String id) {
        return userService.getUserById(Integer.parseInt(id));
    }

    @GetMapping
    public Page<User> getUsersPage(@RequestParam int pageNumber, @RequestParam int size) {
        return userService.getPage(pageNumber, size);
    }
    
    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
