package xyz.ibudai.translation.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import xyz.ibudai.translation.web.entity.Comments;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ibudai.translation.web.service.CommentsService;

import java.util.List;
import java.util.Locale;

/**
 * (TbComments)表控制层
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsResource {

    private final CommentsService commentsService;

    @GetMapping("list")
    public ResponseEntity<List<Comments>> list(@RequestParam("language") String language) {
        Locale locale = Locale.forLanguageTag(language);
        LocaleContextHolder.setLocale(locale);

        return ResponseEntity.ok(commentsService.list());
    }

    @GetMapping("{id}")
    public ResponseEntity<Comments> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentsService.queryById(id));
    }
}

