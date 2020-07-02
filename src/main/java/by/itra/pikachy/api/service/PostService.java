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

import javax.transaction.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public PostService(PostRepository postRepository,
                       PostMapper postMapper,
                       UserService userService,
                       GenreService genreService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userService = userService;
        this.genreService = genreService;
    }

    @Transactional
    public PostDto create(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        preparePostFields(post);
        return postMapper.toDto(postRepository.save(post));
    }

    public PostDto update(PostDto postDto) {
        Post post = postMapper.toEntity(postDto);
        post.setUpdated(GetDate.getLocalDate());
        return postMapper.toDto(postRepository.save(post));
    }

    public Post getById(int id) {
        return postRepository.getOne(id);
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

    private void preparePostFields(Post post) {
        post.setGenre(genreService.findByGenreName(post.getGenre().getGenreName()));
        post.setAuthor(userService.getAuthenticatedUser(SecurityContextHolder.getContext()));
        post.setMark(0);
        post.setCreated(GetDate.getLocalDate());
        post.getSections().forEach(s -> s.setPost(post));
    }
}