package config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveImageToDatabase {
    public void set(){
    	
    	Connection con=null;
    	String sql="update 类别 set 图片=? where 类别ID=2";
    	try
    	{
    		con=Conn.getConnection();
    		PreparedStatement ps=con.prepareStatement(sql);
    		File f = new File("jiang.png");
    		FileInputStream input= new FileInputStream(f);
    		ps.setBinaryStream(1, input,(int)f.length());
    		ps.executeUpdate();
    		input.close();
    		System.out.println("yes");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
