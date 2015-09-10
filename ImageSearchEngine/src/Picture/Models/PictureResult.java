package Picture.Models;
import java.io.IOException;

import udms.core.security.accesscontrol.exception.LoginException;
import udms.core.security.accesscontrol.exception.RoleNotExistException;
import udms.core.utils.zookeeper.ZkConnectException;
import Picture.UDMS.Data_UDMS;
import Picture.UDMS.UDMSUtils;

public class PictureResult {
	private String PACS;
	private String MARK;
	private String PICID;
	private String CLC;
	private String IMGURL;
	private String RDURL;
	private String BKURL;
	private String TTL;
	private String TTLPGS;
	private String bookNo;
	private String tags;


	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	public String getTTL() {
		return TTL;
	}
	public void setTTL(String tTL) {
		TTL = tTL;
	}
	public String getTTLPGS() {
		return TTLPGS;
	}
	public void setTTLPGS(String tTLPGS) {
		TTLPGS = tTLPGS;
	}
	
	public String getRDURL() {
		return RDURL;
	}
	public void setRDURL(String rDURL) {
		RDURL = rDURL;
	}
	public String getBKURL() {
		return BKURL;
	}
	public void setBKURL(String bKURL) {
		BKURL = bKURL;
	}
	public String getIMGURL() {
		return IMGURL;
	}
	public void setIMGURL(String iMGURL) {
		IMGURL = iMGURL;
	}
	public String getMARK() {
		return MARK;
	}
	public void setMARK(String mARK) {
		MARK = mARK;
	}
	public String getPICID() {
		return PICID;
	}
	public void setPICID(String pICID) {
		PICID = pICID;
	}
	public String getPACS() {
		return PACS;
	}
	public void setPACS(String pACS) {
		PACS = pACS;
	}
	public String getCLC() {
		return CLC;
	}
	public void setCLC(String cLC) {
		CLC = cLC;
	}
	public String creatURL(){
		/*PICID.replaceAll("b", "/");
		PICID.replaceAll("p", "/");
		PICID.replaceAll("c", "/");*/
		IMGURL=PICID.replace('b', '/');
		IMGURL=IMGURL.replace('p', '/');
		IMGURL=IMGURL.replace('c', '/');
		IMGURL="http://10.15.62.54:9192/GetPicture"+IMGURL;
		return IMGURL;
	}
	public String creatRDURL(){
		RDURL=PICID;
		int pos=find(RDURL,'c');
		RDURL=RDURL.substring(0,pos);
		RDURL=RDURL.replaceAll("b", "BookNo=");
		RDURL=RDURL.replaceAll("p", "&PageNo=");
		RDURL="/book/SinglePageBook.html?"+RDURL+"&TotalPage=1000";
		return RDURL;
	}
	public String creatBKURL(){
		int S_booknum=find(PICID,'b');
		int E_booknum=find(PICID,'p');
		String booknum=PICID.substring(S_booknum+1, E_booknum);
		BKURL="/Engineering/BookMeta?BookNo="+booknum;
		return BKURL;
	}
	public void getTTL_PGS() throws Exception{
		Data_UDMS dtums=new Data_UDMS();
		UDMSUtils dttls=new UDMSUtils();
		dtums.client=dttls.getInstance();
		int S_booknum=find(PICID,'b');
		int E_booknum=find(PICID,'p');
		String booknum=PICID.substring(S_booknum+1, E_booknum);
		System.out.println("!!!!!!!!!!!!!!booknum:"+booknum);
		TTL=dtums.getTitle(booknum);
		System.out.println("!!!!!!!!!!!"+TTL);
		TTLPGS=String.valueOf(dtums.getTotalPage(booknum));
		//dttls.close();
	}
	public int find(String str,char trg){
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)==trg)
				return i;
		}
		return -1;
	}
	

}
