<%@page import="com.study.model.hotplace.HotPlace"%>
<%@page import="com.study.model.hotplace.HotPlaceDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! HotPlaceDAO hotPlaceDAO = new HotPlaceDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	int hotplace_id = Integer.parseInt(request.getParameter("detail_hotplace_id"));
	String name = request.getParameter("name");
	String tel = request.getParameter("tel");
	String addr = request.getParameter("addr");
	String food = request.getParameter("food");
	
	HotPlace hotPlace = new HotPlace();
	
	hotPlace.setHotplace_id(hotplace_id);
	hotPlace.setName(name);
	hotPlace.setTel(tel);
	hotPlace.setAddr(addr);
	hotPlace.setFood(food);
	
	int result = hotPlaceDAO.edit(hotPlace);
	
	if (result != 0) {
		System.out.println("등록성공");
	}
	else {
		System.out.println("등록실패");
	}
%>