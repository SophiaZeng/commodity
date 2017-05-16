package cn.itcast.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.entity.Customer;
import cn.itcast.entity.PageBean;
import cn.itcast.service.CustomerService;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

	private Customer customer = new Customer();
	public Customer getModel() {
		return customer;
	}
	
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	public Customer getCustomer() {
		return customer;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	private List<Customer> list;
	public List<Customer> getList() {
		return list;
	}
	
	//属性封装得到datagrid传递的值
	private int page; //当前页
	private int rows; //每页记录数
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//添加的方法
	public String addCustomerJson() throws IOException {
		customerService.add(customer);
		//返回值  {"success",true}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		String json = JSON.toJSONString(map);
		//向页面输出
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
		
		return NONE;
	}

	//分页操作
	public String getFindAllPageJson() throws IOException {
		//开始位置
		int begin = (page-1)*rows;
		List<Customer> list = customerService.findPageCustomer(begin,rows);
		//总记录数
		int total = customerService.findCount();
		//转换json数据格式
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", total);
		map.put("rows", list);
		String json = JSON.toJSONString(map);
		//向页面输出
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
		return NONE;
	}
	
	//返回json数据的方法-查询所有客户
	public String getListJson() throws Exception {
		List<Customer> list = customerService.findAll();
		//把list转换json格式
		//使用fastjson实现
//		String jsonlist = JSON.toJSONString(list);
		//[{"cid":1,"custLevel":"vip客户","custName":"百度","custSource":"网络"
//		System.out.println(jsonlist);
		//最终变成 json格式 {"total":20,"rows":[{},{}]}
		//实现思想： 创建map集合，第一个key是total，第二个key是rows，最终把map集合转换json数据
		//1 创建map集合
		Map<String,Object> map = new HashMap<String,Object>();
		//2 向map里面放total
		map.put("total", list.size());
		//3向map里面放rows
		map.put("rows", list);
		//4 把map转换json数据格式
		String json = JSON.toJSONString(map);
		//{"total":11,"rows":[{"cid":1,"custLevel":"vip客户","custName":"百度"
//		System.out.println(json);
		
		//5 把json数据输出到页面中
		//使用response输出
		HttpServletResponse response = ServletActionContext.getResponse();
		//如果数据有中文，乱码问题
		//建议：因为输出json格式，application/json
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
		return NONE;
	}
	
	//使用属性封装得到当前页
	private Integer currentPage;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	//根据来源统计
	public String selectSource() {
		List list = customerService.findCountSource();
		ServletActionContext.getRequest().setAttribute("list", list);
		return "selectSource";
	}

	//根据级别统计
	public String selectLevel() {
		List list = customerService.findCountLevel();
		ServletActionContext.getRequest().setAttribute("list", list);
		return "selectLevel";
	}
	
	//多条件查询的方法
	public String select() {
		//使用模型驱动获取表单提交数据
		List<Customer> list = customerService.findMoreCondition(customer);
		ServletActionContext.getRequest().setAttribute("list", list);
		return "select";
	}
	
	//到查询页面中
	public String toSelectPage() {
		return "toSelectPage";
	}
	
	//条件查询的方法
	public String listcondition() {
		//使用模型驱动得到值
		List<Customer> list = customerService.findAllCondition(customer);
		ServletActionContext.getRequest().setAttribute("list", list);
		return "listcondition";
	}
	
	//分页列表
	public String listpage() {
		//调用方法返回封装之后的pageBean对象
		PageBean pageBean = customerService.findPage(currentPage);
		//pageBean对象放到域对象
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "listpage";
	}
	
	//修改操作- 真正修改数据库
	public String update() {
		//使用模型驱动获取表单提交数据
		customerService.update(customer);
		return "update";
	}

	//修改操作- 根据id查询
	public String showCus() {
		//得到要修改客户的cid值
		int cid = customer.getCid();
		//根据cid查询客户对象
		Customer cus = customerService.findOne(cid);
		//传递到页面中显示
		ServletActionContext.getRequest().setAttribute("customer", cus);
		return "showCus";
	}
	
	//删除客户的方法
	public String deleteCus() {
		//1 查询
		int cid = customer.getCid();
		Customer c = customerService.findOne(cid);
		//2 根据查询对象删除
		customerService.delete(c);
		return "deleteCus";
	}
	
	//客户列表
	public String list() {
//		List<Customer> list = customerService.findAll();
		//放到域对象
//		ServletActionContext.getRequest().setAttribute("list", list);
		
		//放到值栈
		list = customerService.findAll();
		return "list";
	}
	
	//2 添加客户功能
	public String addCustomer() {
		//调用方法实现添加
		customerService.add(customer);
		return "addCustomer";
	}
	
	//1 到添加页面
	public String toAddPage() {
		return "toAddPage";
	}


}
