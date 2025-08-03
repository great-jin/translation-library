package xyz.ibudai.translation.web.service;

import xyz.ibudai.translation.web.entity.Comments;

/**
 * (TbComments)表服务接口
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
public interface CommentsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comments queryById(Integer id);

}
