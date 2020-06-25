<%@page import="com.study.model.store.Icons"%>
<%@page import="com.study.model.store.Store"%>
<%@page import="com.study.model.store.StoreDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! StoreDAO storeDAO = new StoreDAO(); %>
<%
	request.setCharacterEncoding("UTF-8");
	int store_id = Integer.parseInt(request.getParameter("store_id"));
	String name = request.getParameter("name");
	String addr = request.getParameter("addr");
	double lati = Double.parseDouble(request.getParameter("lati"));
	double longi = Double.parseDouble(request.getParameter("longi"));
	int icons_id = Integer.parseInt(request.getParameter("icons_id"));
	String memo = request.getParameter("memo");
	
	Store store = new Store();
	
	store.setStore_id(store_id);
	store.setName(name);
	store.setAddr(addr);
	store.setLati(lati);
	store.setLongi(longi);
	
	Icons icons = new Icons();
	icons.setIcons_id(icons_id);
	store.setIcons(icons);
	
	store.setMemo(memo);
	
	int result = storeDAO.edit(store);
	System.out.println(result); //개발자를 위한 디버깅
	out.print(result); //결과를 보내자 , 클라이언트를 위한 출력
%>