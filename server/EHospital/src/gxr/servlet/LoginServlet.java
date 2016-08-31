package gxr.servlet;

import gxr.bean.User;
import gxr.tools.Data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	/**
	 * µÇÂ¼ servlet. <br>
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
		StringBuffer buffer=new StringBuffer("{\"code\":");
		PrintWriter out = response.getWriter();
		Data data=new Data();
		data.connect();
		User user=data.login(request.getParameter("phone"),request.getParameter("password"));
		if(user.getInfo().equals("success")){
			buffer.append("\"success\",\"id\":\""+user.getId()+"\",\"name\":\""+user.getName()+"\"}");
			
		}
		if(user.getInfo().equals("no_phone")){
			buffer.append("\"no_phone\"}");
		}
		if(user.getInfo().equals("fail")){
			buffer.append("\"fail\"}");
		}
		
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
	}

}
