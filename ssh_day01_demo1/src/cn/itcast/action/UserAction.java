package cn.itcast.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.entity.User;
import cn.itcast.service.UserService;

public class UserAction extends ActionSupport {
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	//使用属性封装
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		//获取提交用户名和密码
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		//调用service的方法实现操作
		User userExist = userService.loginUser(user);
		//判断
		if(userExist != null) {
			return "loginsuccess";
		} else {
			return "login";
		}
	}
}



