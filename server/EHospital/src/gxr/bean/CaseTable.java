package gxr.bean;

public class CaseTable {

	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private String id;
	private String userId;
	private String createTime;
	private String changeTime;
	private String content;
	 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	public String getUserId(){
		return userId;
	}
	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setChangeTime(String changeTime){
		this.changeTime=changeTime;
	}
	public String getChangeTime(){
		return changeTime;
	}
	
	
}
