package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{id}")
    public PostDto getPost(@PathVariable int id) {
        return postService.getPost(id);
    }

    @GetMapping
    public Page<PostDto> getPosts(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "10") int size,
                                  @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return postService.getPosts(page, size, sortBy);
    }

    @PostMapping
    public PostDto create(@AuthenticationPrincipal User user, @RequestBody @Valid PostDto postDto) {
       return postService.create(postDto, user);
    }

    @PostMapping
    public PostDto update(@Valid @RequestBody PostDto postDto) {
        return postService.update(postDto);
    }

}