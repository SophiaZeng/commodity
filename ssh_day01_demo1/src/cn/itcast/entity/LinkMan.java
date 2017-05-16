package cn.itcast.entity;

public class LinkMan {

	private Integer lkmid;
	private String lkmName;
	private String lkmGender;
	private String lkmPhone;
	private String lkmMobile;
	
	//在联系人实体类里面表示所属客户，使用对象
	private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Integer getLkmid() {
		return lkmid;
	}
	public void setLkmid(Integer lkmid) {
		this.lkmid = lkmid;
	}
	public String getLkmName() {
		return lkmName;
	}
	public void setLkmName(String lkmName) {
		this.lkmName = lkmName;
	}
	public String getLkmGender() {
		return lkmGender;
	}
	public void setLkmGender(String lkmGender) {
		this.lkmGender = lkmGender;
	}
	public String getLkmPhone() {
		return lkmPhone;
	}
	public void setLkmPhone(String lkmPhone) {
		this.lkmPhone = lkmPhone;
	}
	public String getLkmMobile() {
		return lkmMobile;
	}
	public void setLkmMobile(String lkmMobile) {
		this.lkmMobile = lkmMobile;
	}
	
}
