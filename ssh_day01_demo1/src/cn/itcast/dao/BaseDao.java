package cn.itcast.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * T大写字母，表示任意类型
 * 
 * @author asus
 *
 * @param <T>
 */
public class BaseDao<T> extends HibernateDaoSupport {

	private Class clazzP ;
	
	//构造方法
	public BaseDao() {
		//目的： 得到实际类型参数
		//1 得到customerdao对象的class
		//得到当前运行对象class
		Class clazz = this.getClass();
		
//		System.out.println("1 getClass  clazz:"+clazz);
		
		//2  得到customerdao的父类的参数化类型
		// Type getGenericSuperclass() 
		Type type = clazz.getGenericSuperclass();
		//一般使用Type子接口 ParameterizedType
		ParameterizedType ptype = (ParameterizedType) type;
		
		//3 得到实际类型参数
		// Type[] getActualTypeArguments() 
		//返回数组 Map<key,value>
		Type[] types = ptype.getActualTypeArguments();
		//得到数组里面第一个值
//		Type typeParameter = types[0];
		//得到实际类型参数的class
		Class clazzParameter = (Class) types[0];
//		System.out.println("2 clazzParameter: "+clazzParameter);
		this.clazzP = clazzParameter;
		
	}

	//根据id查询
	@SuppressWarnings("unchecked")
	public T findOne(int id) {
		//get方法中第一个参数：实体类class
		//没有T.class
		//如果做客户的操作，客户class，如果用户，用户class
		return (T) this.getHibernateTemplate().get(clazzP, id);
	}
	
	//查询所有
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find("from "+clazzP.getSimpleName());
	}
	
	//添加
	public void add(T t) {
		this.getHibernateTemplate().save(t);
	}
	
	//修改
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}
	
	//删除
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}
}



