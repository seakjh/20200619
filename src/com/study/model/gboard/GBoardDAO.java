package com.study.model.gboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.commons.db.DBManager;

//CRUD
public class GBoardDAO {
	DBManager manager = DBManager.getInstance();
	
	public int insert(GBoard gBoard) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String sql = "insert into gboard(gboard_id, title, writer, content, filename)";
		sql += " values(seq_gboard.nextval, ?, ?, ?, ?)";
		
		con = manager.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gBoard.getTitle());
			pstmt.setString(2, gBoard.getWriter());
			pstmt.setString(3, gBoard.getContent());
			pstmt.setString(4, gBoard.getFilename());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	//모두가져오기
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GBoard> list = new ArrayList<GBoard>();
		
		con = manager.getConnection();
		String sql = "select * from gboard order by gboard_id desc";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GBoard gBoard = new GBoard();
				
				gBoard.setGboard_id(rs.getInt("gboard_id"));
				gBoard.setTitle(rs.getString("title"));
				gBoard.setWriter(rs.getString("writer"));
				gBoard.setContent(rs.getString("content"));
				gBoard.setRegdate(rs.getString("regdate"));
				gBoard.setHit(rs.getInt("hit"));
				gBoard.setFilename(rs.getString("filename"));
				
				list.add(gBoard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}

	public GBoard select(int gboard_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GBoard gBoard = null;
		
		String sql = "select * from gboard where gboard_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, gboard_id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				gBoard = new GBoard();
				gBoard.setGboard_id(rs.getInt("gboard_id"));
				gBoard.setTitle(rs.getString("title"));
				gBoard.setWriter(rs.getString("writer"));
				gBoard.setContent(rs.getString("content"));
				gBoard.setRegdate(rs.getString("regdate"));
				gBoard.setHit(rs.getInt("hit"));
				gBoard.setFilename(rs.getString("filename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return gBoard;
	}

	public int edit(GBoard gBoard) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update gboard set title=?, writer=?, content=?, filename=?";
		sql += " where gboard_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gBoard.getTitle());
			pstmt.setString(2, gBoard.getWriter());
			pstmt.setString(3, gBoard.getContent());
			pstmt.setString(4, gBoard.getFilename());
			pstmt.setInt(5, gBoard.getGboard_id());
			System.out.println(gBoard.getGboard_id());
			System.out.println(gBoard.getFilename());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	public int delete(GBoard gBoard) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "delete from gboard where gboard_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, gBoard.getGboard_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
