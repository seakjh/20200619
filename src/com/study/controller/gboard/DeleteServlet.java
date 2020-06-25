package com.study.controller.gboard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.model.gboard.GBoard;
import com.study.model.gboard.GBoardDAO;

public class DeleteServlet extends HttpServlet {
	GBoardDAO gBoardDAO = new GBoardDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		
		int gboard_id = 0;
		System.out.println(request.getParameter("gboard_id"));
		System.out.println(request.getParameter("title"));
		gboard_id = Integer.parseInt(request.getParameter("gboard_id"));
		
		GBoard gBoard = new GBoard();
		gBoard.setGboard_id(gboard_id);
		
		int result = gBoardDAO.delete(gBoard);
		
		if (result != 0) {
			out.print("<script>");
			out.print("alert('삭제성공');");		
			out.print("location.href='/gallery/list.jsp';");		
			out.print("</script>");
		}
		else {
			out.print("<script>");
			out.print("alert('삭제실패');");		
			out.print("history.back();");		
			out.print("</script>");
		}
	}
}
