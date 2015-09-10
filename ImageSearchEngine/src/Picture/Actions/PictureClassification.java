package Picture.Actions;
import Picture.Models.PictureResult;
import Picture.Models.Classification;
import Picture.Models.Pages;
import Picture.Models.ResultCount;

import javax.xml.ws.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.interceptor.ServletRequestAware;  
import org.apache.struts2.interceptor.ServletResponseAware;  
import org.apache.struts2.interceptor.SessionAware;  
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;   
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class PictureClassification extends ActionSupport {
	private String srch="";
	List<ResultCount> list_count=new ArrayList<ResultCount>();
	private List<PictureResult> list_rslt=new ArrayList<PictureResult>();

	

	@Override
	public String execute(){
		try {
			ClientResource client=new ClientResource("http://10.15.62.54:9192/"+"Picture/Mark/"+srch+"/No/0/100000");
			//ClientResource client=new ClientResource("/Picture/Mark/"+srch+"/No/0/100000");
			Representation res=client.get();
			String json_str=res.getText();
			JSONObject json_obj = new JSONObject(json_str);
			JSONArray json_array=json_obj.getJSONArray("records");
			
			for(int i=0;i<json_array.length();i++)
			{
				PictureResult temp_rlst=new PictureResult();
				String CLC=(String) json_array.getJSONObject(i).get("CLC");
				temp_rlst.setCLC(CLC);
				list_rslt.add(temp_rlst);
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count[]=new int[11];
		
		for(int i=0;i<list_rslt.size();i++){
			PictureResult temp=new PictureResult();
			temp=list_rslt.get(i);
			String temp_str=temp.getCLC();
			switch(temp_str.charAt(0)){
				case 'N':count[0]++;break;
				case 'O':count[1]++;break;
				case 'P':count[2]++;break;
				case 'Q':count[3]++;break;
				case 'R':count[4]++;break;
				case 'S':count[5]++;break;
				case 'T':count[6]++;break;
				case 'U':count[7]++;break;
				case 'V':count[8]++;break;
				case 'X':count[9]++;break;
				case 'Z':count[10]++;break;
			}
		}
		for(int i=0;i<11;i++){
			ResultCount temp=new ResultCount();
			temp.setCount(String.valueOf(count[i]));
			list_count.add(temp);
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject( list_count );
		
		return "success";
	}

	/////////////////////////////////////////////////////
	/*setter and getter*/
	public String getSrch() {
		return srch;
	}
	public void setSrch(String srch) {
		this.srch = srch;
	}
}
