<%@page import="com.study.model.store.Store"%>
<%@page import="com.study.model.store.StoreDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! StoreDAO storeDAO = new StoreDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	String store_id = request.getParameter("store_id");
	System.out.println(store_id);
	Store store = storeDAO.select(Integer.parseInt(store_id));
	
	StringBuilder sb = new StringBuilder();
	
	sb.append("{");
	
		sb.append("\"store_id\":"+store.getStore_id()+",");
		sb.append("\"name\":\""+store.getName()+"\",");
		sb.append("\"addr\":\""+store.getAddr()+"\",");
		sb.append("\"lati\":"+store.getLati()+",");
		sb.append("\"longi\":"+store.getLongi()+",");
		sb.append("\"icons_id\":"+store.getIcons().getIcons_id()+",");
		sb.append("\"filename\":\""+store.getIcons().getFilename()+"\",");
		sb.append("\"memo\":\""+store.getMemo()+"\"");

	sb.append("}");
	
	out.print(sb.toString());
%>