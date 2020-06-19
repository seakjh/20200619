<%@page import="com.study.model.news.Comments"%>
<%@page import="java.util.List"%>
<%@page import="com.study.model.news.CommentsDAO"%>
<%@page import="com.study.model.news.News"%>
<%@page import="com.study.model.news.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	NewsDAO newsDAO = new NewsDAO();
	CommentsDAO commentsDAO = new CommentsDAO();
%>
<%
	//news_id 파라미터를 넘겨받아 상세보기 메서드 호출!!
	String news_id = request.getParameter("news_id");
	News news = newsDAO.select(Integer.parseInt(news_id));
	List<Comments> commentslist = commentsDAO.selectAll(Integer.parseInt(news_id));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>뉴스 글 등록</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

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
	margin-top: 5px;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

#commentsArea {
	background-color: #f5f5f5;
}
#commentsArea input[name='msg'] {
	width: 70%;
	float: left;
}
#commentsArea input[name='cwriter'] {
	width: 20%;
	float: left;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//cdn.ckeditor.com/4.14.1/standard/ckeditor.js"></script>
<script type="text/javascript">
$(function() {
	CKEDITOR.replace('content');
	
	//수정
	$($("input[type='button']")[0]).click(function() {
		$($("form")[0]).attr({
			"action":"/comments/edit.jsp", 
			"method":"post"
		})
		$($("form")[0]).submit();
	});
	
	//목록버튼
	$($("input[type='button']")[1]).click(function() {
		location.href="/comments/list.jsp";
	});

	//삭제
	$($("input[type='button']")[2]).click(function() {
		$($("form")[0]).attr({
			"action":"/comments/delete.jsp",
			"method":"post"
		})
		$($("form")[0]).submit();
	});
	
	//덧글 등록버튼
	$($("input[type='button']")[3]).click(function() {
		//서버에 덧글 등록 요청
		$($("form")[1]).attr({
			"method":"post",
			"action":"/comments/cmRegist.jsp"
		});
		$($("form")[1]).submit();
	});
});
</script>
</head>
<body>

	<h3>상세보기</h3>

	<div class="container">
		<form>
			<input type="hidden" name="news_id" value="<%=news.getNews_id()%>">
			<input type="text" id="fname"	name="title" value="<%=news.getTitle()%>"> 
			<input type="text" id="lname"	name="writer" value="<%=news.getWriter()%>">
			<textarea id="content" name="content" style="height: 200px"><%=news.getContent()%></textarea>
			<input type="button" value="수정">
			<input type="button" value="목록">
			<input type="button" value="삭제">
		</form>
	</div>
	
	<div id="commentsArea">
		<div>
			<form>
				<input type="hidden" name="news_id" value="<%=news.getNews_id()%>">
				<input type="text" name="cwriter" placeholder="작성자명..">
				<input type="text" name="msg" placeholder="덧글 메세지..">
				<input type="button" value="덧글 등록">
			</form>
		</div>
		<div>
			<table width="100%" border="1px">
				<%for(int i=0; i<commentslist.size(); i++) {%>
				<%Comments comments = commentslist.get(i); %>
				<tr>
					<td width="20%"><%=comments.getCwriter() %></td>
					<td width="60%"><%=comments.getMsg() %></td>
					<td width="20%"><%=comments.getCregdate() %></td>
				</tr>
				<%} %>
			</table>
		</div>
	</div>

</body>
</html>
