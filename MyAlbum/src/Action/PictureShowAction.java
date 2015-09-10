package Action;

import java.io.IOException;
import java.io.InputStream;
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

import DBTool.UDMSUtil;

import com.opensymphony.xwork2.ActionSupport;

public class PictureShowAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pictureID;
	HttpServletResponse response = ServletActionContext.getResponse();  
    ServletOutputStream sout;
    public static UDMSClient client = null; 
	
	/*public static UDMSClient getInstance(String userName,String password,String udmsProperties) throws IOException, RoleNotExistException, LoginException, ZkConnectException { 
	
		if(client==null)
	    {
			client=new UDMSClient(userName,password,udmsProperties); 				
	    }	
	    return client; 
	}*/
	
	public String execute()throws Exception{
		try{
			InputStream in=null;
			if(client==null){//若client为空时，重连
				//client=getInstance("admin","123","c:\\conf\\udms.properties");
				client = UDMSUtil.getInstance();
				
			}	
			try {
				
				byte[] picture= null;
				CDS cds = null;
				sout = response.getOutputStream(); 
				
				List<InputStream> streamTemp=new ArrayList<InputStream>();
				Statement statement=client.createStatement();
				statement.executeUpdate("use Engineering");
				ResultSet rs=statement.executeQuery("select from PictureNew where PictureID='"+pictureID+"'");
				while(rs.next()){
					cds=rs.getCDS();		
					Blob pictureBlob=(Blob) cds.getFeature("Picture");
					if(pictureBlob==null)break;	
					String cdsid="UDMS.Engineering:PictureNew_"+pictureID;
					System.out.println(cdsid);
					streamTemp=client.getBlob(cdsid, "Picture");
					in=streamTemp.get(0);
					picture = new byte[4096];
			        while ( -1 != in.read( picture ) ) {
			             sout.write( picture );
			        }    
			        
			        			        
			        response.setContentType("image/jpg");
			       			     
					sout.flush();
					sout.close();
					
				
					
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
		
		return null;
		//return SUCCESS;
	}
	
	public void setPictureID(String pictureID){
		this.pictureID=pictureID;
	}
	public String getPictureID(){
		return pictureID;
	}
}
