package Picture.UDMS;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import udms.core.repository.api.CDS;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

public class Data_UDMS {
	public static UDMSClient client=null; 
	
	public static UDMSClient getInstance(String userName,String password,String udmsProperties) throws IOException, RoleNotExistException, LoginException, ZkConnectException { 
	
		if(client==null)
	    {
			client=new UDMSClient(userName,password,udmsProperties); 				
			System.out.println(userName+"  "+password+"  "+udmsProperties);
	    }	
	    return client; 
	}
	
	public static int getTotalPage(String bookNo) throws Exception{
		int totalPage=1000;
		CDS cds=null;
		String cdsid="UDMS.Engineering:TBook_"+bookNo;
		if(client.getCDS(cdsid)!=null) cds=client.getCDS(cdsid);
		if(cds==null)return totalPage;
		totalPage=(Integer) cds.getFeature("PageNum");
		return totalPage;
	}
	
	public static String getTitle(String bookNo) throws Exception{
		String title="";
		CDS cds=null;
		String cdsid="UDMS.Engineering:TBook_"+bookNo;
		if(client.getCDS(cdsid)!=null) cds=client.getCDS(cdsid);
		if(cds==null)return title;
		title=(String) cds.getFeature("Title");
		return title;
	}
}
