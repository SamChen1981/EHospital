package gxr.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import gxr.bean.CaseTable;
import gxr.bean.Doctor;
import gxr.bean.RegisteTable;
import gxr.bean.User;

public class Data {
	/*
	 * 操作数据库的类
	 */
	Statement stmt;
	Connection conn;

	public void connect() {
		// 1.注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 2.创建数据库的连接
		// useUnicode=true&characterEncoding=GBK：支持中文
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/ehospital?useUnicode=true&characterEncoding=GBK",
							"root", "root");
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String regist(String phone, String password) {
		
		//用户注册

		String checkUser = "select * from user where phone=" + phone;
		String registStr = "insert into user(phone,password,name,sex) values(" + phone
				+ "," + password +","+phone+","+"\"用户未定义\""+ ")";
		try {
			ResultSet res = stmt.executeQuery(checkUser);
			while (res.next()) {
				int id = res.getInt(1);
				return "user_had";
			}
			stmt.execute(registStr);
			res.close();
			createCaseTable();//添加病例
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "regist_success";

	}
	
	public void createCaseTable(){
		String newId="select id from user";
		int userId = 0;
		try{
			ResultSet res = stmt.executeQuery(newId);
			while (res.next()) {
				userId = res.getInt(1);	
			}
			String create="insert into case_table(userId,create_time,change_time) values("+userId+",\""+getTime()+"\",\""+getTime()+"\")";
			stmt.execute(create);
			res.close();
		}
		catch(Exception e){
			
		}
		
	}
	
	public CaseTable getCase(int userId){
		CaseTable caseTable=new CaseTable();
		String getCaseStr="select * from case_table where userId ="+userId;
		ResultSet res;
		try {
			res = stmt.executeQuery(getCaseStr);
			while (res.next()) {
				caseTable.setId(res.getInt("id")+"");
				caseTable.setUserId(res.getInt("userId")+"");
				caseTable.setCreateTime(res.getString("create_time"));
				caseTable.setChangeTime(res.getString("change_time"));
				caseTable.setContent(res.getString("content"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return caseTable;
	}

	public boolean addCaseContent(int caseId,Doctor doctor,String newCaseContent,String caseHistory){
		String addCaseContentText="update case_table set content =? , change_time=? where id=?";
		try {
			java.sql.PreparedStatement pstmt = conn.prepareStatement(addCaseContentText);
			pstmt.setString(1, caseHistory+"/hh主治医师： "+doctor.getName()+"    科室： "+doctor.getDepartment()+"    就诊时间： "+getTime()+"/hh"+newCaseContent);
			pstmt.setString(2, getTime());
			pstmt.setInt(3, caseId);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public User login(String phone, String password) {
		String loginStr = "select * from user where phone=" + phone
				+ " and password=" + password;
		User user=new User();
		try {
			ResultSet res = stmt.executeQuery(loginStr);
			
			while (res.next()) {
				
				
				user.setId(res.getInt("id")+"");
				user.setName(res.getString("name"));
				user.setInfo("success");
				return user;
			}
			user.setInfo("no_phone");
			res.close();
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setInfo("fail");
		return user;
	}

	public int doctorLogin(String phone, String password) {

		/*
		 * 医生登陆
		 */
		String loginStr = "select * from doctor where phone=" + phone
				+ " and password=" + password;
		try {
			ResultSet res = stmt.executeQuery(loginStr);
			while (res.next()) {
				int id = res.getInt("id");
				return id;
			}
			res.close();
			return -2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	public boolean updateInfo(int id, String name, int age, String sex,String phone) {
		String updateStr = "update user set name=?,age=?,sex=? ,phone=? where id=" + id;
		try {
			java.sql.PreparedStatement pstmt = conn.prepareStatement(updateStr);
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setString(3, sex);
			pstmt.setString(4, phone);
			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<Doctor> checkDoctors(String department) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		String doctorsStr = "select * from doctor where department="
				+ department;

		try {
			ResultSet res = stmt.executeQuery(doctorsStr);
			while (res.next()) {
				Doctor doctor = new Doctor();
				doctor.setId(res.getInt(1));
				doctor.setPhone(res.getString(2));
				doctor.setName(res.getString(4));
				doctor.setSex(res.getString(5));
				doctor.setDepartment(res.getString(6));
				doctor.setHeadimgUrl(res.getString(7));
				doctors.add(doctor);
			}
			res.close();
			return doctors;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doctors;
	}

	
	
	public User getUserInfo(String id) {
		User user = new User();
		String selectUser = "select * from user where id=" + id;
		try {
			ResultSet res = stmt.executeQuery(selectUser);
			while (res.next()) {
				user.setId(res.getInt("id") + "");
				user.setPhone(res.getString("phone"));
				user.setName(res.getString("name"));
				user.setSex(res.getString("sex"));
				user.setAge(res.getInt("age") + "");
				user.setInfo("success");
			}
			res.close();
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		user.setInfo("fail");
		return user;
	}

	public List<RegisteTable> scanRegistedUsers(String id){
		List<RegisteTable> registTables = new ArrayList<RegisteTable>();
		String doctorsStr = "select * from register_table where ( doctor_id="
				+ id + " and status=\"access\" )";
		try {
			ResultSet res = stmt.executeQuery(doctorsStr);
			while (res.next()) {
				RegisteTable registeTable = new RegisteTable();
				registeTable.setId(res.getInt("id") + "");
				registeTable.setDoctorId(res.getInt("doctor_id") + "");
				registeTable.setUserId(res.getInt("user_id") + "");
				registeTable.setTime(res.getString("time"));
				registTables.add(registeTable);
			}
			res.close();
			return registTables;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RegisteTable> checkUsers(String id) {
		List<RegisteTable> registTables = new ArrayList<RegisteTable>();
		String doctorsStr = "select * from register_table where ( doctor_id="
				+ id + " and status=\"wait\" )";
		try {
			ResultSet res = stmt.executeQuery(doctorsStr);
			while (res.next()) {
				RegisteTable registeTable = new RegisteTable();
				registeTable.setId(res.getInt("id") + "");
				registeTable.setDoctorId(res.getInt("doctor_id") + "");
				registeTable.setUserId(res.getInt("user_id") + "");
				registeTable.setTime(res.getString("time"));
				registTables.add(registeTable);
			}
			res.close();
			return registTables;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Doctor> checkAllDoctors() {
		List<Doctor> doctors = new ArrayList<Doctor>();
		String doctorsStr = "select * from doctor";
		try {
			ResultSet res = stmt.executeQuery(doctorsStr);
			while (res.next()) {
				Doctor doctor = new Doctor();
				doctor.setId(res.getInt("id"));
				doctor.setPhone(res.getString("phone"));
				doctor.setName(res.getString("name"));
				doctor.setSex(res.getString("sex"));
				doctor.setDepartment(res.getString("department"));
				doctor.setHeadimgUrl(res.getString("head_img"));
				doctor.setWorkTime(res.getString("work_time"));
				doctor.setRegisterNum(res.getInt("register_num") + "");
				doctors.add(doctor);
			}
			res.close();
			return doctors;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doctors;
	}

	public String insertPreRegiste(RegisteTable registeTable) {
		/*
		 * 插入申请挂号数据表
		 */
		String checkRegist = "select * from register_table where (user_id ="
				+ registeTable.getUserId() + " and status= \"success\")";
		String insertPreRegistStr = "insert into register_table(doctor_id,user_id,status,time) values("
				+ Integer.parseInt(registeTable.getDoctorId())
				+ ","
				+ Integer.parseInt(registeTable.getUserId())
				+ ",\"wait\",\""
				+ getTime() + "\")";
		// String
		// getDoctor="select register_num from doctor where id="+registeTable.getDoctorId();

		try {
			ResultSet res = stmt.executeQuery(checkRegist);
			while (res.next()) {
				int id = res.getInt("id");
				res.close();
				return "had_register";
			}

			stmt.execute(insertPreRegistStr);
			res.close();
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "fail";
	}

	public Doctor getDoctorInfo(int doctorId) {
		Doctor doctor = new Doctor();
		String selectInfo = "select * from doctor where id=" + doctorId;
		try {
			ResultSet res = stmt.executeQuery(selectInfo);
			while (res.next()) {
				doctor.setId(res.getInt("id"));
				doctor.setPhone(res.getString("phone"));
				doctor.setName(res.getString("name"));
				doctor.setSex(res.getString("sex"));
				doctor.setDepartment(res.getString("department"));
				doctor.setHeadimgUrl(res.getString("head_img"));
				doctor.setWorkTime(res.getString("work_time"));
				doctor.setRegisterNum(res.getString("register_num"));
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doctor;
	}
	
	public List<RegisteTable> getRegisteTables(String userId, String info) {
		// TODO Auto-generated method stub
				String getRegisteTableStr="select * from register_table where user_id='"+userId+"' and status ='"+info+"'";
		List<RegisteTable> registeTables=new ArrayList<RegisteTable>();
	
		try {
			ResultSet res = stmt.executeQuery(getRegisteTableStr);
			while (res.next()) {
				RegisteTable table=new RegisteTable();
				String doctorId=res.getString("doctor_id");
				table.setId(res.getInt("id")+"");
				table.setTime(res.getString("time"));
				table.setStatus(res.getString("status"));
				table.setDoctorId(doctorId);
			
				registeTables.add(table);
			}
			
			res.close();
			return registeTables;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean managerRegistes(String[] ids,String status){
		
		
		String updateStr = "update register_table set status=?"+"where id=?";
		try {
			java.sql.PreparedStatement pstmt = conn.prepareStatement(updateStr);
			for(int i=0;i<ids.length;i++){
				pstmt.setString(1,status);
				pstmt.setInt(2,Integer.parseInt(ids[i]));
				pstmt.executeUpdate();
			}
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	
	}

	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	public void closeSql() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean updateRegiste(int userId) {
		// TODO Auto-generated method stub
		String updateStr = "update register_table set status=?"+"where user_id=?";
		try {
			java.sql.PreparedStatement pstmt = conn.prepareStatement(updateStr);
			
				pstmt.setString(1,"finish");
				pstmt.setInt(2,userId);
				pstmt.executeUpdate();
			
			pstmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	

}
