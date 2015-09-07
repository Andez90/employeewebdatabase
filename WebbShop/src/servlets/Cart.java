package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession cart = request.getSession();
    	response.setContentType("Text/HTML");
    	PrintWriter out = response.getWriter();
    	out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Cart</TITLE>");
		out.println("</HEAD>");
		
		out.println("<BODY>");

		out.println("<CENTER>");
		out.println("<H1>Order List</H1>");
		out.println("<TABLE>");
		out.println("<TR>");
		out.println("<TH>Product</TH>");
		out.println("<TH>Amount</TH>");
		out.println("<TH>Price</TH>");
		out.println("</TR>");
		
		cart.removeAttribute("currentProduct");
		Enumeration productName = cart.getAttributeNames();
		int totalCost = 0;
		int numProducts = 0;
		
		while(productName.hasMoreElements()){
			int amount = 0, cost = 0;
			String product = (String)productName.nextElement();
			String stringAmount = (String)cart.getAttribute(product);
			amount = Integer.parseInt(stringAmount);
			Connection conn = null;
	        
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineshop?user=root");
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			PreparedStatement psSelectRecord=null;
			ResultSet rsSelectRecord=null;
			String sqlSelectRecord=null;
		   
			sqlSelectRecord ="SELECT ProductPrice FROM onlineshop.products WHERE Product='" + product + "'";
			int price = 0;
			try {
				psSelectRecord=conn.prepareStatement(sqlSelectRecord);
				rsSelectRecord=psSelectRecord.executeQuery();
				if(rsSelectRecord.next())
					price = rsSelectRecord.getInt("ProductPrice");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			cost = price * amount;
			out.println("<TR>");
			out.println("<TD>" + product + "</TD>");
			out.println("<TD>" + amount + "</TD>");
			out.println("<TD>" + cost + "</TD>");
			out.println("</TR>");
			totalCost += cost;
			numProducts++;
		}
		
		if(numProducts == 0){
			out.println("<TR>");
			out.println("<TD>No products!</TD></TR>");
		}
		else{
			out.println("<TR>");
			out.println("<TD></TD>");
			out.println("<TD>Price</TD>");
			out.println("<TD>" + totalCost + "</TD>");
			out.println("</TR>");
		}
		
		out.println("</TABLE>");
		out.println("<FORM action='' method''>");
		out.println("FirstName<BR>");
		out.println("<INPUT name='FirstName' type='text'><BR>");
		out.println("LastName<BR>");
		out.println("<INPUT name='LastName' type='text'><BR>");
		out.println("Adress<BR>");
		out.println("<INPUT name='Adress' type='text'><BR>");
		out.println("ZipCode<BR>");
		out.println("<INPUT name='ZipCode' type='text'><BR>");
		out.println("Town<BR>");
		out.println("<INPUT name='Town' type='text'><BR>");
		out.println("Email<BR>");
		out.println("<INPUT name='Email' type='email'><BR>");
		out.println("Phone<BR>");
		out.println("<INPUT name='Phone' type='tel'><BR>");
		out.println("<BR>");
		out.println("<INPUT name='submit' type='submit'>");
		out.println("</FORM>");
		out.println("</CENTER>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
	}
}
