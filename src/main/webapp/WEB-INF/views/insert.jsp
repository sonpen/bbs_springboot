<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert Form</title>
</head>
<body>
<h2> 게시글 작성 </h2>

<div class="container">
    <!-- 파일 업로드를 할 경우 http 요청시 헤더의 Content-Type 을 multipart/form-data 이어야 하기 때문에 form 의 enctype 을 추가 -->
    <form action="/insertProc" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="subject">제목</label>
            <input type="text" class="form-control" id="subject" name="subject" placeholder="제목을 입력하세요.">
        </div>
        <div class="form-group">
            <label for="writer">작성자</label>
            <input type="text" class="form-control" id="writer" name="writer" placeholder="내용을 입력하세요.">
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea class="form-control" id="content" name="content" rows="3"></textarea>
        </div>

        <input type="file" name="files">

        <button type="submit" class="btn btn-primary">작성</button>
    </form>
</div>
<%@ include file="bootstrap.jsp" %>
</body>
</html>
