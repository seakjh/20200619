package com.study.commons.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//드라이버로드, 접속 및 해제를 대신해주는 객체
//DAO의 메서드마다 db관련 코드의 중복이 발생하니까
public class DBManager {
	String driver= "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "c##java";
	String password = "1234";
	private static DBManager instance; // 외부의 필요한자가 인스턴스를 가져갈 수 있도록
	
	private DBManager() {
	}
	
	public static DBManager getInstance() {
		if (instance == null) { //인스턴스가 없는 경우만.. 따라서 딱 한번
			instance = new DBManager();
		}
		return instance;
	}
	
	//호출자가 Connection을 얻어갈 수 있게 반환하는 메서드
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	//자원해제,..관련
	//DML 수행후 반납을 처리하는 메서드
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
