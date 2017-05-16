package cn.itcast.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.Customer;
import cn.itcast.entity.User;
import cn.itcast.entity.Visit;
import cn.itcast.service.CustomerService;
import cn.itcast.service.UserService;
import cn.itcast.service.VisitService;

public class VisitAction extends ActionSupport implements ModelDriven<Visit>{

	private Visit visit = new Visit();
	
	public Visit getModel() {
		return visit;
	}
	
	private VisitService visitService;

	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}
	
	private UserService userService;
	private CustomerService customerService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	//2 添加的方法
	public String addVisit() {
		visitService.add(visit);
		return "addVisit";
	}
	
	//1 到添加页面
	public String toAddPage() {
		// 查询所有用户
		List<User> listUser = userService.findAll();
		// 查询所有客户
		List<Customer> listCustomer = customerService.findAll();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("listUser", listUser);
		request.setAttribute("listCustomer", listCustomer);
		return "toAddPage";
	}

}
