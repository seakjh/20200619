package com.study.controller.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.study.model.store2.Icons;
import com.study.model.store2.Store;
import com.study.model.store2.StoreDAO;

//클라이언트가 multipart/form-data 로 전송할때는
//해당파라미터를 request 객체로 직접 받을 수 없다!!
//따라서 업로드 컴포넌트를 이용해야한다..
//MultipartRequest(오레일리 출판사)
//ServletUpload (apach org)
public class JsonUploadServlet extends HttpServlet{
	StoreDAO storeDAO = new StoreDAO();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		//업로드 관련 설정 정보 객체
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		
		//용량.. 저장위치 설정..
		//jsp에서의 application 내장객체
		ServletContext context = request.getServletContext();
		String saveDir = context.getRealPath("/data");
		File saveFile = new File(saveDir); // 저장경로를 가진 파일객체
		fileItemFactory.setRepository(saveFile); //저장위치 설정 완료
		
		//업로드처리 객체
		ServletFileUpload upload = null;
		upload = new ServletFileUpload(fileItemFactory);
		
		//클라이언트가 전송한 파라미터가 여러개 있을 수 있으므로,
		//파라미터를 집합으로 보유한 FileItem을 분석하자
		//FileItem이란? html 컴포넌트를 표현한 객체
		try {
			
			//요청 파라미터 분석시작
			List<FileItem> itemList = upload.parseRequest(request);
			
			for(int i=0; i<itemList.size(); i++) {
				//텍스트필드인지 아닌지 구분하여, 아닌 경우는 파일업로드 처리!
				FileItem item = itemList.get(i);
				
				if (item.isFormField()) {
					//vo
				}
				else { //텍스트박스가 아니라면
					System.out.println("파일업로드 발견 "+ item.getName());
					
					//스트림으로 파일을 읽어보자!
					BufferedReader buffr = null;
					buffr = new BufferedReader(new InputStreamReader(item.getInputStream()));
					
					//읽기
					String data = null;
					StringBuilder sb = new StringBuilder();
					
					while (true) {
						data = buffr.readLine();
						if (data == null) break;
						sb.append(data);
					}
					System.out.println(sb.toString().trim());
					if (buffr != null) {
						try {
							buffr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					int result = parseJson(sb.toString());
					
					PrintWriter out = response.getWriter();
					out.print(result);
					System.out.println("업로드 성공 여부" + result);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} 
	}
	
	public int parseJson(String data) {
		int result = 0;
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(data);
			JSONArray jsonArray =  (JSONArray) obj.get("storeList");
			
			//반복문 돌리면서 DAO.insert 호출
			for (int i=0; i<jsonArray.size(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				System.out.println(json.get("name"));
				
				//store VO
				Store store = new Store();
				Icons icons = new Icons();
				
				//json -> store 옮기자
				store.setName((String)json.get("name"));
				store.setAddr((String)json.get("addr"));
				store.setLati((Double)json.get("lati"));
				store.setLongi((Double)json.get("longi"));
				store.setMemo((String)json.get("memo"));
				long icons_id = (Long)json.get("icons_id");
				icons.setIcons_id((int)icons_id); //demotion
				
				//VO결합
				store.setIcons(icons);
				
				storeDAO.insert(store);
			}
			result = 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
