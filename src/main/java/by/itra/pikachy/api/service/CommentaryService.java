package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.mapper.CommentaryMapper;
import by.itra.pikachy.api.repository.CommentaryRepository;
import by.itra.pikachy.api.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaryService {
    private final CommentaryRepository commentaryRepository;
    private final CommentaryMapper commentaryMapper;
    private final UserService userService;

    @Autowired
    public CommentaryService(CommentaryRepository commentaryRepository,
                             CommentaryMapper commentaryMapper,
                             UserService userService) {
        this.commentaryRepository = commentaryRepository;
        this.commentaryMapper = commentaryMapper;
        this.userService = userService;
    }

    public CommentaryDto created(CommentaryDto commentaryDto) {
        Commentary commentary = commentaryMapper.toCommentary(commentaryDto);
        commentary.setCreated(GetDate.getLocalDate());
        commentary.setUser(userService.getAuthenticatedUser(SecurityContextHolder.getContext()));
        return commentaryMapper.fromCommentary(commentaryRepository.save(commentary));
    }

    public List<Commentary> getCommentariesByPostId(int postId) {
        return commentaryRepository.findByPostId(postId);
    }

    public void deleteCommentary(int id) {
        commentaryRepository.deleteById(id);
    }
}