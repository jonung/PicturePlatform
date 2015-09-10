package Picture.Actions;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import udms.core.repository.api.Blob;
import udms.core.repository.api.CDS;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.api.ResultSet;
import udms.core.udbc.client.api.Statement;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

import Picture.UDMS.UDMSUtils;

import com.opensymphony.xwork2.ActionSupport;

public class OnePictureShow extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pictureID;
	private String clc;
    public static UDMSClient client=null;
    private Connection conn = null;
    
    // the variable that will display on the scene
    private String mark;
    private String source;
	private String tags;
	private String classify;
	private String time;
	private String creator;
	private String hot;
	

	public String execute()throws Exception{
		
		if(clc=="picture"){
			try{
				InputStream in=null;
				if(client==null){//若client为空时，重连
					client=UDMSUtils.getInstance();
				}	
				try {
					
					Statement statement=client.createStatement();
					statement.executeUpdate("use Engineering");
					ResultSet rs=statement.executeQuery("select from PictureNew where PictureID='"+pictureID+"'");
					while(rs.next()){
						
					} 
				} catch (Exception e) {//client挂掉时，断掉连接，等待下一次请求GetCatalog时重连
					// TODO Auto-generated catch block
					client.close();
					client=null;
					e.printStackTrace();
				}	
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(clc=="ALbum"){
			
		}
		else if(clc=="picture"){
			
		}
		
		
		return SUCCESS;
	}
	
	
	
	
	
	
	//////////////////////////////////////////////////
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getClc() {
		return clc;
	}
	public void setClc(String clc) {
		this.clc = clc;
	}
	public void setPictureID(String pictureID){
		this.pictureID=pictureID;
	}
	public String getPictureID(){
		return pictureID;
	}
}
