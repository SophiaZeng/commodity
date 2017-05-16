package cn.itcast.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.entity.User;

public class UserDao extends HibernateDaoSupport {
	
//	private HibernateTemplate hibernateTemplate;
//
//	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
//		this.hibernateTemplate = hibernateTemplate;
//	}
	
	//登录的方法
	@SuppressWarnings("all")
	public User loginUser(User user) {
		// 1 得到hibernate模板对象
		// this.getHibernateTemplate();
		//2 根据用户和密码查询数据库，返回对象
		// hibernate模板里面的方法 find
		// 第一个参数：hql语句
		// 第二个参数：参数值
		List<User> list = (List<User>) this.getHibernateTemplate().
				find("from User where username=? and password=?", user.getUsername(),user.getPassword());

		//从list获取一个user对象
		// 如果查询没有数据，list里面没有值，判断
		if(list != null && list.size()!=0 ) {
			User u = list.get(0);
			return u;
		}	
		return null;
	}

	public List<User> findAll() {
		return (List<User>) this.getHibernateTemplate().find("from User");
	}


	
}
