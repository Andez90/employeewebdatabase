package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Add
 */
@WebServlet("/Add")
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentProduct = request.getParameter("Product");
		HttpSession cart = request.getSession();
		cart.putValue("currentProduct", currentProduct);
		if(currentProduct.equals("Cart")){
			response.sendRedirect("Cart");
		}
		else{
			sendPage(response, currentProduct);
		}
	}
	
	private void sendPage(HttpServletResponse reply, String product) throws IOException{
		reply.setContentType("text/HTML");
		PrintWriter out = reply.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>" + product + "</TITLE>");
		out.println("</HEAD>");
		
		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<FORM METHOD=POST ACTION='Amount'>");
		out.println("<TABLE>");
		out.println("<TR>");
		out.println("<TD>Amount required (st)");
		out.println("<INPUT TYPE='number' NAME='Amount' min='1' max='50' VALUE=1 SIZE=5></TD>");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println("<TABLE>");
		out.println("<TR>");
		out.println("<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Add' CHECKED");
		out.println("<FONT>Add</FONT></TD>");
		out.println("</TR>");
		out.println("<TR>");
		out.println("<TD><INPUT TYPE='Radio' NAME='Option' VALUE='Cart'");
		out.println("<FONT>Checkout</FONT></TD>");
		out.println("</TR>");
		out.println("</TABLE>");
		out.println("<INPUT TYPE='Submit' VALUE='Submit'>");
		out.println("</FORM>");
		out.println("</CENTER>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
	}
}
