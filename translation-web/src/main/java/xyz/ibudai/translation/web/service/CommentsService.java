package xyz.ibudai.translation.web.service;

import xyz.ibudai.translation.web.entity.Comments;

import java.util.List;

/**
 * (TbComments)表服务接口
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
public interface CommentsService {

    List<Comments> list();

    Comments queryById(Integer id);

}
