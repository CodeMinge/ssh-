package action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import dao.*;
import model.User;
import util.*;

public class LoginAction extends ActionSupport implements ServletRequestAware {

	private User user;
	private String error;
	private String imageCode;

	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();
	HttpServletRequest request;

	@Override
	public String execute() throws Exception {
		// 获取Session
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			error = "用户名或密码为空！";
			return ERROR;
		}
		
		if (StringUtil.isEmpty(imageCode)) {
			error = "验证码为空！";
			return ERROR;
		}
		
		if(!imageCode.equals(session.getAttribute("sRand"))) {
			error = "验证码错误！";
			return ERROR;
		}

		Connection con = null;
		try {
			con = dbUtil.getCon();
			User currentUser = userDao.login(con, user);
			if (currentUser == null) {
				error = "用户名或密码错误！";
				return ERROR;
			} else {
				session.setAttribute("currentUser", currentUser);
				return SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}	
}