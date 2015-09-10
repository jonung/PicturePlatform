package JsonAction;

import java.io.IOException;



import org.json.JSONArray;
import org.json.JSONObject;

import udms.core.repository.api.CDS;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.api.ResultSet;
import udms.core.udbc.client.api.Statement;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

import DBTool.UDMSUtil;

import com.opensymphony.xwork2.ActionSupport;



public class otherPictureInBook extends ActionSupport{
	
	//bookNo
	private String bookNo;
	//resultString
	String resultString;
	
	private static final long serialVersionUID = 1L;
	
	public static UDMSClient client=null; 
	

	public String execute()throws Exception{
		
		///////////////////////////////////getAlbums///////////////////////////////////
		if(client==null){//若client为空时，重连
			client= UDMSUtil.getInstance();
		}
		
		Statement statement=client.createStatement();
		statement.executeUpdate("use Engineering");
		String sql="select top 9 from PictureNew where PictureID contain '"+bookNo+"'";
		ResultSet rs= statement.executeQuery(sql);
		JSONArray records=new JSONArray();
		while(rs.next()){
			CDS tempCDS=rs.getCDS();
			JSONObject ob=new JSONObject();
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
			String PictureID;
			if(tempCDS.getFeature("PictureID")==null){
				PictureID="";
			}
			else{
				PictureID=tempCDS.getFeature("PictureID").toString();
			}
			ob.put("PACS",pacs);
			ob.put("CLC", CLC);
			ob.put("BookNo", BookNo);
			ob.put("Mark", Mark);
			ob.put("PictureID", PictureID);
			records.put(ob);
		}
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("records",records);
		resultString=jsonObj.toString();
		return SUCCESS;
	}
	
	
	
	
	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

}
