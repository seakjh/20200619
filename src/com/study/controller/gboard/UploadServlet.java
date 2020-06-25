package com.study.controller.gboard;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.study.commons.file.FileManager;
import com.study.model.gboard.GBoard;
import com.study.model.gboard.GBoardDAO;

//jsp가 서블릿이라는 원칙을 잊지않았으니 안해도되는데 괜히 시킴;
public class UploadServlet extends HttpServlet{
	GBoardDAO gboardDAO = new GBoardDAO();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post 요청 받음!");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//itemFactory를 이용하면, 저장경로, 파일사이즈 제한..
		DiskFileItemFactory itemFactory = new DiskFileItemFactory();
		
		ServletContext context = request.getServletContext(); //application 내장객체의 자료형
		String savePath = context.getRealPath("/data");
		//System.out.println(savePath);
		File saveDir = new File(savePath);
		itemFactory.setRepository(saveDir);
		
		int maxSize = 3*1024*1024;
		itemFactory.setSizeThreshold(maxSize);
		
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		
		//업로드시작
		try {
			//클라이언트가 html 지정한 컴포넌트
			//html 컴포넌트들을 fileitem 으로 반환
			List<FileItem> itemList = upload.parseRequest(request);
			//System.out.println("넘어온 파라미터 수는 "+itemList.size());
			
			//파라미터 추출하기!
			//넘겨받은 파라미터들중에 파일관련한 파라미터와 텍스트데이터 파라미터를 구분해야한다
			//VO
			GBoard gBoard = new GBoard();
			for (int i=0; i<itemList.size(); i++) {
				FileItem item = itemList.get(i);
				
				if(item.isFormField()) { //데이터베이스에 넣을자리
					//System.out.println(i+"번째인 저는 파일아님");
					//System.out.println(item.getFieldName());
					
					if (item.getFieldName().equals("title")) {
						gBoard.setTitle(item.getString("utf-8"));
					}
					else if (item.getFieldName().equals("writer")) {
						gBoard.setWriter(item.getString("utf-8"));						
					}
					else if (item.getFieldName().equals("content")) {
						gBoard.setContent(item.getString("utf-8"));
					}
				}else {//이미지처리
					//System.out.println(i+"번째는 저는 파일임");
					//System.out.println(item.getFieldName());
					
					//파일명을 바꿔야 하므로, 업로드된 파일명부터 추출!
					String ori = item.getName();
					//System.out.println("업로드된 원래 파일명은 "+ori);
					
					String dest = FileManager.getFileRename(ori);
					gBoard.setFilename(dest);
					
					//서버에 저장
					File destFile = new File(savePath+"/"+dest);
					item.write(destFile);
				}
			}
			//파일컴포넌트가 아닌 일반컴포넌트
			int result = gboardDAO.insert(gBoard);
			
			if (result != 0) {
				out.print("<script>");					
				out.print("alert('등록성공');");
				out.print("location.href='/gallery/list.jsp'");
				out.print("</script>");
			}
			else {
				out.print("<script>");					
				out.print("alert('등록실패');");
				out.print("history.back();");
				out.print("</script>");
			}
			System.out.print("제목 " +gBoard.getTitle()+ " 업로드성공");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
