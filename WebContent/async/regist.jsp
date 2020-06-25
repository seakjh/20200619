<%@page import="com.study.model.hotplace.HotPlace"%>
<%@page import="com.study.model.hotplace.HotPlaceDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! HotPlaceDAO hotPlaceDAO = new HotPlaceDAO(); %>
<%
	//클라이언트가 전송한 파라미터 값들을 이용하여 오라클에 넣기
	//서버측에서는 클라이언트의 통신방식이 동기 / 비동기 관심없다
	request.setCharacterEncoding("UTF-8");

	String name = request.getParameter("name");
	String tel = request.getParameter("tel");
	String addr = request.getParameter("addr");
	String food = request.getParameter("food");
	
	System.out.println(name);
	System.out.println(tel);
	System.out.println(addr);
	System.out.println(food);
	
	HotPlace hotPlace = new HotPlace();
	
	hotPlace.setName(name);
	hotPlace.setTel(tel);
	hotPlace.setAddr(addr);
	hotPlace.setFood(food);
	
	int result = hotPlaceDAO.insert(hotPlace);
	
	if (result != 0) {
		System.out.println("등록성공");
	}
	else {
		System.out.println("등록실패");
	}
%>