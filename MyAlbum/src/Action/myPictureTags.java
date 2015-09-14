package Action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import DBTool.DB59Util;
import DBTool.DBUtility;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;


public class myPictureTags extends ActionSupport{
	
	
	//username
	private String username="";
	//userid
	private String userid="";
	//albumTags
	List<String> tagList=new ArrayList<String>();
	//resultString
	String resultString;

	private static final long serialVersionUID = 1L;
	//public static Connection conn = null;
	

	public String execute()throws Exception{
		 Connection conn = null;
		//userid=getUserID();
		username=getUserName();
		userid = username;
		
		if(conn==null) {
			conn=DB18Util.connectMySql();
		}
		
		/*if (userid == null || userid==""){
			String sql = "select userid from userinfo where username ='"+username+"'";  
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			
			 * 根据username对应的userID只有一条记录
			 
			while(rs.next()){
				userid = rs.getString("userid");
			}
			stmt.close();
		}*/
		
		
		///////////////////////////////////getAlbums///////////////////////////////////
		/*Statement statement=conn.createStatement();
		String sql="select * from userinfo.album where isDeleted = 0 and userID="+"'"+userid + "'";
		statement = conn.prepareStatement(sql);
		ResultSet rs=statement.executeQuery(sql);*/
		
		//fixed by Liujh
		String sql="select * from userinfo.album where isDeleted = 0 and userID = ? ";
		PreparedStatement statement  = conn.prepareStatement(sql);
		statement.setString(1,  userid );
		ResultSet rs = statement.executeQuery();
		//fixed by Liujh
		
		
		JSONArray records=new JSONArray();
		Set<String> tagSet=new HashSet<String>();
		while(rs.next()){
			String tag=rs.getString("tags");
			if(tag!=null){
				String [] temp=tag.split(" ");
				for(int i=0;i<temp.length;i++){
					tagSet.add(temp[i]);	
				}
			}
		}
		for(Iterator it=tagSet.iterator();it.hasNext();){
			JSONObject ob=new JSONObject();
			ob.put("name",it.next());
			ob.put("size", 1000);
			records.put(ob);
		}
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

	
	
	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	
	
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}
}
