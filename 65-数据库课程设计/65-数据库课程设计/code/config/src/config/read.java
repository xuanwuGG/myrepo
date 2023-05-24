package config;
import java.beans.Statement;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class read {
	static int a=1;
	read(int b){
		a=b;
	}
	public void get() {
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs;
	String strSql = "select * from 类别 where 类别ID=?";
	try {
		con=Conn.getConnection();
		InputStream in = null;
		ps=con.prepareStatement(strSql);
		ps.setInt(1, a);
		rs=ps.executeQuery();
		while (rs.next()) {
			DataOutputStream dos= new DataOutputStream(new BufferedOutputStream(new FileOutputStream( rs.getString("类别名称")+".jpg")));
			// 读出流
			in = rs.getBinaryStream("图片");
			int len = 0;
			System.out.println("导出成功");
			byte[] b = new byte[1024];
			while ((len = in.read(b)) != -1) {
				dos.write(b, 0, len);
			}
			dos.close();
			in.close();
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}
}
}
