package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.mapper.CommentaryMapper;
import by.itra.pikachy.api.repository.CommentaryRepository;
import by.itra.pikachy.api.util.GetDate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;

@Service
@AllArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepository;
    private final CommentaryMapper commentaryMapper;
    private final AuthenticationService authenticationService;
    private final PostService postService;

    @Transactional
    public CommentaryDto create(CommentaryDto commentaryDto, Principal user) {
        Commentary commentary = commentaryMapper.toEntity(commentaryDto);
        commentary.setCreated(GetDate.getLocalDate());
        commentary.setUser(authenticationService.getAuthenticatedUser(user));
        Post post = postService.getById(commentary.getPost().getId());
        post.getCommentaries().add(commentary);
        commentary.setPost(post);
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