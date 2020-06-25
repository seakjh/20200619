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

public class EditServlet extends HttpServlet {
	GBoardDAO gBoardDAO = new GBoardDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		//itemFactory를 이용하면, 저장경로, 파일사이즈 제한..
		DiskFileItemFactory itemFactory = new DiskFileItemFactory();
		
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/data");
		
		File saveDir = new File(savePath);
		itemFactory.setRepository(saveDir);
		
		int maxSize = 5*1024*1024;
		itemFactory.setSizeThreshold(maxSize);
		
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		
		int gboard_id = 0;
		try {
			List<FileItem> itemList = upload.parseRequest(request);
			
			GBoard gBoard = new GBoard();
			
			for (int i=0; i<itemList.size(); i++) {
				FileItem item = itemList.get(i);
				
				if(item.isFormField()) {
					if (item.getFieldName().equals("title")) {
						gBoard.setTitle(item.getString("utf-8"));
					}
					else if (item.getFieldName().equals("writer")) {
						gBoard.setWriter(item.getString("utf-8"));
					}
					else if (item.getFieldName().equals("content")) {
						gBoard.setContent(item.getString("utf-8"));
					}
					else if (item.getFieldName().equals("gboard_id")) {
						gboard_id = Integer.parseInt(item.getString()); //숫자는 인코딩 필요없이 그냥넣으셈
						gBoard.setGboard_id(gboard_id);
					}
				}
				else { // 이미지 처리
					String ori = item.getName();
					
					String dest = FileManager.getFileRename(ori);
					gBoard.setFilename(dest);
					
					//서버저장							ㄹㅇ경로    /    파일이름
					File destFile = new File(savePath+"/"+dest);
					item.write(destFile);
				}
			}
			int result = gBoardDAO.edit(gBoard);
			
			if (result != 0) {
				out.print("<script>");					
				out.print("alert('수정성공');");
				out.print("location.href='/gallery/content.jsp?gboard_id="+gBoard.getGboard_id()+"';");
				out.print("</script>");
			}
			else {
				out.print("<script>");					
				out.print("alert('수정실패');");
				out.print("history.back();");
				out.print("</script>");
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}















