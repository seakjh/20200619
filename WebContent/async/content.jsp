<%@page import="com.study.model.hotplace.HotPlace"%>
<%@page import="com.study.model.hotplace.HotPlaceDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!HotPlaceDAO hotPlaceDAO = new HotPlaceDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	String hotplace_id = request.getParameter("hotplace_id");
	System.out.println(hotplace_id);
	HotPlace hotPlace = hotPlaceDAO.select(Integer.parseInt(hotplace_id));
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("{");

		sb.append("\"storeList\":");
	
			sb.append("{");
				sb.append("\"number\":\""+hotPlace.getHotplace_id()+"\",");
				sb.append("\"name\":\""+hotPlace.getName()+"\",");
				sb.append("\"tel\":\""+hotPlace.getTel()+"\",");
				sb.append("\"addr\":\""+hotPlace.getAddr()+"\",");
				sb.append("\"food\":\""+hotPlace.getFood()+"\"");
			sb.append("}");

	sb.append("}");
	
	out.print(sb.toString());
%>