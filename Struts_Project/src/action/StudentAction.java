package action;

import java.sql.Connection;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import dao.StudentDao;
import model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DbUtil;
import util.JsonUtil;
import util.ResponseUtil;
import util.StringUtil;

public class StudentAction extends ActionSupport {

	private Student student;
	private String s_stuNo;
	private String s_stuName;
	private String s_sex;
	private String s_bbirthday;
	private String s_ebirthday;
	private String s_gradeId;
	private String page;
	private String rows;
	private String delIds;
	private String stuId;

	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();

	@Override
	public String execute() throws Exception {
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		student = new Student();
		try {
			if (s_stuNo != null) {
				student.setStuNo(s_stuNo);
				student.setStuName(s_stuName);
				student.setSex(s_sex);
				if (StringUtil.isNotEmpty(s_gradeId)) {
					student.setGradeId(Integer.parseInt(s_gradeId));
				}
			}

			con = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil
					.formatRsToJsonArray(studentDao.studentList(con, pageBean, student, s_bbirthday, s_ebirthday));
			int total = studentDao.studentCount(con, student, s_bbirthday, s_ebirthday);
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
			int delNums=studentDao.studentDelete(con, delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "…æ≥˝ ß∞‹");
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
		if(StringUtil.isNotEmpty(stuId)){
			student.setStuId(Integer.parseInt(stuId));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "±£¥Ê ß∞‹");
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getS_stuNo() {
		return s_stuNo;
	}

	public void setS_stuNo(String s_stuNo) {
		this.s_stuNo = s_stuNo;
	}

	public String getS_stuName() {
		return s_stuName;
	}

	public void setS_stuName(String s_stuName) {
		this.s_stuName = s_stuName;
	}

	public String getS_sex() {
		return s_sex;
	}

	public void setS_sex(String s_sex) {
		this.s_sex = s_sex;
	}

	public String getS_bbirthday() {
		return s_bbirthday;
	}

	public void setS_bbirthday(String s_bbirthday) {
		this.s_bbirthday = s_bbirthday;
	}

	public String getS_ebirthday() {
		return s_ebirthday;
	}

	public void setS_ebirthday(String s_ebirthday) {
		this.s_ebirthday = s_ebirthday;
	}

	public String getS_gradeId() {
		return s_gradeId;
	}

	public void setS_gradeId(String s_gradeId) {
		this.s_gradeId = s_gradeId;
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

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

}
