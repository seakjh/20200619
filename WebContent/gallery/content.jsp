<%@page import="com.study.model.gboard.GBoard"%>
<%@page import="com.study.model.gboard.GBoardDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! GBoardDAO gBoardDAO = new GBoardDAO(); %>
<%
	String gboard_id =request.getParameter("gboard_id");
	GBoard gBoard = gBoardDAO.select(Integer.parseInt(gboard_id));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//cdn.ckeditor.com/4.14.1/standard/ckeditor.js"></script>
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}

input[type=button] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=button]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}
</style>
<script>
$(function() {
	CKEDITOR.replace( 'content' );
	
	$($("input[type='button']")[0]).click(function() {
		location.href="/gallery/list.jsp";
	});

	$($("input[type='button']")[1]).click(function() {
		$("form").attr({
			"method":"post",
			"enctype":"multipart/form-data",
			"action":"/edit"
		});
		$("form").submit();
	});
	
	$($("input[type='button']")[2]).click(function() {
		$("form").attr({
			"method":"post",
			"action":"/delete"
		});
		$("form").submit();
	});
});

//파일을 포함한 대량의 데이터를 웹서버 요청시 가져가려면?
//http의 전송박식 중 post를 이용해야한다. 전송하는 데이터중 파일이 포함되어 있을경우(바이너리파일)
//form의 속성에 반드시! multipart/form-data 지정되어 있어야함
</script>
</head>
<body>

<h3>상세보기</h3>

<div class="container">
  <form>
  	<input type="hidden" name="gboard_id" value="<%=gBoard.getGboard_id() %>">
    <input type="text" name="title" placeholder="제목" value="<%=gBoard.getTitle() %>">
    <input type="text" name="writer" placeholder="작성자" value="<%=gBoard.getWriter() %>">
    <input type="file" name="imgFile">
    <textarea id="content" name="content" placeholder="Write something.." style="height:200px">
    	<%=gBoard.getContent()%>
    </textarea>
    <input type="button" value="목록">
    <input type="button" value="수정">
    <input type="button" value="삭제">
    <img alt="" src="/data/<%=gBoard.getFilename()%>" name="filename">
  </form>
</div>

</body>
</html>