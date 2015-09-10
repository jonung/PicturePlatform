package JsonAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.validation.Assertion;
import org.json.JSONArray;
import org.json.JSONObject;

import DBTool.DB18Util;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;


public class albumsIncludePicture extends ActionSupport{
	
	
	//username
	private String username="";
	//userid
	private String userid="";
	//pictureID
	//albumID
	private String albumID="2";
	
	private String pictureID;
	//resultString
	String resultString;
	

	private static final long serialVersionUID = 1L;
	private Connection conn = null;

	public String execute()throws Exception{
		
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		///////////////////////////////////getAlbums///////////////////////////////////
		Statement statement=conn.createStatement();
		String sql="select * from userinfo.pictureStore where isDeleted = 0 and pictureID='"+pictureID+"'";
		String _sql="select * from pictureStore, album where pictureStore.isDeleted = 0 and album.isDeleted = 0 and album.id = pictureStore.albumID and pictureStore.pictureID='"+pictureID+"'";
		statement = conn.prepareStatement(_sql);
		ResultSet rs=statement.executeQuery(_sql);
		JSONArray records=new JSONArray();
		
		while(rs.next()){
			JSONObject ob=new JSONObject();
			ob.put("albumID", rs.getString("album.id"));
			ob.put("name", rs.getString("album.name"));
			ob.put("tags", rs.getString("album.tags"));
			ob.put("userID", rs.getString("album.userID"));
			ob.put("createTime", rs.getString("album.createTime"));
			ob.put("sharedCount", rs.getString("album.sharedCount"));
			ob.put("classID", rs.getString("album.classID"));
			records.put(ob);
		}
		/*Set<String> albumIDSet=new HashSet<String>();
		while(rs.next()){
			String albumID=rs.getString("albumID");
			albumIDSet.add(albumID);			
		}
		for(Iterator it=albumIDSet.iterator();it.hasNext();){
			sql="select * from userinfo.album where isDeleted = 0 and id="+it.next();
			statement = conn.prepareStatement(sql);
			ResultSet rstemp=statement.executeQuery(sql);
			while(rstemp.next()){
				JSONObject ob=new JSONObject();
				ob.put("albumID", rstemp.getString("id"));
				ob.put("name", rstemp.getString("name"));
				ob.put("tags", rstemp.getString("tags"));
				ob.put("userID", rstemp.getString("userID"));
				ob.put("createTime", rstemp.getString("createTime"));
				ob.put("sharedCount", rstemp.getString("sharedCount"));
				ob.put("classID", rstemp.getString("classID"));
				records.put(ob);
			}
		}*/
		
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("records",records);
		resultString=jsonObj.toString();
		
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
	
	public String getPictureID() {
		return pictureID;
	}

	public void setPictureID(String pictureID) {
		this.pictureID = pictureID;
	}

	
	public String getAlbumID() {
		return albumID;
	}

	public void setAlbumID(String albumID) {
		this.albumID = albumID;
	}


	
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}
}
