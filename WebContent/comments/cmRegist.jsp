<%@page import="com.study.model.news.News"%>
<%@page import="com.study.model.news.Comments"%>
<%@page import="com.study.model.news.CommentsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! CommentsDAO commentsDAO = new CommentsDAO();%>
<%
	//덧글 파라미터를 받아, DAO에게 등록 시키자!
	request.setCharacterEncoding("UTF-8");

	String news_id = request.getParameter("news_id");
	String msg = request.getParameter("msg");
	String cwriter = request.getParameter("cwriter");
	
	//VO채우기
	News news = new News();
	news.setNews_id(Integer.parseInt(news_id));
	
	Comments comments = new Comments();
	comments.setMsg(msg);
	comments.setCwriter(cwriter);
	
	//VO간 합체
	comments.setNews(news);
	
	int result = commentsDAO.insert(comments);
	
	if (result != 0) {
		out.print("<script>");
		//out.print("alert('덧글 등록성공');");		
		out.print("location.href='/comments/content.jsp?news_id="+news_id+"';");		
		out.print("</script>");
	}
	else {
		out.print("<script>");
		out.print("alert('덧글 등록실패');");		
		out.print("history.back();");		
		out.print("</script>");
	}
%>