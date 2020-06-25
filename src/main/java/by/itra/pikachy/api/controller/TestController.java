package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.mapper.PostMapper;
import by.itra.pikachy.api.repository.PostRepository;
import by.itra.pikachy.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class TestController {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserDetailsImpl userDetails;

    @Autowired
    public TestController(PostRepository postRepository, PostMapper postMapper, UserDetailsImpl userDetails) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userDetails = userDetails;
    }

    @GetMapping
    public List<PostDto> getPosts() {
        return postRepository.findAll().stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        postDto.setId(userDetails.getId());
        return postMapper.toDto(postRepository.save(postMapper.toEntity(postDto)));
    }
}