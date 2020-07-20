package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.service.PostSearchService;
import by.itra.pikachy.api.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearch;

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

    @GetMapping("/genre")
    public Page<PostDto> getByGenre(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size,
                                    @RequestParam(required = false, defaultValue = "id") String sortBy,
                                    @RequestParam String genre) {
        return postService.getByGenre(genre, page, size, sortBy);
    }

    @GetMapping("/tags")
    public Page<PostDto> getByTags(@RequestParam String tag,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return postService.getByTags(tag, page, size, sortBy);
    }

    @GetMapping("/search/{text}")
    public List<PostDto> search(@PathVariable String text) {
        return postSearch.search(text);
    }

    @PostMapping
    public PostDto create(@RequestBody @Valid PostDto postDto, @AuthenticationPrincipal Principal user) {
        return postService.create(postDto, user);
    }

    @PutMapping
    public PostDto update(@RequestBody @Valid PostDto postDto) {
        return postService.update(postDto);
    }
}