package com.study.model.hotplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.commons.db.DBManager;

public class HotPlaceDAO {
	DBManager manager = DBManager.getInstance();
	
	public int insert(HotPlace hotPlace) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into hotplace(hotplace_id, name, tel, addr, food)";
		sql += " values(seq_hotplace.nextval, ?, ?, ?, ?)";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hotPlace.getName());
			pstmt.setString(2, hotPlace.getTel());
			pstmt.setString(3, hotPlace.getAddr());
			pstmt.setString(4, hotPlace.getFood());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<HotPlace> list = new ArrayList<HotPlace>();
		
		con = manager.getConnection();
		
		String sql = "select * from hotplace order by hotplace_id desc";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				HotPlace hotPlace = new HotPlace();
				hotPlace.setHotplace_id(rs.getInt("hotplace_id"));
				hotPlace.setName(rs.getString("name"));
				hotPlace.setTel(rs.getString("tel"));
				hotPlace.setAddr(rs.getString("addr"));
				hotPlace.setFood(rs.getString("food"));
				list.add(hotPlace);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	public HotPlace select(int hotplace_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HotPlace hotPlace = null;
		
		String sql = "select * from hotplace where hotplace_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, hotplace_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				hotPlace = new HotPlace();
				hotPlace.setHotplace_id(rs.getInt("hotplace_id"));
				hotPlace.setName(rs.getString("name"));
				hotPlace.setTel(rs.getString("tel"));
				hotPlace.setAddr(rs.getString("addr"));
				hotPlace.setFood(rs.getString("food"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return hotPlace;
	}
	
	public int edit(HotPlace hotPlace) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update hotplace set name=?, tel=?, addr=?, food=?";
		sql += " where hotplace_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hotPlace.getName());
			pstmt.setString(2, hotPlace.getTel());
			pstmt.setString(3, hotPlace.getAddr());
			pstmt.setString(4, hotPlace.getFood());
			pstmt.setInt(5, hotPlace.getHotplace_id());
//			System.out.println(hotPlace.getHotplace_id());
//			System.out.println(hotPlace.getName());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	public int delete(HotPlace hotPlace) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "delete from hotplace where hotplace_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, hotPlace.getHotplace_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
}
