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

	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();
	HttpServletRequest request;

	@Override
	public String execute() throws Exception {
		// ��ȡSession
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			error = "�û���������Ϊ�գ�";
			return ERROR;
		}

		Connection con = null;
		try {
			con = dbUtil.getCon();
			User currentUser = userDao.login(con, user);
			if (currentUser == null) {
				error = "�û������������";
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
}