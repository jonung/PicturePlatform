package Picture.Models;

public class BookInfo {
	private String title;
	private String bookTag;
	private String subject;
	private String date;
	private String totalPage;
	private String publisher;
	private String creator;
	private String zhongtuClass;
	public String getZhongtuClass() {
		return zhongtuClass;
	}
	public void setZhongtuClass(String zhongtuClass) {
		this.zhongtuClass = zhongtuClass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBookTag() {
		return bookTag;
	}
	public void setBookTag(String bookTag) {
		this.bookTag = bookTag;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
