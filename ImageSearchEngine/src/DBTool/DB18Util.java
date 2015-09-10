package DBTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mortbay.jetty.security.SSORealm;

import Picture.Models.AlbumResult;

import com.mysql.jdbc.PreparedStatement;

public class DB18Util {
	
	private static DataSource datasource ;
	static{
		PoolProperties p = new PoolProperties();
	    p.setUrl("jdbc:mysql://10.15.62.59:3306/userinfo");
	    p.setDriverClassName("com.mysql.jdbc.Driver");
	    p.setUsername("root");
	    p.setPassword("Cadal205");
	    p.setJmxEnabled(true);
	    p.setTestWhileIdle(false);
	    p.setTestOnBorrow(true);
	    p.setValidationQuery("SELECT 1");
	    p.setTestOnReturn(false);
	    p.setValidationInterval(30000);
	    p.setTimeBetweenEvictionRunsMillis(30000);
	    p.setMaxActive(100);
	    p.setInitialSize(10);
	    p.setMaxWait(10000);
	    p.setRemoveAbandonedTimeout(60);
	    p.setMinEvictableIdleTimeMillis(30000);
	    p.setMinIdle(10);
	    p.setLogAbandoned(true);
	    p.setRemoveAbandoned(true);
	    p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
	      "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	    datasource = new DataSource();
	    datasource .setPoolProperties(p);
	}

		public static Connection connectMySql(){
			Connection conn = null;
			try {
				conn = datasource.getConnection();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return conn;
		}
		
	
	private static void setDatasource(){
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://10.15.62.59:3306/userinfo");
		//p.setUrl("jdbc:mysql://localhost:3306/mysql");
	    p.setDriverClassName("com.mysql.jdbc.Driver");
	    p.setUsername("root");
	    p.setPassword("Cadal205");
	    p.setJmxEnabled(true);
	    p.setTestWhileIdle(false);
	    p.setTestOnBorrow(true);
	    p.setValidationQuery("SELECT 1");
	    p.setTestOnReturn(false);
	    p.setValidationInterval(30000);
	    p.setTimeBetweenEvictionRunsMillis(30000);
	    p.setMaxActive(100);
	    p.setInitialSize(10);
	    p.setMaxWait(10000);
	    p.setRemoveAbandonedTimeout(60);
	    p.setMinEvictableIdleTimeMillis(30000);
	    p.setMinIdle(10);
	    p.setLogAbandoned(false);
	    p.setRemoveAbandoned(true);
	    p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
	      "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	    
	    datasource = new DataSource();
	    datasource.setPoolProperties(p);
	    
	    //Connection conn = null;
	    
	    //conn = datasource.getConnection();
	}
	
	/*
	public static Connection connectMySql() throws SQLException {
		// TODO Auto-generated method stub
		String driver = "com.mysql.jdbc.Driver";
		Connection connec;
		// URL指向要访问的数据库名userinfo

		String url = "jdbc:mysql://10.15.62.59:3306/userinfo?useUnicode=true&characterEncoding=utf8";
		String user="root";
		String password="Cadal205";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connec = DriverManager.getConnection(url, user, password);
		return connec;
		
	}
	*/
	
	public  static void  main(String[] args) throws SQLException{
		/*
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.15.62.59:3306/userinfo?useUnicode=true&characterEncoding=utf8";
		String user="root";
		String password="Cadal205";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//Connection conn = DriverManager.getConnection(url, user, password);
		
		Connection conn = connectMySql();
		String a = "0";
		String searchText = "细胞";
		
		String sql = "select * from userinfo.album where  name like ?";
		
		//Connection conn = connectMySql();
		
		java.sql.PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "%" +searchText + "%");
		//ps.set
		System.out.println(ps.toString());
		//ps.setString(2, searchText);
		
		ResultSet rs= ps.executeQuery();
		
		while(rs.next()){
			System.out.println(rs.getString("name"));
			System.out.println(rs.getString("id"));
		}
		
	}
}
