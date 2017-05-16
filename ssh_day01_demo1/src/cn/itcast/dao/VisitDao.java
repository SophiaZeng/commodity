package cn.itcast.dao;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.entity.Visit;

public class VisitDao extends HibernateDaoSupport {

	public void add(Visit visit) {
		this.getHibernateTemplate().save(visit);
	}

}
