package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Mark;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class MarkService {
    private final MarkRepository markRepository;
    private final AuthenticationService authenticationService;
    private final PostService postService;

    public void rate(int mark, int postId, Principal principal) {
        Post post = postService.getById(postId);
        User user = authenticationService.getAuthenticatedUser(principal);
        Mark m = markRepository.findByPostAndUser(post, user);
        if (m == null) {
            m = new Mark();
            m.setPost(post);
            m.setUser(user);
        }
        m.setMark(mark);
        markRepository.save(m);
    }
}