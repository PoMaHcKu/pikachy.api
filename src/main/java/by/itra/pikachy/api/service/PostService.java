package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.mapper.PostMapper;
import by.itra.pikachy.api.repository.PostRepository;
import by.itra.pikachy.api.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, PostMapper postMapper, UserService userService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
    }

    public PostDto create(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        post.setAuthor(userService.getAuthenticatedUser(SecurityContextHolder.getContext()));
        post.setMark(0);
        post.setCreated(GetDate.getLocalDate());
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDto update(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        post.setUpdated(GetDate.getLocalDate());
        return postMapper.toDto(postRepository.save(post));
    }

    public Page<PostDto> getPosts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    public PostDto getPost(int id) {
        return postMapper.toDto(postRepository.getOne(id));
    }

    public void delete(int id) {
        postRepository.deleteById(id);
    }
}