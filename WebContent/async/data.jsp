<%@page import="java.util.List"%>
<%@page import="com.study.model.hotplace.HotPlaceDAO"%>
<%@page import="com.study.model.hotplace.HotPlace"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! HotPlaceDAO hotPlaceDAO = new HotPlaceDAO(); %>
<%
List<HotPlace> storeList = hotPlaceDAO.selectAll();

StringBuilder sb = new StringBuilder();
sb.append("{");

sb.append("\"storeList\":[");

for (int i=0; i<storeList.size(); i++) {
	HotPlace hotPlace = storeList.get(i);
	sb.append("{");
	sb.append("\"number\":\""+hotPlace.getHotplace_id()+"\",");
	sb.append("\"name\":\""+hotPlace.getName()+"\",");
	sb.append("\"tel\":\""+hotPlace.getTel()+"\",");
	sb.append("\"addr\":\""+hotPlace.getAddr()+"\",");
	sb.append("\"food\":\""+hotPlace.getFood()+"\"");
	if (i >= storeList.size()-1) {
		sb.append("}");
	}
	else {
		sb.append("},");
	}
}

sb.append("]");

sb.append("}");

out.print(sb.toString());
%>