package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.mapper.CommentaryMapper;
import by.itra.pikachy.api.repository.CommentaryRepository;
import by.itra.pikachy.api.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentaryService {
    private final CommentaryRepository commentaryRepository;
    private final CommentaryMapper commentaryMapper;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentaryService(CommentaryRepository commentaryRepository,
                             CommentaryMapper commentaryMapper,
                             UserService userService,
                             PostService postService) {
        this.commentaryRepository = commentaryRepository;
        this.commentaryMapper = commentaryMapper;
        this.userService = userService;
        this.postService = postService;
    }

    public CommentaryDto create(CommentaryDto commentaryDto) {
        Commentary commentary = commentaryMapper.toEntity(commentaryDto);
        commentary.setCreated(GetDate.getLocalDate());
        commentary.setUser(userService.getAuthenticatedUser(SecurityContextHolder.getContext()));
        Post post = postService.getById(commentary.getPost().getId());
        post.getCommentaries().add(commentary);
        return commentaryMapper.toDto(commentaryRepository.save(commentary));
    }

    public Page<CommentaryDto> getByPostId(int postId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, sort);
        return commentaryRepository.findByPostId(postId, pageable).map(commentaryMapper::toDto);
    }

    public void delete(int id) {
        commentaryRepository.deleteById(id);
    }
}