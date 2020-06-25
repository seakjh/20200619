package com.study.model.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.study.commons.db.DBManager;

public class StoreDAO {
	DBManager manager = DBManager.getInstance();
	
	//모두
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Store> list = new ArrayList<Store>(); 
		
		String sql = "select * from store s, icons i";
		sql +=  " where s.icons_id=i.icons_id order by store_id desc";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
				Icons icons = new Icons();
				icons.setIcons_id(rs.getInt("icons_id"));
				icons.setTitle(rs.getString("title"));
				icons.setFilename(rs.getString("filename"));
				
				Store store = new Store();
				store.setStore_id(rs.getInt("store_id"));
				store.setName(rs.getString("name"));
				store.setAddr(rs.getString("addr"));
				store.setLati(rs.getDouble("lati"));
				store.setLongi(rs.getDouble("longi"));
				store.setMemo(rs.getString("memo"));
				
				store.setIcons(icons);
				list.add(store);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	//한건
	public Store select(int store_id) {
		Store store = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from store s, icons i";
		sql += " where s.icons_id=i.icons_id and s.store_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, store_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Icons icons = new Icons();
				icons.setIcons_id(rs.getInt("icons_id"));
				icons.setTitle(rs.getString("title"));
				icons.setFilename(rs.getString("filename"));
				
				store = new Store();
				store.setStore_id(rs.getInt("store_id"));
				store.setName(rs.getString("name"));
				store.setAddr(rs.getString("addr"));
				store.setLati(rs.getDouble("lati"));
				store.setLongi(rs.getDouble("longi"));
				store.setMemo(rs.getString("memo"));
				
				store.setIcons(icons);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt, rs);
		}
		
		return store;
	}
	
	//등록
	public int insert(Store store) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "insert into store(store_id, name, addr, lati, longi, icons_id, memo)";
		sql += " values(seq_store.nextval, ?, ?, ?, ?, ?, ?)";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, store.getName());
			pstmt.setString(2, store.getAddr());
			pstmt.setDouble(3, store.getLati());
			pstmt.setDouble(4, store.getLongi());
			pstmt.setInt(5, store.getIcons().getIcons_id());
			pstmt.setString(6, store.getMemo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	//수정
	public int edit(Store store) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "update store  set name=?, addr=?, lati=?, longi=?, icons_id=?, memo=?";
		sql += " where store_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, store.getName());
			pstmt.setString(2, store.getAddr());
			pstmt.setDouble(3, store.getLati());
			pstmt.setDouble(4, store.getLongi());
			pstmt.setInt(5, store.getIcons().getIcons_id());
			pstmt.setString(6, store.getMemo());
			pstmt.setInt(7, store.getStore_id());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
	
	//삭제
	public int delete(int store_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "delete from store where store_id=?";
		
		con = manager.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, store_id);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.freeConnection(con, pstmt);
		}
		
		return result;
	}
}
