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
	private String delIds;
	private String id;
	
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
	
	public String delete()throws Exception{
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			String str[]=delIds.split(",");
			for(int i=0;i<str.length;i++){
				boolean f=studentDao.getStudentByGradeId(con, str[i]);
				if(f){
					result.put("errorIndex", i);
					result.put("errorMsg", "班级下面有学生，不能删除！");
					ResponseUtil.write(ServletActionContext.getResponse(), result);
					return null;
				}
			}
			int delNums=gradeDao.gradeDelete(con, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String save()throws Exception{
		if(StringUtil.isNotEmpty(id)){
			grade.setId(Integer.parseInt(id));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String gradeComboList()throws Exception{
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null,null)));
			ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}	
	
}