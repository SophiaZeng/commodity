package cn.itcast.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.Customer;
import cn.itcast.entity.LinkMan;
import cn.itcast.service.CustomerService;
import cn.itcast.service.LinkManService;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan>{

	private LinkMan linkMan = new LinkMan();
	public LinkMan getModel() {
		return linkMan;
	}
	
	private LinkManService linkManService;
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	
	//把customerService对象注入到action里面
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	//定义两个变量，一个代表上传文件，一个代表上传文件名称
	//上传文件，变量命名：表单文件上传项，name属性值
	private File upload;
	//上传文件名称，文件名称命名： 文件上传项name属性值FileName
	private String uploadFileName;
	// 上传文件mime类型
//	private String uploadContentType;
	
	//变量的get和set方法
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	//4 根据id查询
	public String showLinkMan() {
		//得到id
		int linkmanid = linkMan.getLkmid();
		LinkMan link = linkManService.findOne(linkmanid);
		
		//查询所有客户
		List<Customer> list = customerService.findAll();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("linkman", link);
		request.setAttribute("listCustomer", list);
		return "showLinkMan";
	}
	
	//3 联系人列表
	public String list() {
		List<LinkMan> list = linkManService.findAll();
		ServletActionContext.getRequest().setAttribute("list", list);
		return "list";
	}
	
	//2 添加联系人的方法
	public String addLinkman() throws IOException {
		
		//上传过程
		//判断是否需要上传文件
		if(upload != null) {
			//1 在上传到服务器文件夹里面创建文件
			File serverFile = new File("I:\\uploadtest"+"/"+uploadFileName);
			//2 把上传本地文件复制到服务器的文件中
			//第一个参数：上传文件
			//第二个参数：服务器文件
			FileUtils.copyFile(upload, serverFile);
		}
		
		//最终目的：把表单提交客户cid值，放到linkman的customer对象里面
		//使用模型驱动得到联系人信息，但是cid值不能得到
//		String cid = ServletActionContext.getRequest().getParameter("cid");
//		// 在联系人实体类有客户对象，把cid放到联系人客户的对象里面
//		//创建客户对象，把cid放到对象里面
//		Customer c = new Customer();
//		c.setCid(Integer.parseInt(cid));
//		//把对象放到linkman里面
//		linkMan.setCustomer(c);
		linkManService.add(linkMan);
		return "addLinkman";
	}
	
	//1 到新增联系人页面
	public String toAddPage() {
		// 查询所有客户，返回list集合
		// 把所有客户的list集合传递到页面下拉列表中		
		//因为之前在客户的service里面写了查询所有客户的方法，
		//调用customerService里面的方法就可以了
		// 把customerService对象注入到action里面
		List<Customer> listCustomer = customerService.findAll();
		ServletActionContext.getRequest().setAttribute("listCustomer", listCustomer);
		
		return "toAddPage";
	}


	
}
