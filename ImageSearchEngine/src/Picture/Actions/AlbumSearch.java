package Picture.Actions;


import DBTool.DB18Util;
import Picture.Models.AlbumResult;
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
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.mysql.jdbc.PreparedStatement;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;   

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


public class AlbumSearch extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchText=" ";
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
    
	private List<AlbumResult> albumList=new ArrayList<AlbumResult>();
	
	
	//////////////////////////////////////////////////////////////////////
	private String info_pgs;
	private String cur_pgs="1";
	private Pages pages=new Pages();
	private String nexturl;
	private String befourl;
	String str_totalCount="0";
	

	private Pages pgs=new Pages();
    
	private String strt;
	
	//public static Connection conn = null;
	

	public String execute(){
		Connection conn = null;
		//solve mysql thread pool
		/*
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://10.15.62.59:3306/userinfo");
		p.setUrl("jdbc:mysql://localhost:3306/mysql");
	    p.setDriverClassName("com.mysql.jdbc.Driver");
	    p.setUsername("root");
	    p.setPassword("Cadal205");
	    p.setJmxEnabled(true);
	    p.setTestWhileIdle(false);
	    p.setTestOnBorrow(true);
	    p.setValidationQuery("SELECT 1");
	    p.setTestOnReturn(false);
	    p.setValidationInterval(30000);
	    p.setTimeBetweenEvictionRunsMillis(30000);
	    p.setMaxActive(100);
	    p.setInitialSize(10);
	    p.setMaxWait(10000);
	    p.setRemoveAbandonedTimeout(60);
	    p.setMinEvictableIdleTimeMillis(30000);
	    p.setMinIdle(10);
	    p.setLogAbandoned(true);
	    p.setRemoveAbandoned(true);
	    p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
	      "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
	    DataSource datasource = new DataSource();
	    datasource.setPoolProperties(p);
	    
	    Connection conn = null;
	    
	    conn = datasource.getConnection();
	    String sql = "";
	    java.sql.PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    preparedStatement.setString(1, x);
	    */
		//
		searchText = searchText.trim();
		//srch="hello";
		
		pages.setNUMPPGS("12");
		String count[]=new String[11];
		
		int totalCount=0;
		int totalPages=0;
		int numpgs = 0;
		for(int i=0;i<11;i++){count[i]="0";}
		int tmppgs=Integer.valueOf(cur_pgs);
		try {
			
			
			System.out.println("searchtext:"+searchText);
			numpgs=Integer.valueOf(pages.getNUMPPGS());
			String indexStart=String.valueOf((tmppgs-1)*numpgs);
			
			if(conn==null){
				conn= DB18Util.connectMySql();
			}
			
			
			Statement statement=conn.createStatement();
			String sql="select * from userinfo.album where isDeleted = 0 and private = 0 and name like '%"+searchText+""+"%' limit " +indexStart+","+pages.getNUMPPGS();
			statement = conn.prepareStatement(sql);
			
			//fixed by gongjun
			/*
			String sql = "select * from userinfo.album where isDeleted = 0 and private = 0 and name like ? limit ?,?";
			java.sql.PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "%" + searchText + "%");
			statement.setInt(2, Integer.parseInt(indexStart));
			statement.setInt(3,Integer.parseInt(pages.getNUMPPGS()));
			ResultSet rs=statement.executeQuery();
			*/
			
			//fixed by gongjun
			
			ResultSet rs=statement.executeQuery(sql);
			
			
			while(rs.next()){
				AlbumResult m_album=new AlbumResult();
				m_album.setName(rs.getString("name"));
				String albumID=rs.getString("id");
				m_album.setId(albumID);
				
				statement=conn.createStatement();
				sql="select * from userinfo.pictureStore where isDeleted = 0 "+" and albumID="+albumID+" limit 0,4";
				statement = conn.prepareStatement(sql);
				
				
				ResultSet rstemp=statement.executeQuery(sql);
				String [] cover=new String[4];
				int i=0;
				while(rstemp.next()){
					cover[i]=rstemp.getString("pictureID");
					i++;
				}
				
				if(cover[0] == "" || cover[0] == null) //¿ÕµÄÍ¼²á²»ÏÔÊ¾
					return SUCCESS;
				
				for(int j=0;j<4;j++){
					if(cover[j]==""||cover[j]==null){
						cover[j]="images/img02.png";
					}
					else{
						cover[j]="pictureShowAction.action?pictureID="+cover[j];
					}
					
				}
				
				m_album.setCover(cover);
				
				m_album.setClassify(rs.getString("classID"));
				
				m_album.setShareCount(rs.getString("sharedCount"));
				m_album.setTags(rs.getString("tags"));
				m_album.setUsername(rs.getString("userID"));
				albumList.add(m_album);
			}
			
			rs.close();
			statement.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
			
			if(conn==null){
				conn= DB18Util.connectMySql();
			}
			
			Statement statement=conn.createStatement();
			String sql="select count(*) from userinfo.album where isDeleted = 0 and private = 0 and name like '%"+searchText+"%'";
			statement = conn.prepareStatement(sql);
			ResultSet rs=statement.executeQuery(sql);
			while(rs.next()){
				str_totalCount=rs.getString("COUNT(*)");
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		//pgs.setNUMPPGS("12");
		
		//session.put("list_rslt", list_rslt);
		int tmpbefo;
		int tempnext;
		if(tmppgs-1<=0)tmpbefo=1;
		else tmpbefo=tmppgs-1;
		
		if(totalCount%numpgs==0)totalPages=totalCount/numpgs;
		else totalPages=totalCount/numpgs+1;
		totalCount=Integer.valueOf(str_totalCount);
		if(totalCount%numpgs==0)totalPages=totalCount/numpgs;
		else totalPages=totalCount/numpgs+1;
		
		if(tmppgs+1>totalPages) tempnext=totalPages;
		else tempnext=tmppgs+1;
		befourl="/Picture/albumSearch.action?cur_pgs="+String.valueOf(tmpbefo)+"&srch="+searchText+"&clcText="+clcText;
		nexturl="/Picture/albumSearch.action?cur_pgs="+String.valueOf(tempnext)+"&srch="+searchText+"&clcText="+clcText;
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
				pageContent[i-1]="<a id='num' href=/Picture/albumSearch.action?cur_pgs="+i+"&searchText="+searchText+"&clcText="+clcText+">"+i+"</a>";
			}
		}
		else{
			if(currentPages<=5){
				for(int i=1;i<=10;i++){
					if(i==currentPages){
						pageContent[i-1]="<a id='currentnum' >"+String.valueOf(i)+"</a>";
						continue;
					}
					pageContent[i-1]="<a id='num' href=/Picture/albumSearch.action?cur_pgs="+i+"&searchText="+searchText+"&clcText="+clcText+">"+i+"</a>";
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
						pageContent[i]="<a id='num' href=/Picture/albumSearch.action?cur_pgs="+j+"&searchText="+searchText+"&clcText="+clcText+">"+j+"</a>";
					}
					
				}
				else{
					int j=totalPages-9;
					for(int i=0;i<10;i++,j++){
						if(j==currentPages){
							pageContent[i]="<a id='currentnum' >"+String.valueOf(j)+"</a>";
							continue;
						}
						pageContent[i]="<a id='num' href=/Picture/albumSearch.action?cur_pgs="+j+"&searchText="+searchText+"&clcText="+clcText+">"+j+"</a>";
					}
				}
					
			
			}
		}
		
	}
	
	
	
	
	/////
	public void imgexe(){
		
	}
	
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
	
	public List<AlbumResult> getAlbumList() {
		return albumList;
	}
	public void setAlbumList(List<AlbumResult> albumList) {
		this.albumList = albumList;
	}
	
	public String getStr_totalCount() {
		return str_totalCount;
	}
	public void setStr_totalCount(String str_totalCount) {
		this.str_totalCount = str_totalCount;
	}

	private String pageContent[]=new String[10];
	
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
    
    public String getClss() {
		return clss;
	}
	public void setClss(String clss) {
		this.clss = clss;
	}
	
	
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
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}

