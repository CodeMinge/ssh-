package action;

import model.*;
import net.sf.json.*;
import util.*;
import dao.*;

import java.sql.Connection;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GradeAction extends ActionSupport {
	private String page;
	private String rows;
	private PageBean pageBean;
	private String s_gradeName = "";
	private Grade grade;
	
	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	StudentDao studentDao=new StudentDao();

	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try {
			if(grade==null){
				grade=new Grade();
			}
			grade.setGradeName(s_gradeName);
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, pageBean,grade));
			int total=gradeDao.gradeCount(con,grade);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
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
		return null;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getS_gradeName() {
		return s_gradeName;
	}

	public void setS_gradeName(String s_gradeName) {
		this.s_gradeName = s_gradeName;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}	
	
}