package com.sonpen.board.service;

import com.sonpen.board.domain.CommentVO;
import com.sonpen.board.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 1109806 on 2019-03-28.
 */
@Service("com.sonpen.board.service.CommentService")
public class CommentService {

    @Resource(name="com.sonpen.board.mapper.CommentMapper")
    CommentMapper commentMapper;

    public List<CommentVO> commentListSerivce(int bno) throws Exception{
        return commentMapper.commentList(bno);
    }

    public int commentInsertService(CommentVO commentVO) throws Exception{
        return commentMapper.commentInsert(commentVO);
    }

    public int commentUpdateService(CommentVO commentVO) throws Exception{
        return commentMapper.commentUpdate(commentVO);
    }

    public int commentDeleteService(int cno) throws Exception{
        return commentMapper.commentDelete(cno);
    }

}
