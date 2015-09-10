package Picture.Actions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import udms.core.repository.api.Blob;
import udms.core.repository.api.CDS;
import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.udbc.client.api.ResultSet;
import udms.core.udbc.client.api.Statement;
import udms.core.udbc.client.impl.UDMSClient;
import udms.core.utils.zookeeper.ZkConnectException;

import Picture.Models.BookInfo;
import Picture.Models.PictureResult;

import com.opensymphony.xwork2.ActionSupport;

public class PictureData extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String pictureID;
	private String bookNo;
	private BookInfo m_bookInfo=new BookInfo();
	private String searchText;
	private PictureResult m_pictureResult=new PictureResult();
    

	
	
	public static UDMSClient client=null; 
	
	public static UDMSClient getInstance(String userName,String password,String udmsProperties) throws IOException, RoleNotExistException, LoginException, ZkConnectException { 
	
		if(client==null)
	    {
			client=new UDMSClient(userName,password,udmsProperties); 				
	    }	
	    return client; 
	}
	
	
	public String execute()throws Exception{
		///////////////////////////Get Book Meta Data////////////////////////////////////
		int start= pictureID.indexOf("b");
		int end=pictureID.indexOf("p");
		bookNo=pictureID.substring(start+1,end);
		System.out.println("bookNo:"+bookNo);
		ClientResource client=new ClientResource("http://10.15.62.57:9136/Book/GenInfo/"+bookNo);
		if(client!=null){
			Representation res=null;
			String json_str = null;
			try {
				res=client.get();
				json_str = res.getText();
				System.out.println(json_str);//for test
				JSONObject json_obj = new JSONObject(json_str);
				JSONObject json_bookinfo=json_obj.getJSONObject("BookInfo");
				String title=(String) json_bookinfo.get("title");
				String bookTag=(String) json_bookinfo.get("bookTag");
				String subject=(String) json_bookinfo.get("subject");
				String zhongtuClass=(String ) json_bookinfo.get("÷–Õº∑÷¿‡");
				String date=(String) json_bookinfo.get("date");
				String totalPage=String.valueOf(json_bookinfo.get("totalPage"));
				String publisher=(String) json_bookinfo.get("publisher");
				String creator=(String) json_bookinfo.get("creator");
				m_bookInfo.setZhongtuClass(zhongtuClass);
				m_bookInfo.setBookTag(bookTag);
				m_bookInfo.setTitle(title);
				m_bookInfo.setSubject(subject);
				m_bookInfo.setDate(date);
				m_bookInfo.setTotalPage(totalPage);
				m_bookInfo.setPublisher(publisher);
				m_bookInfo.setCreator(creator);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(json_str);//for test
				
			}
			
		}
		
		////////////////////////////Get Picture Meta Data////////////////////////////////////
		client=new ClientResource("http://localhost:8080/MyPicture/pictureMetaData.action?pictureID="+pictureID);
		if(client.get()!=null){
			Representation res=client.get();
			String json_str=res.getText();
			
			System.out.println(json_str);//for test
			JSONObject json_obj = new JSONObject(json_str);
			String mark=json_obj.getString("Mark");
			String bookNo=json_obj.getString("BookNo");
			String PictureTags=json_obj.getString("PictureTags");
			m_pictureResult.setBookNo(bookNo);
			m_pictureResult.setMARK(mark);
			m_pictureResult.setTags(PictureTags);
		}
	

		return SUCCESS;
	}
	
	
	///////////////////////////////////
	public void setPictureID(String pictureID){
		this.pictureID=pictureID;
	}
	public String getPictureID(){
		return pictureID;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public BookInfo getM_bookInfo() {
		return m_bookInfo;
	}
	public void setM_bookInfo(BookInfo mBookInfo) {
		m_bookInfo = mBookInfo;
	}
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public PictureResult getM_pictureResult() {
		return m_pictureResult;
	}
	public void setM_pictureResult(PictureResult mPictureResult) {
		m_pictureResult = mPictureResult;
	}
}
