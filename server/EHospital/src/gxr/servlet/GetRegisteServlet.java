package gxr.servlet;

import gxr.bean.Doctor;
import gxr.bean.RegisteTable;
import gxr.bean.User;
import gxr.tools.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRegisteServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
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

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer buffer=new StringBuffer("{\"tables\":[");
		PrintWriter out = response.getWriter();
		Data data=new Data();
		data.connect();
		String userId=request.getParameter("userId");
		String info=request.getParameter("info");
		List<RegisteTable> tables=data.getRegisteTables(userId,info);
		for(int i=0;i<tables.size()-1;i++){
			RegisteTable table=tables.get(i);
			Doctor doctor=data.getDoctorInfo(Integer.parseInt(table.getDoctorId()));
			
			buffer.append("{\"id\":"+table.getId()+",\"time\":\""+table.getTime()+"\",\"status\":\""+table.getStatus()+"\",\"doctorId\":"+doctor.getId()+",\"doctorName\":\""+doctor.getName()+"\",\"department\":\""+doctor.getDepartment()+"\",\"workTime\":\""+doctor.getWorkTime()+"\",\"phone\":\""+doctor.getPhone()+"\"},");
		}
		int length=tables.size();
		if(length>0){
		RegisteTable table=tables.get(tables.size()-1);
		
		Doctor doctor=data.getDoctorInfo(Integer.parseInt(table.getDoctorId()));
		
		buffer.append("{\"id\":"+table.getId()+",\"time\":\""+table.getTime()+"\",\"status\":\""+table.getStatus()+"\",\"doctorId\":"+doctor.getId()+",\"doctorName\":\""+doctor.getName()+"\",\"department\":\""+doctor.getDepartment()+"\",\"workTime\":\""+doctor.getWorkTime()+"\",\"phone\":\""+doctor.getPhone()+"\"}");
		
		}
		buffer.append("]}");
		out.print(buffer);
		out.flush();
		out.close();
		data.closeSql();
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
