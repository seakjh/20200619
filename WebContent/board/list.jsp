<%@ page contentType="text/html; charset=UTF-8"%>
<%
	//데이터를 나누어서 보여주는 기법을 페이징 처리라 하며,
	//이 처리에 필요한 로직!
	int currentPage = 1; //현재 페이지
	if (request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));		
	}
	int totalRecord = 1053; //총 레코드 수
	int pageSize = 10; //한페이지당 보여줄 레코드 수
	int totalPage = (int)Math.ceil((float)totalRecord / pageSize); // 총 페이지 수
	int blockSize = 10; //한블럭당 보여질 페이지 수

	//						7			   근접한작은수(=6)
//	int firstPage = currentPage-(currentPage-1)%blockSize; //강사님 공식 
	int firstPage = (((currentPage-1)/blockSize) * blockSize) + 1; //각 블럭의 시작페이지
//	int lastPage = firstPage + (blockSize-1); //강사님 공식
	int lastPage = firstPage + blockSize; //각 블럭의 마지막페이지
	int num = totalRecord - (currentPage-1) * pageSize; //페이지당 시작 번호!
%>
<%="totalPage : "+ totalPage + "<br>" %>
<%
	out.print("first : "+firstPage+"<br>");
	out.print("현재 "+currentPage+" 페이지임");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>빵</title>
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
a {
	text-decoration: none;
}
a:hover {
	text-decoration: underline;
}
.pageNum {
	font-weight: bold;
	color: #c00;
}
</style>
</head>
<body>

	<h2>Zebra Strid Tablepe</h2>

	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%for(int i=1; i<=pageSize; i++){ %>
		<tr>
			<%if(num<1) break; %>
			<th><%=num-- %></th>
			<th>폭8</th>
			<th>김근육</th>
			<th>1972-11-21</th>
			<th>8</th>
		</tr>
		<%} %>
		<tr>
			<td colspan="5" align="center" style="text-align: center;">
				<%if (firstPage > 1) { %>
				<a href="/board/list.jsp?currentPage=<%=firstPage-blockSize%>">◀</a> <!-- firstPage-1 -->
				<%} %>
				<%for (int i=firstPage; i<lastPage; i++) {%>	
					<%if (i > totalPage) break; %>			
					<a <%if (currentPage == i) { %> class="pageNum" <% } %> href="/board/list.jsp?currentPage=<%=i %>">[<%=i %>]</a>
				<%} %>
				
				<%if (lastPage < totalPage) { %>
				<a href="/board/list.jsp?currentPage=<%=firstPage+blockSize%>">▶</a> <!-- lastPage+1 -->
				<%} %>
			</td>
		</tr>
	</table>
</body>
</html>
