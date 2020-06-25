<%@page import="com.study.model.gboard.GBoard"%>
<%@page import="java.util.List"%>
<%@page import="com.study.model.gboard.GBoardDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! GBoardDAO gBoardDAO = new GBoardDAO(); %>
<% 
	List<GBoard> list = gBoardDAO.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}

th, td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
<script type="text/javascript">
</script>
</head>
<body>
	<h2>갤러리 목록</h2>
	<table>
		<tr>
			<th>No</th>
			<th>이미지</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%int total = list.size(); %>
		<%for(int i=0; i<list.size(); i++) { %>
		<%GBoard gBoard = list.get(i); %>
		<tr>
			<td><%=total-- %></td>
			<td><img src="/data/<%=gBoard.getFilename() %>" width="50px"></td>
			<td><a href="/gallery/content.jsp?gboard_id=<%=gBoard.getGboard_id()%>"><%=gBoard.getTitle() %></a></td>
			<td><%=gBoard.getWriter() %></td>
			<td><%=gBoard.getRegdate().substring(0, 10) %></td>
			<td><%=gBoard.getHit() %></td>
		</tr>
		<%} %>
		<tr>
			<td colspan="6">
				<button onclick="location.href='/gallery/registForm.jsp';">등록</button>
			</td>
		</tr>
	</table>
</body>
</html>