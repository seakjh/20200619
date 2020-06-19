package com.study.model.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	//목록 - CRUD read에 해당
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<News> list = new ArrayList<News>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("select n.news_id as news_id, title, writer, regdate, hit, count(comments_id) as cnt");
		sb.append(" from news n left outer join comments c");
		sb.append(" on n.news_id = c.news_id");
		sb.append(" group by n.news_id, title, writer, regdate, hit");
		sb.append(" order by n.news_id desc");
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				News news = new News();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				//news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				news.setCnt(rs.getInt("cnt"));
				
				list.add(news);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		return list;
	}
	
	//글 한건 가져오기
	public News select(int news_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News news = null;
		
		String sql = "select * from news where news_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				news = new News();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		return news;
	}
	
	//한 건 수정
	public int edit(News news) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update news set title=?, writer=?, content=?";
		sql += " where news_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			pstmt.setInt(4, news.getNews_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		return result;
	}
	
	//한 건 삭제된 것처럼 보이게하기
	public int delete(News news) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0;
		
		String sql = "update news set title=? where news_id=?";
		news.setTitle("삭제된게시글입니다..");
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setInt(2, news.getNews_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
}
