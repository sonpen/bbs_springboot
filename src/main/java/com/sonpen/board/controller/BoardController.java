package com.sonpen.board.controller;

import com.sonpen.board.domain.BoardVO;
import com.sonpen.board.domain.FileVO;
import com.sonpen.board.service.BoardService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by 1109806 on 2019-03-18.
 */
@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping("/list")
    private String boardList(Model model) throws Exception {
        model.addAttribute("list", boardService.boardListService());
        return "list"; // jsp
    }

    @RequestMapping("/detail/{bno}")
    private String boardDetail(@PathVariable int bno, Model model) throws Exception{
        model.addAttribute("detail", boardService.boardDetailService(bno));
        model.addAttribute("file", boardService.fileDetailService(bno));
        return "detail";
    }

    @RequestMapping("/insert") // 게시글 작성폼 호출
    private String boardInsertForm() {
        return "insert";
    }
    @RequestMapping("/insertProc")
    private String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
        BoardVO board = new BoardVO();
        FileVO fileVO = new FileVO();

        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setWriter(request.getParameter("writer"));

        if(files.isEmpty()) { // 업로드할 파일이 없을시
            boardService.boardInsertService(board);
        } else {
            String sourceFileName = files.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            File destinationFile;
            String destinationFileName;
            String fileUrl = "C:\\workspace\\IdeaProjects\\bbs_springboot\\src\\main\\webapp\\WEB-INF\\uploadFiles\\";

            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                destinationFile = new File(fileUrl, destinationFileName);
            } while (destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            files.transferTo(destinationFile);

            boardService.boardInsertService(board);     // 게시글 insert

            fileVO.setBno(board.getBno());
            fileVO.setFileName(destinationFileName);
            fileVO.setFileOriName(sourceFileName);
            fileVO.setFileUrl(fileUrl);

            boardService.fileInsertService(fileVO);     // 파일 insert
        }

        return "redirect:/list";
    }

    @RequestMapping("/update/{bno}") // 게시글 수정폼 호출
    private String boardUpdateForm(@PathVariable int bno, Model model) throws Exception{
        model.addAttribute("detail", boardService.boardDetailService(bno));
        return "update";
    }
    @RequestMapping("/updateProc")
    private String boardUpdateProc(HttpServletRequest request) throws Exception{

        BoardVO board = new BoardVO();
        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setBno(Integer.parseInt(request.getParameter("bno")));

        boardService.boardUpdateService(board);
        return "redirect:/detail/" + request.getParameter("bno");
    }

    @RequestMapping("/delete/{bno}")
    private String boardDelete(@PathVariable int bno) throws Exception {
        boardService.boardDeleteService(bno);
        return "redirect:/list";
    }

    @RequestMapping("/fileDown/{bno}")
    private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("UTF-8");
        FileVO fileVO = boardService.fileDetailService(bno);

        // 파일 업로드 된 경로
        try{
            String fileUrl = fileVO.getFileUrl();
            fileUrl += "/";
            String savePath = fileUrl;
            String fileName = fileVO.getFileName();

            // 실제 내보낼 파일명
            String oriFileName = fileVO.getFileOriName();
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";

            // 파일을 읽어 스트림에 담기
            try{
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch(FileNotFoundException fe) {
                skip = true;
            }

            client = request.getHeader("User-Agent");

            // 파일 다운로드 헤더 지정
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");

            if(!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                // IE 11이상
                } else if(client.indexOf("Trident") != -1 ) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" +file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int)file.length()];
                int leng = 0;
                while((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html; charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();;
            os.close();
        } catch(Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}
