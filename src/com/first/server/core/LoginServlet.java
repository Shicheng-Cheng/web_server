package com.first.server.core;

import com.server.Response;

public class LoginServlet implements Servlet {

	@Override
	public void service(Request request,Response response) {
		// TODO Auto-generated method stub
		response.print("<html>");
		response.print("<title>");
		response.print("服务器响应成功");
		response.print("</title>");
		response.print("<body>");
		response.print("DIY SERVER");
//		response.print("DIY SERVER"+" "+request.getParater("uname"));
		response.print("</body>");
		response.print("</html>");
		
	}


}
