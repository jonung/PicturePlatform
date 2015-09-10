package JsonAction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;
import org.jasig.cas.client.validation.Assertion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import udms.core.repository.api.Blob;
import udms.core.repository.api.CDS;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.api.ResultSet;
import udms.core.udbc.client.api.Statement;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

import DBTool.UDMSUtil;

import com.ckcest.sso.UserProfile;
import com.opensymphony.xwork2.ActionSupport;


public class pictureMetaData extends ActionSupport{
	
	
	//username
	private String username="";
	//userid
	private String userid="";
	//pictureID
	public String pictureID;
	//jsonMap
	Map<String, String> jsonMap=new HashMap<String, String>();
	
	public static UDMSClient client=null; 
	

	private static final long serialVersionUID = 1L;

	public String execute()throws Exception{
		///////////////////////////////////getPicture///////////////////////////////////
		if(client==null){//若client为空时，重连
			client= UDMSUtil.getInstance();
		}
		
		Statement statement=client.createStatement();
		statement.executeUpdate("use Engineering");
		String sql="select top 1 from PictureNew where PictureID='"+pictureID+"'";
		ResultSet rs= statement.executeQuery(sql);
		while(rs.next()){
			CDS tempCDS=rs.getCDS();
			String pacs;
			if(tempCDS.getFeature("PACS")==null){
				pacs="";
			}
			else{
				pacs=tempCDS.getFeature("PACS").toString();
			}
			String CLC;
			if(tempCDS.getFeature("CLC")==null){
				CLC="";
			}
			else{
				CLC=tempCDS.getFeature("CLC").toString();
			}
			String BookNo;
			if(tempCDS.getFeature("BookNo")==null){
				BookNo="";
			}
			else{
				BookNo=tempCDS.getFeature("BookNo").toString();
			}
			String Mark;
			if(tempCDS.getFeature("Mark")==null){
				Mark="";
			}
			else{
				Mark=tempCDS.getFeature("Mark").toString();
			}
			String PictureTags;
			if(tempCDS.getFeature("PictureTags")==null){
				PictureTags="";
			}
			else{
				PictureTags=tempCDS.getFeature("PictureTags").toString();
			}
			jsonMap.put("PictureTags", PictureTags);
			jsonMap.put("PACS",pacs);
			jsonMap.put("CLC", CLC);
			jsonMap.put("BookNo", BookNo);
			jsonMap.put("Mark", Mark);
		}
	
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
	
	
	
	
	
	//
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

	
	public Map<String, String> getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map<String, String> jsonMap) {
		this.jsonMap = jsonMap;
	}
}
