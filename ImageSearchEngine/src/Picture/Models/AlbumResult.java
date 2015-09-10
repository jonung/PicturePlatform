package Picture.Models;

public class AlbumResult {
	private String id;
	private String name;
	private String username;
	private String tags;
	private String classify;
	private String shareCount;
	private String picCount;
	private String [] cover=new String[4];
	
	public String[] getCover() {
		return cover;
	}
	public void setCover(String[] cover) {
		this.cover = cover;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setId(String id) {
		this.id = id;
	}


	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getShareCount() {
		return shareCount;
	}
	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
	}
	public String getPicCount() {
		return picCount;
	}
	public void setPicCount(String picCount) {
		this.picCount = picCount;
	}
	
	
}
