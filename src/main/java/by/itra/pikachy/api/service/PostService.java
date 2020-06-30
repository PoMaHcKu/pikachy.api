package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mapper.PostMapper;
import by.itra.pikachy.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostDto create(PostDto postDto, User user) {
        Post post = postMapper.toEntity(postDto);
        post.setUser(user);
        post.setMark(0);
        //add setCreated after adding created field in post;
        return postMapper.toDto(postRepository.save(post));
    }

    public void update(PostDto postDto) {
        //add setUpdated after adding updatedField in post;
        postRepository.save(postMapper.toEntity(postDto));
    }

    public Page<PostDto> getPosts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    public void delete(int id) {
        postRepository.deleteById(id);
    }
}