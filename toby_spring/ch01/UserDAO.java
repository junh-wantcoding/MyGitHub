package springbook.user.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	ConnectionMaker cm;
	Connection con;
	public UserDAO(ConnectionMaker cm) {
		this.cm = cm;
	}
	
	public void add(User user) {
		try {		
			con = cm.getConnection();
			PreparedStatement ps = con.prepareStatement("insert into users values (?, ?, ?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPw());
			
			ps.executeUpdate();
			
			ps.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAll() throws SQLException, ClassNotFoundException {
		String sql = "truncate users";
		con = cm.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.executeUpdate();
		
		ps.close();
		con.close();
	}
	
	public User get(String id) throws SQLException, ClassNotFoundException {
		String sql = "select * from users where id = ?";
		User user = null;
		con = cm.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setPw(rs.getString("pw"));
			user.setName(rs.getString("name"));
		}
		
		rs.close();
		ps.close();
		con.close();
		
		return user;
	}
}
