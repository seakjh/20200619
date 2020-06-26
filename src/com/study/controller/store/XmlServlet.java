package com.study.controller.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.study.model.store2.Icons;
import com.study.model.store2.Store;
import com.study.model.store2.StoreDAO;

public class XmlServlet extends HttpServlet {
	StoreDAO storeDAO = new StoreDAO(); //서버를 끌때까지 생존
	ArrayList<Store> list;
	Store store;
	Icons icons;
	
	boolean isStore = false;
	boolean isName = false;
	boolean isAddr = false;
	boolean isLati = false;
	boolean isLongi = false;
	boolean isMemo = false;
	boolean isIcons_id = false;
	
	PrintWriter out;
	int result = 0;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		out = response.getWriter();
		
		// 1단계 업로드
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// application 내장객체의 자료형은 ServletContext 이다
		// ServletContext? : 어플리케이션의 전역정보를 가진 객체
		ServletContext context = request.getServletContext();
		String saveDir = context.getRealPath("/data/");
		File saveFilePath = new File(saveDir);
		factory.setRepository(saveFilePath);

		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> itemList = upload.parseRequest(request);

			for (int i=0; i < itemList.size(); i++) {
				FileItem item = itemList.get(i);

				if (item.isFormField()) {
					// vo
				} else {
					System.out.println("업로드 된 파일명" + item.getName());
					
					//xml파일의 내용 한줄씩 읽어보자 !
					BufferedReader buffr = null;
					buffr = new BufferedReader(new InputStreamReader(item.getInputStream()));
					
					StringBuilder sb = new StringBuilder();
					while(true) {
						String data = buffr.readLine();
						if (data == null) break;
						sb.append(data);
					}
					if (buffr != null) {
						buffr.close();
					}
					parseXml(sb.toString()); //스트림으로 결과 전달
				}
			}
			out.print(result);
			System.out.println("수행결과 : "+result);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseXml(String xml) {
		//System.out.println("있나? : "+xml);
		//넘겨받은 xml 스트림을 파싱하자! 그후 오라클에 인서트
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		//팩토리로부터 파서 생성!
		try {
			SAXParser saxParser = factory.newSAXParser();
			
			//파일이 아닌 스트링 데이터로부터 파싱을 해야한다
			InputSource source = new InputSource(new StringReader(xml));
			saxParser.parse(source, new DefaultHandler() {
				@Override
				public void startDocument() throws SAXException {
					list = new ArrayList<Store>();
				}
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes)	throws SAXException {
					System.out.print("<"+qName+">");
					if (qName.equals("store")) {
						store = new Store();
						icons = new Icons();
						isStore = true;
					}
					if (qName.equals("name")) {
						isName = true;
					}
					if (qName.equals("addr")) {
						isAddr = true;
					}
					if (qName.equals("lati")) {
						isLati = true;
					}
					if (qName.equals("longi")) {
						isLongi = true;
					}
					if (qName.equals("memo")) {
						isMemo = true;
					}
					if (qName.equals("icons_id")) {
						isIcons_id = true;
					}
				}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					String data = new String(ch, start, length);
					System.out.print(data);
					if (isName) {
						store.setName(data);
					}
					else if (isAddr) {
						store.setAddr(data);
					}
					else if (isLati) {
						store.setLati(Double.parseDouble(data));
					}
					else if (isLongi) {
						store.setLongi(Double.parseDouble(data));
					}
					else if (isMemo) {
						store.setMemo(data);
					}
					else if (isIcons_id) {
						icons.setIcons_id(Integer.parseInt(data));
						store.setIcons(icons);
					}
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					System.out.println("</"+qName+">");
					
					if (qName.equals("name")) {
						isName = false;
					}
					else if (qName.equals("addr")) {
						isAddr= false;
					}
					else if (qName.equals("lati")) {
						isLati= false;
					}
					else if (qName.equals("longi")) {
						isLongi= false;
					}
					else if (qName.equals("memo")) {
						isMemo= false;
					}
					else if (qName.equals("icons_id")) {
						isIcons_id= false;
					}
					else if (qName.equals("store")) {
						list.add(store);
					}
				}
				
				@Override
				public void endDocument() throws SAXException {
					System.out.println("채워진 리스트 수 : " + list.size());
					for (int i=0; i<list.size(); i++) {
						Store store = list.get(i);
						storeDAO.insert(store);
					}
					result = 1;
				}
			});
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
