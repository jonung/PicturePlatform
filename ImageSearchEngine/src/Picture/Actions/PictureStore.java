package Picture.Actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.validation.Assertion;

import DBTool.DB18Util;
import DBTool.DB59Util;
import Picture.Models.AlbumResult;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;

public class PictureStore extends ActionSupport{
	
	
	//username
	private String username="";
	//userid
	private String userid="";
	//PictureID
	private String pictureID="";
	//tags
	private String tags="";
	//queryresult
	private String queryresult="";
	//albumID
	private String albumID="";
	//albumList
	private List<AlbumResult> albumList=new ArrayList<AlbumResult>();
	
	
	//global variable
	private static final long serialVersionUID = 1L;
	public static Connection conn = null;

	/*public String executeGetAlbumName() throws SQLException{
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		
		////////////////////////////getAlbumName//////////////////////////////////
		if(albumList.isEmpty()){
			
			Statement statement=conn.createStatement();
			String sql="select * from userinfo.album where isDeleted = 0 and userID="+ "'"+userid + "'";
			statement = conn.prepareStatement(sql);
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()){
				AlbumResult m_albumResult=new AlbumResult();
				m_albumResult.setName(rs.getString("name"));
				m_albumResult.setId(rs.getString("id"));
				albumList.add(m_albumResult);
			}
			return SUCCESS;
			
		}
		return "success";
	}*/
	
	public String execute()throws Exception{
		//userid=getUserID();
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		
		////////////////////////////getAlbumName//////////////////////////////////
		if(albumList.isEmpty()){
			
			Statement statement=conn.createStatement();
			String sql="select * from userinfo.album where isDeleted = 0 and userID="+ "'"+userid + "'";
			statement = conn.prepareStatement(sql);
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()){
				AlbumResult m_albumResult=new AlbumResult();
				m_albumResult.setName(rs.getString("name"));
				m_albumResult.setId(rs.getString("id"));
				albumList.add(m_albumResult);
			}
			//return SUCCESS;
			
		}
		////////////////////////////////////////////////////////////////////////
				
		if(tags.length() < 1){
			queryresult="请填写标签";
			return SUCCESS;
			
		}
		
		
		Statement statement=conn.createStatement();
		String sql="select * from userinfo.pictureStore where pictureID='"+pictureID+"' and albumID= "+albumID+" and userID="+ "'"+userid+"'";
		statement = conn.prepareStatement(sql);
		ResultSet rs=statement.executeQuery(sql);
		if(rs.next()){
			queryresult="该相册中已经有了本图";
			return SUCCESS;
		}
		
		sql="insert into userinfo.pictureStore(userID,pictureID,tags,storeTime,isDeleted,albumID) values(" +
				"'"+userid + "'"
				+",'"+pictureID
				+"','"+tags
				+"',now()"
				+",0"
				+","+albumID+")";
		statement.execute(sql);
		//statement.executeQuery(sql);
		//username=getUserName();
		queryresult="收藏成功";
		
		statement.close();
		return SUCCESS;
		
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
	
	
	
	
	public void setPictureID(String pictureID) {
		this.pictureID=pictureID;
	}
	public String getPictureID() {
		return pictureID;
	}
	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	
	
	public String getQueryresult() {
		return queryresult;
	}

	public void setQueryresult(String queryresult) {
		this.queryresult = queryresult;
	}
	
	
	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}

	
	
	/*public List<AlbumResult> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<AlbumResult> albumList) {
		this.albumList = albumList;
	}*/
}
