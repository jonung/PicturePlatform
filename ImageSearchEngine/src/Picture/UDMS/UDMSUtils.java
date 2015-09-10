package Picture.UDMS;

import java.io.IOException;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

public class UDMSUtils {
	
	private static UDMSClient client = null;//µ¥Àý
	
	public static UDMSClient getInstance() throws ZkConnectException, IOException, RoleNotExistException, LoginException 
	{ 
		if(client==null)
	    {
			client= new UDMSClient("admin","123","c:\\conf\\udms.properties");
	    }
	    return client; 	    
	}
	
	public void close() throws IOException
	{
		client.close();
	}
	public static void main(String[] args) throws ZkConnectException, IOException, RoleNotExistException, LoginException
    {
          client=UDMSUtils.getInstance();
    }
}
