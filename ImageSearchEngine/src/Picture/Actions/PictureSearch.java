package Picture.Actions;


import Picture.Models.PictureResult;
import Picture.Models.Classification;
import Picture.Models.Pages;

import javax.xml.ws.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
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

import org.jasig.cas.client.validation.Assertion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;


public class PictureSearch extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String srch=" ";
	private String clcText="default";//for clc compare
	//username
	private String username="";
	
	
	
	////////////////////////////////////////////
	
	
	private String clss;
	ActionContext context = ActionContext.getContext();  
    HttpServletRequest request;  
    HttpServletResponse response;
    SessionMap session;
    private Classification stt=new Classification();
    private String hwmany;
	private List<PictureResult> list_rslt=new ArrayList<PictureResult>();
	private String info_pgs;
	private String cur_pgs="1";
	private Pages pages=new Pages();
	private String nexturl;
	private String befourl;
	String str_totalCount="0";
	
	private String pageContent[]=new String[10];
	
	
	public void imgexe(){
		
	}
	@Override
	public String execute(){
		
		srch = srch.trim();
		
		//srch="hello";
		pages.setNUMPPGS("12");//设置每页显示的图片数
		String count[]=new String[11];
		
		int totalCount=0;
		int totalPages=0;
		int numpgs = 0;
		for(int i=0;i<11;i++){count[i]="0";}
		int tmppgs=Integer.valueOf(cur_pgs);
		try {
			System.out.println("searchtext:"+srch);
			numpgs=Integer.valueOf(pages.getNUMPPGS());
			String temp1=String.valueOf((tmppgs-1)*numpgs);
			ClientResource client=new ClientResource("http://10.15.62.54:8300/"+"Picture/"+clcText+"/Mark/"+srch+"/No/"+temp1+"/"+pages.getNUMPPGS());
			Representation res=client.get();
			String json_str=res.getText();
			System.out.println(json_str);//for test
			JSONObject json_obj = new JSONObject(json_str);
			JSONArray json_array=json_obj.getJSONArray("records");
			
			for(int i=0;i<json_array.length();i++)
			{
				PictureResult temp_rlst=new PictureResult();
				String PACS=(String) json_array.getJSONObject(i).get("PACS");
				String Mark=(String) json_array.getJSONObject(i).get("Mark");
				String BookNo=(String) json_array.getJSONObject(i).get("BookNo");
				
				if(Mark.length() > 11) {
					Mark = Mark.substring(0, 11)+"...";
				}
				String PictureID=(String) json_array.getJSONObject(i).get("PictureID");
				String CLC=(String) json_array.getJSONObject(i).get("CLC");
				temp_rlst.setBookNo(BookNo);
				temp_rlst.setPACS(PACS);
				temp_rlst.setMARK(Mark);
				temp_rlst.setPICID(PictureID);
				temp_rlst.setCLC(CLC);
				//temp_rlst.creatURL();
				//temp_rlst.creatRDURL();
				//temp_rlst.getTTL_PGS();
				//temp_rlst.creatBKURL();
				list_rslt.add(temp_rlst);
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ClientResource classClient=new ClientResource("http://10.15.62.54:8300/PictureCount/"+clcText+"/Mark/"+srch+"/No/0/100000");
			Representation classRes=classClient.get();
			String classJson_str=classRes.getText();
			System.out.println(classJson_str);
			JSONObject classJson_obj = new JSONObject(classJson_str);
			JSONArray classJson_array=classJson_obj.getJSONArray("records");
			
			count[0]=(String) classJson_array.getJSONObject(0).get("N");
			count[1]=(String) classJson_array.getJSONObject(0).get("O");
			count[2]=(String) classJson_array.getJSONObject(0).get("P");
			count[3]=(String) classJson_array.getJSONObject(0).get("Q");
			count[4]=(String) classJson_array.getJSONObject(0).get("R");
			count[5]=(String) classJson_array.getJSONObject(0).get("S");
			count[6]=(String) classJson_array.getJSONObject(0).get("T");
			count[7]=(String) classJson_array.getJSONObject(0).get("U");
			count[8]=(String) classJson_array.getJSONObject(0).get("V");
			count[9]=(String) classJson_array.getJSONObject(0).get("X");
			count[10]=(String) classJson_array.getJSONObject(0).get("Z");
			str_totalCount=(String) classJson_array.getJSONObject(0).getString("total");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		stt.setNNN("("+count[0]+")");
		stt.setOOO("("+count[1]+")");
		stt.setPPP("("+count[2]+")");
		stt.setQQQ("("+count[3]+")");
		stt.setRRR("("+count[4]+")");
		stt.setSSS("("+count[5]+")");
		stt.setTTT("("+count[6]+")");
		stt.setUUU("("+count[7]+")");
		stt.setVVV("("+count[8]+")");
		stt.setXXX("("+count[9]+")");
		stt.setZZZ("("+count[10]+")");
		//pgs.setNUMPPGS("12");
		
		//session.put("list_rslt", list_rslt);
		int tmpbefo;
		int tempnext;
		if(tmppgs-1<=0)tmpbefo=1;
		else tmpbefo=tmppgs-1;
		
		/*if(totalCount%numpgs==0)totalPages=totalCount/numpgs;
		else totalPages=totalCount/numpgs+1;*/
		totalCount=Integer.valueOf(str_totalCount);
		if(totalCount%numpgs==0)totalPages=totalCount/numpgs;
		else totalPages=totalCount/numpgs+1;
		
		if(tmppgs+1>totalPages) tempnext=totalPages;
		else tempnext=tmppgs+1;
		befourl="/Picture/search.action?cur_pgs="+String.valueOf(tmpbefo)+"&srch="+srch+"&clcText="+clcText;
		nexturl="/Picture/search.action?cur_pgs="+String.valueOf(tempnext)+"&srch="+srch+"&clcText="+clcText;
		//pageContent[0]=totalCount;
		username=getUserName();
		System.out.println("total"+totalPages);
		genneratePages(tmppgs,totalPages);
		
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
	
	
	private void genneratePages(int currentPages,int totalPages){
		if(totalPages<=10){
			for(int i=1;i<=totalPages;i++){
				if(i==currentPages){
					pageContent[i-1]="<a id='currentnum' >"+String.valueOf(i)+"</a>";
					continue;
				}
				pageContent[i-1]="<a id='num' href=/Picture/search.action?cur_pgs="+i+"&srch="+srch+"&clcText="+clcText+">"+i+"</a>";
			}
		}
		else{
			if(currentPages<=5){
				for(int i=1;i<=10;i++){
					if(i==currentPages){
						pageContent[i-1]="<a id='currentnum' >"+String.valueOf(i)+"</a>";
						continue;
					}
					pageContent[i-1]="<a id='num' href=/Picture/search.action?cur_pgs="+i+"&srch="+srch+"&clcText="+clcText+">"+i+"</a>";
				}
			}
			else{
				if(currentPages+5<totalPages){
					int j=currentPages-4;
					for(int i=0;i<10;i++,j++){
						if(j==currentPages){
							pageContent[i]="<a id='currentnum' >"+String.valueOf(j)+"</a>";
							continue;
						}
						pageContent[i]="<a id='num' href=/Picture/search.action?cur_pgs="+j+"&srch="+srch+"&clcText="+clcText+">"+j+"</a>";
					}
					
				}
				else{
					int j=totalPages-9;
					for(int i=0;i<10;i++,j++){
						if(j==currentPages){
							pageContent[i]="<a id='currentnum' >"+String.valueOf(j)+"</a>";
							continue;
						}
						pageContent[i]="<a id='num' href=/Picture/search.action?cur_pgs="+j+"&srch="+srch+"&clcText="+clcText+">"+j+"</a>";
					}
				}
					
			
			}
		}
		
	}
	
	
	
	
	//////////////////////////////////////////
	public String getClcText() {
		return clcText;
	}
	public void setClcText(String clcText) {
		this.clcText = clcText;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getStr_totalCount() {
		return str_totalCount;
	}
	public void setStr_totalCount(String str_totalCount) {
		this.str_totalCount = str_totalCount;
	}
	public String[] getPageContent() {
		return pageContent;
	}
	public void setPageContent(String[] pageContent) {
		this.pageContent = pageContent;
	}
	public String getCur_pgs() {
		return cur_pgs;
	}
	public void setCur_pgs(String cur_pgs) {
		this.cur_pgs = cur_pgs;
	}

	
	public String getNexturl() {
		return nexturl;
	}
	public void setNexturl(String nexturl) {
		this.nexturl = nexturl;
	}
	public String getBefourl() {
		return befourl;
	}
	public void setBefourl(String befourl) {
		this.befourl = befourl;
	}
	public String getInfo_pgs() {
		return info_pgs;
	}
	public void setInfo_pgs(String info_pgs) {
		this.info_pgs = info_pgs;
	}

	private Pages pgs=new Pages();
    
	public Pages getPgs() {
		return pgs;
	}
	public void setPgs(Pages pgs) {
		this.pgs = pgs;
	}
	
	public Classification getStt() {
		return stt;
	}
	public void setStt(Classification stt) {
		this.stt = stt;
	}
	public void setSession(Map map) {  
        this.session = (SessionMap) map;  
    }  
    public void setServletRequest(HttpServletRequest request) {  
        this.request = request;  
    }  
    public void setServletResponse(HttpServletResponse response) {  
        this.response = response;  
    }  
    
	public List<PictureResult> getList_rslt() {
		return list_rslt;
	}
	public void setList_rslt(List<PictureResult> list_rslt) {
		this.list_rslt = list_rslt;
	}
	public String getClss() {
		return clss;
	}
	public void setClss(String clss) {
		this.clss = clss;
	}
	
	private String strt;
	public String getStrt() {
		return strt;
	}
	public void setStrt(String strt) {
		this.strt = strt;
	}
	public String getHwmany() {
		return hwmany;
	}
	public void setHwmany(String hwmany) {
		this.hwmany = hwmany;
	}
	public String getSrch() {
		return srch;
	}
	public void setSrch(String srch) {
		this.srch = srch;
	}
}

