package com.sonpen.board.mapper;

import org.springframework.stereotype.Repository;

/**
 * Created by 1109806 on 2019-03-14.
 */
@Repository("com.sonpen.board.mapper.BoardMapper")
public interface BoardMapper {

    int boardCount() throws Exception;
}
