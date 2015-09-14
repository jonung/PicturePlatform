package Action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBTool.DB18Util;
import DBTool.DB59Util;
import DBTool.DBUtility;
import Model.album;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.validation.Assertion;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;

public class personalAlbums extends ActionSupport {
	
	private static final long serialVersionUID = -6529319369847000493L;
	
	//username
	private String username="";
	//userid
	private String userid="";
	
	//albumList
	private List<album> albumList=new ArrayList<album>();
	
	//albumPrivate
	private int albumPrivate;
		
	//public static Connection conn = null;

	
	public String execute()throws Exception{
		Connection conn = null;
		//userid=getUserID();
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		//added by lwm, for getuserID=null
		/*if (userid == null || userid==""){
			String sql = "select userid from userinfo where username ='"+username+"'";  //ckcest personal port use user_ckcest for user information. userinfo is old.
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			
			 * ����username��Ӧ��userIDֻ��һ����¼
			 
			while(rs.next()){
				userid = rs.getString("userid");
			}
			stmt.close();
		}*/
		//end added by lwm
	
		
		
		///////////////////////////////////getAlbums///////////////////////////////////
		/*Statement statement=conn.createStatement();
		String sql="select * from userinfo.album where isDeleted = 0 and userID="+ "'"+userid + "'";
			
		statement = conn.prepareStatement(sql);
		ResultSet rs=statement.executeQuery(sql);*/
		
		//fixed by Liujh
		String sql = "select * from userinfo.albumStore where isDelected = 0 and userID = ? ";
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1,  userid );
		ResultSet rs = statement.executeQuery();
				
		//fixed by Liujh
		
		System.out.println("personal albums done !");
		while(rs.next()){
			album m_album=new album();
			String albumID=rs.getString("id");
			m_album.setId(albumID);
			m_album.setName(rs.getString("name"));
			m_album.setTags(rs.getString("tags"));
			
	/*		statement=conn.createStatement();
			sql="select * from userinfo.pictureStore where isDeleted = 0 and userID="+ "'"+userid+ "'"+" and albumID="+albumID+" limit 0,4";
			statement = conn.prepareStatement(sql);
			ResultSet rstemp=statement.executeQuery(sql);*/
			
			//fixed by Liujh
			sql = "select * from useinfo.pictureStore where isDeleted = 0 and userID = ? and albumID = ? limit 0,4 ";
			statement = conn.prepareStatement(sql);
			statement.setString(1,  userid );
			statement.setInt(2, Integer.parseInt(albumID));
			ResultSet rstemp = statement.executeQuery();
			//fixed by Liujh
			
			
			String [] cover=new String[4];
			int i=0;
			while(rstemp.next()){
				cover[i]=rstemp.getString("pictureID");
				i++;
			}
			for(int j=0;j<4;j++){
				if(cover[j]==""||cover[j]==null){
					cover[j]="images/img02.png";
				}
				else{
					cover[j]="pictureShowAction.action?pictureID="+cover[j];
				}
				
			}
			m_album.setCover(cover);

			
			albumList.add(m_album);
			
			//albumList.add(rs.getString("name"));
		}
		
		statement.close();
		
		
		
		return "success";
	}
	
	
	private String getUserName() {
		// TODO Auto-generated method stub
		String username="";
		HttpServletRequest request=ServletActionContext.getRequest();
		Object object = request.getSession().getAttribute("_const_cas_assertion_");
		if(object != null) {

		    Assertion assertion = (Assertion)object;
		    username = assertion.getPrincipal().getName();
		}
		return username;
	}

	private String getUserID() {
		// TODO Auto-generated method stub

		HttpServletRequest request=ServletActionContext.getRequest();
		Object object = request.getSession().getAttribute("_const_userprofile_assertion_");
		if(object!=null){
			UserProfile userProfile= (UserProfile)object;
			return userProfile.userId;
		}
		return null;
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	public List<album> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<album> albumList) {
		this.albumList = albumList;
	}
	

	public int getAlbumPrivate() {
		return albumPrivate;
	}

	public void setAlbumPrivate(int albumPrivate) {
		this.albumPrivate = albumPrivate;
	}
}
