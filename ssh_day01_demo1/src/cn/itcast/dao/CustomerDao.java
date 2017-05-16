package cn.itcast.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Map;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.entity.Customer;

public class CustomerDao extends BaseDao<Customer> {

	/**
	 * <> : typeof
	 * BaseDao<Customer>整体部分： 参数化类型
	 * <>里面Customer： 实际类型参数
	 * 
	 * @return
	 */
	//添加客户
//	public void add(Customer customer) {
//		this.getHibernateTemplate().save(customer);
//	}

	//查询所有客户
//	public List<Customer> findAll() {
//		// 调用hibernate模板find方法实现
//		// find方法有两个参数：第一个参数是hql语句，第二个参数值（可以省略）
//		List<Customer> list = 
//				(List<Customer>) this.getHibernateTemplate().find("from Customer");
//		return list;
//	}

	//根据id查询
//	public Customer findOne(int cid) {
//		// 调用hibernate模板get方法
//		Customer customer = this.getHibernateTemplate().get(Customer.class, cid);
//		return customer;
//	}

	//删除客户
//	public void delete(Customer c) {
//		// 调用hibernate模板delete方法
//		this.getHibernateTemplate().delete(c);
//	}

	//修改客户
//	public void update(Customer customer) {
//		// 调用hibernate模板update方法
//		this.getHibernateTemplate().update(customer);
//	}

	//查询记录数
	public int findCount() {
		// 调用hibernate模板find方法实现
		List<Object> list = 
				(List<Object>) this.getHibernateTemplate().find("select count(*) from Customer");
		// 从list中得到记录数
		if(list != null && list.size()!=0) {
			Object obj = list.get(0);
			//变成int类型
			Long lobj = (Long) obj;
			int count = lobj.intValue();
			return count;
		}
		return 0;
	}

	//分页查询
	public List<Customer> findAllPage(int begin, int pageSize) {
		// 第一种方式
		//1 得到sessionFactory对象
//		SessionFactory sessionFactory = this.getSessionFactory();
//		//2 得到session
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery("from Customer");
//		query.setFirstResult(begin);
//		query.setMaxResults(pageSize);
//		List<Customer> list = query.list();
		
		//第二种 使用离线对象，使用hibernate模板的方法
		//1 使用离线对象，指定对哪个实体类进行操作
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		//2 调用hibernate模板的方法实现分页		
		//第一个参数离线对象，第二个参数开始位置，第三个参数每页记录数
		List<Customer> list = 
				(List<Customer>) this.getHibernateTemplate().findByCriteria(detachedCriteria, begin, pageSize);
		return list;
	}

	//条件查询
	@SuppressWarnings("unchecked")
	public List<Customer> findAllCondition(Customer customer) {
		String hql = "from Customer where 3=3 ";
		List<Object> prarm = new ArrayList<Object>();
		if(customer.getCustName()!= null && !"".equals(customer.getCustName())) {
			//拼接hql
			hql += " and custName=?";
			prarm.add(customer.getCustName());
		}
		return (List<Customer>) this.getHibernateTemplate().find(hql, prarm.toArray());
	}
	
	//第二种方式：离线对象实现多条件组合查询
	@SuppressWarnings("all")
	public List<Customer> findMoreCondition(Customer customer) {
		
		//1 创建离线对象，指定对哪个实体类进行操作
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		//2 判断条件值是否为空，设置值
		if(customer.getCustName()!=null && !"".equals(customer.getCustName())) {
			//调用方法实现
			//调用add方法，表示设置条件值
			//add方法里面使用Restrictions类里面静态的方法实现
			detachedCriteria.add(Restrictions.eq("custName", customer.getCustName()));
		}
		if(customer.getCustLevel()!=null && !"".equals(customer.getCustLevel())) {
			detachedCriteria.add(Restrictions.eq("custLevel", customer.getCustLevel()));
		}
		if(customer.getCustSource()!=null && !"".equals(customer.getCustSource())) {
			detachedCriteria.add(Restrictions.eq("custSource", customer.getCustSource()));
		}
		//3执行离线对象
		//调用hibernate模板里面的方法实现
		List<Customer> list = 
				(List<Customer>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return list;
	}

	//级别统计客户
	public List findCountLevel() {
		//级别统计的普通sql语句
		String sql = "SELECT COUNT(*) AS num,t.custLevel FROM t_customer t GROUP BY t.custLevel";
		//调用普通sql语句
		//1  得到Session对象
//		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Session session = this.getSessionFactory().getCurrentSession();
		
		//2 创建SQLQuery对象
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		
//		sqlQuery.addEntity(Customer.class);
		
		//返回list集合，list集合每部分有很多值，没有对应的实体类进行封装
		//把list中每部分值变成 map集合结构
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		
		//3 调用sqlQuery里面的方法得到结果
		List list = sqlQuery.list();
		
		return list;
	}

	//根据来源统计
	public List findCountSource() {
		String sql = "SELECT COUNT(*) AS num,t.custSource FROM t_customer t GROUP BY t.custSource";
		//1 获取session对象
		Session session = this.getSessionFactory().getCurrentSession();
		//2 创建sqlquery对象
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		
		//3 处理返回结果
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		
		//4 调用方法得到返回list集合
		List list = sqlQuery.list();
		
		return list;
	}
	

	//第一种方式：拼接hql语句实现
//	public List<Customer> findMoreCondition(Customer customer) {
//		//拼接hql语句
//		String hql = "from Customer where 1=1 ";
//		//创建list集合设置参数值
//		List<Object> listparam = new ArrayList<Object>();
//		//判断customer条件值是否为空，如果不为空拼接hql语句
//		if(customer.getCustName()!=null && !"".equals(customer.getCustName())) {
//			hql += " and custName=?";
//			//把值设置到list集合里面
//			listparam.add(customer.getCustName());
//		}
//		if(customer.getCustLevel()!=null && !"".equals(customer.getCustLevel())) {
//			hql += " and custLevel=?";
//			listparam.add(customer.getCustLevel());
//		}
//		if(customer.getCustSource()!=null && !"".equals(customer.getCustSource())) {
//			hql += " and custSource=?";
//			listparam.add(customer.getCustSource());
//		}
//		System.out.println("*************hql: "+hql);
//		System.out.println("*************listparam: "+listparam);
//		//调用hibernate模板的方法实现查询
//		List<Customer> list = 
//				(List<Customer>) this.getHibernateTemplate().find(hql, listparam.toArray());
//		return list;
//	}

}
