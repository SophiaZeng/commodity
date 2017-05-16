package cn.itcast.entity;
/**
 * 拜访实体类
 * @author asus
 *
 */
public class Visit {

	private Integer visitid;
	private String visitAddress;//拜访地址
	private String visitConent;//拜访内容
	
	//表示所属客户
	private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	//表示所属用户
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getVisitid() {
		return visitid;
	}
	public void setVisitid(Integer visitid) {
		this.visitid = visitid;
	}
	public String getVisitAddress() {
		return visitAddress;
	}
	public void setVisitAddress(String visitAddress) {
		this.visitAddress = visitAddress;
	}
	public String getVisitConent() {
		return visitConent;
	}
	public void setVisitConent(String visitConent) {
		this.visitConent = visitConent;
	}
}
