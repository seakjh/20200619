package com.study.commons.file;

public class FileManager {
	//파일명 오늘날짜로 변경하기!
	public static String getFileRename(String ori) {
		long time = System.currentTimeMillis(); //20200622....
		String rename = time+"."+getExt(ori);
		return rename;
	}
	
	//파일경로 중 확장자만 추출하는 메서드!
	public static String getExt(String filename) {
		//1. 가장 마지막 점을 구한다! 왜? 확장자만 뽑아내기위해
		int index = filename.lastIndexOf(".");
		String ext = filename.substring(index+1, filename.length());	
		return ext;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getFileRename("아아아.jpg"));
//	}
}
