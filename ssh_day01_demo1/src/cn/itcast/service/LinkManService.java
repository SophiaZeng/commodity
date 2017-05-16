package cn.itcast.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.LinkManDao;
import cn.itcast.entity.LinkMan;

@Transactional
public class LinkManService {

	private LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	public void add(LinkMan linkMan) {
		linkManDao.add(linkMan);
	}

	public List<LinkMan> findAll() {
		return linkManDao.findAll();
	}

	public LinkMan findOne(int linkmanid) {
		return linkManDao.findOne(linkmanid);
	}
	
}
