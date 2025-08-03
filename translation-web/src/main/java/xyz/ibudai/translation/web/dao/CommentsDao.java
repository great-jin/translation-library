package xyz.ibudai.translation.web.dao;

import xyz.ibudai.translation.web.entity.Comments;

/**
 * (TbComments)表数据库访问层
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
public interface CommentsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comments queryById(Integer id);

}

