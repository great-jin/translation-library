package xyz.ibudai.translation.web.rest;

import lombok.RequiredArgsConstructor;
import xyz.ibudai.translation.web.entity.Comments;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.ibudai.translation.web.service.CommentsService;

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


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Comments> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentsService.queryById(id));
    }
}

