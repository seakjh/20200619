<%@page import="com.study.model.gboard.GBoard"%>
<%@page import="com.study.model.gboard.GBoardDAO"%>
<%@page import="java.io.File"%>
<%@page import="com.study.commons.file.FileManager"%>
<%@page import="java.io.IOException"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!GBoardDAO gBoardDAO = new GBoardDAO();%>
<%
	//파라미터들을 넘겨받아, 파일의 경우엔 서버에 저장하고,
	//텍스트 데이터의 경우엔 오라클 넣자!
	
	//application내장객체의 getRealPath()를 이용하면
	//현재의 웹사이트가 어떤 환경에서 실행도고 있는지에 상관없이
	//프로그래밍적으로 물리적하드경로를 반환해준다
	String saveDir = application.getRealPath("/data");
	int maxSize = 3*1024*1024; //계산하지 말자.. 유지보수
	String encoding = "utf-8";
	
	MultipartRequest multi = null;
	System.out.println(saveDir);
	//생성자에서 업로드가 발생!
	try {
		multi = new MultipartRequest(request, saveDir, maxSize, encoding);
		
		//파일명 교체
		String ori = multi.getOriginalFileName("imgFile");
		
		//새로운 이름생성
		String dest = FileManager.getFileRename(ori);
		
		//업로드된 바로 그 파일을 반환받음
		File file = multi.getFile("imgFile");
		File destFile = new File(saveDir+"/"+dest); //새로운 파일 생성
		
		if(file.renameTo(destFile)) { //파일명을 제대로 바꿧다면 true
			
			//oracle에 넣기
			GBoard gBoard = new GBoard();
			gBoard.setTitle(multi.getParameter("title"));
			gBoard.setWriter(multi.getParameter("writer"));
			gBoard.setContent(multi.getParameter("content"));
			gBoard.setFilename(dest);
			
			int result = gBoardDAO.insert(gBoard);
			if (result == 0) {
				//이미지를 처음부터 없었던것처럼 싹 지우자
				destFile.delete();
			}
			else {
				out.print("<script>");
				out.print("alert('업로드 성공');");
				out.print("location.href='/gallery/list.jsp';");
				out.print("</script>");				
			}
		}
		else {
			System.out.print("변경실패");
		}
		
	} catch (IOException e) {
		e.printStackTrace();
		out.print("<script>");
		out.print("alert('이미지 용량을 확인하세요');");
		out.print("history.back();");
		out.print("</script>");
	}
	
%>