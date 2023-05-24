package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {
	
	private Conn()
	{};
	
	static {
	 	String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		try
		{
			Class.forName(driverName);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=测试";
	String userName = "Fortune";
	String userPwd = "fcfx00820030705";
	return DriverManager.getConnection(dbURL, userName, userPwd);

	}
	public static void close(Connection conn, Statement ps, ResultSet rs) {
		if(rs!=null) {
			try{
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(ps!=null) {
			try{
				ps.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn!=null) {
			try{
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
