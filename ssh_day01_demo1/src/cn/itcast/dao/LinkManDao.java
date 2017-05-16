package cn.itcast.dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.entity.LinkMan;

public class LinkManDao extends HibernateDaoSupport {

	//添加联系人
	public void add(LinkMan linkMan) {
		this.getHibernateTemplate().save(linkMan);
	}

	//查询所有联系人
	public List<LinkMan> findAll() {
		return (List<LinkMan>) this.getHibernateTemplate().find("from LinkMan");
	}

	public LinkMan findOne(int linkmanid) {
		return this.getHibernateTemplate().get(LinkMan.class, linkmanid);
	}

} 
