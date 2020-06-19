<%@page import="com.study.model.news.News"%>
<%@page import="com.study.model.news.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! NewsDAO newsDAO = new NewsDAO();%>
<%
	request.setCharacterEncoding("UTF-8");
	String title = request.getParameter("title");
	int news_id = Integer.parseInt(request.getParameter("news_id"));
	
	News news = new News();
	
	news.setTitle(title);
	news.setNews_id(news_id);
	
	int result = newsDAO.delete(news);
	
	if (result != 0) {
		out.print("<script>");
		out.print("alert('삭제성공');");		
		out.print("location.href='/comments/list.jsp';");		
		out.print("</script>");
	}
	else {
		out.print("<script>");
		out.print("alert('삭제실패');");		
		out.print("history.back();");		
		out.print("</script>");
	}
%>