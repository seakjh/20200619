<%@page import="com.study.commons.file.FileManager"%>
<%@page import="java.io.IOException"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
%>
<%
	request.setCharacterEncoding("utf-8");
	//넘겨받은 파라미터 중 파일에 대한 저장처리!!(파일업로드)
	String saveDir = null;

	//웹서버상의 경로를 하드경로로 지정하지 말고, 
	//프로그래밍적으로 추출한 후 이용하자!
	//이유? 경로를 개발자가 결정지어 놓으면, 서버의 환경이 바뀔경우에 
	//에러가 발생할 수 있다.
	//jsp 내장객체중 웹 어플리케이션의 전역적 정보를 가진객체를 이용하여 경로를 추출해낼수 있다.
	saveDir = application.getRealPath("/data");
	int maxSize = 5*1024*1024; // mb
	String encoding = "utf-8";
	
	//업로드가 처리되는 시점(생성자)
	MultipartRequest multi = null;
	try{
		multi = new MultipartRequest(request, saveDir, maxSize, encoding);
		//업로드 컴포넌트를 이용할때는, 일반적인 방법으로 파라미터를 받을 수 없고,
		//컴포넌트 객체를 이용해서 받아야한다.
		//따라서 MultipartRequest 객체의 메서드중 getParameter()메서드를 이용하면된다.
		String product_name = multi.getParameter("product_name");
		out.print("제품명 : "+ product_name+"<br>");
		
		String ori = multi.getOriginalFileName("myFile");
		out.print("당신이 업로드한 파일명 : "+ori+"<br>");
		
		out.print("변경된 파일명 : "+FileManager.getFileRename(ori)+"<br>");
		
		out.print("<script>");
		out.print("alert('업로드 성공');");
		out.print("location.href='/gallery/list.jsp';");
		out.print("</script>");
	} catch(IOException e) {
		e.printStackTrace();
		out.print("<script>");
		out.print("alert('파일의 용량은 5mb를 넘을 수 없습니다.');");
		out.print("history.back();");
		out.print("</script>");
	}
%>