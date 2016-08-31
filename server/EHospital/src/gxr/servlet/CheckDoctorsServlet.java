package gxr.servlet;

import gxr.tools.Data;

import gxr.bean.Doctor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckDoctorsServlet extends HttpServlet {


	/**
	 * 获取医生列表servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String department=request.getParameter("department");
		
		List<Doctor>doctors;
		PrintWriter out = response.getWriter();
		Data data=new Data();
		
		data.connect();
		if(department==null){
		doctors=data.checkAllDoctors();
		}
		else{
		doctors=data.checkDoctors(department);
		}
		data.closeSql();
		StringBuffer buffer=new StringBuffer("{\"doctors\":[");
		for(int i=0;i<doctors.size();i++){
			buffer.append("{\"id\":\""+doctors.get(i).getId()+"\",");
			buffer.append("\"phone\":\""+doctors.get(i).getPhone()+"\",");
			buffer.append("\"name\":\""+doctors.get(i).getName()+"\",");
			buffer.append("\"sex\":\""+doctors.get(i).getSex()+"\",");
			buffer.append("\"department\":\""+doctors.get(i).getDepartment()+"\",");
			buffer.append("\"headImg\":\""+doctors.get(i).getHeadimgUrl()+"\",");
			buffer.append("\"workTime\":\""+doctors.get(i).getWorkTime()+"\",");
			buffer.append("\"registerNum\":\""+doctors.get(i).getRegisterNum()+"\"");
			if(i==doctors.size()-1){
				buffer.append("}");	
			}
			else{
			buffer.append("},");
		}
		}
		
		buffer.append("]}");
		out.print(buffer);
		out.flush();
		out.close();
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
