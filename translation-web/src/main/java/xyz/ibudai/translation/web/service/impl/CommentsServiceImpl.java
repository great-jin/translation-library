package xyz.ibudai.translation.web.service.impl;

import lombok.RequiredArgsConstructor;
import xyz.ibudai.translation.web.entity.Comments;
import xyz.ibudai.translation.web.dao.CommentsDao;
import xyz.ibudai.translation.web.service.CommentsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TbComments)表服务实现类
 *
 * @author makejava
 * @since 2025-08-03 10:26:24
 */
@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsDao commentsDao;


    @Override
    public List<Comments> list() {
        return commentsDao.list();
    }

    @Override
    public Comments queryById(Integer id) {
        return commentsDao.queryById(id);
    }
}
