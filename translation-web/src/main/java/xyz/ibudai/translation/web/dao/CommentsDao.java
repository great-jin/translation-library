package xyz.ibudai.translation.web.dao;

import xyz.ibudai.translation.web.entity.Comments;

import java.util.List;

/**
 * (TbComments)表数据库访问层
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
public interface CommentsDao {

    List<Comments> list();

    Comments queryById(Integer id);

}

