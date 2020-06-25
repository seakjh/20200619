<%@page import="com.study.model.store.Store"%>
<%@page import="com.study.model.store.StoreDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! StoreDAO storeDAO = new StoreDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	int store_id = Integer.parseInt(request.getParameter("store_id"));
	
	//Store store = new Store();
	//store.setStore_id(store_id);
	
	int result = storeDAO.delete(store_id);
	
	System.out.println(result);
	out.print(result);
%>