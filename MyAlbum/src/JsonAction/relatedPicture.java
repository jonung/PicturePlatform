package JsonAction;

import java.io.IOException;
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
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.api.ResultSet;
import udms.core.udbc.client.api.Statement;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

import DBTool.UDMSUtil;

import com.opensymphony.xwork2.ActionSupport;


public class relatedPicture extends ActionSupport{
	
	
	//username
	private String username="";
	//userid
	private String userid="";
	//searchText
	public String searchText;
	//resultString
	String resultString;
	
	
	private static final long serialVersionUID = 1L;
	public static UDMSClient client = null;

	public String execute()throws Exception{
		///////////////////////////////////getPicture///////////////////////////////////
		/*ClientResource client=new ClientResource("http://10.15.62.54:8300/Picture/default/Mark/"+searchText+"/No/0/9");
		Representation res=client.get();
		String json_str=res.getText();
		System.out.println(json_str);//for test
		JSONObject json_obj = new JSONObject(json_str);
		JSONArray json_array=json_obj.getJSONArray("records");
		
		JSONArray records=new JSONArray();
		for(int i=0;i<json_array.length();i++){
			JSONObject ob=new JSONObject();
			ob.put("PACS", json_array.getJSONObject(i).get("PACS"));
			ob.put("Mark", json_array.getJSONObject(i).get("Mark"));
			ob.put("PictureID", json_array.getJSONObject(i).get("PictureID"));
			ob.put("BookNo", json_array.getJSONObject(i).get("BookNo"));
			ob.put("CLC", json_array.getJSONObject(i).get("CLC"));
			records.put(ob);
		}*/
		
		if(client==null){//若client为空时，重连
			client=UDMSUtil.getInstance();
		}
		
		Statement statement = client.createStatement();
		statement.executeUpdate("use Engineering");
		String _query="select top 9 from PictureNew where Mark contain '"+searchText+"' limit 0,9";
		ResultSet rs= statement.executeQuery(_query);
		JSONArray records=new JSONArray();
		while(rs.next()){
			JSONObject ob=new JSONObject();
			String pacs="";
			if(rs.getCDS().getFeature("PACS")!=null){
				pacs=rs.getCDS().getFeature("PACS").toString();
			}
			String clc="";
			if(rs.getCDS().getFeature("CLC")!=null){
				clc=rs.getCDS().getFeature("CLC").toString();
			}
			String bookNo="";
			if(rs.getCDS().getFeature("BookNo")!=null){
				bookNo=rs.getCDS().getFeature("BookNo").toString();
			}
			String mark="";
			if(rs.getCDS().getFeature("Mark")==null){
				mark=rs.getCDS().getFeature("Mark").toString();
			}
			String pictureID=rs.getCDS().getFeature("PictureID").toString();
			
			ob.put("PACS", pacs);
			ob.put("Mark", mark);
			ob.put("PictureID", pictureID);
			ob.put("BookNo", bookNo);
			ob.put("CLC", clc);
			records.put(ob);
		}
		
		
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("records",records);
		resultString=jsonObj.toString();
		return SUCCESS;
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
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	

	
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}
}
