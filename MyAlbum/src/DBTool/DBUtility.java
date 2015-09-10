package DBTool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtility {
	
	public static Connection conn;
	public static void main(String [] arg) throws SQLException{
		long start=System.currentTimeMillis();
		connectMySql();
		long end=System.currentTimeMillis();
		System.out.println(end-start);
		
	}
	
	public static Connection connectMySql() throws SQLException {
		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		Connection connec;
		// URL指向要访问的数据库名userinfo

		String url = "jdbc:mysql://10.15.62.18:3306/userinfo?useUnicode=true&characterEncoding=utf8";
		String user="root";
		String password="123456";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connec = DriverManager.getConnection(url, user, password);
		return connec;
		
	}
}
