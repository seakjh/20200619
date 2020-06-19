package com.study.model.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.study.commons.db.DBManager;

public class NewsDAO {
	DBManager manager = DBManager.getInstance();
	
	//뉴스 등록
	public int insert(News news) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into news(news_id, title, writer, content)";
		sql += " values(seq_news.nextval, ?, ?, ?)";
		
		con = manager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			
			result = pstmt.executeUpdate(); //쿼리수행
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		return result;
	}
}
