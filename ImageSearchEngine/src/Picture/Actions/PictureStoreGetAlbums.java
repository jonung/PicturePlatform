package Picture.Actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.validation.Assertion;

import DBTool.DB18Util;
import Picture.Models.AlbumResult;

import com.opensymphony.xwork2.ActionSupport;

public class PictureStoreGetAlbums extends ActionSupport {
	
	//username
	private String username="";
	//userid
	private String userid="";
	//albumList
	//PictureID
	private String pictureID="";
	private List<AlbumResult> albumList=new ArrayList<AlbumResult>();
	
	
	//global variable
	private static final long serialVersionUID = 1L;
	public static Connection conn = null;

	public String executeGetAlbumName() throws SQLException{
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
			
			statement.close();
			return SUCCESS;
			
		}
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

	public List<AlbumResult> getAlbumList() {
		return albumList;
	}

	public void setAlbumList(List<AlbumResult> albumList) {
		this.albumList = albumList;
	}

	public String getPictureID() {
		return pictureID;
	}

	public void setPictureID(String pictureID) {
		this.pictureID = pictureID;
	}
	
	
}
