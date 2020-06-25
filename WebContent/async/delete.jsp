<%@page import="com.study.model.hotplace.HotPlace"%>
<%@page import="com.study.model.hotplace.HotPlaceDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! HotPlaceDAO hotPlaceDAO = new HotPlaceDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	int hotplace_id = Integer.parseInt(request.getParameter("hotplace_id"));
	
	HotPlace hotPlace = new HotPlace();
	
	hotPlace.setHotplace_id(hotplace_id);
	
	int result =hotPlaceDAO.delete(hotPlace);
	
	if (result != 0) {
		System.out.println("등록성공");
	}
	else {
		System.out.println("등록실패");
	}
%>