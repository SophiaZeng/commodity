package cn.itcast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.CustomerDao;
import cn.itcast.entity.Constant;
import cn.itcast.entity.Customer;
import cn.itcast.entity.PageBean;

@Transactional
public class CustomerService {

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void add(Customer customer) {
		customerDao.add(customer);
	}

	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	public Customer findOne(int cid) {
		return customerDao.findOne(cid);
	}

	public void delete(Customer c) {
		customerDao.delete(c);
	}

	public void update(Customer customer) {
		customerDao.update(customer);
	}

	//封装数据到pagebean里面
	public PageBean findPage(Integer currentPage) {
		PageBean pageBean = new PageBean();
		//当前页
		pageBean.setCurrentPage(currentPage);
		
		//总记录数
		//查询数据库得到总记录数
		int totalCount = customerDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		int pageSize = Constant.PAGESIZE;
		//总页数
//		-- 总记录数除以每页显示记录数，
//		如果能够整除，结果是相除结果
//		如果不能整除，结果是相除的结果+1
		int totalPage = 0;
		if(totalCount%pageSize==0) {//整除
			totalPage = totalCount/pageSize;
		} else {
			totalPage = totalCount/pageSize+1;
		}
		pageBean.setTotalPage(totalPage);
		
		//每页查询list集合
		//计算开始位置
		int begin = (currentPage-1)*pageSize;
		List<Customer> list = customerDao.findAllPage(begin,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	public List<Customer> findAllCondition(Customer customer) {
		return customerDao.findAllCondition(customer);
	}

	public List<Customer> findMoreCondition(Customer customer) {
		return customerDao.findMoreCondition(customer);
	}

	public List findCountLevel() {
		return customerDao.findCountLevel();
	}

	public List findCountSource() {
		return customerDao.findCountSource();
	}

	public List<Customer> findPageCustomer(int begin, int rows) {
		return customerDao.findAllPage(begin, rows);
	}

	public int findCount() {
		return customerDao.findCount();
	}
	
}




