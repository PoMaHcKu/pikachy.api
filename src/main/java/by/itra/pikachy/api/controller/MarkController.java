package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.MarkService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rate")
@AllArgsConstructor
public class MarkController {
    private final MarkService markService;

    @GetMapping
    public void ratePost(@RequestParam int mark,
                         @RequestParam int postId,
                         @AuthenticationPrincipal Principal principal) {
        markService.rate(mark, postId, principal);
    }
}