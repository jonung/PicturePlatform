package DBTool;

import java.io.IOException;

import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

public class UDMSUtil {

	private static UDMSClient client=null; 
	
	public static UDMSClient getInstance() throws IOException, RoleNotExistException, LoginException, ZkConnectException { 
		
		if(client==null)
	    {
			client=new UDMSClient("admin","123","c:\\conf\\udms.properties"); 				
	    }	
	    return client; 
	}
	
}
