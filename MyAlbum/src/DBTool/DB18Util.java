package DBTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DB18Util {
	
	
//private static volatile DataSource datasource = null;

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

}
