package Action;

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
import DBTool.DBUtility;
import Model.picture;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;

public class newAlbum extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	//albumID
	private String albumID="";
	//username
	private String username="";
	//userid
	private String userid="";
	//albumName
	private String albumName;
	//albumTag
	private String albumTag;
	//albumClassify
	private String albumClassify;
	//albumPrivate
	private String albumPrivate;
	//resultStr
	private String resultStr="";
	
	
	
	//public static Connection conn = null;
	
	public String execute()throws Exception{
		Connection conn = null;
		//userid=getUserID();
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		/*
		if (userid == null || userid==""){
			String sql = "select userid from userinfo where username ='"+username+"'";  
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			
			 * 根据username对应的userID只有一条记录
			 
			while(rs.next()){
				userid = rs.getString("userid");
			}
			stmt.close();
		}*/
		
		
		Statement statement=conn.createStatement();
		String sql="insert into userinfo.album(userID,name,tags,createTime,isDeleted,classID,private) values(" +
		"'"+ userid + "'"
		+",'"+albumName
		+"','"+albumTag
		+"',now()"
		+",0"
		+",'"+albumClassify
		+"',"+albumPrivate+")";
		
		statement.execute(sql);
		statement = conn.prepareStatement(sql);
		resultStr="创建成功，请关闭窗口！";
		
		
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

	
	
	
	public String getAlbumID() {
		return albumID;
	}
	public void setAlbumID(String albumID) {
		this.albumID = albumID;
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
	

	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
		

	public String getAlbumTag() {
		return albumTag;
	}
	public void setAlbumTag(String albumTag) {
		this.albumTag = albumTag;
	}	
	
	public String getAlbumClassify() {
		return albumClassify;
	}
	public void setAlbumClassify(String albumClassify) {
		this.albumClassify = albumClassify;
	}
		
	public String getAlbumPrivate() {
		return albumPrivate;
	}
	public void setAlbumPrivate(String albumPrivate) {
		this.albumPrivate = albumPrivate;
	}
	
	
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
}
