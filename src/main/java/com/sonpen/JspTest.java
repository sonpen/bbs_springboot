package com.sonpen;

import com.sonpen.board.mapper.BoardMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by 1109806 on 2019-03-13.
 */
@Controller
public class JspTest {

    @Resource(name="com.sonpen.board.mapper.BoardMapper")
    BoardMapper boardMapper;

    @RequestMapping("/test")
    private String jspTest() throws  Exception{
        System.out.println("SONIKJU:" + boardMapper.boardCount());
        return "test";
    }
}
