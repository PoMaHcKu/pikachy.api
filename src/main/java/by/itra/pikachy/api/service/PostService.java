package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Tag;
import by.itra.pikachy.api.mapper.PostMapper;
import by.itra.pikachy.api.repository.PostRepository;
import by.itra.pikachy.api.util.GetDate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final GenreService genreService;
    private final TagService tagService;

    @Transactional
    public PostDto create(PostDto postDto, Principal user) {
        Post post = postMapper.toEntity(postDto);
        post.setAuthor(userService.getAuthenticatedUser(user));
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
        return postRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, sort))
                .map(postMapper::toDto);
    }

    public PostDto getPost(int id) {
        return postMapper.toDto(postRepository.getOne(id));
    }

    public Page<PostDto> getByGenre(String genre, int page, int size, String sort) {
        Genre genreFromDb = genreService.findByGenreName(genre);
        return postRepository
                .findByGenre(
                        PageRequest.of(page, size, Sort.Direction.DESC, sort),
                        genreFromDb)
                .map(postMapper::toDto);
    }

    public Page<PostDto> getByTags(String tag, int page, int size, String sort) {
        List<Tag> realTags = new ArrayList<Tag>() {{
            add(tagService.getByTagName(tag));
        }};
        return postRepository
                .findByTagsIn(PageRequest.of(page, size, Sort.Direction.DESC, sort), realTags)
                .map(postMapper::toDto);
    }

    public void delete(int id) {
        postRepository.deleteById(id);
    }

    private void preparePostFields(Post post) {
        post.setGenre(genreService.findByGenreName(post.getGenre().getGenreName()));
        post.setMark(0);
        post.setCreated(GetDate.getLocalDate());
        post.getSections().forEach(s -> s.setPost(post));
    }
}